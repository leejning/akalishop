package com.akali.cloud.image_code.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ImageCodeDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageCodeDTO {
    /**
     * 图片验证码id
     */
    private String photoValidCodeId;
    /**
     * 图片验证码
     */
    private String photoValidCode;
}
