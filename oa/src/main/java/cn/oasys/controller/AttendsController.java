package cn.oasys.controller;

import cn.oasys.config.StringtoDate;
import cn.oasys.pojo.AttendsList;
import cn.oasys.pojo.StatusList;
import cn.oasys.pojo.TypeList;
import cn.oasys.pojo.user.User;
import cn.oasys.service.attends.AttendsListService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AttendsController {
    @Autowired
    UserService userService;
    @Autowired
    AttendsListService attendsListService;
    @Autowired
    TypeListService typeListService;
    @Autowired
    StatusListService statusListService;

    Date start,end;
    String month_;
    DefaultConversionService service = new DefaultConversionService();

    /**
     * 员工签到
     * @param session
     * @param model
     * @return
     * @throws InterruptedException
     * @throws UnknownHostException
     */
    @RequestMapping("singin")
    public String Datag(HttpSession session, Model model) throws UnknownHostException {
        InetAddress inetAddress = null;
        inetAddress=inetAddress.getLocalHost();
        String address = inetAddress.getHostAddress();
        String start = "08:00:00", end = "17:00:00";
        service.addConverter(new StringtoDate());
        Long typeId, statusId = 10L;
        AttendsList attendsList = new AttendsList();
        User user = (User)session.getAttribute("user");
        Long userId = Long.parseLong( user.getUserId()+ "");
        userService.selectOne(user);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String nowdate = sdf.format(date);
        // 星期 判断该日期是星期几
        SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE");
        // 截取时分
        SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");
        // 截取时分秒
        SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm:ss");

        // 一周当中的星期几
        String weekofday = sdf3.format(date);
        // 时分
        String hourmin = sdf4.format(date);

        // 时分秒
        String hourminsec = sdf5.format(date);
        Long aid = null;
        Integer attendsCount = attendsListService.attendsCount(nowdate, userId);
        if (hourminsec.compareTo(end) > 0) {
            // 在17之后签到无效
            System.out.println("----不能签到");
            model.addAttribute("error", "1");
        }
        if(hourminsec.compareTo("05:00:00") <0){
            //在凌晨5点之前不能签到
            System.out.println("----不能签到");
            model.addAttribute("error", "2");
        }
        else if((hourminsec.compareTo("05:00:00") >0)&&(hourminsec.compareTo(end) <0)){
            // 明确一点就是一个用户一天只能产生两条记录
            if (attendsCount == 0) {
                if (hourminsec.compareTo(end) < 0) {
                    // 没有找到当天的记录就表示此次点击是上班 就是用来判断该记录的类型
                    // 上班id8
                    typeId = 8L;
                    // 上班就只有迟到和正常
                    if (hourminsec.compareTo(start) > 0) {
                        // 迟于规定时间 迟到
                        statusId = 11L;
                    } else if (hourminsec.compareTo(start) < 0) {
                        statusId = 10L;
                    }
                    attendsList.setTypeId(typeId);
                    attendsList.setStatusId(statusId);
                    attendsList.setAttendsTime(date);
                    attendsList.setAttendHmtime(hourmin);
                    attendsList.setAttendsIp(address);
                    attendsList.setWeekOfday(weekofday);
                    attendsList.setAttendsUserId(user);
                    System.out.println("测试插入数据："+attendsList);
                    attendsListService.insertSign(attendsList);

                }
            }
            if (attendsCount == 1) {
                // 找到当天的一条记录就表示此次点击是下班
                // 下班id9
                typeId = 9L;
                // 下班就只有早退和正常
                if (hourminsec.compareTo(end) > 0) {
                    // 在规定时间晚下班正常
                    statusId = 10L;
                } else if (hourminsec.compareTo(end) < 0) {
                    // 在规定时间早下班早退
                    statusId = 12L;
                }
                attendsList.setTypeId(typeId);
                attendsList.setStatusId(statusId);
                attendsList.setAttendsTime(date);
                attendsList.setAttendHmtime(hourmin);
                attendsList.setAttendsIp(address);
                attendsList.setWeekOfday(weekofday);
                attendsList.setAttendsUserId(user);
                attendsListService.insert(attendsList);
            }
            if (attendsCount >= 2) {
                // 已经是下班的状态了 大于2就是修改考勤时间了
                // 下班id9
                if (hourminsec.compareTo(end) > 0) { // 最进一次签到在规定时间晚下班正常
                    statusId = 10L;
                } else if (hourminsec.compareTo(end) < 0) {
                    // 最进一次签到在规定时间早下班早退
                    statusId = 12L;
                }
                aid = attendsListService.offWorkId(nowdate, userId);
                AttendsList attends2=attendsListService.select(aid);
                attends2.setAttendsIp(address);
                attendsListService.insert(attends2);
                attendsListService.updateTime(date, hourmin, statusId, aid);
                AttendsList aList = attendsListService.findLastEst(("%"+nowdate+"%"), userId);
            }
        }

        // 显示用户当天最新的记录
        System.out.println(nowdate);
        System.out.println(userId);
        AttendsList aList = attendsListService.findLastEst(("%"+nowdate+"%"), userId);
        System.out.println("签到最新记录："+aList.toString());
        if (aList != null) {
            String type = typeListService.findName(aList.getTypeId());
            model.addAttribute("type", type);
        }
        model.addAttribute("alist", aList);
        return "systemcontrol/signin";
    }

    /**
     * 考勤管理某个管理员下面的所有员工的信息
     * @param request
     * @param session
     * @param page
     * @param size
     * @param baseKey
     * @param type
     * @param status
     * @param time
     * @param icon
     * @param model
     * @return
     */
    @RequestMapping("attendceatt")
    public String testdasf(HttpServletRequest request, HttpSession session,
                           @RequestParam(name = "pageIndex", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           @RequestParam(value = "baseKey", required = false) String baseKey,
                           @RequestParam(value = "type", required = false) String type,
                           @RequestParam(value = "status", required = false) String status,
                           @RequestParam(value = "time", required = false) String time,
                           @RequestParam(value = "icon", required = false) String icon,Model model) {
        allsortpaging(request, session, page,size, baseKey, type, status, time, icon, model);
        return "attendce/attendceview";
    }

    /**
     * 该管理下面的所有用户
     * @param request
     * @param session
     * @param page
     * @param size
     * @param baseKey
     * @param type
     * @param status
     * @param time
     * @param icon
     * @param model
     */
    private void allsortpaging(HttpServletRequest request, HttpSession session, int page,int size, String baseKey, String type,
                               String status, String time, String icon, Model model) {
        setSomething(baseKey, type, status, time, icon, model);
        User user = (User)session.getAttribute("user");
        List<Long> ids = new ArrayList<>();
        List<User> userList = userService.staff(user.getUserId());
        for (User userl : userList) {
            ids.add(userl.getUserId());
        }
        if (ids.size() == 0) {
            ids.add(0L);
        }
        typestatus(request);
        System.out.println("查找"+baseKey);
        System.out.println("查找"+ids);
        PageInfo<AttendsList> attendsListPageInfo  = attendsListService.selectByExampleList(ids, baseKey, page, size);
        System.out.println(attendsListPageInfo.toString());
        request.setAttribute("aList", attendsListPageInfo.getList());
        request.setAttribute("pageInfo", attendsListPageInfo);
        request.setAttribute("url", "attendcetable");
    }

    /**
     * 考勤管理查找
     * @param request
     * @param session
     * @param page
     * @param size
     * @param baseKey
     * @param type
     * @param status
     * @param time
     * @param icon
     * @param model
     * @return
     */
    @RequestMapping("attendcetable")
    public String table(HttpServletRequest request, HttpSession session,
                        @RequestParam(name = "pageIndex", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "baseKey", required = false) String baseKey,
                        @RequestParam(value = "type", required = false) String type,
                        @RequestParam(value = "status", required = false) String status,
                        @RequestParam(value = "time", required = false) String time,
                        @RequestParam(value = "icon", required = false) String icon,Model model) {
        allsortpaging(request, session,page, size, baseKey, type, status, time, icon, model);
        return "attendce/attendcetable";
    }

    public void setSomething(String baseKey, Object type, Object status, Object time, Object icon, Model model) {
        if(!StringUtils.isEmpty(icon)){
            model.addAttribute("icon", icon);
            if(!StringUtils.isEmpty(type)){
                model.addAttribute("type", type);
                if("1".equals(type)){
                    model.addAttribute("sort", "&type=1&icon="+icon);
                }else{
                    model.addAttribute("sort", "&type=0&icon="+icon);
                }
            }
            if(!StringUtils.isEmpty(status)){
                model.addAttribute("status", status);
                if("1".equals(status)){
                    model.addAttribute("sort", "&status=1&icon="+icon);
                }else{
                    model.addAttribute("sort", "&status=0&icon="+icon);
                }
            }
            if(!StringUtils.isEmpty(time)){
                model.addAttribute("time", time);
                if("1".equals(time)){
                    model.addAttribute("sort", "&time=1&icon="+icon);
                }else{
                    model.addAttribute("sort", "&time=0&icon="+icon);
                }
            }
        }
        if(!StringUtils.isEmpty(baseKey)){
            model.addAttribute("sort", "&baseKey="+baseKey);
        }
    }

    /**
     * 状态类型方法
     * @param request
     */
    private void typestatus(HttpServletRequest request) {
        List<TypeList> type = typeListService.findByTypeModel("aoa_attends_list");
        List<StatusList> status = statusListService.findByStatusModel("aoa_attends_list");
        request.setAttribute("typelist", type);
        request.setAttribute("statuslist", status);
    }

    @RequestMapping("attendceedit")
    public String test4(@Param("aid") String aid, Model model, HttpServletRequest request, HttpSession session) {
        if (aid == null) {
            model.addAttribute("write", 0);
        } else if (aid != null) {
            long id = Long.valueOf(aid);
            AttendsList attends = attendsListService.select(id);
            model.addAttribute("write", 1);
            model.addAttribute("attends", attends);
        }
        typestatus(request);
        return "attendce/attendceedit";
    }


    /**
     * 修改保存
     * @param remark
     * @param status
     * @param id
     * @return
     */
    @RequestMapping(value = "attendcesave")
    public String test4(@RequestParam String remark,@RequestParam String status,@RequestParam Long id) {
        StatusList statusList=  statusListService.selectStatusId("aoa_attends_list",status);
        AttendsList attendsList = attendsListService.selectByPrimaryKey(id);
        attendsList.setAttendsRemark(remark);
        attendsList.setStatusId(statusList.getStatusId());
        attendsListService.update(attendsList);
        return "redirect:/attendceatt";
    }

    /**
     * 删除
     * @param aid
     * @param session
     * @return
     */
    @RequestMapping("attdelete")
    public String dsfa(@RequestParam Long aid, HttpSession session) {
        int i = attendsListService.deleteById(aid);
        System.out.println(i);
        return "redirect:/attendceatt";
    }

    /**
     * 周报表分页
     * @param
     * @param session
     * @param page
     * @param baseKey
     */
    private void weektablepaging(Model model, HttpSession session, int page,int size, String baseKey) {
        User userId = (User) (session.getAttribute("user"));
        List<Long> ids = new ArrayList<>();
        PageInfo<User> userPageInfo = userService.myEmployUser(userId.getUserId(), baseKey, page, size);
        for (User user : userPageInfo.getList()) {
            ids.add(user.getUserId());
        }
        if (ids.size() == 0) {
            ids.add(0L);
        }
        String[] weekday = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
        model.addAttribute("ulist", userPageInfo.getList()).addAttribute("pageInfo", userPageInfo)
                .addAttribute("weekday", weekday).addAttribute("url", "realweektable");
    }

    /**
     * 考勤周报表
     * @param model
     * @param session
     * @param page
     * @param size
     * @param baseKey
     * @return
     */
    @RequestMapping("attendceweek")
    public String test3(Model model, HttpSession session,
                        @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "baseKey", required = false) String baseKey) {
        weektablepaging(model, session, page,size, baseKey);
        return "attendce/weektable";
    }

    @RequestMapping("attendcemonth")
    public String test2(HttpServletRequest request, Model model, HttpSession session,
                        @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "baseKey", required = false) String baseKey) {
        monthtablepaging(request,model, session, page,size, baseKey);
        return "attendce/monthtable";
    }

    @RequestMapping("realmonthtable")
    public String dfshe(HttpServletRequest request, Model model, HttpSession session,
                        @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size,
                        @RequestParam(value = "baseKey", required = false) String baseKey) {
        monthtablepaging(request, model, session, page,size, baseKey);
        return "attendce/realmonthtable";
    }

    //月报表
    private void monthtablepaging(HttpServletRequest request, Model model, HttpSession session, int page,int size,
                                  String baseKey) {
        Integer offnum,toworknum;
        User userId = (User) (session.getAttribute("user"));
        List<Long> ids = new ArrayList<>();
        PageInfo<User> userPageInfo = userService.myEmployUser(userId.getUserId(), baseKey, page, size);
        for (User user : userPageInfo.getList()) {
            ids.add(user.getUserId());
        }
        if (ids.size() == 0) {
            ids.add(0L);
        }
        String month = request.getParameter("month");

        if(month!=null)
            month_=month;
        else
            month=month_;

        Map<String, List<Integer>> uMap = new HashMap<>();
        List<Integer> result = null;

        for (User user : userPageInfo.getList()) {
            result = new ArrayList<>();
            //当月该用户下班次数
            offnum=attendsListService.countOffWork(month, user.getUserId());
            //当月该用户上班次数
            toworknum=attendsListService.countToWork(month, user.getUserId());
            for (long statusId = 10; statusId < 13; statusId++) {
                //这里面记录了正常迟到早退等状态
                if(statusId==12) {
                    result.add(attendsListService.countNum(month,statusId, user.getUserId()) + toworknum - offnum);
                    System.out.println("月份"+month);
                    System.out.println(attendsListService.countNum(month,statusId, user.getUserId()) + toworknum - offnum);
                }
                else
                    result.add(attendsListService.countNum(month,statusId, user.getUserId()));
            }
            System.out.println("迟到早退"+result);
            //添加请假和出差的记录//应该是查找 使用sql的sum（）函数来统计出差和请假的次数
            System.out.println("请假天数"+attendsListService.countOtherNum(month, 46L, user.getUserId()));
            if(attendsListService.countOtherNum(month, 46L, user.getUserId())!=null)
                result.add(attendsListService.countOtherNum(month, 46L, user.getUserId()));
            else
                result.add(0);
            if(attendsListService.countOtherNum(month, 47L, user.getUserId())!=null)
                result.add(attendsListService.countOtherNum(month, 47L, user.getUserId()));
            else
                result.add(0);
            //这里记录了旷工的次数 还有请假天数没有记录 旷工次数=30-8-请假次数-某天签到次数
            //这里还有请假天数没有写
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
            String date_month=sdf.format(date);
            if(month!=null){
                if(month.compareTo(date_month)>=0)
                    result.add(0);
                else
                    result.add(30-8-offnum);
            }

            uMap.put(user.getUserName(), result);
        }
        model.addAttribute("uMap", uMap);
        model.addAttribute("ulist", userPageInfo.getList());
        model.addAttribute("pageInfo", userPageInfo);
        model.addAttribute("url", "realmonthtable");
    }

    @RequestMapping(value="attendcelist",method=RequestMethod.GET)
    public String test(HttpServletRequest request,  Model model,HttpSession session,
                       @RequestParam(name = "pageIndex", required = false, defaultValue = "1") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size,
                       @RequestParam(value = "baseKey", required = false) String baseKey,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "time", required = false) String time,
                       @RequestParam(value = "icon", required = false) String icon) {
        signsortpaging(request, model, session, page,size, baseKey, type, status, time, icon);
        return "attendce/attendcelist";
    }

    //单个用户的排序和分页
    private void signsortpaging(HttpServletRequest request, Model model, HttpSession session, int page,int size, String baseKey,
                                String type, String status, String time, String icon) {
        User userId = (User)(session.getAttribute("user"));
        setSomething(baseKey, type, status, time, icon, model);
        System.out.println(baseKey);
        System.out.println(userId.getUserId());
        PageInfo<AttendsList> attendsListPageInfo = attendsListService.findOneMoHu(userId.getUserId(), baseKey, page, size);
        typestatus(request);
        request.setAttribute("alist", attendsListPageInfo.getList());
        for (AttendsList attends :attendsListPageInfo.getList()) {
            System.out.println(attends);
        }
        request.setAttribute("pageInfo", attendsListPageInfo);
        request.setAttribute("url", "attendcelisttable");
    }

    @RequestMapping(value="attendcelisttable",method=RequestMethod.GET)
    public String testdf(HttpServletRequest request,  Model model,HttpSession session,
                         @RequestParam(name = "pageIndex", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         @RequestParam(value = "baseKey", required = false) String baseKey,
                         @RequestParam(value = "type", required = false) String type,
                         @RequestParam(value = "status", required = false) String status,
                         @RequestParam(value = "time", required = false) String time,
                         @RequestParam(value = "icon", required = false) String icon) {
        signsortpaging(request, model, session, page,size, baseKey, type, status, time, icon);
        return "attendce/attendcelisttable";
    }

}
