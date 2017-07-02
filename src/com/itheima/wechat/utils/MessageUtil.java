package com.itheima.wechat.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.itheima.wechat.domain.TextMessage;
import com.thoughtworks.xstream.XStream;


public class MessageUtil {
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
	
	
}
