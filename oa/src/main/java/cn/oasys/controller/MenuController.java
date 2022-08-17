package cn.oasys.controller;

import cn.oasys.pojo.*;
import cn.oasys.pojo.task.TaskList;
import cn.oasys.pojo.task.TaskLogger;
import cn.oasys.pojo.user.User;
import cn.oasys.service.dept.DeptService;
import cn.oasys.service.position.PositionService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.sysmenu.SysMenuService;
import cn.oasys.service.task.TaskListService;
import cn.oasys.service.task.TaskLoggerService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import tk.mybatis.mapper.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MenuController {

    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    StatusListService statusListService;
    @Autowired
    TypeListService typeListService;
    @Autowired
    TaskListService taskListService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;
    @Autowired
    TaskLoggerService taskLoggerService;

    /**
     * 菜单管理主页
     * @param model
     * @return
     */
    @RequestMapping("/testsysmenu")
    public String testsysmenu(Model model) {
        List<SysMenu> oneMenuAll = sysMenuService.findByParentAll();
        List<SysMenu> twoMenAll = sysMenuService.findByParentsXian();
        model.addAttribute("oneMenuAll", oneMenuAll);
        model.addAttribute("twoMenuAll", twoMenAll);
        return "systemcontrol/menumanage";
    }

    @RequestMapping("menuedit")
    public String typeCheck(HttpServletRequest request,Model model, SysMenu sysMenu) {
        if(!StringUtils.isEmpty(request.getAttribute("errormess"))){
            System.out.println("errormess");
            request.setAttribute("errormess", request.getAttribute("errormess"));
        }
        if(!StringUtils.isEmpty(request.getAttribute("success"))){
            request.setAttribute("success", request.getAttribute("success"));
        }
        List<SysMenu> parentList = sysMenuService.selectFu();
        request.setAttribute("parentList", parentList);
        HttpSession session = request.getSession();
        session.removeAttribute("getId");
        if (!StringUtils.isEmpty(request.getParameter("id"))) {
            Long getId = Long.parseLong(request.getParameter("id"));
            SysMenu menuObj = sysMenuService.selectByPrimaryKey(getId);
            request.setAttribute("menuObj", menuObj);
            //小的增加
            if(!StringUtils.isEmpty(request.getParameter("add"))){
                Long getAdd=menuObj.getMenuId();
                String getName=menuObj.getMenuName();
                sysMenuService.insert(sysMenu);
                System.out.println("aaaaaaaa");
                request.setAttribute("getAdd",getAdd);
                request.setAttribute("getName",getName);
//                log.info("getAdd:{}", getAdd);
            }else{
                session.setAttribute("getId",getId);
//                log.info("getId:{}", getId);
                request.setAttribute("menuObj",menuObj);
            }
        }else{
            //大的增加
            sysMenu.setParentId(0L);
            System.out.println(sysMenu.getParentId());
            SysMenu menuObj = sysMenuService.selectOne(sysMenu);
            System.out.println(parentList.get(0));
            request.setAttribute("menuObj", parentList.get(0));
        }
        return "systemcontrol/menuedit";
    }

    @RequestMapping("deletethis")
    public String delete(HttpServletRequest req){
        Long menuId=Long.parseLong(req.getParameter("id"));
        int i=sysMenuService.deleteById(menuId);
        return "redirect:testsysmenu";
    }


    /**
     * 从数据库中获取到地址，后台接收，返回到管理页面，显示页面上面
     * @param model
     * @return
     */
    @RequestMapping("testsystype")
    public String testsystype(Model model) {
        Iterable<TypeList> typeList = typeListService.selectAll();
        model.addAttribute("typeList", typeList);
        return "systemcontrol/typemanage";
    }

    @RequestMapping("typetable")
    public String chakan(HttpServletRequest request, Model model) {
        if (!StringUtils.isEmpty(request.getParameter("name"))) {
            String name = "%" + request.getParameter("name") + "%";
            System.out.println(name);
            model.addAttribute("typeList", typeListService.selectAuthor(name));

        } else {
            Iterable<TypeList> typeList = typeListService.selectAll();
            System.out.println(typeList.toString());
            model.addAttribute("typeList", typeList);
        }
        return "systemcontrol/typetable";
    }

    //修改增加用的同一个方法
    @RequestMapping("typeedit")
    public String typeCheck( HttpServletRequest req, Model model) {
        //接收修改的id
        if (!StringUtils.isEmpty(req.getParameter("typeid"))) {
            Long typeid = Long.parseLong(req.getParameter("typeid"));
            //根据id查询之前的数据
            TypeList typeObj = typeListService.selectByPrimaryKey(typeid);
            req.setAttribute("typeObj", typeObj);
            HttpSession session = req.getSession();
            session.setAttribute("typeid", typeid);
            //typeDao.update(typeObj);
            //System.out.println("..."+typeObj.toString());
        }
        return "systemcontrol/typeedit";
    }

    @RequestMapping("addTypeList")
    public String add(@Valid TypeList typeList, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long menuId = null;
        if (!StringUtils.isEmpty(session.getAttribute("typeid"))) {
            System.out.println(session.getAttribute("typeid"));
            menuId = (Long) session.getAttribute("typeid"); // 获取进入编辑界面的menuID值
            typeList.setTypeId(menuId);
//            log.info("getId:{}", session.getAttribute("typeid"));
            session.removeAttribute("typeid");
            //根据id修改之前的数据
            typeListService.update(typeList);
            System.out.println("..." + typeList.toString());
        }else {
            //根据id增加数据
            sysMenuService.addMenu(typeList);
            System.out.println("..." + typeList.toString());
        }
        return "systemcontrol/typeedit";

    }

    @RequestMapping("deletetype")
    public String deletetype(HttpServletRequest request) {
        Long typeId=Long.parseLong(request.getParameter("id"));
        sysMenuService.deleteById(typeId);
        return "redirect:/testsystype";
    }

    /**
     *状态管理主页
     * @param model
     * @return
     */
    @RequestMapping("testsysstatus")
    public String testsysstatus(Model model) {
        Iterable<StatusList> statusList = statusListService.selectAll();
        model.addAttribute("statusList", statusList);
        return "systemcontrol/statusmanage";
    }

    @RequestMapping("statustable")
    public String statusTable(HttpServletRequest request,Model model){
        if (!StringUtils.isEmpty(request.getParameter("name"))) {
            String name = "%" + request.getParameter("name") + "%";
            System.out.println(name);
            model.addAttribute("statusList", statusListService.selectName(name));

        }else {
            Iterable<StatusList> statusList = statusListService.selectAll();
            System.out.println(statusList.toString());
            model.addAttribute("statusList", statusList);
        }
        return "systemcontrol/statustable";
    }
    @RequestMapping("statusedit")
    public String statusedit(HttpServletRequest req,Model model) {
        if(!StringUtils.isEmpty(req.getParameter("statusid"))){
            Long statusid=Long.parseLong(req.getParameter("statusid"));
            StatusList statusList= statusListService.selectByPrimaryKey(statusid);
            model.addAttribute("status", statusList);
            HttpSession session = req.getSession();
            session.setAttribute("statusid", statusid);
        }
        return "systemcontrol/statusedit";
    }

    @RequestMapping("statuscheck")
    public String add(@Valid StatusList statusList,HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Long menuId = null;
        if (!StringUtils.isEmpty(session.getAttribute("statusid"))) {
            System.out.println(session.getAttribute("statusid"));
            menuId = (Long) session.getAttribute("statusid"); // 获取进入编辑界面的menuID值
            statusList.setStatusId(menuId);
//            log.info("getId:{}", session.getAttribute("statusid"));
            session.removeAttribute("statusid");
            statusListService.update(statusList);
            System.out.println("..." + statusList.toString());
        }else {
            statusListService.insert(statusList);
            System.out.println("..." + statusList.toString());
        }
        return "systemcontrol/statusedit";
    }
    @RequestMapping("deletestatus")
    public String deletestatus(HttpServletRequest request) {
        Long statusId=Long.parseLong(request.getParameter("id"));
        statusListService.deleteById(statusId);
        return "redirect:/testsysstatus";
    }


    /**
     *任务管理主页
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("taskmanage")
    public String taskmanage(Model model, @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int page,
                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageInfo<TaskList> pageInfo = taskListService.taskListPage(page, size);
        System.out.println("tasklist" + pageInfo.toString());
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "paixu");
        return "task/taskmanage";
    }

    @RequestMapping("paixu")
    public String paixu(HttpServletRequest request,Model model,
                        @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int page,
                        @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageInfo<TaskList> pageInfo = taskListService.taskListPage(page, size);
        if (!StringUtil.isEmpty(request.getParameter("val"))) {
            String val = request.getParameter("val").trim();
            System.out.println(val);
            pageInfo = taskListService.selectManageTable(val, page, size);
            model.addAttribute("pageInfo", pageInfo);
        }
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "paixu");

        return "task/managetable";
    }

    @RequestMapping("addtask")
    public String add(@SessionAttribute("user") User user, Model model) {
        //查询类型表
        List<TypeList> typeLists = typeListService.selectAll();
        model.addAttribute("typelist", typeLists);
        //查询状态表
        List<StatusList> statusListList = statusListService.selectAll();
        model.addAttribute("statuslist", statusListList);
        //查询部门下面的员工
        List<User> userList = userService.se(user.getUserId());
        model.addAttribute("emplist", userList);
        //查询部门表
        List<Dept> deptList = deptService.selectAll();
        System.out.println("1111111111111");
        System.out.println(deptList);
        model.addAttribute("deptlist", deptList);
        //查询职位表
        List<Position> positions = positionService.selectAll();
        model.addAttribute("poslist", positions);
        userService.user(model);
        return "task/addtask";

    }


    /**
     * 新增任务保存
     */
    @RequestMapping("adana")
    public String addbc(@RequestParam("typeId")String typeId,@RequestParam("statusId")String statusId,@RequestParam("starTime")String starTime,@RequestParam("endTime")String endTime,@RequestParam("title")String title,@RequestParam("reciverlist")String reciverlist,
                        @RequestParam("taskDescribe")String taskDescribe,@RequestParam("comment")String comment,@RequestParam("top")String top,@RequestParam("cancel")String cancel, HttpServletRequest request,Model model) {
        int i=taskListService.save();
        return "redirect:addtask";

    }

    /**
     * 点击修改任务
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("edittasks")
    public String edit(@SessionAttribute("user") User user,Model model, HttpServletRequest request){
        //得到链接中的id
        String taskId=request.getParameter("id");
        System.out.println(taskId);
        Long ltaskid=Long.parseLong(taskId);
        //通过任务id得到相应的任务
        TaskList task=taskListService.selectOn(ltaskid);
        model.addAttribute("task",task);
        //得到状态id
        Long statusId=task.getStatusId().longValue();
        //得到类型id
        Long typeId=task.getTypeId();
        //查看状态表
        StatusList status=statusListService.selectO(statusId);
        model.addAttribute("status",status);
        //查询类型表
        TypeList type=typeListService.selectByPrimaryKey(typeId);
        model.addAttribute("type",type);

        //查询部门下面的员工
        List<User> userList = userService.se(user.getUserId());
        model.addAttribute("emplist", userList);

        //查询部门表
        List<Dept> deptList = deptService.selectAll();
        model.addAttribute("deptlist", deptList);
        //查询职位表
        List<Position> positionList = positionService.selectAll();
        model.addAttribute("poslist", positionList);
        userService.user(model);
        model.addAttribute("ltaskid", ltaskid);
        return "task/edittask";
    }

    /**
     * 任务管理修改
     * @param typeId
     * @param statusId
     * @param starTime
     * @param endTime
     * @param title
     * @param reciverlist
     * @param taskDescribe
     * @param comment
     * @param top
     * @param cancel
     * @param request
     * @param model
     * @return
     * @throws ParseException
     * @throws ParseException
     */
    @RequestMapping("update")
    public String update( @RequestParam("typeId")Long typeId, @RequestParam("statusId")Integer statusId, @RequestParam("starTime")String starTime, @RequestParam("endTime")String endTime
            , @RequestParam("title")String title, @RequestParam("reciverlist")String reciverlist, @RequestParam("taskDescribe")String taskDescribe, @RequestParam("comment")String comment, @RequestParam("top")Boolean top, @RequestParam("cancel")Boolean cancel, HttpServletRequest request, Model model) throws ParseException, ParseException {
        HttpSession session = request.getSession();
        Long ltaskid = (Long) session.getAttribute("ltaskid");
        System.out.println("修改id："+ltaskid);
        if (!StringUtils.isEmpty(session.getAttribute("ltaskid"))) {
//           Long id= (Long) session.getAttribute("ltaskid");
            System.out.println("或");
            TaskList tasklist = new TaskList();
//          List<tasklist> list=tdao.selectAll();
//           System.out.println(tasklist.toString());
            tasklist.setTaskId(ltaskid);
            tasklist.setPublishTime(new Date());
            tasklist.setModifyTime(new Date());
            tasklist.setTypeId(typeId);
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            tasklist.setStatusId(statusId);
            tasklist.setStarTime(simpleDateFormat.parse(starTime));
            tasklist.setEndTime(simpleDateFormat.parse(endTime));
            tasklist.setTitle(title);
            tasklist.setReciverlist(reciverlist);
            tasklist.setTaskDescribe(taskDescribe);
            tasklist.setComment(comment);
            tasklist.setTop(top);
            tasklist.setCancel(cancel);
            int i=taskListService.update(tasklist);
            System.out.println("修改的数据"+tasklist.toString());
            System.out.println(i);
            if(i>0){
                model.addAttribute("aaaaa","修改成功");
            }else{
                model.addAttribute("bbbbbb","修改失败");
            }

            String start=simpleDateFormat.format(tasklist.getStarTime());
            String end=simpleDateFormat.format(tasklist.getEndTime());
            String now=simpleDateFormat.format(new Date());
            long now2=simpleDateFormat.parse(now).getTime();
            long start2=simpleDateFormat.parse(start).getTime();
            long end2=simpleDateFormat.parse(end).getTime();
            long cha=start2-now2;
        }
        return "redirect:taskmanage";
    }

    /**
     * 任务管理查看
     * @param request
     * @return
     */
    @RequestMapping("seetasks")
    public String seetask(HttpServletRequest request){
        //得到任务的id
        String taskid=request.getParameter("id");
        Long ltaskid=Long.parseLong(taskid);
        //通过任务id得到相应的任务
        TaskList task=taskListService.selectOn(ltaskid);
        request.setAttribute("task",task);
        Long statusid=task.getStatusId().longValue();
        //根据状态id查看状态表
        StatusList status=statusListService.selectO(statusid);
        System.out.println("id"+statusid);
        request.setAttribute("status",status);
        System.out.println(status.getSortPrecent());
        //查看状态表
        List<StatusList> statulist=statusListService.selectAll();
        request.setAttribute("statuslist",statulist);
        //查看发布人
        List<User> user=userService.dd(task.getTaskPushUserId());
        //List<User> user=userMapper.se(userId);
        //request.setAttribute("user",user);
        System.out.println(":");
        System.out.println(user);
        //查看任务日志表
        List<TaskLogger> logger=taskLoggerService.findByTaskId(ltaskid);
        request.setAttribute("loggerlist",logger);
        return "task/seetask";
    }

    //存反馈日志
    @RequestMapping("tasklogger")
    public String task(TaskLogger logger, Long userId){
        System.out.println(userId);
        User user=userService.aa(userId);
        logger.setCreateTime(new Date());
        logger.setUsername(user.getUserName());
        //存日志
        taskLoggerService.xinzeng(logger);
        //修改状态
        taskLoggerService.updateStatusId(logger.getLoggerId());
        return "redirect:/taskmanage";
    }

    /**
     * 我的任务
     */
    @RequestMapping("mytask")
    public String myTask(Model model,
                         @RequestParam(value = "pageIndex", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        PageInfo<TaskList> pageInfo = taskListService.taskListPage(page, size);
        model.addAttribute("tasklist",pageInfo.getList());
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url", "mychaxun");
        return "task/mytask";
    }

    /**
     * 在我的任务里面进行查询
     *
     * @throws ParseException
     */
    @RequestMapping("mychaxun")
    public String select(HttpServletRequest request,Model model,Long userId,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "size", required = false, defaultValue = "10") int size){

        String title=null;
        if(!StringUtil.isEmpty(request.getParameter("title"))) {
            title = request.getParameter("title").trim();
            PageInfo<TaskList> pageInfo = taskListService.selectTitle(title, page, size);
            model.addAttribute("tasklist",pageInfo.getList());
            model.addAttribute("pageInfo", pageInfo);
            model.addAttribute("url", "paixu");
        }
        return "task/mytasklist";
    }

}
