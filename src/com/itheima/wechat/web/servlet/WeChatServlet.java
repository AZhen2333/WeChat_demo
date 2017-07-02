package com.itheima.wechat.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.wechat.domain.TextMessage;
import com.itheima.wechat.utils.CheckUtil;
import com.itheima.wechat.utils.MessageUtil;

/**
 * Servlet implementation class WeChatServlet
 */
public class WeChatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = response.getWriter();
		try {
			// 获取四个参数
			String signature = request.getParameter("signature");// 微信加密签名
			String timestamp = request.getParameter("timestamp");// 时间戳
			String nonce = request.getParameter("nonce");// 随机数
			String echostr = request.getParameter("echostr");// 随机字符串
			
			if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
				response.getWriter().print(echostr);
			}
			
			//map接收文本消息参数，xml转换为map
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message=null;
			if("text".equals(msgType)){
				TextMessage textMessage = new TextMessage();
				textMessage.setFromUserName(toUserName);
				textMessage.setToUserName(fromUserName);
				textMessage.setMsgType("text");
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setContent("你发送的消息是："+content);
				//转为xml格式
				message=MessageUtil.toXML(textMessage);
				System.out.println("message:"+message);
			}
			//输出流
			printWriter.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关流
			printWriter.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
