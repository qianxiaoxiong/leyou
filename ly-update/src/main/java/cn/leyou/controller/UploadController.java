package cn.leyou.controller;

import cn.leyou.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     */
    @PostMapping(value = "image")
    public ResponseEntity<String>  uploadImage( MultipartFile file){
        return  ResponseEntity.ok(this.uploadService.uploadImage(file));
    }
}
