package com.itheima.wechat.domain;

/**
 * @author Tay
 *
 */
public class TextMessage extends BaseMessage {
	private String MsgType;//text
	private String Content;//文本消息内容
	private String MsgId;//消息id，64位整型
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
}
