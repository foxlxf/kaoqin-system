package com.qiuzhiguo.springdemo.mapper;

import com.qiuzhiguo.springdemo.entity.Person;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
public interface PersonMapper {

    @Insert("insert into person (name,birth,sex,department,native_place,person_number) values(#{person.name},#{person.birth},#{person.sex},#{person.department},#{person.nativePlace},#{person.personNumber})")
    @Options(useGeneratedKeys = true,keyProperty = "person.id")
    void addPerson(@Param("person") Person person);

    @Update("update person set del=1 where id=#{id}")
    Integer delPerson(Person person);

}
