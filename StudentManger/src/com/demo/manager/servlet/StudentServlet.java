 package com.demo.manager.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.manager.dao.ClazzDao;
import com.demo.manager.dao.StudentDao;
import com.demo.manager.model.Clazz;
import com.demo.manager.model.Page;
import com.demo.manager.model.Student;
import com.demo.manager.util.SnGenerateUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String method=request.getParameter("method");
	    if("toStudentListView".equals(method))
	    {
	    	studentList(request,response);
	    }else if("AddStudent".equals(method))
	    {
	    	addStudent(request,response);
	    }else if("StudentList".equals(method))
	    {
	    	getStudentList(request,response);
	    }else if("EditStudent".equals(method))
	    {
	    	editStudent(request,response);
	    }else if("DeleteStudent".equals(method))
	    {
	    	deleteStudent(request,response);
	    }
		
	}



	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		String [] ids = request.getParameterValues("ids[]");
		String idStr="";
		for(String id : ids ) {
			idStr += id +",";
		}
		idStr = idStr.substring(0, idStr.length()-1);
		StudentDao studao = new StudentDao();
		if(studao.deleteStudent(idStr))
		{
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studao.closeconn();
			}
		}
		
	}

	private void editStudent(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
		int id=Integer.parseInt(request.getParameter("id"));
		String sex=request.getParameter("sex");
		String mobile=request.getParameter("mobile");
		String qq=request.getParameter("qq");
		int clazzId=Integer.parseInt(request.getParameter("clazzid"));
		
		Student stu=new Student();
		stu.setClazzId(clazzId);
		stu.setId(id);
		stu.setName(name);
		stu.setMobile(mobile);
		stu.setSex(sex);
		stu.setQq(qq);
		StudentDao studao=new StudentDao();
	    if(studao.editStudent(stu))
	    {
	    	try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studao.closeconn();
			}
	   }
	}
	private void addStudent(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
		String password =request.getParameter("password");
		String sex=request.getParameter("sex");
		String mobile=request.getParameter("mobile");
		String qq=request.getParameter("qq");
		int clazzId=Integer.parseInt(request.getParameter("clazzid"));
		
		Student stu=new Student();
		stu.setClazzId(clazzId);
		stu.setName(name);
		stu.setMobile(mobile);
		stu.setPassword(password);
		stu.setSex(sex);
		stu.setQq(qq);
		stu.setSn(SnGenerateUtil.generateSn(clazzId));
		StudentDao studao=new StudentDao();
		if(studao.addStudent(stu))
		{
			try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				studao.closeconn();
			}
		}
		
	}
	
	private void getStudentList(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("studentName");
		Integer currentPage= request.getParameter("page") == null ? 1: Integer.parseInt(request.getParameter("page"));
		Integer pageSize=request.getParameter("rows") == null ? 999: Integer.parseInt(request.getParameter("rows"));
		Integer clazz=request.getParameter("clazzid") == null ? 0: Integer.parseInt(request.getParameter("clazzid"));
		
		int userType = Integer.parseInt(request.getSession().getAttribute("userType").toString());
		Student stu = new Student();
		stu.setName(name);
		stu.setClazzId(clazz);
		if(userType == 2) {
			//学生只能查看修改自己的信息
			Student currentUser = (Student) request.getSession().getAttribute("user");
			stu.setId(currentUser.getId());
		}
		
		StudentDao stuDao = new StudentDao();
		List<Student> studentList = stuDao.getStudentList(stu,new Page(currentPage,pageSize));
		int total =stuDao.getStudentListTotal(stu);
		stuDao.closeconn();
	    Map<String, Object> ret =new HashMap<String,Object>();
	    ret.put("total", total);
	    ret.put("rows",studentList);
		response.setCharacterEncoding("utf-8");
		try {
			String from =request.getParameter("from");
			if("combox".equals(from)) {
				response.getWriter().write(JSONArray.fromObject(studentList).toString());
			}else {
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void studentList(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		request.getRequestDispatcher("/jsp/studentList.jsp").forward(request, response);
	}

}