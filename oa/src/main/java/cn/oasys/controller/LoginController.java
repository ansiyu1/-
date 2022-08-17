package cn.oasys.controller;

import cn.oasys.pojo.user.User;
import cn.oasys.service.user.UserService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    private  String CAPTCHA_KEY =null;

    private Random rnd = new Random();

    @RequestMapping("loginout")
    public String loginout(HttpSession session) {
        session.removeAttribute("userId");
        return "redirect:logins";
    }

    /**
     * 图片验证码
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/captcha")
    public String defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception{
        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            //验证码字符串并保存到session中
            CAPTCHA_KEY = defaultKaptcha.createText();
            request.getSession().setAttribute("verifyCode", CAPTCHA_KEY);
            //验证码字符串生成图片，并返回给浏览器
            BufferedImage challenge = defaultKaptcha.createImage(CAPTCHA_KEY);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        return "logins  ";
    }

    /**
     * 检测登录
     * @param session
     * @param req
     * @param model
     * @param userName
     * @param password
     * @return
     * @throws IOException
     */
    @RequestMapping("logins")
    public String loginaaa(HttpSession session, HttpServletRequest req, Model model,String userName,String password) {
//        String ca = req.getParameter("code").toLowerCase();
        String sesionCode = (String) req.getSession().getAttribute("verifyCode");
        model.addAttribute("userName", userName);
//        System.out.println(ca);
//        System.out.println(sesionCode);
//        if (!ca.equals(sesionCode.toLowerCase())) {
//            System.out.println("验证码输入错误!");
//            model.addAttribute("errormess", "验证码输入错误!");
//            req.setAttribute("errormess", "验证码输入错误!");
//            return "login/login";
//        }

        User user = userService.findLoginUser(userName, password,"");
        session.setAttribute("user",user);
        System.out.println(user);
        System.out.println("是否被锁："+user.getIsLock());
        System.out.println("..."+user.getIsLock());
        System.out.println(user.getUserId()+"和"+session.getAttribute("userId"));
        if(user.getUserId()==session.getAttribute("userId")){
            System.out.println("该账户一登陆");
            model.addAttribute("hasmess","该账户已登录");
            session.setAttribute("thisuser", user);
            return "login/login";
        }
        if(user.getIsLock()==1) {
            System.out.println("..."+user.getIsLock());
            System.out.println("账号已被冻结!");
            model.addAttribute("errormess", "账号已被冻结!");
            return "login/login";
        }
        if (user == null) {

            model.addAttribute("errormess", "账号或密码错误");
            return "login/login";
        } else {
            session.setAttribute("userId",user.getUserId());
            model.addAttribute("errormess", "正确");
        }
        return "index";


    }

    /**
     * 重复登录
     * @param session
     * @return
     */
    @RequestMapping("handlehas")
    public String handleHas(HttpSession session){
        if(!StringUtils.isEmpty(session.getAttribute("thisuser"))){
            User user=(User) session.getAttribute("thisuser");
            System.out.println(user);
            session.removeAttribute("userId");
            session.setAttribute("userId", user.getUserId());
        }else{
            System.out.println("有问题！");
            return "login/login";
        }
        return "index";

    }
    @RequestMapping("login")
    public String login(){
        return "login/login";
    }
}
