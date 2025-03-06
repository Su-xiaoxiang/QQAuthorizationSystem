package com.suxiaoxiang.qqauthorizationsystem.QQBotEvents;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.model.ArrayMsg;
import com.suxiaoxiang.qqauthorizationsystem.QQBot.ExtractTargetQq;
import com.suxiaoxiang.qqauthorizationsystem.QQBotService.AccreditService;

import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 实现授权
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 22:43
 * @Version 1.0
 */
@Component
public class AccreditEvents extends BotPlugin {

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        String message = event.getMessage();
        long senderId = event.getSender().getUserId();

        if (message.startsWith(".授权")) {
            String[] parts = message.split(" ");
            if (parts.length < 2) {
                bot.sendGroupMsg(event.getGroupId(), "指令格式错误，请使用：.授权<类型>@<目标用户>", false);
                return MESSAGE_IGNORE;
            }

            // 提取插件名字（从 ".授权" 后到第一个 @ 或空格之前）
            String rawPluginPart = parts[0].substring(".授权".length()).trim();
            String[] pluginParts = rawPluginPart.split("[@]");
            String pluginName = pluginParts[0].trim();

            if (pluginName.isEmpty()) {
                bot.sendGroupMsg(event.getGroupId(), "插件名字不能为空", false);
                return MESSAGE_IGNORE;
            }
            // 提取目标QQ号
            long targetQq = ExtractTargetQq.extractQqFromMessage(message);
            if (targetQq == 0) {
                bot.sendGroupMsg(event.getGroupId(), "无法解析目标用户", false);
                return MESSAGE_IGNORE;
            }

            // 调用授权服务
            String result = AccreditService.handleAuthCommand(senderId, targetQq, pluginName);
            String sendMsg = MsgUtils.builder()
                    .at(targetQq)
                    .text("\n"+result+"\n请注意查收授权结果")
                    .build();
            bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
        }
        return MESSAGE_IGNORE;
    }
}
