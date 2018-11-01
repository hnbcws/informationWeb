package com.demo.manager.util;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
      public static String getFormatDate(Date date,String format)
      {
    	  SimpleDateFormat sdf=new SimpleDateFormat();
    	  return sdf.format(date);
      }
}
