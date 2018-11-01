package com.demo.manager.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.demo.manager.dao.AdminDao;
import com.demo.manager.dao.StudentDao;
import com.demo.manager.dao.TeacherDao;
import com.demo.manager.model.Admin;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;


public class SystemServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7258264317769166483L;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException{
		doPost(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String method = request.getParameter("method");
		if("toPersonalView".equals(method)){
			personalView(request,response);
			return;
		}else if("EditPasswod".equals(method)){
			editPassword(request,response);
			return;
		}
		try {
			request.getRequestDispatcher("jsp/system.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void editPassword(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newpassword");
		response.setCharacterEncoding("UTF-8");
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 1){
			//管理员
			Admin admin = (Admin)request.getSession().getAttribute("user");
			if(!admin.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			AdminDao adminDao = new AdminDao();
			if(adminDao.editPassword(admin, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					adminDao.closeconn();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					adminDao.closeconn();
				}
			}
		}
		
		if(userType == 2){
			//学生
			Student stu = (Student)request.getSession().getAttribute("user");
			if(!stu.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			StudentDao studao = new StudentDao();
			if(studao.editPassword(stu, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					studao.closeconn();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					studao.closeconn();
				}
			}
		}
		
		if(userType == 3){
			//老师
			Teacher tea = (Teacher)request.getSession().getAttribute("user");
			if(!tea.getPassword().equals(password)){
				try {
					response.getWriter().write("原密码错误！");
					return;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TeacherDao teadao = new TeacherDao();
			if(teadao.editPassword(tea, newPassword)){
				try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					teadao.closeconn();
				}
			}else{
				try {
					response.getWriter().write("数据库修改错误");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					teadao.closeconn();
				}
			}
		}
		
	}
	private void personalView(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.getRequestDispatcher("jsp/personalView.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
