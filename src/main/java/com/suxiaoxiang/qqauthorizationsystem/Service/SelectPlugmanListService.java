package com.suxiaoxiang.qqauthorizationsystem.Service;

import com.suxiaoxiang.qqauthorizationsystem.Pojo.PlugmanList;
import com.suxiaoxiang.qqauthorizationsystem.mapper.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/4 23:37
 * @Version 1.0
 */
@Service
public class SelectPlugmanListService {
    @Autowired
    private Admin adminRepository;

    public List<PlugmanList> selectPlugmanList() {
        List<PlugmanList> plugmanLists = adminRepository.findPlugmanList();
        if (plugmanLists!= null) {
            return plugmanLists;
        }
        return null;
    }
}
