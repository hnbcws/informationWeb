package com.demo.manager.dao;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.demo.manager.model.Clazz;
import com.demo.manager.model.Page;
import com.demo.manager.util.StringUtil;

public class ClazzDao extends BaseDao {
       public List<Clazz> getClazzList(Clazz clazz,Page page)
       {
    	   List<Clazz> ret=new ArrayList<Clazz>();
    	   String sql="select * from clazz ";
    	   if(!StringUtil.isEmpty(clazz.getBname())) {
    		   sql += "where bname like '%" + clazz.getBname() + "%'";
    	   }
    	   sql+=" limit "+page.getStart()+","+page.getPageSize();
    	   ResultSet resultSet = query(sql);
    	   try {
			while(resultSet.next())
			   {
				   Clazz  cl=new Clazz();
				   cl.setId(resultSet.getInt("id"));
				   cl.setBname(resultSet.getString("bname"));
				   cl.setInfo(resultSet.getString("info"));
				   ret.add(cl);
			   }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   return ret;
       }
       
       public int getClazzListTotal(Clazz clazz)
       {
    	   int total=0;
    	   String sql="select count(*) as total from clazz ";
    	   if(!StringUtil.isEmpty(clazz.getBname())) {
    		   sql+="where bname like '%"+clazz.getBname()+"%'";
    	   }
    	
    	   ResultSet resultSet = query(sql);
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

	public boolean addClazz(Clazz clazz) {
		  
		String sql = "insert into clazz values(null,'"+clazz.getBname()+"','"+clazz.getInfo()+"') ";
 	   return update(sql);
	}

	public boolean deletClazz(int id) {
		String sql = "delete from clazz where id= "+id;
	 	   return update(sql);
	}

	public boolean editClazz(Clazz clazz) {
		String sql="update clazz set bname ='"+clazz.getBname()+"',"+"info='"+clazz.getInfo()+" 'where id="+clazz.getId();
		return update(sql);
	}
}
