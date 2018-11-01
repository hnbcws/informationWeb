package com.demo.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.manager.model.Admin;
import com.demo.manager.model.Clazz;
import com.demo.manager.model.Page;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;
import com.demo.manager.util.StringUtil;

public class StudentDao extends BaseDao {
	public boolean addStudent(Student stu) {
		  
		String sql = "insert into student values(null,'"+stu.getSn()+"','"+stu.getName()+"' ";
		sql+=",'"+stu.getPassword()+"',"+stu.getClazzId();
		sql+=",'"+stu.getSex()+"','"+stu.getMobile()+"'";
		sql+=",'"+stu.getQq()+"',null)";
 	   return update(sql);
	}
	
	public boolean editStudent(Student student) {
		// TODO Auto-generated method stub
		String sql = "update student set name = '"+student.getName()+"'";
		sql += ",sex = '" + student.getSex() + "'";
		sql += ",mobile = '" + student.getMobile() + "'";
		sql += ",qq = '" + student.getQq() + "'";
		sql += ",clazz_id = " + student.getClazzId();
		sql += " where id = " + student.getId();
		return update(sql);
	}
	

	
	public boolean setStudentPhoto(Student student) {
		// TODO Auto-generated method stub
		String sql = "update student set photo = ? where id = ?";
		Connection connection = getConnection();
		try {
			PreparedStatement prepareStatement = connection.prepareStatement(sql);
			prepareStatement.setBinaryStream(1, student.getPhoto());
			prepareStatement.setInt(2, student.getId());
			return prepareStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return update(sql);
	}
	
	
	
	public Student getStudent(int id)
	{
		String sql = "select * from student where id ="+id;
		Student stu=null;
		ResultSet resultSet = query(sql);
		try {
			if(resultSet.next())
			{
				stu =new Student();
				stu.setId(resultSet.getInt("id"));
				stu.setClazzId(resultSet.getInt("clazz_id"));
				stu.setMobile(resultSet.getString("mobile"));
				stu.setName(resultSet.getString("name"));
				stu.setPassword(resultSet.getString("password"));
				stu.setQq(resultSet.getString("qq"));
				stu.setSex(resultSet.getString("sex"));
				stu.setSn(resultSet.getString("sn"));
				stu.setPhoto(resultSet.getBinaryStream("photo"));
				return stu;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stu;
	}
	
	public List<Student> getStudentList(Student stu,Page page)
    {
 	   List<Student> ret=new ArrayList<Student>();
 	   String sql="select * from student ";
 	   if(!StringUtil.isEmpty(stu.getName())) {
 		   sql += "and name like '%" + stu.getName() + "%'";
 	   }
 	   if(stu.getClazzId() != 0)
 	   {
 		   sql += " and clazz_id = "+ stu.getClazzId(); 
 	   }
 	  if(stu.getId() != 0) {
		   sql+=" and id = "+stu.getId();
	   }
 	   sql+=" limit "+page.getStart()+","+page.getPageSize();
 	   ResultSet resultSet = query(sql.replaceFirst("and", "where"));
 	   try {
			while(resultSet.next())
			   {
				    Student  stu1 = new Student();
				    stu1.setId(resultSet.getInt("id"));
					stu1.setClazzId(resultSet.getInt("clazz_id"));
					stu1.setMobile(resultSet.getString("mobile"));
					stu1.setName(resultSet.getString("name"));
					stu1.setPassword(resultSet.getString("password"));
					stu1.setQq(resultSet.getString("qq"));
					stu1.setSex(resultSet.getString("sex"));
					stu1.setSn(resultSet.getString("sn"));
					stu1.setPhoto(resultSet.getBinaryStream("photo"));
				    ret.add(stu1);
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	   return ret;
    }
    
    public int getStudentListTotal(Student stu)
    {
 	   int total=0;
 	   String sql="select count(*) as total from student";
 	  if(!StringUtil.isEmpty(stu.getName())) {
		   sql += " and name like '%" + stu.getName() + "%'";
	   }
	   if(stu.getClazzId() != 0)
	   {
		   sql += " and clazz_id ="+ stu.getClazzId(); 
	   }
	   if(stu.getId() != 0) {
		   sql+=" and id = "+stu.getId();
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

	public boolean deleteStudent(String ids) {
		
		String sql = "delete from student where id in("+ids+")";
		return update(sql);
	}

	public Student login(String name, String password) {
		String sql ="select * from student where name='"+name+"' and password='"+password+"'";
    	ResultSet resultSet = query(sql);
    	try {
			if(resultSet.next())
			{
				Student student =new Student();
			
				student.setId(resultSet.getInt("id"));
				student.setClazzId(resultSet.getInt("clazz_id"));
				student.setMobile(resultSet.getString("mobile"));
				student.setName(resultSet.getString("name"));
				student.setPassword(resultSet.getString("password"));
				student.setPhoto(resultSet.getBinaryStream("photo"));
				student.setQq(resultSet.getString("qq"));
				student.setSex(resultSet.getString("sex"));
				student.setSn(resultSet.getString("sn"));
				return student;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
	}

	
	public boolean editPassword(Student stu, String newPassword) {
		String sql = "update student set password = '"+newPassword+"' where id = " + stu.getId();
		return update(sql);
	}

	

	
}
