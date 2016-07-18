package com.mvc.controller;

import java.util.List;

import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mvc.entity.Admin;
import com.mvc.entity.Dormitory;
import com.mvc.entity.Std;
import com.mvc.service.DormitoryService;
import com.mvc.service.StudentService;

@Controller
@RequestMapping("/dormitory.do")
public class DormitoryController {
	protected final transient Log log = LogFactory
			.getLog(DormitoryController.class);
	
	@Autowired
	private DormitoryService dormitoryService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping
	public String load(ModelMap modelMap){
		List<Object> dormitoryList = dormitoryService.getDormitoryList();
		modelMap.put("dormitoryList", dormitoryList);
		modelMap.put("msg", "");
		return "dormitory";
	}
	
	@RequestMapping(params = "method=goDormitoryAdd")
	public String goDormitoryAdd(
			HttpServletRequest request,
			HttpSession session,
			ModelMap modelMap){
		modelMap.put("msg", "");
		return "dormitoryAdd";
	}
	
	@RequestMapping(params = "method=goCheckDormitory")
	public String goCheckDormitory(HttpServletRequest request, ModelMap modelMap,HttpSession session){
		String id = request.getParameter("id");
		session.setAttribute("domitortId", id);
		List studentList = studentService.getSudentsByDormitoryId(Integer.valueOf(id));
		modelMap.put("studentList", studentList);
		modelMap.put("msg", "");
		return "checkDormitory";
	}
	
	@RequestMapping(params = "method=addStd")
	public String addStd(HttpServletRequest request, ModelMap modelMap,HttpSession session){
		String domitoryId = (String) session.getAttribute("domitoryId");
		String studentId = request.getParameter("studentId");
		String authority = request.getParameter("authority");
		Std std = new Std();
		
		std.setAuthority(authority);
		std.setDomitoryId(Integer.valueOf(domitoryId));
		std.setStudentId(Integer.valueOf(studentId));
		modelMap.put("msg", "添加成功");
		return "checkDomitory";
	}
	
	@RequestMapping(params = "method=save")
	public String save(
			HttpServletRequest request,
			HttpSession session,
			ModelMap modelMap){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String phone = request.getParameter("phone");
		Dormitory dormitory = new Dormitory();
		try {
			dormitory.setId(Integer.valueOf(id));
		} catch (Exception e) {
			modelMap.put("msg", "编号必须为数字");
			return "dormitoryAdd";
		}
		dormitory.setName(name);
		dormitory.setPhone(phone);
		dormitory.setAddress(address);
		dormitoryService.save(dormitory);
		modelMap.put("msg", "添加成功");
		return "dormitoryAdd";
	}
}
