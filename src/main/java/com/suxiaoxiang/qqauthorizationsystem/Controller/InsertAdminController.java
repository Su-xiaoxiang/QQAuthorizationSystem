package com.suxiaoxiang.qqauthorizationsystem.Controller;

import com.suxiaoxiang.qqauthorizationsystem.Pojo.PlugmanList;
import com.suxiaoxiang.qqauthorizationsystem.Service.AdminService;
import com.suxiaoxiang.qqauthorizationsystem.mapper.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/6 22:38
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/auth")
public class InsertAdminController {

    @Autowired
    private Admin adminRepository;
    @Autowired
    private AdminService adminService;
    @GetMapping("/insertAdmin")
    public ResponseEntity<String> insertAdmin(
            @RequestParam Long adminQQ,
            @RequestParam long targetQQ
    ){
        try {
            Integer byId = adminRepository.findById(adminQQ);
            if (byId == null) {
                return ResponseEntity.ok("非管理员！你想干啥！");
            }else {
                adminService.insertAdmin(adminQQ, targetQQ);
                return ResponseEntity.ok("成功授权用户 " + targetQQ+" 授权插件权限 ");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
