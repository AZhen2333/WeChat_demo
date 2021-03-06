package com.itheima.wechat.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itheima.wechat.domain.News;
import com.itheima.wechat.domain.NewsMessage;
import com.itheima.wechat.domain.TextMessage;
import com.thoughtworks.xstream.XStream;


public class MessageUtil {
	public static final String MESSAGE_TEXT="text";//文本
	public static final String MESSAGE_IMAGE="image";//图片
	public static final String MESSAGE_VOID="void";//视频
	public static final String MESSAGE_LINK="link";//连接
	public static final String MESSAGE_LOCATION="location";//位置
	public static final String MESSAGE_NEWS="news";//位置
	//事件
	public static final String MESSAGE_EVENT="event";
	public static final String MESSAGE_SUBSCRIBE="subscribe";
	public static final String MESSAGE_UNSUBSCRIBE="unsubscribe";
	public static final String MESSAGE_CLICK="CLICK";
	public static final String MESSAGE_VIEW="VIEW";
	
	
	
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws Exception{
		Map<String, String> map=new HashMap<String, String>();
		
		SAXReader reader=new SAXReader();
		//获取输入流
		ServletInputStream inputStream = request.getInputStream();
		
		Document document = reader.read(inputStream);
		
		Element rootElement = document.getRootElement();
		
		List<Element> list = rootElement.elements();
		
		for (Element element : list) {
			map.put(element.getName(),element.getText());
		}
		
		//关流
		inputStream.close();
		return map;
	}
	
	
	//转换为xml类型
	public static String toXML(TextMessage textMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	//拼接文本消息
	public static String initText(String toUserName,String fromUserName,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setFromUserName(toUserName);
		textMessage.setToUserName(fromUserName);
		textMessage.setMsgType(MessageUtil.MESSAGE_TEXT);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setContent(content);
		return toXML(textMessage);
	}
	
	
	//主菜单
	public static String menuText(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("欢迎关注，请按照菜单提示进行操作：\n\n");
		stringBuffer.append("1、公众号介绍\n");
		stringBuffer.append("2、个人介绍\n\n");
		stringBuffer.append("输入? 弹出菜单");
		
		return stringBuffer.toString();
	}
	
	//接收1
	public static String firstMenu(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("纯属测试号");
		return stringBuffer.toString();
	}
	//接收2
	public static String secondMenu(){
		StringBuffer stringBuffer=new StringBuffer();
		stringBuffer.append("test-test");
		return stringBuffer.toString();
	}
	
	/*
	 * 图文消息转xml
	 * */
	public static String newsMessageToXML(NewsMessage newsMessage){
		XStream xStream=new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());//消息体
		return xStream.toXML(newsMessage);
	}
	
	/*
	 * 图文消息的请求参数
	 * */
	public static String initNewsMessage(String toUserName,String fromUserName){
		String message=null;
		List<News> newsList=new ArrayList<News>();
		NewsMessage newsMessage = new NewsMessage();
		//封装news
		News news = new News();
		news.setTitle("派大星~！！");//
		news.setDescription("测试");
		news.setPicUrl("http://mytest.tunnel.qydev.com/WeiXin/image/ca1349540923dd54a77123b0d109b3de9d8248ec.jpg");
		news.setUrl("www.bing.com");//跳转地址
		//放入list
		newsList.add(news);
		//封装newsMessage
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MESSAGE_NEWS);
		newsMessage.setArticles(newsList);
		newsMessage.setArticleCount(newsList.size());
		//转xml
		message=newsMessageToXML(newsMessage);
		return message;
		
	}
	
}
