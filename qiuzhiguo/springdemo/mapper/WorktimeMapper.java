package com.qiuzhiguo.springdemo.mapper;

import com.qiuzhiguo.springdemo.entity.Worktime;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.sql.Date;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
public interface WorktimeMapper {


    @Select("select * from worktime where time_date=#{date}")
    Worktime getWorktime(Date date);

    @Insert("insert into worktime (in_time,out_time,time_date,status) values(#{inTime},#{outTime},#{timeDate},#{status})")
    Integer insertWorktime(Worktime worktime);

    @Update("update worktime set in_time=#{inTime},out_time=#{outTime},status=#{status}")
    Integer updateWorktime(Worktime worktime);
}
