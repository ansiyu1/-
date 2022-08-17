package cn.oasys.controller;

import cn.oasys.config.formValid.BindingResultVOUtil;
import cn.oasys.config.formValid.MapToList;
import cn.oasys.config.formValid.ResultEnum;
import cn.oasys.config.formValid.ResultVO;
import cn.oasys.pojo.mail.MailReciver;
import cn.oasys.pojo.notice.Notepaper;
import cn.oasys.pojo.notice.NoticeUserRelation;
import cn.oasys.pojo.user.User;
import cn.oasys.service.dept.DeptService;
import cn.oasys.service.mail.MailReciverService;
import cn.oasys.service.noice.NotepaperService;
import cn.oasys.service.noice.NoticeUserRelationService;
import cn.oasys.service.position.PositionService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    DeptService deptService;
    @Autowired
    PositionService positionService;
    @Autowired
    NoticeUserRelationService noticeUserRelationService;
    @Autowired
    MailReciverService mailReciverService;
    @Autowired
    NotepaperService notepaperService;

    @Value("${img.rootpath}")
    private String rootpath;

    public void UserpanelController(){
        try {
            rootpath= ResourceUtils.getURL("classpath:").getPath().replace("/target/classes/","/static/images/user");
            //System.out.println(rootpath);

        }catch (IOException e){
            System.out.println("获取项目路径异常");
        }
    }

    /**
     * 用户面板
     * @param user
     * @param model
     * @param req
     * @param page
     * @return
     */
    @RequestMapping("userpanel")
    public String index(@SessionAttribute("user") User user, Model model, HttpServletRequest req,
                        @RequestParam(name = "pageIndex", required = false, defaultValue = "1") Integer page){
        int size=10;

        //找到部门名称
        String deptName=deptService.deptName(user.getDeptId().getDeptId());

        //找到职位名称
        String positionName=positionService.positionName(user.getPositionId().getPositionId());

        //找未读通知消息
        List<NoticeUserRelation> noticeList=noticeUserRelationService.unread(0L, user.getUserId());
        System.out.println(noticeList.toString());

        //找未读邮件
        List<MailReciver> mailList=mailReciverService.readMail(user.getUserId());

        //找便签
        Example example = new Example(Notepaper.class);
        example.createCriteria().andEqualTo("notepaperUserId",user.getUserId()).andEqualTo("ORDER BY aoa_notepaper.create_time ASC");
        PageInfo<Notepaper> pageInfo = notepaperService.selectByExample(example, page, size);
        System.out.println(pageInfo.toString());
        List<Notepaper> notepaperlist=pageInfo.getList();
        User user1 = userService.selectReviewed(user.getUserId());
        System.out.println("图片路径："+user1.getImgPath());
        System.out.println(user1.getImgPath());
        model.addAttribute("user", user1);
        model.addAttribute("deptname", deptName);
        model.addAttribute("positionname", positionName);
        model.addAttribute("noticelist", noticeList.size());
        model.addAttribute("maillist", mailList.size());
        model.addAttribute("notepaperlist", notepaperlist);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url", "panel");
        System.out.println("aaaaa");

        return "user/userpanel";
    }

    @RequestMapping("panel")
    public String index(@SessionAttribute("userId") Long userId,Model model,
                        @RequestParam(value = "pageIndex", defaultValue = "1") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size){
        User user=userService.selectByPrimaryKey(userId);
        //找便签
        Example example = new Example(Notepaper.class);
        example.createCriteria().andEqualTo("notepaperUserId",userId).andEqualTo(" ORDER BY aoa_notepaper.create_time DESC");
        PageInfo<Notepaper> pageInfo = notepaperService.selectByExample(example, page, size);
        List<Notepaper> notepaperlist=pageInfo.getList();
        model.addAttribute("notepaperlist", notepaperlist);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "panel");
        return "user/panel";
    }

    @RequestMapping("file")
    public String file(@RequestParam("filePath") MultipartFile filePath,@SessionAttribute("user") User user) throws IllegalStateException, IOException {
        System.out.println("更换头像");
        String imgpath=notepaperService.upload(filePath);
        if(!StringUtil.isEmpty(imgpath)){
            userService.updateFilePath(imgpath,user.getUserId());
        }

        return "forward:/userpanel";
    }

        /**
         * 修改用户
         * @throws IOException
         * @throws IllegalStateException
         */
    @RequestMapping("saveuser")
    public String saveemp(@RequestParam("filePath") MultipartFile filePath, HttpServletRequest request, @Valid User user,
                          BindingResult br, @SessionAttribute("user") User users) throws IllegalStateException, IOException{
//        String imgpath=notepaperService.upload(filePath);

        //重新set用户
        user.setRealName(users.getRealName());
        user.setUserTel(users.getUserTel());
        user.setEamil(users.getEamil());
        user.setAddress(users.getAddress());
        user.setUserEdu(users.getUserEdu());
        user.setUserSchool(users.getUserSchool());
        System.out.println(users.getUserSchool());
        user.setIdCard(users.getIdCard());
        user.setBank(users.getBank());
        user.setSex(users.getSex());
        user.setThemeSkin(users.getThemeSkin());
        user.setBirth(users.getBirth());
        if(!StringUtil.isEmpty(users.getUserSign())){
            user.setUserSign(users.getUserSign());
        }
        if(!StringUtil.isEmpty(users.getPassword())){
            user.setPassword(users.getPassword());
        }
//        if(!StringUtil.isEmpty(imgpath)){
//            user.setImgPath(imgpath);
//
//        }

        request.setAttribute("users", user);

        ResultVO res = BindingResultVOUtil.hasErrors(br);
        if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
            List<Object> list = new MapToList<>().mapToList(res.getData());
            request.setAttribute("errormess", list.get(0).toString());

            System.out.println("list错误的实体类信息：" + user);
            System.out.println("list错误详情:" + list);
            System.out.println("list错误第一条:" + list.get(0));
            System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());

        }else{
            userService.update(user);
            request.setAttribute("success", "执行成功！");
        }
        return "forward:/userpanel";

    }

    @RequestMapping("images/user/**")
    public void image(Model model, HttpServletResponse response, @SessionAttribute("userId") Long userId, HttpServletRequest request)
            throws Exception {
        String projectPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();
        System.out.println("测试："+projectPath);
        String startpath = new String(URLDecoder.decode(request.getRequestURI(), "utf-8"));

        String path = startpath.replace("/images/user", "");

        File f = new File(rootpath, path);

        ServletOutputStream sos = response.getOutputStream();
        FileInputStream input = new FileInputStream(f.getPath());
        byte[] data = new byte[(int) f.length()];
        IOUtils.readFully(input, data);
        // 将文件流输出到浏览器
        IOUtils.write(data, sos);
        input.close();
        sos.close();
    }

    /**
     * 存便签
     */
    @RequestMapping("writep")
    public String savepaper(Notepaper npaper,@SessionAttribute("user") User userId,@RequestParam(value="concent",required=false)String concent){
        User user=userService.selectReviewed(userId.getUserId());
        npaper.setCreateTime(new Date());
        npaper.setNotepaperUserId(user.getUserId());
        System.out.println("内容"+npaper.getConcent());
        if(npaper.getTitle()==null|| npaper.getTitle().equals(""))
            npaper.setTitle("无标题");
        if(npaper.getConcent()==null|| npaper.getConcent().equals(""))
            npaper.setConcent(concent);
        notepaperService.insertNotepaper(npaper);

        return "redirect:/userpanel";
    }

    /**
     * 删除便签
     */
    @RequestMapping("notepaper")
    public String deletepaper(HttpServletRequest request,@SessionAttribute("user") User userId){
        User user=userService.selectReviewed(userId.getUserId());
        String paperid=request.getParameter("id");
        Long lpid = Long.parseLong(paperid);
        Notepaper note=notepaperService.selectByPrimaryKey(lpid);
        if(user.getUserId().equals(note.getNotepaperUserId())){
            notepaperService.deleteById(lpid);
        }else{
            System.out.println("权限不匹配，不能删除");
            return "redirect:/notlimit";
        }
        return "redirect:/userpanel";

    }

}
