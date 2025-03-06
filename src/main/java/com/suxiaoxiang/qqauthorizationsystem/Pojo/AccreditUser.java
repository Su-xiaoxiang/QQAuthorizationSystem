package com.suxiaoxiang.qqauthorizationsystem.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Corder-Suxiaoxiang
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 22:59
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccreditUser {
    // 目标QQ
    private Long targetQq;
    // 授权人QQ
    private Long grantedBy;
    // 授权插件
    private Integer pluginName;
    // 授权时间
    private LocalDateTime grantedTime;

    public Long getTargetQq() {
        return targetQq;
    }

    public void setTargetQq(Long targetQq) {
        this.targetQq = targetQq;
    }

    public Long getGrantedBy() {
        return grantedBy;
    }

    public void setGrantedBy(Long grantedBy) {
        this.grantedBy = grantedBy;
    }

    public Integer getPluginName() {
        return pluginName;
    }

    public void setPluginName(Integer pluginName) {
        this.pluginName = pluginName;
    }

    public LocalDateTime getGrantedTime() {
        return grantedTime;
    }

    public void setGrantedTime(LocalDateTime grantedTime) {
        this.grantedTime = grantedTime;
    }
}
