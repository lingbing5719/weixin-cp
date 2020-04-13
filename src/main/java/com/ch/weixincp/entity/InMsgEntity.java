package com.ch.weixincp.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class InMsgEntity {
    /**
     * 接收的应用id，可在应用的设置页面获取
     */
    @XmlElement(name = "AgentID")
    private String agentID;
    /**
     * 经过加密的密文
     */
    @XmlElement(name = "Encrypt")
    private String encrypt;
    /**
     * 发送方帐号
     */
    @XmlElement(name="ToUserName")
    private String toUserName;

    public String getAgentID() {
        return agentID;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}