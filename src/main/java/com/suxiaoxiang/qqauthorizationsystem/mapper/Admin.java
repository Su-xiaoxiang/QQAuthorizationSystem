package com.suxiaoxiang.qqauthorizationsystem.mapper;
import com.suxiaoxiang.qqauthorizationsystem.Pojo.PlugmanList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;


import java.util.List;

/**
 * @UserName 程序员_Suxiaoxiang
 * @date 2025/3/3 23:09
 * @Version 1.0
 */
@Mapper
public interface Admin {
    //查询管理员QQ
    @Select("SELECT * FROM adminqq WHERE admin_QQ = #{adminQQ}")
    Integer findById(Long adminQQ);
    //查询插件列表
    @Select("select plugmanName from plugmanlist")
    List<PlugmanList> findPlugmanList();

    @Insert("INSERT INTO adminqq (admin_QQ) VALUES (#{targetQQ})")
    void insertAdmin(long targetQQ);
}
