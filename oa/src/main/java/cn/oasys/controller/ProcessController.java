package cn.oasys.controller;

import cn.oasys.pojo.*;
import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.bursement.BurSeMeNt;
import cn.oasys.pojo.details.DetailsBurse;
import cn.oasys.pojo.evection.EveCtIonMoney;
import cn.oasys.pojo.evection.Evection;
import cn.oasys.pojo.holiday.Holiday;
import cn.oasys.pojo.overtime.Overtime;
import cn.oasys.pojo.regular.Regular;
import cn.oasys.pojo.resign.Resign;
import cn.oasys.pojo.stay.Stay;
import cn.oasys.pojo.subject.Subject;
import cn.oasys.pojo.traffic.Traffic;
import cn.oasys.pojo.user.User;
import cn.oasys.pojo.user.UserAub;
import cn.oasys.service.attch.AttachmentService;
import cn.oasys.service.attends.AttendsListService;
import cn.oasys.service.bursement.BurSeMeNtService;
import cn.oasys.service.dept.DeptService;
import cn.oasys.service.details.DetailsBurseService;
import cn.oasys.service.evection.EveCtIonMoneyService;
import cn.oasys.service.evection.EvectionService;
import cn.oasys.service.holiday.HolidayService;
import cn.oasys.service.overtime.OvertimeService;
import cn.oasys.service.position.PositionService;
import cn.oasys.service.process.ProcessListService;
import cn.oasys.service.regular.RegularService;
import cn.oasys.service.resign.ResignService;
import cn.oasys.service.reviewed.ReviewedService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.stay.StayService;
import cn.oasys.service.subject.SubjectService;
import cn.oasys.service.traffic.TrafficService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProcessController {

    @Autowired
    TypeListService typeListService;
    @Autowired
    StatusListService statusListService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    ProcessListService processListService;
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;
    @Autowired
    ReviewedService reviewedService;
    @Autowired
    BurSeMeNtService burSeMeNtService;
    @Autowired
    EveCtIonMoneyService eveCtIonMoneyService;
    @Autowired
    DetailsBurseService detailsBurseService;
    @Autowired
    StayService stayService;
    @Autowired
    TrafficService trafficService;
    @Autowired
    OvertimeService overtimeService;
    @Autowired
    HolidayService holidayService;
    @Autowired
    RegularService regularService;
    @Autowired
    ResignService resignService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttendsListService attendsListService;
    @Autowired
    EvectionService evectionService;


    @Value("${file.root.path}")
    private String rootPath;

    /**
     * 查找出自己的申请
     * @return
     */
    @RequestMapping("flowmanage")
    public String flowManage(@SessionAttribute("user") User user,Model model,
                             @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size){
        PageInfo<ProcessList> pageInfo=processListService.findByUserId(user.getUserId(),page,size);
        List<ProcessList> prolist=pageInfo.getList();

        Iterable<StatusList>  statusname=statusListService.findByStatusModel("aoa_process_list");
        Iterable<TypeList> typename=typeListService.findByTypeModel("aoa_process_list");
        model.addAttribute("typename", typename);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("prolist", prolist);
        model.addAttribute("statusname", statusname);
        model.addAttribute("url", "shenser");
        return "process/flowmanage";
    }

    /**
     * 流程审核
     * @return
     */
    @RequestMapping("audit")
    public String auding(@SessionAttribute("user") User user,Model model,
                         @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size){
        user=userService.selectOne(user);
        PageInfo<UserAub> pageInfo=index(user, page, size,null,model);
        System.out.println(pageInfo);
        List<Map<String, Object>> mapList=index2(pageInfo,user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("prolist", mapList);
        model.addAttribute("url", "serch");

        return "process/auditing";
    }

    /**
     * 流程审核的条件查询
     * @param user
     * @param model
     * @param req
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("serch")
    public String serch(@SessionAttribute("user") User user,Model model,HttpServletRequest req,
                        @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size){
        user=userService.selectOne(user);
        String val=null;
        if(!StringUtil.isEmpty(req.getParameter("val"))){
            val=req.getParameter("val");
        }
        PageInfo<UserAub> pageInfo=index(user, page, size,val,model);
        List<Map<String, Object>> mapList=index2(pageInfo,user);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("prolist", mapList);
        model.addAttribute("url", "serch");

        return "process/audtable";
    }

    /**
     * 进入审核页面
     * @return
     */
    @RequestMapping("auditing")
    public String auditing(@SessionAttribute("user") User user,Model model,HttpServletRequest req,
                           @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size){

        //流程id
        Long id=Long.parseLong(req.getParameter("id"));
        ProcessList process=processListService.selectId(id);

        Reviewed re=reviewedService.findByProIdAndUserId(process.getProcessId(), user.getUserId());//查找审核表

        String typename=process.getTypeName().trim();
        if(("费用报销").equals(typename)){
            BurSeMeNt bu=burSeMeNtService.findByProId(process.getProcessId());
            model.addAttribute("bu", bu);

        }else if(("出差费用").equals(typename)){
            EveCtIonMoney eMoney=eveCtIonMoneyService.findByProId(process.getProcessId());
            model.addAttribute("bu", eMoney);
        }else if(("转正申请").equals(typename)||("离职申请").equals(typename)){
            User zhuan=userService.select(process.getProcessUserId().getUserId());
            model.addAttribute("position", zhuan);
        }
        userService.user(model);
        List<Map<String, Object>> list=index4(process);

        model.addAttribute("statusid", process.getStatusId());
        model.addAttribute("process", process);
        model.addAttribute("revie", list);
        model.addAttribute("size", list.size());
        model.addAttribute("statusid", process.getStatusId());
        model.addAttribute("ustatusid", re.getStatusId());
        model.addAttribute("positionid",user.getPositionId().getPositionId());
        model.addAttribute("typename", typename);

        return "process/audetail";

    }

    /**
     * 审核确定的页面
     * @return
     */
    @RequestMapping("susave")
    public String save(@SessionAttribute("user") User userId,Model model,HttpServletRequest req,Reviewed reviewed){
        User u=userService.selectReviewed(userId.getUserId());
        String name=null;
        String typename=req.getParameter("type");
        Long proid=Long.parseLong(req.getParameter("proId"));

        ProcessList pro=processListService.selectThisApplication(proid);//找到该条流程

        User shen=userService.selectReviewed(pro.getProcessUserId().getUserId());//申请人
        if(!StringUtil.isEmpty(req.getParameter("liuzhuan"))){
            name=req.getParameter("liuzhuan");
        }
        if(!StringUtil.isEmpty(name)){
            //审核并流转
            User u2=userService.certificationAudit(reviewed.getUsername());//找到下一个审核人
            if(("离职申请").equals(typename)){
                if(u.getUserId().equals(pro.getProcessUserId().getFatherId())){
                    if(u2.getPositionId().getPositionId().equals(5L)){
                        insertProcess(proid, u, reviewed, pro, u2);
                    }else{
                        model.addAttribute("error", "请选财务经理。");
                        return "common/proce";
                    }
                }else{
                    if(u2.getPositionId().getPositionId().equals(7L)){
                        insertProcess(proid, u, reviewed, pro, u2);
                    }else{
                        model.addAttribute("error", "请选人事经理。");
                        return "common/proce";
                    }
                }

            }else if(("费用报销").equals(typename)||("出差费用").equals(typename)){

                if(u2.getPositionId().getPositionId().equals(5L)){
                    insertProcess(proid, u, reviewed, pro, u2);
                }else{
                    model.addAttribute("error", "请选财务经理。");
                    return "common/proce";
                }
            }else{
                if(u2.getPositionId().getPositionId().equals(7L)){
                    insertProcess(proid, u, reviewed, pro, u2);
                }else{
                    model.addAttribute("error", "请选人事经理。");
                    return "common/proce";
                }
            }

        }else{
            //审核并结案
            Reviewed re=reviewedService.findByProIdAndUserId(proid,u.getUserId());
            re.setAdvice(reviewed.getAdvice());
            re.setStatusId(reviewed.getStatusId());
            re.setReviewedTime(new Date());
            reviewedService.insertReviewed(re);
            pro.setStatusId(reviewed.getStatusId());//改变主表的状态
            processListService.insertProcess(pro);
            if(("请假申请").equals(typename)||("出差申请").equals(typename)){
                if(reviewed.getStatusId()==25){
                    AttendsList attend=new AttendsList();
                    attend.setHolidayDays(pro.getProcseeDays());
                    attend.setHolidayStart(pro.getStartTime());
                    attend.setAttendsUserId(pro.getProcessUserId());
                    if(("请假申请").equals(typename)){
                        attend.setStatusId(46L);
                    }else if(("出差申请").equals(typename)){
                        attend.setStatusId(47L);
                    }
                    attendsListService.insertSign(attend);
                }
            }


        }


        if(("费用报销").equals(typename)){
            BurSeMeNt  bu=burSeMeNtService.findByProId(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                bu.setManagerAdvice(reviewed.getAdvice());
                burSeMeNtService.insertBurSeMeNt(bu);
            }
            if(u.getPositionId().getPositionId()==5){
                bu.setFinancialAdvice(reviewed.getAdvice());
                bu.setBurseTime(new Date());
                bu.setOperationName(u);
                burSeMeNtService.insertBurSeMeNt(bu);
            }
        }else if(("出差费用").equals(typename)){
            EveCtIonMoney emoney=eveCtIonMoneyService.findByProId(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                emoney.setManagerAdvice(reviewed.getAdvice());
                eveCtIonMoneyService.insertEveCtIonMoney(emoney);
            }
            if(u.getPositionId().getPositionId()==5){
                emoney.setFinancialAdvice(reviewed.getAdvice());
                eveCtIonMoneyService.insertEveCtIonMoney(emoney);
            }
        }else if(("出差申请").equals(typename)){
            Evection ev=evectionService.findByProId(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                ev.setManagerAdvice(reviewed.getAdvice());
                evectionService.insertEvection(ev);
            }
            if(u.getPositionId().getPositionId().equals(7L)){
                ev.setPersonnelAdvice(reviewed.getAdvice());
                evectionService.insertEvection(ev);
            }
        }else if(("加班申请").equals(typename)){
            Overtime over=overtimeService.selectOvertime(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                over.setManagerAdvice(reviewed.getAdvice());
                overtimeService.insertOvertime(over);
            }
            if(u.getPositionId().getPositionId().equals(7L)){
                over.setPersonnelAdvice(reviewed.getAdvice());
                overtimeService.insertOvertime(over);
            }
        }else if(("请假申请").equals(typename)){
            Holiday over=holidayService.selectHoliday(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                over.setManagerAdvice(reviewed.getAdvice());
                holidayService.insertholiday(over);
            }
            if(u.getPositionId().getPositionId().equals(7L)){
                over.setPersonnelAdvice(reviewed.getAdvice());
                holidayService.insertholiday(over);
            }
        }else if(("转正申请").equals(typename)){
            Regular over=regularService.selectRegular(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                over.setManagerAdvice(reviewed.getAdvice());
                regularService.insertRegular(over);
            }
            if(u.getPositionId().getPositionId().equals(7L)){
                over.setPersonnelAdvice(reviewed.getAdvice());
                regularService.insertRegular(over);
            }
        }else if(("离职申请").equals(typename)){

            Resign over=resignService.selectResignation(pro.getProcessId());
            if(shen.getFatherId().equals(u.getUserId())){
                over.setManagerAdvice(reviewed.getAdvice());
                resignService.insertResign(over);
            }
            if(u.getPositionId().getPositionId()==5){
                over.setPersonnelAdvice(reviewed.getAdvice());
                resignService.insertResign(over);
            }else if(u.getPositionId().getPositionId().equals(7L)){
                over.setFinancialAdvice(reviewed.getAdvice());
                resignService.insertResign(over);
            }
        }
        return "redirect:/audit";

    }

    /**
     * 存主表
     */
    public void insertProcess(Long proid,User u,Reviewed reviewed,ProcessList pro,User u2){
        Reviewed re=reviewedService.findByProIdAndUserId(proid,u.getUserId());
        re.setAdvice(reviewed.getAdvice());
        re.setStatusId(reviewed.getStatusId());
        re.setReviewedTime(new Date());
        re.setStatusId(reviewed.getStatusId());
        reviewedService.insertReviewed(re);


        Reviewed re2=new  Reviewed();
        re2.setProId(pro);
        re2.setUserId(u2);
        re2.setStatusId(23L);
        reviewedService.insertReviewed(re2);

        pro.getShenuser();
        pro.setShenuser(pro.getShenuser()+";"+u2.getUserName());
        pro.setStatusId(24L);//改变主表的状态
        processListService.insertProcess(pro);
    }


    /**
     * 查看详细
     * @return
     */
    @RequestMapping("particular")
    public String particular(@SessionAttribute("user") User user,Model model,HttpServletRequest req){
        user=userService.selectOne(user);//审核人或者申请人
        User audit=null;//最终审核人
        String id=req.getParameter("id");
        Long proid=Long.parseLong(id);
        String typename=req.getParameter("typename");//类型名称
        String name=null;

        Map<String, Object> map=new HashMap<>();
        ProcessList process=processListService.selectThisApplication(proid);//查看该条申请
        Boolean flag=process.getProcessUserId().getUserId().equals(user.getUserId());//判断是申请人还是审核人

        if(!flag){
            name="审核";
        }else{
            name="申请";
        }
        map=index3(name,user,typename,process);
        if(("费用报销").equals(typename)){
            BurSeMeNt bu=burSeMeNtService.selectReimbursement(process.getProcessId());
            System.out.println(bu.toString());
            System.out.println(bu.getBursementId());
            User prove=userService.select(bu.getUserId().getUserId());//证明人
            if(!Objects.isNull(bu.getOperationName())){
                audit=userService.select(bu.getOperationName().getUserId());//最终审核人
            }
            List<DetailsBurse> bursList=detailsBurseService.selectBurs(bu.getBursementId());
            String type=typeListService.findName(bu.getTypeId());
            String money=processListService.numberTo(bu.getAllMoney());
            model.addAttribute("prove", prove);
            model.addAttribute("audit", audit);
            model.addAttribute("type", type);
            model.addAttribute("bu", bu);
            model.addAttribute("money", money);
            model.addAttribute("detaillist", bursList);
            model.addAttribute("map", map);
            return "process/serch";
        }else if(("出差费用").equals(typename)){
            Double	staymoney=0.0;
            Double	tramoney=0.0;
            EveCtIonMoney eMoney=eveCtIonMoneyService.findByProId(process.getProcessId());
            String money=processListService.numberTo(eMoney.getMoney());
            List<Stay> stayList=stayService.selectStay();
            System.out.println(stayList.toString());
            for (Stay stay : stayList) {
                staymoney += stay.getStayMoney();
            }
            System.out.println("测试："+eMoney.getTraffic());
            List<Traffic> trafficList=trafficService.selectTraffic();
            for (Traffic traffic : trafficList) {
                tramoney+=traffic.getTrafficMoney();
            }
            model.addAttribute("staymoney", staymoney);
            model.addAttribute("tramoney", tramoney);
            model.addAttribute("allmoney", money);
            model.addAttribute("emoney", eMoney);
            model.addAttribute("staylist", stayList);
            model.addAttribute("tralist", trafficList);
            model.addAttribute("map", map);
            return "process/evemonserch";
        }else if(("出差申请").equals(typename)){
            EveCtIonMoney eve=eveCtIonMoneyService.findByProId(process.getProcessId());
            model.addAttribute("eve", eve);
            model.addAttribute("map", map);
            return "process/eveserach";
        }else if(("加班申请").equals(typename)){
            Overtime eve=overtimeService.selectOvertime(process.getProcessId());
            String type=typeListService.findName(eve.getTypeId());
            model.addAttribute("eve", eve);
            model.addAttribute("map", map);
            model.addAttribute("type", type);
            return "process/overserch";
        }else if(("请假申请").equals(typename)){
            Holiday eve=holidayService.selectHoliday(process.getProcessId());
            String type=typeListService.findName(eve.getTypeId());
            model.addAttribute("eve", eve);
            model.addAttribute("map", map);
            model.addAttribute("type", type);
            return "process/holiserch";
        }else if(("转正申请").equals(typename)){
            Regular eve=regularService.selectRegular(process.getProcessId());
            model.addAttribute("eve", eve);
            model.addAttribute("map", map);
            return "process/reguserch";
        }else if(("离职申请").equals(typename)){
            Resign eve=resignService.selectResignation(process.getProcessId());
            model.addAttribute("eve", eve);
            model.addAttribute("map", map);
            return "process/resserch";
        }
        return "process/serch";
    }


    /**
     * process数据封装
     */
    public Map<String,Object> index3(String name,User user,String typename,ProcessList process){
        System.out.println(name);
        Map<String,Object> result=new HashMap<>();
        TypeList typeList = typeListService.selectByPrimaryKey(process.getDeeplyId());
        result.put("proId", process.getProcessId());
        result.put("harryname", typeList.getTypeName());
        result.put("processName", process.getProcessName());
        result.put("processDescribe",process.getProcessDes());
        if(("审核").equals(name)){
            result.put("username", process.getProcessUserId().getUserName());//提单人员
            result.put("deptname", deptService.selectByPrimaryKey(process.getProcessUserId().getDeptId().getDeptId()));//部门
        }else if(("申请").equals(name)){
            result.put("username", user.getUserName());
            result.put("deptname", deptService.selectByPrimaryKey(process.getProcessUserId().getDeptId().getDeptId()));
        }
        result.put("applytime", process.getApplyTime());
        if(!Objects.isNull(process.getProFileid())){
            result.put("file", process.getProFileid());
        }else{
            result.put("file", "file");
        }
        result.put("name", name);
        result.put("typename", process.getTypeName());
        result.put("startime", process.getStartTime());
        result.put("endtime", process.getEndTime());
        result.put("tianshu", process.getProcseeDays());
        result.put("statusid", process.getStatusId());
        if( process.getProFileid()!=null){
            result.put("filepath", process.getProFileid().getAttachmentPath());
            if(process.getProFileid().getAttachmentType().startsWith("image")){
                result.put("filetype", "img");
            }else{
                result.put("filetype", "appli");
            }
        }
        return result;
    }

    /**
     * 审核人封装
     */
    private List<Map<String,Object>> index4(ProcessList process){
        List<Map<String,Object>> mapList=new ArrayList<>();
        List<Reviewed> revie=reviewedService.findByReviewedTimeNotNullAndProId(process.getProcessId());
        for (int i = 0; i <revie.size(); i++) {
            Map<String, Object> result=new HashMap<>();
            User u=userService.selectReviewed (revie.get(i).getUserId().getUserId());
            Position po=positionService.select(u.getPositionId().getPositionId());
            StatusList status=statusListService.select(revie.get(i).getStatusId());
            result.put("poname", po.getName());
            result.put("username", u.getUserName());
            result.put("retime",revie.get(i).getReviewedTime());
            result.put("restatus",status.getStatusName());
            result.put("statuscolor",status.getStatusColor());
            result.put("des", revie.get(i).getAdvice());
            result.put("img",u.getImgPath());
            result.put("positionid",u.getPositionId().getPositionId());
            mapList.add(result);
        }
        return mapList;
    }

    public List<Map<String,Object>> index2(PageInfo<UserAub> page,User user){
        List<Map<String, Object>> list = new ArrayList<>();
        List<UserAub> prolist=page.getList();
        for (int i = 0; i < prolist.size(); i++) {
            String harryName=typeListService.findName(prolist.get(i).getDeeplyId());
            StatusList status=statusListService.selectO(prolist.get(i).getStatusId());
            Map<String, Object> result=new HashMap<>();
            result.put("typename", prolist.get(i).getTypeName());
            result.put("title", prolist.get(i).getProcessName());
            result.put("pushuser", prolist.get(i).getUserName());
            result.put("applytime",  prolist.get(i).getApplyTime());
            result.put("harry", harryName);
            result.put("statusname", status.getStatusName());
            result.put("statuscolor", status.getStatusColor());
            result.put("proid", prolist.get(i).getProcessId());
            list.add(result);

        }
        return list;
    }

    public PageInfo<UserAub> index(User user,int page,int size,String val,Model model){
//        Pageable pa= new PageRequest(page, size);
        PageInfo<UserAub> pageInfo=null;
        PageInfo<UserAub> pageInfo1=null;
        List<Sort.Order> orders = new ArrayList<>();
        User  u=userService.certificationAudit(val);//找用户
        StatusList status=statusListService.selectStatusId("aoa_process_list", val);
        if(StringUtil.isEmpty(val)){
            orders.add(new Sort.Order(Sort.Direction.DESC, "applyTime"));
            Sort sort = new Sort(orders);
//            pa=new PageRequest(page, size,sort);
            pageInfo=reviewedService.findByUserIdOrderByStatusId(user.getUserId(),page, size);
            System.out.println(pageInfo);
        }
        else if(!Objects.isNull(u)){
            pageInfo=reviewedService.findProcessList(user.getUserId(),u.getUserName(),page, size);
            model.addAttribute("sort", "&val="+val);
        }
        else if(!Objects.isNull(status)){
            System.out.println(status);
            pageInfo=reviewedService.findByStatusProcessList(user.getUserId(),status.getStatusId(),page, size);
            model.addAttribute("sort", "&val="+val);
        }else{
            pageInfo1=reviewedService.findByTypeNameProcessList(user.getUserId(), val,page, size);
            if(pageInfo1.getList()==null){
                pageInfo1=reviewedService.findByProcessNameProcessList(user.getUserId(), val,page, size);
            }
            model.addAttribute("sort", "&val="+val);
            return pageInfo1;
        }
        return pageInfo;
    }

    /**
     * 新建流程
     * @return
     */
    @RequestMapping("newProcess")
    public String newProcess(){
        return "process/procedure";
    }


    /**
     * 公用
     */
    public void  index6(Model model, Long id){
        User lu=userService.selectByPrimaryKey(id);//申请人
        List<TypeList> harrylist=typeListService.findByTypeModel("aoa_process_list");
        //查看用户
        List<User> userList = userService.selectAllUser();
        System.out.println(userList.toString());
        // 查询部门表
        Iterable<Dept> deptList = deptService.selectAll();
        // 查职位表
        Iterable<Position> posList =positionService.selectAll();
        System.out.println(posList.toString());
        model.addAttribute("emplist", userList);
        model.addAttribute("deptlist", deptList);
        model.addAttribute("poslist", posList);
        model.addAttribute("url", "names");
        model.addAttribute("username", lu.getUserName());
        model.addAttribute("harrylist", harrylist);
    }

    /**
     * 费用报销表单
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("burse")
    public String bursement(Model model, @SessionAttribute("userId") Long userId){
        //查找类型
        List<TypeList> upList=typeListService.findByTypeModel("aoa_bursement");
        //查找费用科目生成树
        List<Subject> second=subjectService.findParentId(1L);
        List<Subject> sublist=subjectService.findNotParentId(1L);
        index6(model,userId);
        model.addAttribute("second", second);
        model.addAttribute("sublist", sublist);
        model.addAttribute("uplist", upList);
        return "process/bursement";
    }

    /**
     * 费用表单接收
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("apply")
    public String apply(@RequestParam("filePath") MultipartFile filePath, HttpServletRequest req, @Valid BurSeMeNt bu, BindingResult br,
                        @SessionAttribute("user") User user) throws IllegalStateException, IOException{
        User lu=userService.selectReviewed(user.getUserId());//申请人
        System.out.println("申请人："+lu);
        User reUser=userService.certificationAudit(bu.getUsername());//审核人
        User zhuTi=userService.certificationAudit(bu.getNamemoney());//证明人
        Integer allInvoice=0;
        Double allMoney=0.0;
        Long roleId=lu.getRoleId().getRoleId();//申请人角色id
        Long fatherId=lu.getFatherId();//申请人父id
        Long userId=reUser.getUserId();//审核人userid

        String val=req.getParameter("val");

        if(roleId>=3L && Objects.equals(fatherId, userId)){


//            List<DetailsBurse> mm=bu.getDetails();
//            for (DetailsBurse detailsBurse : mm) {
//                allinvoice+=detailsBurse.getInvoices();
//                allmoney+=detailsBurse.getDetailmoney();
//                detailsBurse.setBurs(bu);
//            }
            //在报销费用表里面set票据总数和总金额
            bu.setAllinvoices(allInvoice);
            bu.setAllMoney(allMoney);
            bu.setUserId(zhuTi);
            //set主表
            ProcessList pro=bu.getProId();
            index5(pro, val, lu, filePath,reUser.getUserName());
//            budao.save(bu);

            //存审核表
            index7(reUser, pro);
        }else{
            return "common/proce";
        }
        return "redirect:/newProcess";
    }

    /**
     * 存审核表
     * @param reuser
     * @param pro
     */
    public void index7(User reuser,ProcessList pro){
        Reviewed reviewed=new Reviewed();
        reviewed.setUserId(reuser);
        reviewed.setStatusId(23L);
        reviewed.setProId(pro);
        reviewedService.insert(reviewed);
    }

    /**
     * 存表
     * @param pro
     * @param val
     * @param lu
     * @param filePath
     * @param userName
     * @throws IOException
     */
    private void index5(ProcessList pro, String val, User lu, MultipartFile filePath, String userName) throws IOException {
        pro.setTypeName(val);
        pro.setApplyTime(new Date());
        pro.setProcessUserId(lu);
        pro.setStatusId(23L);
        pro.setShenuser(userName);
        Attachment attaId=null;
        if(!StringUtil.isEmpty(filePath.getOriginalFilename())){
            attaId=upload(filePath, lu);
            attaId.setModel("aoa_bursement");
            attachmentService.insert(attaId);
            pro.setProFileid(attaId);
        }
    }

    /**
     * 上传附件
     * @throws IOException
     * @throws IllegalStateException
     */
    public Attachment upload(MultipartFile file,User mu) throws IOException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM");
        File root = new File(rootPath,simpleDateFormat.format(new Date()));
        File savePath = new File(root,mu.getUserName());

        if (!savePath.exists()) {
            savePath.mkdirs();
        }
        String fileName=file.getOriginalFilename();
        if(!StringUtil.isEmpty(fileName)){
            String suffix= FilenameUtils.getExtension(fileName);
            String newFileName = UUID.randomUUID().toString().toLowerCase()+"."+suffix;
            File targetFile = new File(savePath,newFileName);
            file.transferTo(targetFile);

            Attachment attachment=new Attachment();
            attachment.setAttachmentName(file.getOriginalFilename());
            attachment.setAttachmentPath(targetFile.getAbsolutePath().replace("\\", "/").replace(rootPath, ""));
            attachment.setAttachmentShuffix(suffix);
            attachment.setAttachmentSize(file.getSize());
            attachment.setAttachmentType(file.getContentType());
            attachment.setUploadTime(new Date());
            attachment.setUserId(mu.getUserId()+"");

            return attachment;
        }
        return null;
    }

    /**
     * 出差申请单
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("evection")
    public String evection(Model model, @SessionAttribute("userId") Long userId){
        //查找类型
        List<TypeList> outType=typeListService.findByTypeModel("aoa_evection");
        index6(model, userId);
        model.addAttribute("outtype", outType);
        return "process/evection";
    }

    /**
     * 加班申请单
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("overtime")
    public String overtime(Model model, @SessionAttribute("userId") Long userId){
        //查找类型
        List<TypeList> overType=typeListService.findByTypeModel("aoa_overtime");
        index6(model, userId);
        model.addAttribute("overtype", overType);
        return "process/overtime";
    }

    /**
     * 转正申请
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("regular")
    public String regular(Model model, @SessionAttribute("userId") Long userId){
        index6(model, userId);
        return "process/regular";
    }

    /**
     *请假申请
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("holiday")
    public String holiday(Model model, @SessionAttribute("userId") Long userId){
        //查找类型
        List<TypeList> overType=typeListService.findByTypeModel("aoa_holiday");
        index6(model, userId);
        model.addAttribute("overtype", overType);
        return "process/holiday";
    }

    /**
     * 离职申请
     * @param model
     * @param userId
     * @return
     */
    @RequestMapping("resign")
    public String resign(Model model, @SessionAttribute("userId") Long userId){
        index6(model, userId);
        return "process/resign";
    }

    //出差费用
    @RequestMapping("evemoney")
    public String evemoney(Model model, @SessionAttribute("userId") Long userId,HttpServletRequest req){
        Long proid=Long.parseLong(req.getParameter("id"));//出差申请的id
        ProcessList proList=processListService.byUserIdAndTitle(userId, proid);//找这个用户的出差申请
        index6(model, userId);
        model.addAttribute("prolist", proList);
        return "process/evectionmoney";
    }
}
