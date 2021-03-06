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
import com.sun.xml.internal.bind.v2.model.core.ID;

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

			// map接收文本消息参数，xml转换为map
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String msgType = map.get("MsgType");
			String content = map.get("Content");

			String message = null;
			// 文本类型
			if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				/* TextMessage textMessage = new TextMessage();
				 textMessage.setFromUserName(toUserName);
				 textMessage.setToUserName(fromUserName);
				 textMessage.setMsgType("text");
				 textMessage.setCreateTime(new Date().getTime());
				 textMessage.setContent("你发送的消息是：" + content);
				 message = MessageUtil.toXML(textMessage);*/
				
				// 转为xml格式
				// 判断客户端输入内容
				if ("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
//					printWriter.print(message);
				} else if ("2".equals(content)) {
					message = MessageUtil.initNewsMessage(toUserName, fromUserName);
//					printWriter.print(message);
				} else if ("?".equals(content) || "？".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}

				// 事件
			} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				String eventType = map.get("Event");
				// 事件类型：关注
				if (MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}

			System.out.println("message:" + message);
			// 输出流
			printWriter.print(message);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关流
			printWriter.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
