package cn.oasys.controller;

import cn.oasys.pojo.director.Director;
import cn.oasys.pojo.director.DirectorUser;
import cn.oasys.pojo.user.User;
import cn.oasys.service.attch.AttachmentService;
import cn.oasys.service.director.DirectorService;
import cn.oasys.service.director.DirectorUserService;
import cn.oasys.service.user.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class DirectorController {

    @Autowired
    DirectorUserService directorUserService;
    @Autowired
    DirectorService directorService;
    @Autowired
    AttachmentService attachmentService;
    @Autowired
    UserService userService;
    /**
     * 通讯录管理
     * @param user
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("addrmanage")
    public String addrmanage(@SessionAttribute("user") User user, Model model,
                             @RequestParam(value="pageIndex",defaultValue="1") int page,
                             @RequestParam(value="size",defaultValue="10") int size
    ) {
        Set<String> catalogs=directorUserService.findByUser(user.getUserId());
        PageHelper.startPage(page,size);
        PageInfo<User> pageInfo=new PageInfo<>(userService.selectAllUser());
        List<User> users=pageInfo.getList();
        List<DirectorUser> nothandles=directorUserService.findByUserAndShareUserNotNullAndHandle(user.getUserId(),0L);
        model.addAttribute("count", nothandles.size());
        model.addAttribute("catalogs", catalogs);
        model.addAttribute("users", users);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("url", "inaddresspaging");
        return "address/addrmanage";
    }


    /**
     * 查看内部联系人
     */
    @RequestMapping("inmessshow")
    public String inMessShow(Model model,@RequestParam(value="userId")Long userId){
        User user=userService.selectReviewed(userId);
        model.addAttribute("user", user);
        return "address/inmessshow";
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping("addaddress")
    public String addAddress(HttpServletRequest req,
                             @RequestParam(value="did",required=false) Long did, HttpSession session,
                             @SessionAttribute("user") User user, Model model){
        Set<String> calogs=directorUserService.findByUser(user.getUserId());
        model.addAttribute("calogs", calogs);
        if(!StringUtils.isEmpty(did)){
            Director director=directorService.selectDirector(did);
            System.out.println();
            if(Objects.isNull(director)|| !Objects.equals(director.getUserId().getUserId(), user.getUserId())){
                System.out.println("权限不匹配，不能操作");
                return "redirect:/notlimit";
            }
            DirectorUser du=directorUserService.findByDirectorAndUser(director.getDirectorId(), user.getUserId());
            model.addAttribute("director", director);
            model.addAttribute("du", du);
            session.setAttribute("did", did);
        }
        return "address/addressedit";
    }

    /**
     * 内部通讯录表格，并处理分页
     * @return
     */
    @RequestMapping("inaddresspaging")
    public String inAddress(@RequestParam(value="pageIndex",defaultValue="1") int page,Model model,
                            @RequestParam(value="size",defaultValue="10") int size,
                            @RequestParam(value="baseKey",required=false) String baseKey,
                            @RequestParam(value="alph",defaultValue="ALL") String alph
    ){
        System.out.println("进去");
        PageInfo<User> userspage=null;
        PageHelper.startPage(page,size);
        if(StringUtils.isEmpty(baseKey)){
            if("ALL".equals(alph)){
                userspage=new PageInfo<User>(userService.selectAllUser());
            }else{
                userspage=new PageInfo<User>(userService.selectByPinyinLike(alph+"%"));
            }
        }else{
            if("ALL".equals(alph)){
                userspage=new PageInfo<User>(userService.findUsers("%"+baseKey+"%",baseKey+"%"));
            }else{
                userspage=new PageInfo<User>(userService.findSelectUsers("%"+baseKey+"%", alph+"%"));
            }
        }
        if(!StringUtils.isEmpty(baseKey)){
            model.addAttribute("baseKey", baseKey);
            model.addAttribute("sort", "&alph="+alph+"&baseKey="+baseKey);
        }else{
            model.addAttribute("sort", "&alph="+alph);
        }
        List<User> users=userspage.getList();
        model.addAttribute("users", users);
        model.addAttribute("pageInfo", userspage);
        model.addAttribute("url", "inaddresspaging");
        return "address/inaddrss";
    }


}
