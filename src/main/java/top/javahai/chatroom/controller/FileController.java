package top.javahai.chatroom.controller;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.javahai.chatroom.utils.FastDFSUtil;
import top.javahai.chatroom.utils.AliyunOssUtil;

import java.io.IOException;

/**
 */
@RestController
public class FileController {

  @Value("${fastdfs.nginx.host}")
  String nginxHost;

  @Autowired
  AliyunOssUtil aliyunOssUtil;

  @PostMapping("/file")
  public String uploadFlie(MultipartFile file) throws IOException, MyException {
    String fileId= FastDFSUtil.upload(file);
    return nginxHost+fileId;
  }

  @PostMapping("/ossFileUpload")
  public String ossFileUpload(@RequestParam("file")MultipartFile file,@RequestParam("module") String module) throws IOException, MyException {
    return aliyunOssUtil.upload(file.getInputStream(),module,file.getOriginalFilename());
  }

}
