package cn.oasys.controller;

import cn.oasys.config.formValid.BindingResultVOUtil;
import cn.oasys.config.formValid.MapToList;
import cn.oasys.config.formValid.ResultEnum;
import cn.oasys.config.formValid.ResultVO;
import cn.oasys.pojo.StatusList;
import cn.oasys.pojo.TypeList;
import cn.oasys.pojo.attch.Attachment;
import cn.oasys.pojo.mail.Inmaillist;
import cn.oasys.pojo.mail.MailNumber;
import cn.oasys.pojo.mail.MailReciver;
import cn.oasys.pojo.user.User;
import cn.oasys.service.attch.AttachmentService;
import cn.oasys.service.mail.InmaillistService;
import cn.oasys.service.mail.MailNumberService;
import cn.oasys.service.mail.MailReciverService;
import cn.oasys.service.mail.MailService;
import cn.oasys.service.process.ProcessListService;
import cn.oasys.service.status.StatusListService;
import cn.oasys.service.type.TypeListService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.util.*;

@Controller
public class MailController {
    @Autowired
    UserService userService;
    @Autowired
    MailService mailService;
    @Autowired
    MailNumberService mailNumberService;
    @Autowired
    MailReciverService mailReciverService;
    @Autowired
    InmaillistService inmaillistService;
    @Autowired
    StatusListService statusListService;
    @Autowired
    TypeListService typeListService;
    @Autowired
    ProcessListService processListService;
    @Autowired
    AttachmentService attachmentService;

    /**
     * 账号管理
     */
    @RequestMapping("accountmanage")
    public String account(@SessionAttribute("user") User user, Model model,
                          @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size){
        // 通过邮箱建立用户id找用户对象
//        user = userService.select(user.getUserId());

        PageInfo<MailNumber> pageInfo=mailNumberService.index(page, size, user, null,model);
        List<Map<String, Object>> list=up(pageInfo);
        model.addAttribute("account", list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "mailpaixu");
        return "mail/mailmanage";
    }

    /**
     * 账号各种排序
     * 和查询
     */
    @RequestMapping("mailpaixu")
    public String paixu(HttpServletRequest request, @SessionAttribute("user") User user, Model model,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size){
        // 通过发布人id找用户
//        User tu = udao.findOne(userId);
        //得到传过来的值
        String val =null;
        if(!StringUtil.isEmpty(request.getParameter("val"))){

            val = request.getParameter("val");
        }
        PageInfo<MailNumber> pageInfo=mailNumberService.index(page, size, user, val,model);
        List<Map<String, Object>> list=up(pageInfo);
        model.addAttribute("account", list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "mailpaixu");

        return "mail/mailtable";
    }


    private List<Map<String, Object>> up(PageInfo<MailNumber> pageInfo) {
        List<MailNumber> account=pageInfo.getList();
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < account.size(); i++) {
            Map<String, Object> result=new HashMap<>();
            StatusList status=statusListService.selectO(account.get(i).getStatus());
            result.put("accountid", account.get(i).getMailNumberId());
            result.put("typename", typeListService.select(account.get(i).getMailType()));
            result.put("statusname", status.getStatusName());
            result.put("statuscolor", status.getStatusColor());
            result.put("accountname", account.get(i).getMailUserName());
            result.put("creattime", account.get(i).getMailCreateTime());
            list.add(result);
        }
        return list;
    }
    /**
     * 新增账号
     * 修改账号
     */
    @RequestMapping("addaccount")
    public String add(@SessionAttribute("user") User user, Model model,HttpServletRequest req){
        // 通过用户id找用户
//        User tu = udao.findOne(user);

        MailNumber mailn=null;
        if(StringUtil.isEmpty(req.getParameter("id"))){
            List<TypeList> typelist=typeListService.findByTypeModel("aoa_mailnumber");
            List<StatusList> statuslist=statusListService.findByStatusModel("aoa_mailnumber");
            model.addAttribute("typelist", typelist);
            model.addAttribute("statuslist", statuslist);

            if(!StringUtil.isEmpty((String) req.getAttribute("errormess"))){
                mailn=(MailNumber) req.getAttribute("mail");
                req.setAttribute("errormess",req.getAttribute("errormess"));
                model.addAttribute("mails", mailn);
                model.addAttribute("type", typeListService.select(mailn.getMailType()));
                model.addAttribute("status", statusListService.selectO(mailn.getStatus()));

            }else if(!StringUtil.isEmpty((String) req.getAttribute("success"))){
                mailn=(MailNumber) req.getAttribute("mail");
                req.setAttribute("success","fds");
                model.addAttribute("mails", mailn);
                model.addAttribute("type", typeListService.select(mailn.getMailType()));
                model.addAttribute("status", statusListService.selectO(mailn.getStatus()));
            }
        }else{

            Long id=Long.parseLong(req.getParameter("id"));
            MailNumber mailNumber=mailNumberService.selectAccountInformation(id);
            System.out.println("测试："+mailNumber);
            model.addAttribute("type", typeListService.select(mailNumber.getMailType()));
            model.addAttribute("status", statusListService.selectO(mailNumber.getStatus()));
            model.addAttribute("mails", mailNumber);


        }
        model.addAttribute("username", user.getUserName());
        return "mail/addaccounts";
    }

    /**
     * 存邮箱账号
     */
    @RequestMapping("saveaccount")
    public String save(HttpServletRequest request, @Valid MailNumber mail, BindingResult br, @SessionAttribute("user") User user){
//        User user=udao.findOne(user);
        request.setAttribute("mail", mail);
        ResultVO res = BindingResultVOUtil.hasErrors(br);
        if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
            List<Object> list = new MapToList<>().mapToList(res.getData());
            request.setAttribute("errormess", list.get(0).toString());

            System.out.println("list错误的实体类信息：" + mail);
            System.out.println("list错误详情:" + list);
            System.out.println("list错误第一条:" + list.get(0));
            System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());

        }else{
            if(Objects.isNull(mail.getMailNumberId())){
                mail.setMailNumUserId(user);
                mail.setMailCreateTime(new Date());
                mailNumberService.insertMailNumber(mail);
            }else{
                MailNumber mails=mailNumberService.selectByPrimaryKey(mail.getMailNumberId());
                mails.setMailType(mail.getMailType());
                mails.setStatus(mail.getStatus());
                mails.setMailDes(mail.getMailDes());
                mails.setMailAccount(mail.getMailAccount());
                mails.setPassword(mail.getPassword());
                mails.setMailUserName(mail.getMailUserName());
                mailNumberService.update(mails);
            }
            request.setAttribute("success", "执行成功！");

        }

        return "forward:/addaccount";
    }

    /**
     * 删除账号
     */
    @RequestMapping("dele")
    public String edit(HttpServletRequest request,@SessionAttribute("user") User user){
        //得到账号id
        Long accountId=Long.parseLong(request.getParameter("id"));
        MailNumber mail=mailNumberService.selectAccountInformation(accountId);
        if(mail.getMailNumUserId().getUserId().equals(user.getUserId())){
            mailNumberService.deleteById(accountId);
        }else{
            return "redirect:/notlimit";
        }
        System.out.println("ffffffff");
        return "redirect:/accountmanage";
    }

    /**
     * 邮件管理的分页查找
     * @param page
     * @param size
     * @param user
     * @param val
     * @param title
     * @return
     */
    public PageInfo<MailReciver> recive(int page, int size, User user, String val, String title) {
        PageInfo<MailReciver>pageInfo=null;
        List<Sort.Order> orders = new ArrayList<>();
        StatusList status=statusListService.selectStatusId("aoa_in_mail_list", val);
        TypeList type=typeListService.findByTypeModelAndTypeName("aoa_in_mail_list", val);
        if(("收件箱").equals(title)){
            if(StringUtil.isEmpty(val)){
                orders.add(new Sort.Order(Sort.Direction.ASC, "read"));
                Sort sort = new Sort(orders);
                pageInfo=mailReciverService.findMail(page,size,user.getUserId(),0);
                System.out.println("测试分页："+pageInfo);
            }else if(!Objects.isNull(status)){
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMailByStatus(user.getUserId(),status.getStatusId(),0));
            }else if(!Objects.isNull(type)){
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMailByType(user.getUserId(),type.getTypeId(),0));
            }else{
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMails(user.getUserId(),0, val));
            }
        }else{
            if(StringUtil.isEmpty(val)){
                orders.add(new Sort.Order(Sort.Direction.ASC, "read"));
                Sort sort = new Sort(orders);
                pageInfo=mailReciverService.findMail(page,size,user.getUserId(),1);
            }else if(!Objects.isNull(status)){
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMailByStatus(user.getUserId(),status.getStatusId(),1));
            }else if(!Objects.isNull(type)){
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMailByType(user.getUserId(),type.getTypeId(),0));
            }else{
                pageInfo=new PageInfo<MailReciver>(mailReciverService.findMails(user.getUserId(), 1,val));
            }
        }
        System.out.println(pageInfo);
        return pageInfo;
    }

    /**
     * 封装json
     */
    public List<Map<String,Object>> mail(PageInfo<MailReciver> mail){
        List<MailReciver> mailList=mail.getList();
        System.out.println("测试："+mailList);
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < mailList.size(); i++) {
            Map<String,Object> result=new HashMap<>();
            String typename=typeListService.findName(mailList.get(i).getMailId().getMailType());
            StatusList status=statusListService.selectO(mailList.get(i).getMailId().getMailStatusId());
            result.put("typename", typename);
            result.put("statusname", status.getStatusName());
            result.put("statuscolor", status.getStatusColor());
            result.put("star", mailList.get(i).getIsStar());
            result.put("read", mailList.get(i).getIsRead());
            result.put("time", mailList.get(i).getMailId().getMailCreateTime());
            result.put("reciver", mailList.get(i).getMailId().getInReceiver());
            result.put("title", mailList.get(i).getMailId().getMailTitle());
            result.put("mailid", mailList.get(i).getMailId().getMailId());
            result.put("fileid", mailList.get(i).getMailId().getMailFileId().getAttachmentId());
            list.add(result);

        }
        return list;
    }

    /**
     * 邮件管理主页
     * @return
     */
    @RequestMapping("mail")
    public  String index(@SessionAttribute("user") User user, Model model,
                         @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) {
        //查找未读邮件
        List<MailReciver> noReadList=mailReciverService.findByReadAndDelAndReciverId(1, 0, user.getUserId());
        //查找创建了但是却没有发送的邮件
        List<Inmaillist>  noPushList=inmaillistService.findByPushAndDelAndMailUserid(1,0, user.getUserId());
        //查找发件条数
        List<Inmaillist> pushList=inmaillistService.findByPushAndDelAndMailUserid(1,0, user.getUserId());
        //查找收件箱删除的邮件条数
        List<MailReciver> rubbish=mailReciverService.findByDelAndReciverId(0,user.getUserId());
        //分页及查找
        PageInfo<MailReciver> pageInfo=recive(page, size, user, "","收件箱");
        List<Map<String, Object>> mapList=mail(pageInfo);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("maillist",mapList);
        model.addAttribute("url","mailtitle");
        model.addAttribute("noread", noReadList.size());
        model.addAttribute("nopush", noPushList.size());
        model.addAttribute("push", pushList.size());
        model.addAttribute("rubbish", rubbish.size());
        model.addAttribute("mess", "收件箱");
        model.addAttribute("sort", "&title=收件箱");
        return "mail/mail";
    }

    /**
     * 写信
     */
    @RequestMapping("wmail")
    public  String index2(Model model, @SessionAttribute("user") User userId,HttpServletRequest request,
                          @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size) {

        User mu=userService.selectReviewed(userId.getUserId());
        //得到编辑过来的id
        String id=null;
        if(!StringUtil.isEmpty(request.getParameter("id"))){
            id=request.getParameter("id");
        }
        //回复那边过来的
        String huifu=null;

        if(!StringUtil.isEmpty(id)){
            Long lid=Long.parseLong(id);
            //找到这条邮件
            Inmaillist	mail=inmaillistService.selectInMail(lid);
            if(!StringUtil.isEmpty(request.getParameter("huifu"))){
                huifu=request.getParameter("huifu");
                model.addAttribute("title",huifu+mail.getMailTitle());
                model.addAttribute("content",mail.getMailContent());

            }else{
                model.addAttribute("title",mail.getMailTitle());
                model.addAttribute("content", mail.getMailContent());
            }
            model.addAttribute("status", statusListService.selectO(mail.getMailStatusId()));
            model.addAttribute("type", typeListService.select(mail.getMailType()));
            model.addAttribute("id", "回复");

        }else{

            List<TypeList> typelist=typeListService.findByTypeModel("aoa_in_mail_list");
            List<StatusList>  statuslist=statusListService.findByStatusModel("aoa_in_mail_list");
            model.addAttribute("typelist", typelist);
            model.addAttribute("statuslist", statuslist);
            model.addAttribute("id", "新发");

        }
        //查看该用户所创建的有效邮箱账号
        List<MailNumber> mailnum=mailNumberService.findByStatusAndMailUserId(1L, mu.getUserId());
        userService.user(model);
        model.addAttribute("mailnum", mailnum);

        return "mail/wirtemail";
    }

    /**
     * 发送邮件
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("pushmail")
    public String push(@RequestParam("file") MultipartFile file, HttpServletRequest request, @Valid Inmaillist mail, BindingResult br, @SessionAttribute("user") User userId) throws IllegalStateException, IOException{
        User tu=userService.selectReviewed(userId.getUserId());

        String name=null;
        Attachment attaid=null;
        MailNumber number=null;
        StringTokenizer st =null;
        ResultVO res = BindingResultVOUtil.hasErrors(br);
        if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
            List<Object> list = new MapToList<>().mapToList(res.getData());
            request.setAttribute("errormess", list.get(0).toString());
        }else{
            if(!StringUtil.isEmpty(request.getParameter("fasong"))){
                name=request.getParameter("fasong");
            }


            if(!StringUtil.isEmpty(name)){
                if(!StringUtil.isEmpty(file.getOriginalFilename())){
                    attaid=attachmentService.upload(file, tu);
                    attaid.setModel("mail");
                    attachmentService.insert(attaid);
                }
                //发送成功
                mail.setMailPush(true);

            }else{
                //存草稿
                mail.setInReceiver(null);
            }
            mail.setMailFileId(attaid);
            mail.setMailCreateTime(new Date());
            mail.setMailInPushUserId(tu);
            if(!mail.getInmail().equals(0)){
                number=mailNumberService.selectMailNumber(mail.getInmail());
                mail.setMailNumberid(number);
            }
            //存邮件
            inmaillistService.insertInMail(mail);
            Inmaillist imail = inmaillistService.selectMaxId();
            if(!StringUtil.isEmpty(name)){
                if(mailNumberService.isContainChinese(mail.getInReceiver())){
                    // 分割任务接收人
                    StringTokenizer st2 = new StringTokenizer(mail.getInReceiver(), ";");
                    while (st2.hasMoreElements()) {
                        User reciver = userService.certificationAudit(st2.nextToken());
                        MailReciver mreciver=new MailReciver();
                        mreciver.setMailId(imail);
                        mreciver.setMailReciverId(reciver);
                        mailReciverService.insertMailReciver(mreciver);
                    }
                }else{
                    if(mail.getInReceiver().contains(";")){
                        st = new StringTokenizer(mail.getInReceiver(), ";");
                    }else{
                        st = new StringTokenizer(mail.getInReceiver(), "；");
                    }

                    while (st.hasMoreElements()) {
                        if(!StringUtil.isEmpty(file.getOriginalFilename())){
                            mailService.pushmail(number.getMailAccount(), number.getPassword(), st.nextToken(), number.getMailUserName(), mail.getMailTitle(),
                                    mail.getMailContent(), attaid.getAttachmentPath(), attaid.getAttachmentName());

                        }else{
                            mailService.pushmail(number.getMailAccount(), number.getPassword(), st.nextToken(), number.getMailUserName(), mail.getMailTitle(),
                                    mail.getMailContent(), null, null);
                        }
                    }
                }

            }
        }

        return "redirect:/mail";
    }

    /**
     * 查看邮件
     */
    @RequestMapping("smail")
    public  String index4(HttpServletRequest req,@SessionAttribute("user") User userId,Model model) {
        User mu=userService.selectReviewed(userId.getUserId());
        //邮件id
        Long id=Long.parseLong(req.getParameter("id"));
        //title
        String title=req.getParameter("title");
        //找到中间表信息
        if(("收件箱").equals(title)||("垃圾箱").equals(title)){
            MailReciver	mailr=mailReciverService.selectByReciverIdAndMailId(mu.getUserId(),id);
            mailr.setIsRead(true);
            mailReciverService.update(mailr);
        }

        //找到该邮件信息
        Inmaillist mail=inmaillistService.selectInMail(id);
        System.out.println(mail);
        String filetype=null;
        if(!Objects.isNull(mail.getMailFileId())){
            String filepath= mail.getMailFileId().getAttachmentPath();
            System.out.println(filepath);
            if(mail.getMailFileId().getAttachmentType().startsWith("image")){

                filetype="img";
            }else{
                filetype="appli";

            }
            model.addAttribute("filepath", filepath);
            model.addAttribute("filetype", filetype);
        }

        User pushuser=userService.selectReviewed(mail.getMailInPushUserId().getUserId());
        model.addAttribute("pushname", pushuser.getUserName());
        model.addAttribute("mail", mail);
        model.addAttribute("mess", title);
        model.addAttribute("file", mail.getMailFileId());

        return "mail/seemail";
    }

//    /**
////     * 最近邮件
////     */
////    @RequestMapping("amail")
////    public  String index3(HttpServletRequest req,@SessionAttribute("user") User user,Model model,
////                          @RequestParam(value = "page", defaultValue = "0") int page,
////                          @RequestParam(value = "size", defaultValue = "10") int size) {
////        Pageable pa=new PageRequest(page, size);
//////        User mu=udao.findOne(userId);
////        String mess=req.getParameter("title");
////
////        Page<Pagemail> pagelist=null;
////        Page<Inmaillist> pagemail=null;
////        List<Map<String, Object>> maillist=null;
////        if(("收件箱").equals(mess)){
////            //分页及查找
////            pagelist=mservice.recive(page, size, mu, null,mess);
////            maillist=mservice.mail(pagelist);
////        }else if(("发件箱").equals(mess)){
////            pagemail=mservice.inmail(page, size, mu, null,mess);
////            maillist=mservice.maillist(pagemail);
////        }else if(("草稿箱").equals(mess)){
////            pagemail=mservice.inmail(page, size,mu, null,mess);
////            maillist=mservice.maillist(pagemail);
////        }else{
////            //垃圾箱
////            //分页及查找
////            pagelist=mservice.recive(page, size, mu, null,mess);
////            maillist=mservice.mail(pagelist);
////
////        }
////
////        if(!Objects.isNull(pagelist)){
////            model.addAttribute("page", pagelist);
////        }else{
////            model.addAttribute("page", pagemail);
////
////        }
////        model.addAttribute("sort", "&title="+mess);
////        model.addAttribute("maillist",maillist);
////        model.addAttribute("url","mailtitle");
////        model.addAttribute("mess",mess);
////        return "mail/allmail";
////    }

}
