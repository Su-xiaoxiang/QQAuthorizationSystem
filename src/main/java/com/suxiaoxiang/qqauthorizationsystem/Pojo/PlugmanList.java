package com.suxiaoxiang.qqauthorizationsystem.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/4 23:38
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlugmanList {
    private String plugmanId;
    private String plugmanName;

    @Override
    public String toString() {
        return "插件名字:"+" " +  plugmanName;
    }

    public String getPlugmanId() {
        return plugmanId;
    }

    public void setPlugmanId(String plugmanId) {
        this.plugmanId = plugmanId;
    }

    public String getPlugmanName() {
        return plugmanName;
    }

    public void setPlugmanName(String plugmanName) {
        this.plugmanName = plugmanName;
    }
}
