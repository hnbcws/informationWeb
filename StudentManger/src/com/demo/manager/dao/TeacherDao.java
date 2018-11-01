package com.demo.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.manager.model.Page;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;
import com.demo.manager.util.StringUtil;

public class TeacherDao extends BaseDao {
   
	public boolean addTeacher(Teacher tea) {
		  
		String sql = "insert into teacher values(null,'"+tea.getSn()+"','"+tea.getName()+"' ";
		sql+=",'"+tea.getPassword()+"',"+tea.getClazzId();
		sql+=",'"+tea.getSex()+"','"+tea.getMobile()+"'";
		sql+=",'"+tea.getQq()+"',null)";
 	   return update(sql);
	}
	public boolean editTeacher(Teacher tea) {
		// TODO Auto-generated method stub
		String sql = "update teacher set name = '"+tea.getName()+"'";
		sql += ",sex = '" + tea.getSex() + "'";
		sql += ",mobile = '" + tea.getMobile() + "'";
		sql += ",qq = '" + tea.getQq() + "'";
		sql += ",clazz_id = " + tea.getClazzId();
		sql += " where id = " + tea.getId();
		return update(sql);
	}
	
	public boolean setTeacherPhoto(Teacher tea) {
		String sql = "update teacher set photo = ? where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, tea.getPhoto());
			prepareStatement.setInt(2, tea.getId());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update(sql);
	}
	
	public Teacher getTeacher(int id) {
		String sql = "select * from teacher where id ="+id;
		Teacher tea=null;
		ResultSet resultSet = query(sql);
		try {
			if(resultSet.next())
			{
				tea =new Teacher();
				tea.setId(resultSet.getInt("id"));
				tea.setClazzId(resultSet.getInt("clazz_id"));
				tea.setMobile(resultSet.getString("mobile"));
				tea.setName(resultSet.getString("name"));
				tea.setPassword(resultSet.getString("password"));
				tea.setQq(resultSet.getString("qq"));
				tea.setSex(resultSet.getString("sex"));
				tea.setSn(resultSet.getString("sn"));
				tea.setPhoto(resultSet.getBinaryStream("photo"));
				return tea;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tea;
	}
	
	public List<Teacher> getTeacherList(Teacher tea,Page page)
    {
 	   List<Teacher> ret=new ArrayList<Teacher>();
 	   String sql="select * from teacher ";
 	   if(!StringUtil.isEmpty(tea.getName())) {
 		   sql += "and name like '%" + tea.getName() + "%'";
 	   }
 	   if(tea.getClazzId() != 0)
 	   {
 		   sql += " and clazz_id = "+ tea.getClazzId(); 
 	   }
 	   if(tea.getId() != 0) {
		   sql+=" and id = "+tea.getId();
	   }
 	   sql+=" limit "+page.getStart()+","+page.getPageSize();
 	   ResultSet resultSet = query(sql.replaceFirst("and", "where"));
 	   try {
			while(resultSet.next())
			   {
				    Teacher  tea1 = new Teacher();
				    tea1.setId(resultSet.getInt("id"));
					tea1.setClazzId(resultSet.getInt("clazz_id"));
					tea1.setMobile(resultSet.getString("mobile"));
					tea1.setName(resultSet.getString("name"));
					tea1.setPassword(resultSet.getString("password"));
					tea1.setQq(resultSet.getString("qq"));
					tea1.setSex(resultSet.getString("sex"));
					tea1.setSn(resultSet.getString("sn"));
					tea1.setPhoto(resultSet.getBinaryStream("photo"));
				    ret.add(tea1);
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   return ret;
    }
    
    public int getTeacherListTotal(Teacher tea)
    {
 	   int total=0;
 	   String sql="select count(*) as total from teacher";
 	  if(!StringUtil.isEmpty(tea.getName())) {
		   sql += " and name like '%" + tea.getName() + "%'";
	   }
	   if(tea.getClazzId() != 0)
	   {
		   sql += " and clazz_id ="+ tea.getClazzId(); 
	   }
	   if(tea.getId() != 0) {
		   sql+=" and id = "+tea.getId();
	   }
 	
 	   ResultSet resultSet = query(sql.replaceFirst("and", "where"));
 	   try {
			while(resultSet.next())
			   {
				 total=resultSet.getInt("total");
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   return total;
    }
    
	public boolean deleteTeacher(String ids) {
		
		String sql = "delete from teacher where id in("+ids+")";
		return update(sql);
	}
	public Teacher login(String name, String password) {
		String sql ="select * from teacher where name='"+name+"' and password='"+password+"'";
    	ResultSet resultSet = query(sql);
    	try {
			if(resultSet.next())
			{
				Teacher teacher =new Teacher();
			
				teacher.setId(resultSet.getInt("id"));
				teacher.setClazzId(resultSet.getInt("clazz_id"));
				teacher.setMobile(resultSet.getString("mobile"));
				teacher.setName(resultSet.getString("name"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setPhoto(resultSet.getBinaryStream("photo"));
				teacher.setQq(resultSet.getString("qq"));
				teacher.setSex(resultSet.getString("sex"));
				teacher.setSn(resultSet.getString("sn"));
				return teacher;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}
	public boolean editPassword(Teacher tea, String newPassword) {
		String sql = "update teacher set password = '"+newPassword+"' where id = " + tea.getId();
		return update(sql);
	}
	

}
