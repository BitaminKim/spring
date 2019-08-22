package ink.bitamin.entity.vo;

import java.util.HashMap;
import java.util.Map;

public class MessageVO {

    private int code;
    private String msg;
    private Map<String, Object> data = new HashMap<String, Object>();

    public static MessageVO success(){
        MessageVO messageVO = new MessageVO();
        messageVO.setCode(200);
        messageVO.setMsg("请求成功!");
        return messageVO;
    }
    public static MessageVO success(int code, String msg){
        MessageVO messageVO = new MessageVO();
        messageVO.setCode(code);
        messageVO.setMsg(msg);
        return messageVO;
    }
    public static MessageVO fail(){
        MessageVO messageVO = new MessageVO();
        messageVO.setCode(0);
        messageVO.setMsg("请求失败!");
        return messageVO;
    }
    public static MessageVO fail(int code, String msg){
        MessageVO messageVO = new MessageVO();
        messageVO.setCode(code);
        messageVO.setMsg(msg);
        return messageVO;
    }

    public MessageVO add(String key, Object value) {
        this.getData().put(key,value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

//    0 Fail - 返回其他异常操作失败信息
//    200 OK - 正常返回，用于GET, PUT, PATCH的这些正常操作。
//    201 Created - 用于POST创建对象正常返回。
//    204 No Content - 用于DELETE，成功操作但没啥返回的。
//    304 Not Modified - 有Cache，表示没改过。
//    400 Bad Request - 指请求里面有些参数不对。
//    401 Unauthorized - 没有登录
//    403 Forbidden - 登录了但是没有授权访问某个资源
//    404 Not Found - 资源不存在
//    405 Method Not Allowed - 登录了但是不允许做某个操作
//    410 Gone - 表示资源不再提供了，用来做老版本提示用的
//    415 Unsupported Media Type - 請求的Centent Type不對
//    422 Unprocessable Entity - 一般用于validation校验
//    429 Too Many Requests - 请求太快太多，达到限制
//    500 Internal Server Error - 内部服务器错误。
}
