package com.akali.provider.user.member.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.MemberCode;
import com.akali.common.dto.member.MemberLoginResponseDTO;
import com.akali.common.dto.member.MemberProfileDTO;
import com.akali.common.dto.member.MemberRegDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.utils.IdUtil;
import com.akali.provider.user.member.bean.UmsMember;
import com.akali.provider.user.member.dao.MemberDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

/**
 * @ClassName MemberServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Service(version = "1.0.0")
@Slf4j
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 会员注册
     * @param memberRegDTO
     * @return
     */
    @Override
    public DubboResponse<Void> registry(MemberRegDTO memberRegDTO) {
        UmsMember umsMember = new UmsMember();
        BeanUtils.copyProperties(memberRegDTO,umsMember);
        String password = bCryptPasswordEncoder.encode(memberRegDTO.getPassword());
        umsMember.setPassword(password);
        umsMember.setMemberAccount(Long.toString(IdUtil.nextId()));
        memberDao.save(umsMember);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return
     */
    @Override
    public DubboResponse<Void> checkEmail(String email) {
        Integer cnt = memberDao.checkEmailExist(email);
        if(cnt==null||cnt!=1){
            return DubboResponse.FAIL(MemberCode.MEMBER_ACCOUNT_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据账号检查用户是否存在
     *
     * @param account
     * @return
     */
    @Override
    public DubboResponse<Void> checkExistByMemberAccount(String account) {
        Integer cnt = memberDao.checkExistByMemberAccount(account);
        if(cnt==null||cnt!=1){
            return DubboResponse.FAIL(MemberCode.MEMBER_ACCOUNT_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据账号查询会员登录信息
     * @param account+
     * @return
     */
    @Override
    public DubboResponse<MemberLoginResponseDTO> getMemberByAccount(String account) {
        log.info(">>>>>会员账号：{}，请求登录>>>>",account);

        Optional<UmsMember> opt = memberDao.findMemberForLogin(account);

        if(!opt.isPresent()){
            log.info(">>>>>会员账号：{}，不存在。返回错误结果>>>>",account);
            DubboResponse.FAIL(MemberCode.MEMBER_ACCOUNT_NOT_EXIST);
        }
        log.info(">>>>>会员账号：{}，获取到会员的账号密码信息，返回结果。>>>>",account);
        MemberLoginResponseDTO data = new MemberLoginResponseDTO();
        BeanUtils.copyProperties(opt.get(),data);
        return DubboResponse.SUCCESS(data);
    }

    /**
     * 获取会员详细信息
     *
     * @param memberId
     * @return
     */
    @Override
    public DubboResponse<MemberProfileDTO> getMemberProfile(Long memberId) {
        Optional<UmsMember> opt = memberDao.findById(memberId);
        if(!opt.isPresent()){
            DubboResponse.FAIL(MemberCode.MEMBER_ACCOUNT_NOT_EXIST);
        }
        MemberProfileDTO data = new MemberProfileDTO();
        BeanUtils.copyProperties(opt.get(),data);
        return DubboResponse.SUCCESS(data);
    }
}
