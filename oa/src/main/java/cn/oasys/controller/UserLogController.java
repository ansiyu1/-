package cn.oasys.controller;

import cn.oasys.pojo.ScheduleList;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserLog;
import cn.oasys.service.schedue.ScheduleListService;
import cn.oasys.service.task.TaskListService;
import cn.oasys.service.user.UserLogRecordService;
import cn.oasys.service.user.UserLogService;
import cn.oasys.service.user.UserService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserLogController {
    @Autowired
    ScheduleListService scheduleListService;
    @Autowired
    UserService userService;
    @Autowired
    TaskListService taskListService;
    @Autowired
    UserLogService userLogService;
    @Autowired
    UserLogRecordService userLogRecordService;

    public static ArrayList<Map.Entry<String,Integer>> sortMap(Map map){
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> obj1 , Map.Entry<String, Integer> obj2) {
                return obj2.getValue() - obj1.getValue();
            }
        });
        return (ArrayList<Map.Entry<String, Integer>>) entries;
    }

    //显示任务统计模块数据
    @RequestMapping("counttasknum")
    public String test3df(HttpServletResponse response) throws IOException {
        List<User> uList= userService.selectAll();
        HashMap< String, Integer> hashMap=new HashMap<>();
        int i=0;
        for (User user : uList) {
            if(taskListService.taskCount(user.getUserId())>0){
                hashMap.put(user.getUserName(), taskListService.taskCount(user.getUserId()));
                i++;
            }
        }
        ArrayList<Map.Entry<String,Integer>> entries= sortMap(hashMap);
        ArrayList<Map.Entry<String,Integer>> entries2=new ArrayList<Map.Entry<String,Integer>>();

        if(entries.size()>=5)
            //获得前5个s
            for (int j = 0; j < 5; j++) {
                entries2.add(entries.get(j));
            }
        else {
            entries2= entries;
        }
        String json=JSONObject.toJSONString(entries2);
        System.out.println(json);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }

    //日历的数据显示
    @RequestMapping("littlecalendar")
    public String test3df(HttpSession session, HttpServletResponse response) throws IOException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        List list=new ArrayList<>();
        List<ScheduleList>  dates=scheduleListService.findstart((Long) session.getAttribute("userId"));
        for (ScheduleList scheduleList : dates) {
            list.add(sdf.format(scheduleList.getStartTime()));
        }
        System.out.println(dates.toString());
        String json= JSONObject.toJSONString(list);
        System.out.println(json);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }

    private static void setToFirstDay(Calendar calendar) {
        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DATE, -1);
        }
    }

    @RequestMapping("countweeklogin")
    public String dfsa(HttpServletResponse response) throws IOException{
        Integer []r=new Integer[7];
        Calendar calendar=Calendar.getInstance();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            r[i]=userLogRecordService.recordCount(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        String json=JSONObject.toJSONString(r);
        System.out.println(json);
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(json);
        return null;
    }


    /**
     * 用来查找用户记录
     * @param page
     * @param session
     * @param model
     * @param basekey
     * @param time
     * @param icon
     * @return
     */
    @RequestMapping("morelog")
    public String test3df(@RequestParam(value="pageIndex",defaultValue = "1")int page,
                          HttpSession session, Model model,
                          @SessionAttribute("user")User user,
                          @RequestParam(value="baseKey",required=false)String basekey,
                          @RequestParam(value="time",required=false)String time,
                          @RequestParam(value="icon",required=false)String icon
    ) {
        System.out.println("11"+basekey);
        getuserlog(page, session, model, basekey, time, icon,user);
        return "user/userlogmanage";
    }

    //用来查找用户记录
    @RequestMapping("morelogtable")
    public String test3dfrt(@RequestParam(value="pageIndex",defaultValue = "0")int page,
                            HttpSession session,Model model,
                            @SessionAttribute("user")User user,
                            @RequestParam(value="baseKey",required=false)String basekey,
                            @RequestParam(value="time",required=false)String time,
                            @RequestParam(value="icon",required=false)String icon) {
        getuserlog(page, session, model, basekey, time, icon,user);
        return "user/userlogmanagetable";

    }

    public void getuserlog(int page, HttpSession session, Model model, String basekey, String time,
                           String icon,User user) {
        setTwo(model, basekey, time,icon);
        PageInfo<UserLog> page3=userLogService.ulogpaging(page, basekey, user.getUserId(), time);
        System.out.println(page3.getList());
        model.addAttribute("pageInfo", page3);
        model.addAttribute("userloglist", page3.getList());

        model.addAttribute("url", "morelogtable");
    }
    //记忆两种规则
    private void setTwo(Model model, String basekey, Object time,Object icon) {
        if(!StringUtils.isEmpty(time)){
            model.addAttribute("time", time);
            model.addAttribute("icon", icon);
            model.addAttribute("sort", "&time="+time+"&icon="+icon);
        }
        if(!StringUtils.isEmpty(basekey)){
            model.addAttribute("basekey", basekey);
            model.addAttribute("sort", "&basekey="+basekey);
        }
    }

}
