package com.itheima.wechat.domain;
/*
 * 共有的属性
 * 
 */
public class BaseMessage {
	private String ToUserName;//开发者微信号
	private String FromUserName;//发送方帐号
	private long CreateTime;//消息创建时间
	private String MsgType;//text
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
	
}
