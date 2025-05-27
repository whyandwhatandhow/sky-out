package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api(tags = "通用操作")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> fileUpload(MultipartFile file){
        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
            //新文件名称
            String name= UUID.randomUUID().toString()+substring;

            String uploadPath = aliOssUtil.upload(file.getBytes(), name);
            return Result.success(uploadPath);
        } catch (IOException e) {
            log.error("文件上传失败 {}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
