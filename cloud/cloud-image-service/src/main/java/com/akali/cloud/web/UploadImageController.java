package com.akali.cloud.web;


import com.akali.cloud.api.UploadImageApi;
import com.akali.cloud.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/** 
 * @ClassName UploadController
 * @Description: TODO 
 * @Author Administrator
 * @Date 2019/9/29 0029
 * @Version V1.0.0
**/
@RestController
@RequestMapping("upload")
@CrossOrigin(origins = "*",allowCredentials="true",allowedHeaders = "",methods = {RequestMethod.POST})
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
    public ResponseEntity<String> uploadImage(HttpServletRequest req) throws IOException {
    	MultipartHttpServletRequest request = (MultipartHttpServletRequest)req;
		MultipartFile file = request.getFile("file");
        return ResponseEntity.ok(uploadService.upload(file));
    }


}
