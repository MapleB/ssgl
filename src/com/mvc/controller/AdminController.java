package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.entity.Admin;
import com.mvc.entity.Student;
import com.mvc.service.AdminService;
import com.mvc.service.AdminService;
import com.mvc.service.DormitoryService;
//import com.mvc.service.DormitoryService;
//import com.mvc.service.StudentServiceImpl;

@Controller
@RequestMapping("/admin.do")
public class AdminController {
	protected final transient Log log = LogFactory
	.getLog(AdminController.class);
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private DormitoryService dormitoryService;
	
	public AdminController(){
		
	}
	
	//初始进入login界面
	@RequestMapping
	public String load(ModelMap modelMap){
		modelMap.put("msg", "");
		return "login";
	}
	
	@RequestMapping(params = "method=login")
	public String login(@RequestParam("name") String name,
			@RequestParam("pwd") String pwd,
			HttpServletRequest request,
			HttpSession session,
			ModelMap modelMap){
		Admin admin = adminService.getAdminByNameAndPwd(name, pwd);
		if (admin == null) {
			modelMap.put("msg", "账号密码错误");
			return "login";
		} else {
			List<Object> dormitoryList = dormitoryService.getDormitoryList();
			session.setAttribute("admin", admin);
			modelMap.put("dormitoryList", dormitoryList);
			modelMap.put("msg", "");
			return "dormitory";
		}
		
	}
	
}

