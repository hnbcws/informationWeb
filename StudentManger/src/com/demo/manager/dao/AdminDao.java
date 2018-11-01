package com.demo.manager.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.demo.manager.model.Admin;

public class AdminDao extends BaseDao {
    public Admin login(String name,String password)
    { 
    	String sql ="select * from admin where name='"+name+"' and password='"+password+"'";
    	ResultSet result = query(sql);
    	try {
			if(result.next())
			{
				Admin admin=new Admin();
			    admin.setId(result.getInt("id"));
			    admin.setName(result.getString("name"));
			    admin.setPassword(result.getString("password"));
			    admin.setStatus(result.getInt("status"));
			    return admin;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }

	public boolean editPassword(Admin admin, String password) {
		String sql="update admin set password = '"+password+"' where id = "+admin.getId();
    	return update(sql);
	}
    
 
   
}
