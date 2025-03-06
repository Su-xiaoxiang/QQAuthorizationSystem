package com.suxiaoxiang.qqauthorizationsystem.QQBotEvents;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.suxiaoxiang.qqauthorizationsystem.QQBot.ExtractTargetQq;
import com.suxiaoxiang.qqauthorizationsystem.QQBotService.InsertAdminService;
import com.suxiaoxiang.qqauthorizationsystem.QQBotService.SelectPluginListService;
import org.springframework.stereotype.Component;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/6 22:30
 * @Version 1.0
 */
@Component
public class InsertAdminEvents extends BotPlugin {
    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        // 获取消息内容
        String message = event.getMessage();
        // 获取发送者QQ号
        long senderId = event.getSender().getUserId();

        // 1. 检查消息是否以 ".增加授权管理员" 开头
        if (message.startsWith(".增加授权管理员")) {
            // 提取目标QQ号
            long targetQq = ExtractTargetQq.extractQqFromMessage(message);
            if (targetQq == 0) {
                bot.sendGroupMsg(event.getGroupId(), "无法解析目标用户", false);
                return MESSAGE_IGNORE;
            }

            // 4. 调用授权服务
            String result = InsertAdminService.insertAdmin(senderId,targetQq);
            String sendMsg = MsgUtils.builder()
                    .at(targetQq)
                    .text(result)
                    .build();
            // 5. 发送操作结果
            bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
        }
        return MESSAGE_IGNORE;
    }
}
