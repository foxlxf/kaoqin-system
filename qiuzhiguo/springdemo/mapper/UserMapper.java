package com.qiuzhiguo.springdemo.mapper;

import com.qiuzhiguo.springdemo.entity.Person;
import com.qiuzhiguo.springdemo.entity.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username=#{username}")
    Users findByUsername(@Param("username") String username);

    @Select("SELECT * FROM person WHERE id=#{id}")
    Person findByPersonId(@Param("id") Integer id);

    @Insert("insert into users (username,password,roles,person_id) values(#{username},#{password},#{roles},#{personId})")
    Integer addUser(Users users);

    @Update("update users set password = #{password} where username = #{username}")
    Integer alterPassword(Users users);

    @Select("select users.username,person.* from users left join person on users.person_id=person.id where person.del=0 limit #{start},#{limit}")
    List<Map<String,Object>> userList(@Param("start") Integer start, @Param("limit") Integer limit);

    @Select("select count(*) from users")
    Integer usersCount();




}
