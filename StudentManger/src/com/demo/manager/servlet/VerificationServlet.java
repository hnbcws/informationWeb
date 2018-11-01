package com.demo.manager.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.manager.util.Verification;;

public class VerificationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4919529414762301338L;
	public void doGet(HttpServletRequest request,HttpServletResponse reponse) throws IOException{
		doPost(request, reponse);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse reponse) throws IOException{
		String method = request.getParameter("method");
		if("loginverification".equals(method)){
			generateLoginCpacha(request, reponse);
			return;
		}
		reponse.getWriter().write("error method");
	}
	private void generateLoginCpacha(HttpServletRequest request,HttpServletResponse reponse) throws IOException{
		Verification veriUtil = new Verification();
		String generatorVCode = veriUtil.generatorVCode();
		request.getSession().setAttribute("loginverification", generatorVCode);
		BufferedImage generatorRotateVCodeImage = veriUtil.generatorRotateVCodeImage(generatorVCode, true);
		ImageIO.write(generatorRotateVCodeImage, "gif", reponse.getOutputStream());
	}
}
