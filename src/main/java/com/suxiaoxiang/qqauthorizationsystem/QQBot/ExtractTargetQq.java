package com.suxiaoxiang.qqauthorizationsystem.QQBot;

import com.mikuac.shiro.common.utils.ShiroUtils;
import com.mikuac.shiro.model.ArrayMsg;
import java.util.List;

public class ExtractTargetQq {

    public static long extractQqFromMessage(String message) {
        // 将消息解析为 ArrayMsg 列表
        List<ArrayMsg> arrayMsgList = ShiroUtils.rawToArrayMsg(message);

        // 使用 ShiroUtils 提供的 getAtList 方法提取所有 @ 的QQ号
        List<Long> atList = ShiroUtils.getAtList(arrayMsgList);

        // 如果有至少一个有效的 @ 目标，返回第一个QQ号
        if (!atList.isEmpty()) {
            return atList.get(0);
        }
     // 如果没有找到 @ 目标，返回0
        return 0;
    }
}