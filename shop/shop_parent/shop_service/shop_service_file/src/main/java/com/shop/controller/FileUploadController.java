package com.shop.controller;

import com.shop.file.FastDFSFile;
import com.shop.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
public class FileUploadController {

    /**
     * 文件上传
     */
    @PostMapping
    public Result upload(@RequestParam(value = "file")MultipartFile file) throws Exception {
        FastDFSFile fastDFSFile=new FastDFSFile(file.getOriginalFilename(),file.getBytes(), StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] upload = FastDFSUtil.upload(fastDFSFile);
        //文件直接访问地址由Nginx fastdfs module 拦截代理，所以端口不是18002
        String url=FastDFSUtil.getTrackerInfo()+"/"+upload[0]+"/"+upload[1];
        return new Result(true, StatusCode.OK,"上传成功",url);
    }


}
