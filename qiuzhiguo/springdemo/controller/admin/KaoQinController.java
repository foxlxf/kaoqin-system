package com.qiuzhiguo.springdemo.controller.admin;

import com.qiuzhiguo.springdemo.entity.Worktime;
import com.qiuzhiguo.springdemo.mapper.KaoqinMapper;
import com.qiuzhiguo.springdemo.mapper.WorktimeMapper;
import com.qiuzhiguo.springdemo.util.DateUtil;
import com.qiuzhiguo.springdemo.util.MD5Util;
import com.qiuzhiguo.springdemo.util.PageUtil;
import com.qiuzhiguo.springdemo.util.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author feng
 * @date 2019/12/29
 * discription:*
 */
@RestController
@RequestMapping("/app/api/kaoqin")
public class KaoQinController {

    @Autowired
    KaoqinMapper kaoqinMapper;
    @Autowired
    WorktimeMapper worktimeMapper;
    @RequestMapping("/all/workdate")
    public Res addworkdata(){
        List<Map<String,Object>> list = kaoqinMapper.allWorkdata();
        for (Map<String,Object> map:list){
            map.put("time_date",((Date)map.get("time_date")).toString());
        }
        return Res.ok(list);
    }

    @RequestMapping("/all/workdate1")
    public PageUtil addworkdata1(){
        List<Map<String,Object>> list = kaoqinMapper.allWorkdata();
        for (Map<String,Object> map:list){
            map.put("in_time",DateUtil.timestampToString((Timestamp)map.get("in_time")));
            map.put("out_time",DateUtil.timestampToString((Timestamp)map.get("out_time")));
            map.put("time_date",((Date)map.get("time_date")).toString());
        }
        return PageUtil.ok(list,list.size());
    }

    /**
     * 根据日期查看考勤列表
     * @param date
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/workdata/kaoqin")
    public Map<String,Object> kaoqinList(String date, Integer page, Integer limit){
        System.out.println(date);
        Map<String,Object> map = new HashMap<>();
        map.put("date",date);
        try {
            List<Map<String,Object>> list = kaoqinMapper.kaoqinByDate(DateUtil.stringToDate(date),(page-1)*limit,limit);
            for (Map<String,Object> map1:list){
                Integer status = (Integer) map1.get("status");
                        if (status==null){
                            map1.put("status",0);
                        }
            }
            map.put("list",PageUtil.ok(list,kaoqinMapper.kaoqinCounts(DateUtil.stringToDate(date))));
            Worktime worktime = worktimeMapper.getWorktime(DateUtil.stringToDate(date));
            map.put("status",worktime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/set/worktime")
    public Res setWorktime(String date,long in_time,long out_time,Integer status){
        Worktime worktime = worktimeMapper.getWorktime(DateUtil.stringToDate(date));
        Timestamp in = new Timestamp(in_time);
        Timestamp out = new Timestamp(out_time);
        if (worktime==null){
            worktime = new Worktime();
            worktime.setInTime(in);
            worktime.setOutTime(out);
            worktime.setStatus(status);
            worktime.setTimeDate(DateUtil.stringToDate(date));
            int result = worktimeMapper.insertWorktime(worktime);
            if (result==1){
                return Res.ok();
            }
                return Res.fail("insert error");
        }else {
            worktime.setInTime(in);
            worktime.setOutTime(out);
            worktime.setStatus(status);
            int result = worktimeMapper.updateWorktime(worktime);
            if (result==1){
                return Res.ok();
            }
            return Res.fail("update error");
        }
    }

}
