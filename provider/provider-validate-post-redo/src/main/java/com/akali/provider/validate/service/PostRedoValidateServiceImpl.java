package com.akali.provider.validate.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.CheckPostRedoDTO;
import com.akali.common.model.response.DubboResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName PostRedoValidateServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/3 0003
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class PostRedoValidateServiceImpl implements PostRedoValidateService{

    @Autowired
    private StringRedisTemplate redisTemplate;

    private final static String POST_FORM_KEY = "post_form_key:";

    /**
     * 检查表单重复提交
     *
     * @param checkPostRedoDTO
     * @return
     */
    @Override
    public DubboResponse<Void> checkRedo(CheckPostRedoDTO checkPostRedoDTO) {
        String formCode = redisTemplate.opsForValue().get(POST_FORM_KEY + checkPostRedoDTO.getFormKey());
        if (StringUtils.isNotBlank(formCode)) {
            return DubboResponse.FAIL(CommonCode.POST_REDO_ERROR);
        }
        redisTemplate.opsForValue().set(POST_FORM_KEY + checkPostRedoDTO.getFormKey(),checkPostRedoDTO.getFormKey(),30, TimeUnit.SECONDS);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }
}
