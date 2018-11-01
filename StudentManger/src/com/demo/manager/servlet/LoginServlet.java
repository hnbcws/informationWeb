package com.demo.manager.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.manager.dao.AdminDao;
import com.demo.manager.dao.StudentDao;
import com.demo.manager.dao.TeacherDao;
import com.demo.manager.model.Admin;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;
import com.demo.manager.util.StringUtil;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("LoginOut".equals(method))
		{
			loginOut(request,response);
			return;
		}
		String vcode = request.getParameter("vcode");
		String name = request.getParameter("account");
		String password = request.getParameter("password");
		int type=Integer.parseInt(request.getParameter("type"));
		String loginverifi = (String) request.getSession().getAttribute("loginverification");
		if(StringUtil.isEmpty(vcode)) 
		{
			response.getWriter().write("vcodeError");
			return;
		}
		if(!vcode.toUpperCase().equals(loginverifi.toUpperCase()))
		{
			response.getWriter().write("vcodeError");
			return;
		}
		//验证用户身份和密码
		String loginStatus="loginFaild";
		switch(type)
		{
		   case 1:
		   {
				AdminDao admindao=new AdminDao();
				Admin admin=admindao.login(name, password);
				admindao.closeconn();
				if(admin==null)
				{
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session=request.getSession();
				session.setAttribute("user", admin);
				session.setAttribute("userType", type);
				loginStatus="loginSuccess";
			    break;
		   } 
		   case 2:
		   {
				StudentDao studao=new StudentDao();
				Student stu=studao.login(name, password);
				studao.closeconn();
				if(stu==null)
				{
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session=request.getSession();
				session.setAttribute("user", stu);
				session.setAttribute("userType", type);
				loginStatus="loginSuccess";
			    break;
		   } 
		   case 3:
		   {
				TeacherDao teadao=new TeacherDao();
				Teacher tea=teadao.login(name, password);
				teadao.closeconn();
				if(tea==null)
				{
					response.getWriter().write("loginError");
					return;
				}
				HttpSession session=request.getSession();
				session.setAttribute("user", tea);
				session.setAttribute("userType", type);
				loginStatus="loginSuccess";
			    break;
		   } 

		   
		   default:
			   break;
		}
	
		response.getWriter().write(loginStatus);
	}
	
	private void loginOut(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("userType");
		response.sendRedirect("index.jsp");
	}

}
