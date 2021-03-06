package cn.tedu.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.context.Context;

import cn.tedu.dao.JobDao;
import cn.tedu.entity.Job;
import cn.tedu.utils.ThUtils;

/**
 * 查找所有人员信息
 * Servlet implementation class JobCheckServlet
 */
public class JobCheckServlet extends HttpServlet {
	
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf8");
		JobDao dao = new JobDao();
		
		List<Job> job = dao.getJob();
		
		Context context = new Context();
		context.setVariable("job", job);
		ThUtils.write("jobCheck", response, context);
	}

}
