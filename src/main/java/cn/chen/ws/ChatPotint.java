package cn.chen.ws;
import cn.chen.pojo.Message;
import cn.chen.utils.MessageUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatPotint {

    // 用来存储每个客户端对象对应的ChatEndPoint对象
    private static Map<String,ChatPotint> onlineUsers = new ConcurrentHashMap<>();
    // 声明一个session对象，通过该对象可以发送消息给指定的用户
    private Session session;
    // 声明一个HttpSession对象，我们之前对HttpSession对象中存储了用户名
    private HttpSession httpSession;
    // 当前用户名
    private String username;

    /**
     * 连接建立时执行
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        this.httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        this.username = (String)this.httpSession.getAttribute("user");
        System.out.println(this.username+" 登录了...");
        // 将当前对象存储到在线用户容器
        onlineUsers.put(this.username,this);
        this.commonBroadcast();
        // 将当前对象的用户名推送给所有客户端
        /*Set<String> names = this.getNames();
        // 1.获取消息
        String message = MessageUtils.getMessage(true,null,names);
        // 2.调用方法推送消息
        this.broadcastAllUser(message,names);*/
    }

    private void commonBroadcast(){
        // 将当前对象的用户名推送给所有客户端
        Set<String> names = this.getNames();
        if(names!=null && names.size()>0){
            // 1.获取消息
            String message = MessageUtils.getMessage(true,null,names);
            // 2.调用方法推送消息
            this.broadcastAllUser(message,names);
        }
    }

    /**
     * 广播推送消息
     */
    private void broadcastAllUser(String message,Set<String> names) {
        try {
            for(String name : names){
                ChatPotint chat = onlineUsers.get(name);
                chat.session.getBasicRemote().sendText(message);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 要推送给的客户端
     * @return
     */
    private Set<String> getNames(){
        return onlineUsers.keySet();
    }

    /**
     * 接收到客户端发送的数据时调用
     * @param message
     * @param session
     */
    @OnMessage
    public void OnMessage(String message,Session session){
        System.out.println("接受到客户端消息...");
        Message mesObj = JSON.parseObject(message, Message.class);

    }

    /**
     * 链接关闭时调用
     * @param session
     */
    @OnClose
    public void OnClose(Session session){
        System.out.println(this.username+" 退出了...");
        if(StringUtils.hasLength(this.username)){
            this.httpSession.setAttribute("user",null);
            onlineUsers.remove(this.username);
        }
        this.commonBroadcast();
    }

}
