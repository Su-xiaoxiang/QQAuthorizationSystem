package com.suxiaoxiang.qqauthorizationsystem.Controller;


import com.suxiaoxiang.qqauthorizationsystem.Service.AccreditService;

import com.suxiaoxiang.qqauthorizationsystem.mapper.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 22:57
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/auth")
public class AccreditController {
    @Autowired
    private AccreditService authService;
    @Autowired
    private Admin adminRepository;
    /**
     * 处理授权请求
     * @param adminQQ 管理员QQ号
     * @param targetQQ 目标用户QQ号
     * @return 操作结果
     */
    @GetMapping("/grant")
    public ResponseEntity<String> grantAuthorization(
            @RequestParam  Long adminQQ,
            @RequestParam  long targetQQ,
            @RequestParam  String pluginName
    ) {
        try {
            Integer byId = adminRepository.findById(adminQQ);
            if (byId == null) {
                return ResponseEntity.ok("非管理员！你想干啥！");
            }else {
                authService.grantAuthorization(adminQQ, targetQQ,pluginName);
                return ResponseEntity.ok("成功授权用户 " + targetQQ+" 使用插件 "+pluginName);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
