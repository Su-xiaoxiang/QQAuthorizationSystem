package com.suxiaoxiang.qqauthorizationsystem.QQBotEvents;


import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.suxiaoxiang.qqauthorizationsystem.QQBot.ExtractTargetQq;
import com.suxiaoxiang.qqauthorizationsystem.QQBotService.AccreditService;
import com.suxiaoxiang.qqauthorizationsystem.QQBotService.SelectPluginListService;
import org.springframework.stereotype.Component;



/**
 * 查询插件列表
 * @author Corder-Suxiaoxiang
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/4 23:29
 * @Version 1.0
 */
@Component
public class SelectPluginList extends BotPlugin {

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        String message = event.getMessage(); // 获取消息内容
        GroupMessageEvent.GroupSender sender = event.getSender();
        long senderId = sender.getUserId();

        // 1. 检查消息是否以 ".授权" 开头
        if (message.startsWith(".查询插件列表")) {

            // 4. 调用授权服务
            String result = SelectPluginListService.selectPluginLists(senderId);
            String sendMsg = MsgUtils.builder()
                    .at(event.getUserId())
                    .text("\n"+result)
                    .build();
            // 5. 发送操作结果
            bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
        }
        return MESSAGE_IGNORE;
}
}
