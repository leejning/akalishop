package com.akali.cloud.image_code.web;

import com.akali.cloud.image_code.api.ImageCodeControllerApi;
import com.akali.cloud.image_code.dto.ImageCodeDTO;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName ImageCodeController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/

@Controller
@RequestMapping("image/code")
@Slf4j
public class ImageCodeController implements ImageCodeControllerApi {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String IMAGE_CODE = "image.code:";


    @Override
    @GetMapping("{key}")
    public void defaultKaptcha(@PathVariable String key, HttpServletResponse response) throws IOException {
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //生产验证码字符串并保存到redis中
            String codeTest = defaultKaptcha.createText();

            saveCodeToRedis(key,codeTest);

            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = defaultKaptcha.createImage(codeTest);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    private void saveCodeToRedis(String key,String codeTest) {
        redisTemplate.opsForValue().set(IMAGE_CODE+key,codeTest,180, TimeUnit.SECONDS);
    }

    @Override
    @PostMapping
    @ResponseBody
    public boolean imgvrifyControllerDefaultKaptcha(@RequestBody ImageCodeDTO imageCodeDTO) {
        String code = redisTemplate.opsForValue().get(IMAGE_CODE + imageCodeDTO.getPhotoValidCodeId());
        if (StringUtils.isNotBlank(code)&&imageCodeDTO.getPhotoValidCode().equals(code)) {
            redisTemplate.delete(IMAGE_CODE+imageCodeDTO.getPhotoValidCodeId());
            return true;
        }
        return false;
    }
}
