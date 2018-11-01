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
import com.demo.manager.model.Clazz;
import com.demo.manager.model.Page;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;


public class ClazzServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method=request.getParameter("method");
		if("toClazzListView".equals(method)) {
			ClazzList(request,response);
		}else if("getClazzList".equals(method)) {
			getClazzList(request,response);
		}else if("AddClazz".equals(method)) {
			addClazz(request,response);
		}else if("DeleteClazz".equals(method)) {
			deleteClazz(request,response);
		}else if("editClazz".equals(method)) {
			editClazz(request,response);
		}
		
	}

	private void ClazzList(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("jsp/clazzList.jsp").forward(request,response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  
	private void getClazzList(HttpServletRequest request, HttpServletResponse response)
	{
		String name=request.getParameter("clazzName");
		Integer currentPage= request.getParameter("page") == null ? 1: Integer.parseInt(request.getParameter("page"));
		Integer pageSize=request.getParameter("rows") == null ? 999: Integer.parseInt(request.getParameter("rows"));
		
		Clazz clazz=new Clazz();
		clazz.setBname(name);
		ClazzDao clazzDao = new ClazzDao();
		List<Clazz> clazzList = clazzDao.getClazzList(clazz,new Page(currentPage,pageSize));
		int total =clazzDao.getClazzListTotal(clazz);
		clazzDao.closeconn();
	    Map<String, Object> ret =new HashMap<String,Object>();
	    ret.put("total", total);
	    ret.put("rows",clazzList);
		response.setCharacterEncoding("utf-8");
		try {
			String from =request.getParameter("from");
			if("combox".equals(from)) {
				response.getWriter().write(JSONArray.fromObject(clazzList).toString());
			}else {
				response.getWriter().write(JSONObject.fromObject(ret).toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    private void addClazz(HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("name");
	    String info=request.getParameter("info");
	    Clazz clazz=new Clazz();
	    clazz.setBname(name);
	    clazz.setInfo(info);
	    ClazzDao clazzDao =new ClazzDao();
	    if(clazzDao.addClazz(clazz))
	    {
	    	try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				clazzDao.closeconn();
			}
	    }
	    
		
	}

	private void deleteClazz(HttpServletRequest request, HttpServletResponse response) {
		Integer id=Integer.parseInt(request.getParameter("clazzid"));
		ClazzDao clazzDao =new ClazzDao();
		 if(clazzDao.deletClazz(id))
		    {
		    	try {
					response.getWriter().write("success");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					clazzDao.closeconn();
				}
		    }    
	}
	
    private void editClazz(HttpServletRequest request, HttpServletResponse response) {
    	Integer id=Integer.parseInt(request.getParameter("id"));
    	String name=request.getParameter("name");
	    String info=request.getParameter("info");
	    Clazz clazz=new Clazz();
	    clazz.setBname(name);
	    clazz.setInfo(info);
	    clazz.setId(id);
	    ClazzDao clazzDao =new ClazzDao();
	    if(clazzDao.editClazz(clazz))
	    {
	    	try {
				response.getWriter().write("success");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				clazzDao.closeconn();
			}
	    }
	    
		
	}
		
}

