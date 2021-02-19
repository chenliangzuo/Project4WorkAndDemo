package cn.chen.utils;
import cn.chen.pojo.ResultMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 消息工具类
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,
           String fromName,Object message){
        try {
            ResultMessage result = new ResultMessage();
            result.setIsSystem(isSystemMessage);
            if(fromName!=null){
                result.setFromName(fromName);
            }
            result.setMessage(message);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
