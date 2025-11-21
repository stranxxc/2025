package com.example.demo.controller.base;

import com.example.demo.utils.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Value("./upload/")
    private String uploadPath;

    @PostMapping("/img")
    public R<Object> uploadImg(MultipartFile file) throws IOException {

        enum ImgType {
            jpg, jpeg, png, gif;

            public static Boolean contains(String extension) {
                for (ImgType type : ImgType.values()) {
                    if (extension.contains(type.name())) {
                        return true;
                    }
                }
                return false;
            }
        }
        //0:判断file
        if (file.isEmpty()) {
            return R.failed("请选择图片");
        }
        //1: 获取图片信息
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;

        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (!ImgType.contains(suffixName)) {
            return R.failed("文件没有有效的扩展名（如 .jpg、.png）");
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String newFileName = uuid + suffixName;
        System.out.println(newFileName);
        //2: 配置上传的图片要保存的位置

        Path path = Paths.get(uploadPath);
        if (!Files.isDirectory(path)) {
            Files.createDirectory(path);
        }
        Path savePath = path.resolve(newFileName);
        System.out.println(savePath);
        file.transferTo(savePath);
        return R.success("上传成功", newFileName);
    }
}
