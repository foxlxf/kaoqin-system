package com.qiuzhiguo.springdemo.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
public interface KaoqinMapper {

    @Select("select * from worktime")
    List<Map<String,Object>> allWorkdata();

    @Select("select person.name,person.person_number,persistent_logins.`status` from (users left join persistent_logins on users.person_id=persistent_logins.person_id AND persistent_logins.now_time=#{date})left join person on users.person_id=person.id where users.roles='ROLE_USER' limit #{start},#{limit}")
    List<Map<String,Object>> kaoqinByDate(@Param("date") Date date,@Param("start") Integer start, @Param("limit") Integer limit);

    @Select("select count(*) from (users left join persistent_logins on users.person_id=persistent_logins.person_id AND persistent_logins.now_time=#{date})left join person on users.person_id=person.id where users.roles='ROLE_USER' ")
    Integer kaoqinCounts(Date data);
}
