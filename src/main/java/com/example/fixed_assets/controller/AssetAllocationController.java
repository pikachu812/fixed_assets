package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.AssetAllocation;
import com.example.fixed_assets.entity.User;
import com.example.fixed_assets.service.AssetAllocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@CrossOrigin(origins = {"http://127.0.0.1:5173", "http://localhost:5173"}, maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/assetAllocation")
public class AssetAllocationController {


    final Logger logger = LoggerFactory.getLogger(AssetAllocationController.class);
    private final AssetAllocationService assetAllocationService;

    public AssetAllocationController(AssetAllocationService assetAllocationService) {
        this.assetAllocationService = assetAllocationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetAllocation> getAssetAllocationById(@PathVariable Integer id) {
        return ResponseEntity.ok(assetAllocationService.getAssetAllocationById(id));
    }

    @GetMapping("/")
    public ResponseEntity<List<AssetAllocation>> getAllAssetAllocations() {
        return ResponseEntity.ok(assetAllocationService.getAllAssetAllocations());
    }


    @PostMapping("/search")
    public ResponseEntity<List<AssetAllocation>> searchAssetAllocation(@RequestBody Map<String, Object> map) {
        return ResponseEntity.ok(assetAllocationService.searchAssetAllocation(map));
    }

    @PostMapping("/pass/{allocationId}")
    public ResponseEntity<?> passAssetAllocation(@PathVariable Integer allocationId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("请先重新登录.");
        }
        if (user.getRoleId() != 1) {
            return ResponseEntity.badRequest().body("您没有权限进行此操作.");
        }
        assetAllocationService.passAssetAllocation(allocationId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/reject/{allocationId}")
    public ResponseEntity<?> rejectAssetAllocation(@PathVariable Integer allocationId, @RequestBody Map<String, Object> map, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("请先重新登录.");
        }
        if (user.getRoleId() != 1) {
            return ResponseEntity.badRequest().body("您没有权限进行此操作.");
        }
        assetAllocationService.rejectAssetAllocation(allocationId, (String) map.get("reason"));
        return new ResponseEntity<>("success", HttpStatus.OK);
    }


    @PostMapping("/allocation")
    public ResponseEntity<?> createAssetAllocation(@RequestBody Map<String, Object> map, HttpSession session) {


        //获取当前登录用户
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.badRequest().body("请先重新登录.");
        }


        if(user.getRoleId() == 1){
            return ResponseEntity.badRequest().body("管理员不可进行领用操作.");
        }


        map.put("user", user);

        logger.info("map: " + map);


        try {
            assetAllocationService.saveAssetAllocation(map);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateAssetAllocation(@RequestBody AssetAllocation assetAllocation) {
        assetAllocationService.updateAssetAllocation(assetAllocation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetAllocation(@PathVariable Integer id) {
        assetAllocationService.deleteAssetAllocationById(id);
        return ResponseEntity.ok().build();
    }
}
