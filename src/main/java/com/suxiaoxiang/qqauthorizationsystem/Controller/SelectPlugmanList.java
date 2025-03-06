package com.suxiaoxiang.qqauthorizationsystem.Controller;

import com.suxiaoxiang.qqauthorizationsystem.Pojo.PlugmanList;
import com.suxiaoxiang.qqauthorizationsystem.Service.SelectPlugmanListService;
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
 * @date 2025/3/4 23:36
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/api/auth")
public class SelectPlugmanList {
    @Autowired
    private SelectPlugmanListService selectPlugmanListService;
    @Autowired
    private Admin adminRepository;
    /**
     * 处理授权请求
     * @param adminQQ 管理员QQ号
     * @return 操作结果
     */
    @GetMapping("/selectPluginList")
    public ResponseEntity<String> grantAuthorization(
            @RequestParam Long adminQQ
    ) {
        try {
            Integer byId = adminRepository.findById(adminQQ);
            if (byId == null) {
                return ResponseEntity.ok("非管理员！你想干啥！");
            }else {
                List<PlugmanList> plugmanList = selectPlugmanListService.selectPlugmanList();
                StringBuilder response = new StringBuilder();

                // Iterate over the list and add each plugin name to the response string
                for (PlugmanList plugman : plugmanList) {
                    response.append(plugman.toString()).append("\n");  // Adding each plugin on a new line
                }

                return ResponseEntity.ok(response.toString());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
