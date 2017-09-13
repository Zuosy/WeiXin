package com.misaka.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.misaka.util.Const;
import com.misaka.util.MessageUtil;
import com.misaka.util.SignUtil;

/**
 * Servlet implementation class WeChat
 */
@WebServlet("/WeChat")
public class WeChat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WeChat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		
		if(SignUtil.checkSignature(signature, timestamp, nonce)) {
			response.getWriter().print(echostr);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		String message = "error";
		try {
			Map<String, String> map = MessageUtil.xmlToMap(request);
			String fromUserName = map.get("FromUserName");
			String toUserName = map.get("ToUserName");
			String content = map.get("Content");
			String msgType = map.get("MsgType");
			
			if(MessageUtil.MESSAGE_TEXT.equals(msgType)) {
				if("?".equals(content) || "£¿".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, 
							Const.MENU);
				} else if("1".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName,
							Const.KEYWORD_ANSWER_1);
				} else if("2".equals(content)) {
					message = MessageUtil.initText(toUserName, fromUserName, 
							Const.KEYWORD_ANSWER_2);
				} else {
					message = MessageUtil.initText(toUserName, fromUserName, 
							Const.AUTO_ANSWER);
				}
			} else {
				message = MessageUtil.initText(toUserName, fromUserName, 
						message);
			}
			
			out.print(message);
			
		} catch(Exception e) {
			
		} finally {
			out.close();
		}
	}

}
