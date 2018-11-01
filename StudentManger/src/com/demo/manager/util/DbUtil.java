package com.demo.manager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbUtil {
      private String dbUrl="jdbc:mysql://localhost:3306/schoolmanger?useUnicode=true&characterEncoding=utf8";
      private String dbUser="root";
      private String dbPassword="";
      private String jdbcName="com.mysql.jdbc.Driver";
      private Connection conn=null;
      public Connection getConnection()
      { 
    	  try
    	  {
    		  Class.forName(jdbcName);
        	  conn=DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        	  System.out.println("���ݿ����ӳɹ�");
        	  
		  } 
    	  catch (Exception e) 
    	  {
			// TODO Auto-generated catch block
    		  System.out.println("���ݿ�����ʧ��");
			e.printStackTrace();
		  }
    	  return conn;
      }
      
      public void closeConn()
      {
    	  if(conn!=null)
			try {
				conn.close();
				System.out.println("���ݿ������ѹرգ�");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
      }
  
}
