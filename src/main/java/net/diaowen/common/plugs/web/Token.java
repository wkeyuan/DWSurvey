package net.diaowen.common.plugs.web;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class Token {
    /***
		 * 设置令牌
		 * @param request
		 */
		public static void setToken(HttpServletRequest request){
			request.getSession().setAttribute("sessionToken", UUID.randomUUID().toString() );
		}


		public static String getToken(HttpServletRequest request){
			String sessionToken = (String)request.getSession().getAttribute("sessionToken");
			if(null == sessionToken || "".equals(sessionToken)){
				sessionToken = UUID.randomUUID().toString();
				request.getSession().setAttribute("sessionToken",sessionToken );
				//把这个sessionToken保存在redis中
				//然后判断的时候根据redis是否有这个sessionToken，有看是不是使用过，使用过不能可以用
			}
			return sessionToken;
		}

		/***
		 * 验证是否为重复提交
		 * @param request
		 * @return String true非重复提交,false重复提交,error非法操作
		 */
		public static boolean validToken(HttpServletRequest request){
			String sessionToken = (String)request.getSession().getAttribute("sessionToken");
			String requestToken = request.getParameter("sessionToken");
//			System.out.println("sessionToken1:"+sessionToken);
//			System.out.println("requestToken1:"+requestToken);
			if(null == sessionToken || "null".equals(sessionToken)){
				sessionToken = "";
			}
			if(null == requestToken || "null".equals(requestToken) ){
				requestToken = "";
			}
			if(sessionToken.equals(requestToken)){
				//返回前一定要重置session中的SesToken
				request.getSession().setAttribute("sessionToken",UUID.randomUUID().toString() );
				//非重复提交
				return true;
			}else{
				//返回前一定要重置session中的SesToken
				request.getSession().setAttribute("sessionToken", UUID.randomUUID().toString() );
				//重复提交
				return false;
			}
		}
	}
