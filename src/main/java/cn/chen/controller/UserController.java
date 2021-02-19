package cn.chen.controller;

import cn.chen.pojo.Result;
import cn.chen.pojo.User;
import cn.monitor4all.springbootwebsocketdemo.model.ChatMessage;
import cn.monitor4all.springbootwebsocketdemo.service.ChatService;
import cn.monitor4all.springbootwebsocketdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/login")
    public Result login(User user, HttpSession session){
        Result result = new Result();
        if(user==null || "123".equals(user.getPassword())){
            result.setFlag(true);
            session.setAttribute("user",user.getUsername());
        } else {
            result.setFlag(false);
            result.setMessage("账号或密码错误!");
        }
        return result;
    }

    @PostMapping("/getUsername")
    public String getUsername(HttpSession session){
        return (String)session.getAttribute("user");
    }

}
