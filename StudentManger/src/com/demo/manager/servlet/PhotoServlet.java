package com.demo.manager.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import com.demo.manager.dao.StudentDao;
import com.demo.manager.dao.TeacherDao;
import com.demo.manager.model.Student;
import com.demo.manager.model.Teacher;
import com.lizhou.exception.FileFormatException;
import com.lizhou.exception.NullFileException;
import com.lizhou.exception.ProtocolException;
import com.lizhou.exception.SizeException;
import com.lizhou.fileload.FileUpload;



public class PhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   String method = request.getParameter("method");
		   if("getPhoto".equals(method))
		   {
			   getPhoto(request,response);
		   }else if("SetPhoto".equals(method))
		   {
			   uploadPhoto(request,response);
		   }
	}


	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) {
		  int sid= request.getParameter("sid") == null ? 0 :Integer.parseInt(request.getParameter("sid"));
		  int tid= request.getParameter("tid") == null ? 0 :Integer.parseInt(request.getParameter("tid"));
		  FileUpload fileUpload = new FileUpload(request);
		  fileUpload.setFileFormat("jpg");
		  fileUpload.setFileFormat("png");
		  fileUpload.setFileFormat("jpeg");
		  fileUpload.setFileSize(2048);
		  response.setCharacterEncoding("utf-8");
		  try {
			InputStream uploadInputStream = fileUpload.getUploadInputStream();
			if(sid != 0) {
			   Student stu = new Student();
			   stu.setId(sid);
			   stu.setPhoto(uploadInputStream);
			   StudentDao studao = new StudentDao();
			   if(studao.setStudentPhoto(stu)) {
				   response.getWriter().write("<div id='message'>上传成功</div>");
			   }else {
				   response.getWriter().write("<div id='message'>上传失败</div>");
			   }
			}
			if(tid != 0) {
				   Teacher tea = new Teacher();
				   tea.setId(tid);
				   tea.setPhoto(uploadInputStream);
				   TeacherDao teadao = new TeacherDao();
				   if(teadao.setTeacherPhoto(tea)) {
					   response.getWriter().write("<div id='message'>上传成功</div>");
				   }else {
					   response.getWriter().write("<div id='message'>上传失败</div>");
				   }
				}
		} catch (ProtocolException  e) {
			// TODO Auto-generated catch block
			try {
				response.getWriter().write("<div id='message'>上传协议 错误</div>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}catch(NullFileException e1) {
			try {
				response.getWriter().write("<div id='message'>上传的文件为空!</div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e1.printStackTrace();
		}catch(SizeException e2) {
			try {
				response.getWriter().write("<div id='message'>上传文件大小不能超过"+fileUpload.getFileSize()+"！</div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e2.printStackTrace();
		}catch(FileFormatException e3) {
			try {
				response.getWriter().write("<div id='message'>上传文件格式不正确，请上传 "+fileUpload.getFileFormat()+" 格式的文件！</div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e3.printStackTrace();
		}catch(IOException e4) {
			try {
				response.getWriter().write("<div id='message'>读取文件出错！</div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e4.printStackTrace();
		}catch(FileUploadException e5) {
			try {
				response.getWriter().write("<div id='message'>上传文件失败！</div>");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			e5.printStackTrace();
		}
	}


	private void getPhoto(HttpServletRequest request, HttpServletResponse response) {
		int sid = request.getParameter("sid") == null ? 0 : Integer.parseInt(request.getParameter("sid"));
		int tid = request.getParameter("tid") == null ? 0 : Integer.parseInt(request.getParameter("tid"));
		if(sid != 0){
			//学生
			StudentDao studentDao = new StudentDao();
			Student student = studentDao.getStudent(sid);
			studentDao.closeconn();
			if(student != null){
				InputStream photo = student.getPhoto();
				if(photo != null){
					try {
						byte[] b = new byte[photo.available()];
						photo.read(b);
						response.getOutputStream().write(b,0,b.length);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
		}	
			if(tid != 0){
				//
				TeacherDao teacherDao = new TeacherDao();
				Teacher teacher = teacherDao.getTeacher(tid); 
				teacherDao.closeconn();
				if(teacher != null){
					InputStream photo = teacher.getPhoto();
					if(photo != null){
						try {
							byte[] b = new byte[photo.available()];
							photo.read(b);
							response.getOutputStream().write(b,0,b.length);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
				}
		    }
		   String path = request.getSession().getServletContext().getRealPath("");
		   File file = new File(path+"\\file\\logo.jpg");
		   try {
			FileInputStream  input = new FileInputStream(file);
			byte[] b = new byte[input.available()];
			input.read(b);
			response.getOutputStream().write(b,0,b.length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  }