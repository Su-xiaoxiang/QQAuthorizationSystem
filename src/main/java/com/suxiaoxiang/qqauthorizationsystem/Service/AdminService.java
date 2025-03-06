package com.suxiaoxiang.qqauthorizationsystem.Service;

import com.suxiaoxiang.qqauthorizationsystem.mapper.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/6 22:42
 * @Version 1.0
 */
@Service
public class AdminService {
    @Autowired
    private Admin admin;

    public void insertAdmin(Long adminQQ, long targetQQ) {
        admin.insertAdmin(targetQQ);
    }
}
