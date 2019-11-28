package com.akali.business.user.web;

import com.akali.business.user.api.MemberRegistryApi;
import com.akali.business.user.feign_client.ImageCodeFeign;
import com.akali.business.user.feign_client.MqMessageSendFeign;
import com.akali.cloud.image_code.dto.ImageCodeDTO;
import com.akali.common.code.RegistryCode;
import com.akali.common.dto.EmailContextDTO;
import com.akali.common.dto.member.MemberRegDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.user.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MemberRegistryController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@RestController
@RequestMapping("member")
@Slf4j
public class MemberRegistryController implements MemberRegistryApi {

    @Reference(version = "1.0.0")
    private MemberService memberService;

    @Autowired
    private ImageCodeFeign imageCodeFeign;

    @Autowired
    private MqMessageSendFeign mqMessageSendFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String EMAIL_CODE_KEY = "email.registry.code:";


    /**
     * 会员注册
     * @param memberRegDTO
     * @return
     */
    @PostMapping("registry")
    @Override
    public ResponseResult<Void> registry(@RequestBody MemberRegDTO memberRegDTO) {
        //验证图片验证码
        ImageCodeDTO imageCodeDTO = new ImageCodeDTO(memberRegDTO.getPhotoValidCodeId(), memberRegDTO.getPhotoValidCode());
        if (imageCodeFeign.imgvrifyControllerDefaultKaptcha(imageCodeDTO)) {
            //图片验证码不正确
            log.info(">>>>>>>>会员注册：图片验证码错误");
            ExceptionCast.cast(RegistryCode.IMAGE_VERIFY_CODE_ERROR);
        }
        memberRegDTO.setPhotoValidCode(null);
        memberRegDTO.setPhotoValidCodeId(null);

        //验证两次密码是否一致
        if (!memberRegDTO.getPassword().equals(memberRegDTO.getPasswordComfirm())) {
            log.info(">>>>>>>>会员注册：两次密码不一致");
            ExceptionCast.cast(RegistryCode.TWO_PASSWORK_NOT_CONSIST);
        }
        memberRegDTO.setPasswordComfirm(null);

        //验证email验证码
        String code = redisTemplate.opsForValue().get(EMAIL_CODE_KEY + memberRegDTO.getEmail());
        if(StringUtils.isBlank(code)||!code.equals(memberRegDTO.getEmailValidCode())){
            //验证码不正确
            log.info(">>>>>>>>会员注册：邮箱验证码错误{}",code);
            ExceptionCast.cast(RegistryCode.EMAIL_VERIFY_CODE_ERROR);
        }
        memberRegDTO.setEmailValidCode(null);

        //注册
        log.info(">>>>>>>>会员注册：调用Dubbo注册服务");
        DubboResponse<Void> response = memberService.registry(memberRegDTO);
        if (!response.isSuccess()) {
            //注册失败
            log.info(">>>>>>>>会员注册：注册失败——",response.getResultCode().message());
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 校验邮箱是否已经注册
     * @param email
     * @return
     */
    @PostMapping("email/check")
    @Override
    public ResponseResult<Void> checkEmail(String email) {
        DubboResponse<Void> response = memberService.checkEmail(email);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 发送邮箱验证码
     * @param email
     * @return
     */
    @GetMapping("email/code")
    @Override
    public ResponseResult<Void> sendEmailValidCode(String email) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        //缓存到redis
        redisTemplate.opsForValue().set(EMAIL_CODE_KEY+email,code,300, TimeUnit.SECONDS);
        //发送MQ
        ResponseResult<Void> res = mqMessageSendFeign.sendMemberRegistryEmailCode(new EmailContextDTO(email, code));
        if (!res.isSuccess()) {

        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 验证图片验证码
     * @param imageCodeDTO
     * @return
     */
    @PostMapping("image/code/verify")
    @Override
    public ResponseResult<Boolean> verifyImageCode(ImageCodeDTO imageCodeDTO) {
        if (imageCodeFeign.imgvrifyControllerDefaultKaptcha(imageCodeDTO)) {
            return ResponseResult.SUCCESS(Boolean.FALSE);
        }
        return ResponseResult.SUCCESS(Boolean.TRUE);
    }
}
