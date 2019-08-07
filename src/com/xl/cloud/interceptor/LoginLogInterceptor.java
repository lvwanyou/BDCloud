package com.xl.cloud.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginLogInterceptor extends HandlerInterceptorAdapter{

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
//		System.out.println("这是完成方法");
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
//		System.out.println("这是后置方法");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		
		HttpSession session = request.getSession();  
        String username = (String)session.getAttribute("userName");  
//        System.out.println("into loginI"+username);
        
        if(username != null){
            return true;  
        }  
        else{

        	String url=request.getRequestURI();
        	//System.out.println(url);
//        	System.out.println("拦截成功:"+url);
        	if(url.endsWith("Login.php")){
        		return true;
        	}else if(url.endsWith("kehuduanAndliulanqi.php")){
        		return true;
        	}
        	else{
        	//	System.out.println("拦截成功122:"+url);
        		String url22 = url.split("/")[2];
//        		System.out.println("url22:"+url22);
        		if ("".equals(url22)) {
        			response.sendRedirect("admin/Login.php");
        			//request.getRequestDispatcher("Login.php").forward(request, response);
				}else {
					response.sendRedirect("Login.php");
				}
        	 return false;  
        	 }
        }
		
	}  
}