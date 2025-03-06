package com.suxiaoxiang.qqauthorizationsystem.mapper;

import com.suxiaoxiang.qqauthorizationsystem.Pojo.AccreditUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @UserName 程序员_Suxiaoxiang
* @date 2025/3/3 23:14
* @Version 1.0
*/
@Mapper
public interface UserMapper {
    //插入授权信息
    @Insert("insert into accredittable(adminQQ,targetQQ,AccreditTime,plugmanName) values(#{grantedBy},#{targetQq},#{grantedTime},#{pluginName})")
    void save(AccreditUser user);
   //查询插件信息
    @Select("select id from plugmanlist where plugmanName = #{plugmanName}")
    Integer selectByPluginName(String pluginName);
}
