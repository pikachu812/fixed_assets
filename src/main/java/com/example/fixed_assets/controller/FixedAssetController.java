package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.FixedAssetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 资产管理控制器 <br><br>
 * @ CrossOrigin     注解表示该类的所有方法都支持跨域请求<br><br>
 * @ RestController  注解表示该类的所有方法返回的数据都是直接写给浏览器（如果是对象转为json数据）
 *                   也就是说，这个类的所有方法都是直接返回数据，而不是返回视图<br><br>
 * @ RequestMapping 注解表示该类的所有方法都是以 /assets 开头的 <br><br>
 * @ Controller     注解表示该类是一个控制器，用于接收请求和返回视图，这个注解是SpringMVC的注解
 */
@CrossOrigin
@RestController
@RequestMapping("/fixedAssets")
public class FixedAssetController {

    private static final Logger logger = LoggerFactory.getLogger(FixedAssetController.class);


    @Autowired
    private FixedAssetService fixedAssetService;

    @PostMapping("/add")
    public ResponseEntity<?> addFixedAsset(@RequestBody FixedAsset fixedAsset) {
        fixedAssetService.addFixedAsset(fixedAsset);
        return new ResponseEntity<>("Asset added successfully.", HttpStatus.CREATED);
    }

    @PostMapping("/update/{assetId}")
    public ResponseEntity<?> updateFixedAsset(@PathVariable Integer assetId, @RequestBody FixedAsset fixedAsset) {
        fixedAsset.setAssetId(assetId);
        fixedAssetService.modifyFixedAsset(fixedAsset);
        return new ResponseEntity<>("Asset updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{assetId}")
    public ResponseEntity<?> deleteFixedAsset(@PathVariable Integer assetId) {
        fixedAssetService.removeFixedAsset(assetId);
        return new ResponseEntity<>("Asset deleted successfully.", HttpStatus.OK);
    }

    @GetMapping("/{assetId}")
    public ResponseEntity<FixedAsset> getFixedAssetById(@PathVariable Integer assetId) {
        FixedAsset fixedAsset = fixedAssetService.getFixedAssetById(assetId);
        return new ResponseEntity<>(fixedAsset, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FixedAsset>> getAllFixedAssets() {
        List<FixedAsset> fixedAssets = fixedAssetService.getAllFixedAssets();
        return new ResponseEntity<>(fixedAssets, HttpStatus.OK);
    }


    @PostMapping("/getFixedAssetsByCondition")
    public ResponseEntity<List<FixedAsset>> getFixedAssetsByCondition(@RequestBody FixedAsset query) {
        List<FixedAsset> fixedAssets = fixedAssetService.getFixedAssetsByCondition(query);
        return new ResponseEntity<>(fixedAssets, HttpStatus.OK);
    }


    @Value("${file.upload-dir}")
    private String uploadDir;


    // 上传文件，接受前端传来的文件file
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Received file upload request: {}", file.getOriginalFilename());

        if(file.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try{

            // 确保上传目录存在
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            // 保存文件的路径
            // 应该将文件名保存为时间戳+随机数,并且文件名要保持原来的后缀
            String fileName = System.currentTimeMillis() + "-" + (int)(Math.random() * 1000) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            Path filePath = uploadPath.resolve(fileName);

            logger.info("File saved to: {}", filePath.toAbsolutePath());
            file.transferTo(filePath.toAbsolutePath());

            Map<String, String> result = new HashMap<>();
            result.put("fileName", fileName);
            //以json格式返回文件路径
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("File upload failed.", e);
            return new ResponseEntity<>("File upload failed.", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path file = Paths.get(uploadDir).resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }



}
