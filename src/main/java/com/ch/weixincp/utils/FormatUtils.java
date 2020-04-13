package com.ch.weixincp.utils;

import com.ch.weixincp.entity.InMsgEntity;

public class FormatUtils {
    /**
     * 接收请求格式封装
     * @param msg
     * @return
     */
    public static String getReqData(InMsgEntity msg){
        return  "<xml><ToUserName><![CDATA["+msg.getToUserName()+"]]></ToUserName><Encrypt><![CDATA[" +msg.getEncrypt()+
                "]]></Encrypt><AgentID><![CDATA["+msg.getAgentID()+"]]></AgentID></xml>";
    }

    /**
     * 发送文字消息格式封装
     * @param content
     * @return
     */
    public static String getTextRespData(String content){
        return  "<xml><MsgType><![CDATA[text]]></MsgType><Content><![CDATA["+content+"]]></Content></xml>";
    }
}