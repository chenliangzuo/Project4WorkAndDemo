package cn.chen.pojo;

/**
 * 服务端发送给客户端的消息
 */
public class ResultMessage {

    private boolean isSystem;
    private String fromName;
    private Object message;

    public boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(boolean system) {
        isSystem = system;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
