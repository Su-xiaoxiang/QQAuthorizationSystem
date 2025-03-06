package com.suxiaoxiang.qqauthorizationsystem.QQBotService;

import org.springframework.web.client.RestTemplate;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/6 22:36
 * @Version 1.0
 */
public class InsertAdminService {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_API = "http://localhost:8080/api/auth";
    /**
     * 处理增加管理员指令
     * @param adminQQ 操作者QQ号
     * @return 操作结果描述
     */
    public static String insertAdmin(long adminQQ, long targetQQ) {
        try {
            return restTemplate.getForObject(
                    AUTH_API + "/insertAdmin?adminQQ={1}&targetQQ={2}",
                    String.class,
                    adminQQ,
                    targetQQ
            );
        } catch (Exception e) {
            return "查询插件列表失败，请检查网络连接或联系管理员";
        }
    }
}
