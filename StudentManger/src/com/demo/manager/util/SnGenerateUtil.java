package com.demo.manager.util;

import java.util.Date;

public class SnGenerateUtil {
       public static String generateSn(int clazzId)
       {
    	   String sn="";
    	   sn="S"+clazzId+System.currentTimeMillis();
    	   return sn;
    	   
       }
       
       public static String generateTeaSn(int clazzId)
       {
    	   String sn="";
    	   sn="T"+clazzId+System.currentTimeMillis();
    	   return sn;
    	   
       }
}
