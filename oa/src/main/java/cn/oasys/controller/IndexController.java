package cn.oasys.controller;

import cn.oasys.dao.*;
import cn.oasys.pojo.*;
import cn.oasys.pojo.notice.Notepaper;
import cn.oasys.pojo.role.Role;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserLog;
import cn.oasys.service.attends.AttendsListService;
import cn.oasys.service.dept.DeptService;
import cn.oasys.service.director.DirectorService;
import cn.oasys.service.discuss.DiscussService;
import cn.oasys.service.file.FileListService;
import cn.oasys.service.mail.MailNumberService;
import cn.oasys.service.mail.MailReciverService;
import cn.oasys.service.noice.NotepaperService;
import cn.oasys.service.noice.NoteListService;
import cn.oasys.service.plan.PlanListService;
import cn.oasys.service.process.ProcessListService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.sysmenu.SysMenuService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserLogService;
import cn.oasys.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptservice;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PositionMapper positionMapper;
    @Autowired
    UserLogService userLogService;
    @Autowired
    AttendsListService attendsListService;
    @Autowired
    FileListService fileListService;
    @Autowired
    DirectorService directorService;
    @Autowired
    DiscussService discussService;
    @Autowired
    NoteListService noticeListService;
    @Autowired
    StatusListService statusListService;
    @Autowired
    TypeListService typeListService;
    @Autowired
    PlanListService planListService;
    @Autowired
    NotepaperService notepaperService;
    @Autowired
    ProcessListService processListService;
    @Autowired
    MailReciverService mailReciverService;
    @Autowired
    SysMenuMapper sysMenuMapper;

    @RequestMapping("/userlogs")
    public String userlogs(@SessionAttribute("userId")Long userId, Model model){
        List<UserLog> userLogList = userLogService.userlogs(userId);
        System.out.println(userLogList.toString());
        model.addAttribute(userLogList);
        return "user/userlog";
    }



    /**
     * 一级菜单查询
     * 二级菜单查询
     * 登录用户名、职位以及所在部门
     * @param model
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("index")
    public String findbyparentall(Model model,HttpSession session,User user){

        List<SysMenu> oneMenuAll = sysMenuService.findByParentAll();
        System.out.println(oneMenuAll.toString());
        List<SysMenu> all = sysMenuService.selectAll();
        System.out.println(all.toString());
        List<SysMenu> twoMenuAll=sysMenuService.findByParentsXian();
        System.out.println(sysMenuService.findByParentsXian());
        user= (User) session.getAttribute("user");
        System.out.println(user.getDeptId());
        System.out.println(user.getUserName());
        Dept dept = deptservice.selectByPrimaryKey(user.getDeptId().getDeptId());
        System.out.println(dept.getDeptName());
        Role role = roleMapper.selectByPrimaryKey(user.getRoleId().getRoleId());
        Position position = positionMapper.selectByPrimaryKey(user.getRoleId().getRoleId());

        List<UserLog> userLogList = userLogService.userlogs(user.getUserId());
        System.out.println(userLogList.toString());
        model.addAttribute("oneMenuAll", oneMenuAll)
             .addAttribute("twoMenuAll", twoMenuAll)
             .addAttribute(dept)
             .addAttribute(role)
             .addAttribute(position);
        model.addAttribute(userLogList);
        model.addAttribute("user",user);
        return "index/index";
    }

    /**
     * 控制面板
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("homepage")
    public String homepage(Model model,HttpSession session){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String nowdate = simpleDateFormat.format(date);
        User user= (User) session.getAttribute("user");
        System.out.println(user.getUserId());
        AttendsList attendsList = attendsListService.CheckInRecord(nowdate, user.getUserId());
        int fileCount = fileListService.fileCount(user.getUserId());
        int directCount = directorService.directCount(user.getUserId());
        int mailCount = mailReciverService.mailCount(user.getUserId());
        model.addAttribute("alist",attendsList).addAttribute("fileCount",fileCount).addAttribute("directCount",directCount).addAttribute("mailCount",mailCount);
//        公告通知
        List<Map<String, Object>> list = noticeListService.myNotice(user.getUserId());
        for (Map<String,Object> map:list) {
            map.put("status",statusListService.select(map.get("status_id")).getStatusName());
            map.put("type",typeListService.select(map.get("type_id")).getTypeName());
            map.put("statusColor",statusListService.select(map.get("status_id")).getStatusColor());
            map.put("userName",userService.select(map.get("user_id")).getUserName());
            map.put("deptName",deptservice.select(map.get("user_id")).getDeptName());
        }
        System.out.println("面板通知"+list);
        model.addAttribute("noticeList",list);
//        工作计划
        List<PlanList> planList = planListService.planLimit(user.getUserId());
        Example type = new Example(TypeList.class);
        Example status = new Example(StatusList.class);
        type.createCriteria().andEqualTo("typeModel", "aoa_plan_list");
        status.createCriteria().andEqualTo("statusModel","aoa_plan_list");
        List<TypeList> ptype = (List<TypeList>) typeListService.selectByExample(type);
        List<StatusList> pstatus = (List<StatusList>) statusListService.selectByExample(status);
        System.out.println(planList.toString());
        model.addAttribute("planList",planList).addAttribute("ptypelist", ptype).addAttribute("pstatuslist", pstatus);
//        我的便签
        List<Notepaper> notepaperList = notepaperService.notepaperBy(user.getUserId());
        model.addAttribute("notepaperList",notepaperList);
//        流程管理
        List<ProcessList> processLists = processListService.processBy(user.getUserId());
        Example statusEx=new Example(StatusList.class);
        status.createCriteria().andEqualTo("statusModel","aoa_process_list");
        List<StatusList> processStatus = (List<StatusList>) statusListService.selectByExample(statusEx);
        System.out.println("我的流程"+processLists.toString());
        System.out.println("模块"+processStatus.toString());
        model.addAttribute("processlist",processLists).addAttribute("prostatuslist",processStatus);
        return "systemcontrol/control";
    }

}
