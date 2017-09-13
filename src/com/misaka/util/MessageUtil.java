package com.misaka.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.misaka.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {

	// 注意大小写
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	// 事件
	public static final String MESSAGE_EVENT = "event";
	public static final String EVENT_SUBSCRIBE = "subscribe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_SCAN = "SCAN";// 扫码
	public static final String EVENT_LOCATION = "LOCATION";// 上报地理位置
	public static final String EVENT_CLICK = "CLICK";
	public static final String EVENT_VIEW = "VIEW";

	/*
	 * 需要有一个xml --> map 的方法 还要有一个map --> xml 的方法
	 */

	/**
	 * xml --> map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
		Map<String, String> map = new HashMap<>();
		SAXReader reader = new SAXReader();
		InputStream input = request.getInputStream();

		Document doc = reader.read(input);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();

		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		input.close();
		return map;
	}

	/**
	 * textMessage --> xml
	 * 
	 * @param textMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

}
