package com.akali.provider.validate.service;

import com.akali.common.dto.CheckPostRedoDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName PostRedoValidateService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/3 0003
 * @Version V1.0
 **/
public interface PostRedoValidateService {
    /**
     * 检查表单重复提交
     * @param checkPostRedoDTO
     * @return
     */
    DubboResponse<Void> checkRedo(CheckPostRedoDTO checkPostRedoDTO);
}
