package com.qiuzhiguo.springdemo.controller.admin;

import com.qiuzhiguo.springdemo.entity.Person;
import com.qiuzhiguo.springdemo.entity.Users;
import com.qiuzhiguo.springdemo.mapper.PersonMapper;
import com.qiuzhiguo.springdemo.mapper.UserMapper;
import com.qiuzhiguo.springdemo.util.DateUtil;
import com.qiuzhiguo.springdemo.util.MD5Util;
import com.qiuzhiguo.springdemo.util.PageUtil;
import com.qiuzhiguo.springdemo.util.Res;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
@RestController
@RequestMapping("/app/api")
public class UsersController {
    @Autowired
    PersonMapper personMapper;
    @Autowired
    UserMapper userMapper;

    /**
     *添加员工
     * @param name
     * @param birth   yy-MM-mm
     * @param department
     * @param person_number
     * @param native_place
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/add/user")
    public Res add(String name,String birth,String sex,String department,String person_number,
                   String native_place,String username,String password){
        if (name==null||birth==null||sex==null||department==null||person_number==null||native_place==null||username==null||password==null){
            return Res.fail("para error");
        }
        Users users = new Users();
        users.setUsername(username);
        users.setPassword(MD5Util.encode(password));
        users.setRoles("ROLE_USER");
        Person person = new Person();
        person.setName(name);
        person.setBirth(DateUtil.stringToDate(birth));
        person.setSex(sex);
        person.setDepartment(department);
        person.setNativePlace(native_place);
        person.setPersonNumber(person_number);
        try {
            personMapper.addPerson(person);
            System.out.println(person.getId());
            users.setPersonId((int)person.getId());
            userMapper.addUser(users);
            return Res.ok();
        }catch (Exception e){
            e.printStackTrace();
            return Res.fail("sql error");
        }
    }
    /**
     * 修改员工信息
     */
    @RequestMapping("/alter/user")
    public Res alter_password(String username,String password){
        if (username==null||password==null){
            return Res.fail("params error");
        }
        System.out.println(username);
        System.out.println(MD5Util.encode(password));
        Users users = new Users();
        users.setPassword(MD5Util.encode(password));
        users.setUsername(username);
        Integer result = userMapper.alterPassword(users);
//        SqlSession sqlSession = getSqlsession
        if (result==1)
            return Res.ok();
        return Res.fail("更新失败");
    }

    /**
     * 用户列表
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/list/user")
    public PageUtil userList(Integer page, Integer limit){
        if (page==null||limit==null){
            return PageUtil.fail("params error");
        }
        try {
            List<Map<String,Object>> list = userMapper.userList((page-1)*limit,limit);
            for (Map<String,Object> map:list){
                java.sql.Date date =(Date) map.get("birth");
                if (date!=null){
                    System.out.println(date.toString());
                    map.put("birth",date.toString());
                }
            }
            return PageUtil.ok(list,userMapper.usersCount());
        }catch (Exception e){
            e.printStackTrace();
            return PageUtil.fail("sql error");
        }
    }

    /**
     * 删除用户
     * @param username
     * @return
     */
    @RequestMapping("/del/user")
    public Res delUser(String username){
        System.out.println("del");
        System.out.println(username);
        Users users = userMapper.findByUsername(username);
        if (users==null){
            return Res.fail("用户不存在");
        }
        System.out.println(users.getRoles());
        System.out.println(users.getUsername());
        System.out.println(users.getPersonId());
        Person person = new Person();
        person.setId(users.getPersonId());
        Integer result = personMapper.delPerson( person);
        System.out.println(person.getId());
        if (result==1){
            return Res.ok();
        }
        return Res.fail("删除失败");
    }
}
