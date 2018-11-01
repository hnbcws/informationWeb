package com.demo.manager.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.manager.dao.TeacherDao;
import com.demo.manager.dao.TeacherDao;

import com.demo.manager.model.Page;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;
import com.demo.manager.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String method=request.getParameter("method");
		    if("toTeacherListView".equals(method))
		    {
		    	TeacherList(request,response);
		    }else if("AddTeacher".equals(method))
		    {
		    	addTeacher(request,response);
		    }else if("TeacherList".equals(method))
		    {
		    	getTeacherList(request,response);
		    }else if("EditTeacher".equals(method))
		    {
		    	editTeacher(request,response);
		    }else if("DeleteTeacher".equals(method))
		    {
		    	deleteTeacher(request,response);
		    }
	
	}

	private void deleteTeacher(HttpServletRequest request, HttpServletResponse response) {
		String [] ids = request.getParameterValues("ids[]");
		String idStr="";
		for(String id : ids ) {
			idStr += id +",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		TeacherDao teadao = new TeacherDao();
		if(teadao.deleteTeacher(idStr))
		{
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teadao.closeconn();
			}
		}
		
	}

	private void editTeacher(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
		int id=Integer.parseInt(request.getParameter("id"));
		String sex=request.getParameter("sex");
		String mobile=request.getParameter("mobile");
		String qq=request.getParameter("qq");
		int clazzId=Integer.parseInt(request.getParameter("clazzid"));
		
		Teacher tea =new Teacher();
		tea.setClazzId(clazzId);
		tea.setId(id);
		tea.setName(name);
		tea.setMobile(mobile);
		tea.setSex(sex);
		tea.setQq(qq);
		TeacherDao teadao=new TeacherDao();
	    if(teadao.editTeacher(tea))
	    {
	    	try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teadao.closeconn();
			}
	   }
	}

	private void getTeacherList(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("teacherName");
		Integer currentPage= request.getParameter("page") == null ? 1: Integer.parseInt(request.getParameter("page"));
		Integer pageSize=request.getParameter("rows") == null ? 999: Integer.parseInt(request.getParameter("rows"));
		Integer clazz=request.getParameter("clazzid") == null ? 0: Integer.parseInt(request.getParameter("clazzid"));
		
		Teacher tea = new Teacher();
		tea.setName(name);
		tea.setClazzId(clazz);
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		if(userType == 3) {
			//教师只能查看修改自己的信息
			Teacher currentUser = (Teacher) request.getSession().getAttribute("user");
			tea.setId(currentUser.getId());
		}
		TeacherDao teadao = new TeacherDao();
		List<Teacher> teacherList = teadao.getTeacherList(tea,new Page(currentPage,pageSize));
		int total =teadao.getTeacherListTotal(tea);
		teadao.closeconn();
	    Map<String, Object> ret =new HashMap<String,Object>();
	    ret.put("total", total);
	    ret.put("rows",teacherList);
		response.setCharacterEncoding("utf-8");
		try {
			String from =request.getParameter("from");
			if("combox".equals(from)) {
				response.getWriter().write(JSONArray.fromObject(teacherList).toString());
			}else {
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addTeacher(HttpServletRequest request, HttpServletResponse response) {
	
		String name=request.getParameter("name");
		String password =request.getParameter("password");
		String sex=request.getParameter("sex");
		String mobile=request.getParameter("mobile");
		String qq=request.getParameter("qq");
		int clazzId=Integer.parseInt(request.getParameter("clazzid"));
		
		Teacher tea=new Teacher();
		tea.setClazzId(clazzId);
		tea.setName(name);
		tea.setMobile(mobile);
		tea.setPassword(password);
		tea.setSex(sex);
		tea.setQq(qq);
		tea.setSn(SnGenerateUtil.generateTeaSn(clazzId));
		TeacherDao teadao=new TeacherDao();
		if(teadao.addTeacher(tea))
		{
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				teadao.closeconn();
			}
		}
	}
	
	

	private void TeacherList(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			request.getRequestDispatcher("/jsp/teacherList.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
