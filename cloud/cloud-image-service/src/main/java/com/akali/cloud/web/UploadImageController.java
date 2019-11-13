package com.akali.cloud.web;


import com.akali.cloud.api.UploadImageApi;
import com.akali.cloud.service.UploadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;


/** 
 * @ClassName UploadController
 * @Description: TODO 
 * @Author Administrator
 * @Date 2019/9/29 0029
 * @Version V1.0.0
**/
@RestController
@RequestMapping("upload")
public class UploadImageController implements UploadImageApi {

    @Autowired
    private UploadService uploadService;

    /**
     * 上传图片功能
     * @param
     * @return
     */
    @PostMapping("image")
    @Override
    public ResponseEntity<String> uploadImage(HttpServletRequest req) {
    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		MultipartFile file = request.getFile("file");
        String url = uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            // url为空，证明上传失败
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        // 返回200，并且携带url路径
        return ResponseEntity.ok(url);
    }


}
