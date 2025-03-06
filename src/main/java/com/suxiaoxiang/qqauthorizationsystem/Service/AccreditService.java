package com.suxiaoxiang.qqauthorizationsystem.Service;

import com.suxiaoxiang.qqauthorizationsystem.Pojo.AccreditUser;
import com.suxiaoxiang.qqauthorizationsystem.mapper.Admin;
import com.suxiaoxiang.qqauthorizationsystem.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 23:01
 * @Version 1.0
 */
@Service
public class AccreditService {
    @Autowired
    private Admin adminRepository;

    @Autowired
    private UserMapper userMapper;

    /**
     * 执行授权操作
     * @param adminQQ 管理员QQ号
     * @param targetQQ 目标用户QQ号
     * @throws IllegalArgumentException 如果权限验证失败
     */
    @Transactional
    public void grantAuthorization(Long adminQQ, Long targetQQ,String pluginName) {
        // 1. 验证管理员权限
        Integer byId = adminRepository.findById(adminQQ);
        if (byId == null) {
            throw new IllegalArgumentException("你不是管理员请走开");
        }else{
            //寻找插件
            if (pluginName != null &&!pluginName.isEmpty()){
                Integer i = userMapper.selectByPluginName(pluginName);
                if (i == null){
                    throw new IllegalArgumentException("插件不存在");
                }
                // 管理员权限验证通过
                // 2. 存储授权记录
                AccreditUser user = new AccreditUser();
                user.setTargetQq(targetQQ);
                user.setGrantedBy(adminQQ);
                user.setPluginName(i);
                user.setGrantedTime(LocalDateTime.now());
                userMapper.save(user);
            }

        }
    }
}
