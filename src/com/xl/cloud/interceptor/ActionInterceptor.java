package com.xl.cloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class ActionInterceptor implements HandlerInterceptor  {
	
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
        	System.out.println("拦截成功:"+url);
       	if(url.endsWith("Login.php")){
        		
        		return true;
        	}
        	else{
        	response.sendRedirect("Login.php");
        	// request.getRequestDispatcher("Login.php").forward(request, response);
        	 return false;  
        	 }
        }
		
	} 

}
