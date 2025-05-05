package com.suxiaoxiang.qqauthorizationsystem.QQBotEvents;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.suxiaoxiang.qqauthorizationsystem.utils.CozeBotClient;
import org.springframework.stereotype.Component;

/**
 * AI对话功能实现
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/6 22:30
 * @Version 1.0
 */
@Component
public class AIChatEvents extends BotPlugin {

    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {
        String message = event.getMessage();
        long senderId = event.getSender().getUserId();

        // 检查消息是否以 "/ai" 开头
        if (message.startsWith(".ai")) {
            // 提取用户的问题内容
            String question = message.substring("/ai".length()).trim();
            if (question.isEmpty()) {
                String sendMsg = MsgUtils.builder()
                        .at(event.getUserId())
                        .text("\n请输入要询问的内容")
                        .build();
                bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
                return MESSAGE_IGNORE;
            }

            try {
                // 创建会话
                String conversationId = CozeBotClient.createConversation();
                if (conversationId == null) {
                    String sendMsg = MsgUtils.builder()
                            .at(event.getUserId())
                            .text("\nAI服务暂时不可用，请稍后再试")
                            .build();
                    bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
                    return MESSAGE_IGNORE;
                }

                // 发送问题并获取回答
                CozeBotClient.MessageResult result = CozeBotClient.sendStreamMessage(conversationId, question);
                if (result != null && result.getContent() != null && !result.getContent().isEmpty()) {
                    String sendMsg = MsgUtils.builder()
                            .at(event.getUserId())
                            .text("\n" + result.getContent())
                            .build();
                    bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
                } else {
                    String sendMsg = MsgUtils.builder()
                            .at(event.getUserId())
                            .text("\nAI暂时无法回答，请稍后再试")
                            .build();
                    bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
                }
            } catch (Exception e) {
                String sendMsg = MsgUtils.builder()
                        .at(event.getUserId())
                        .text("\nAI服务出现错误，请稍后再试")
                        .build();
                bot.sendGroupMsg(event.getGroupId(), sendMsg, false);
                e.printStackTrace();
            }
        }
        return MESSAGE_IGNORE;
    }
} 