package com.example.demo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Controller
public class ShiroController {


    @RequestMapping(value = "/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/out")
    public String out(){
        return "out";
    }

    @RequestMapping(value = "/loginuser")
    public String loginuser(@RequestParam(value = "name")String name, @RequestParam(value = "pws")String pwd,@RequestParam(value = "rememberMe",required = false)boolean rememberMe,@RequestParam(value = "gifCode",required = false)String gifCode ,HttpServletRequest request, HttpSession session){
        if(gifCode==null){
            //throw new AccountException("验证码没填写！");
        }else{
            String code = (String) session.getAttribute("codeValidate");
            System.out.println("验证码:"+code+" "+gifCode);
            if(!code.equals(gifCode)){
               // throw new AccountException("验证码错误！");
            }else{
                System.out.println("验证码正确");
            }

        }
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(name,pwd,rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            SavedRequest savedRequest = WebUtils.getSavedRequest(request);
            String url=null;
            if(null!=savedRequest){
                url = savedRequest.getRequestUrl();
                System.out.println("之前访问的链接是："+url);
            }else{
                url="file";
            }

            //demo_user user=(demo_user) subject.getPrincipal();
            //session.setAttribute("user", user);
            return url;
        }catch (Exception e){
            e.printStackTrace();
            return "login";//返回登录页面
        }

    }

    @RequestMapping("/logOut")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    /**
     * 获取验证码
     *
     */
    @RequestMapping(value="/getGifCode")
    public void getGifCode(HttpServletRequest request,HttpServletResponse response){
         int width = 90;//验证码宽度
         int height = 35;//验证码高度
         int codeCount = 4;//验证码个数
         int lineCount = 15;//混淆线个数
        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        try {
            //定义随机数类
            Random r = new Random();
            //定义存储验证码的类
            StringBuilder builderCode = new StringBuilder();
            //定义画布
            BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //得到画笔
            Graphics g = buffImg.getGraphics();
            //1.设置颜色,画边框
            g.setColor(Color.black);
            g.drawRect(0,0,width,height);
            //2.设置颜色,填充内部
            g.setColor(Color.white);
            g.fillRect(1,1,width-2,height-2);
            //3.设置干扰线
            g.setColor(Color.gray);
            for (int i = 0; i < lineCount; i++) {
                g.drawLine(r.nextInt(width),r.nextInt(width),r.nextInt(width),r.nextInt(width));
            }
            //4.设置验证码
            g.setColor(Color.blue);
            //4.1设置验证码字体
            g.setFont(new Font("宋体",Font.BOLD|Font.ITALIC,15));
            for (int i = 0; i < codeCount; i++) {
                char c = codeSequence[r.nextInt(codeSequence.length)];
                builderCode.append(c);
                g.drawString(c+"",15*(i+1),15);
            }
            //5.输出到屏幕
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(buffImg,"png",sos);
            //6.保存到session中
            HttpSession session = request.getSession();
            session.removeAttribute("codeValidate");
            session.setAttribute("codeValidate",builderCode.toString());
            //7.禁止图像缓存。
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/png");
            //8.关闭sos
            sos.close();
        } catch (Exception e) {
            System.err.println("获取验证码异常："+e.getMessage());
        }
    }

}
