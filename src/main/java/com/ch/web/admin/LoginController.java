package com.ch.web.admin;


import com.ch.po.User;
import com.ch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpSession;

@Controller
//后台登录界面，与dao层的验证
@RequestMapping("/admin")//全局映射，后台控制的部分均由/admin开始
public class LoginController {
    //将userservice注入到本方法之中
    @Autowired
    private UserService userService;

    //登录 跳转页面
    @GetMapping//使用上方的全局地址访问
    public String loginPage(){
        return "admin/login";//返回到admin/login页面
    }
    //登录验证。。。对前端获得的信息收集,前端传来的用户名和密码用@Requestparam注释表明
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes,Model model){
        User user=userService.checkUser(username,password);//将数据一路传到dao层验证数据的正确性
        if(user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }else {
//                在浏览器上方弹出响应的信息，用户名密码错误
            attributes.addFlashAttribute("message","用户名和密码错误");
//               如果用户名和密码不正确的情况下，重定向到登录首页面
            return "redirect:/admin";
        }
    }
//    注销登录的反应，将session中的信息清除,原理是前端的注销按钮有一个链接地址，变为/admin/logout，
//    Getmapping识别到该地址作出反应执行下方的logout程序指令，返回到/admin注册界面，完成注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
            session.removeAttribute("user");
            return "redirect:/admin";
    }

}
