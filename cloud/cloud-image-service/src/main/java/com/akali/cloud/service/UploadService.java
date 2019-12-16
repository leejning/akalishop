package com.akali.cloud.service
        ;

import com.akali.common.code.UploadCode;
import com.akali.common.utils.ExceptionCast;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName UploadService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/29 0029
 * @Version V1.0.0
 **/
@Service
@Slf4j
public class UploadService {
    /**
     * 支持的文件类型
     */
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg","image/gif");


    @Autowired
    FastFileStorageClient storageClient;

    public String upload(MultipartFile file) throws IOException {
        // 1、图片信息校验
        // 1)校验文件类型
        String type = file.getContentType();
        if (!suffixes.contains(type)) {
            log.info("上传失败，文件类型不匹配：{}", type);
            ExceptionCast.cast(UploadCode.IMAGE_TYPE_ILLEGAL);
        }
        // 2)校验图片内容
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            log.info("上传失败，文件内容不符合要求");
            ExceptionCast.cast(UploadCode.IMAGE_CONTENT_ILLEGAL);
        }

        // 2、将图片上传到FastDFS
        // 2.1、获取文件后缀名
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        // 2.2、上传
        StorePath storePath = this.storageClient.uploadFile(
                file.getInputStream(), file.getSize(), extension, null);
        // 2.3、返回完整路径

        return "http://dgut.online/" + storePath.getFullPath();
    }
}