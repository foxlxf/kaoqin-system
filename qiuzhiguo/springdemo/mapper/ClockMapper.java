package com.qiuzhiguo.springdemo.mapper;

import com.qiuzhiguo.springdemo.entity.PersistentLogins;
import com.qiuzhiguo.springdemo.entity.Worktime;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Map;



public interface ClockMapper {


    @Select("SELECT time_date as 'timeDate',in_time as 'inTime',out_time as 'outTime',status as 'status' FROM security_kaoqin.worktime where time_date=#{time_date} and status=0")
    Worktime findByTimeDate(@Param("time_date") String time_date);


    @Insert("INSERT INTO persistent_logins(person_id,in_time,late_reason,out_time,now_time,status) VALUES(#{a.personId},#{a.inTime},#{a.lateReason},#{a.outTime},#{a.nowTime},#{a.status})")
    @Options(useGeneratedKeys = true, keyProperty = "a.id")
    int insertInfo(@Param("a") PersistentLogins persistentLogins);

    @Select("SELECT * FROM persistent_logins WHERE person_id=#{person_id} and now_time=#{now_time} and status=#{status}")
    PersistentLogins findByOneDay(@Param("person_id") int person_id, @Param("now_time") Date date, @Param("status") int status);

    @Select("SELECT * FROM persistent_logins WHERE person_id=#{person_id} and now_time=#{now_time}")
    PersistentLogins findByOneDayNoStatus(@Param("person_id") int person_id, @Param("now_time") Date date);

    @Select("SELECT * FROM persistent_logins WHERE person_id=#{person_id}")
    List<PersistentLogins> findAllByOneDayNoStatus(@Param("person_id") int person_id);

    @Select("select worktime.*,persistent_logins.`status` as 'kaoqin' from worktime left join persistent_logins on worktime.time_date=persistent_logins.now_time and persistent_logins.person_id=(select person_id from users where users.username=#{username}) limit #{start},#{limit}")
    List<Map<String,Object>> allKaoqinByUsername(@Param("username") String username, @Param("start") Integer start, @Param("limit") Integer limit);



    @Select("select count(*) from worktime left join persistent_logins on worktime.time_date=persistent_logins.now_time and persistent_logins.person_id=(select person_id from users where users.username=#{username})")
    Integer allKaoqinCountsByUsername(@Param("username") String username);

    @Update("update users set password=#{password} where person_id=#{person_id}")
    int updateUserPasswd(@Param("password") String password, @Param("person_id") int person_id);

}
