package com.akali.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CheckPostRedoDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/3 0003
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class CheckPostRedoDTO {
    /**
     * 表单类型
     */
    String formType;
    /**
     * 表单key
     */
    String formKey;

    public CheckPostRedoDTO(String formKey) {
        this.formKey = formKey;
    }
}
