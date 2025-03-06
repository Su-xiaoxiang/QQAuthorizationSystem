package com.suxiaoxiang.qqauthorizationsystem.QQBotService;



import org.springframework.web.client.RestTemplate;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 22:46
 * @Version 1.0
 */
public class AccreditService {
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String AUTH_API = "http://localhost:8080/api/auth";
    /**
     * 处理授权指令
     * @param adminQQ 操作者QQ号
     * @param targetQQ 目标用户QQ号
     * @return 操作结果描述
     */
    public static String handleAuthCommand(long adminQQ, Long targetQQ,String pluginName) {
        try {
            return restTemplate.getForObject(
                    AUTH_API + "/grant?adminQQ={1}&targetQQ={2}&pluginName={3}",
                    String.class,
                    adminQQ,
                    targetQQ,
                    pluginName
            );
        } catch (Exception e) {
            return "授权失败，请检查网络连接或联系管理员";
        }
    }


}
