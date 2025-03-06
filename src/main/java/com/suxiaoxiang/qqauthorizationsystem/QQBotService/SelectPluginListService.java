package com.suxiaoxiang.qqauthorizationsystem.QQBotService;

import org.springframework.web.client.RestTemplate;

/**
 * 查询插件列表
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/4 23:32
 * @Version 1.0
 */
public class SelectPluginListService {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_API = "http://localhost:8080/api/auth";

    /**
     * 处理查询指令
     * @param adminQQ 操作者QQ号
     * @return 操作结果描述
     */
    public static String selectPluginLists(long adminQQ) {
        try {
            return restTemplate.getForObject(
                    AUTH_API + "/selectPluginList?adminQQ={1}",
                    String.class,
                    adminQQ
            );
        } catch (Exception e) {
            return "查询插件列表失败，请检查网络连接或联系管理员";
        }
    }
}
