package com.demo.manager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.demo.manager.util.DbUtil;

public class BaseDao {
    private DbUtil dbutil=new DbUtil();
    
    
    public void closeconn()
    {
    	dbutil.closeConn();
    }
    
    public ResultSet query(String sql)
    {
    	try {
			PreparedStatement prepareStatement = dbutil.getConnection().prepareStatement(sql);
			return prepareStatement.executeQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return null;
    }
    
    public boolean update(String sql)
    {
    	try {
			PreparedStatement prepareStatement = dbutil.getConnection().prepareStatement(sql);
			return prepareStatement.executeUpdate()>0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
  
    public Connection getConnection(){
		return dbutil.getConnection();
	}
}
