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
import com.mvc.service.StdService;
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
	
	@Autowired
	private StdService stdService;
	
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
		Integer id = Integer.valueOf(request.getParameter("id"));
		session.setAttribute("dormitoryId", id);
		List studentList = studentService.getSudentsByDormitoryId(Integer.valueOf(id));
		modelMap.put("studentList", studentList);
		modelMap.put("msg", "");
		return "checkDormitory";
	}
	
	@RequestMapping(params = "method=addStd")
	public String addStd(HttpServletRequest request, ModelMap modelMap,HttpSession session){
		Integer domitoryId = (Integer) session.getAttribute("dormitoryId");
		Dormitory dormitory = dormitoryService.getDormitoryById(domitoryId);
		String studentId = request.getParameter("studentId");
		String authority = request.getParameter("authority");
		Std std = new Std();
		Integer id = null;
		try {
			id = Integer.valueOf(studentId);
			
		} catch (Exception e) {
			// TODO: handle exception
			List studentList = studentService.getSudentsByDormitoryId(domitoryId);
			modelMap.put("studentList", studentList);
			modelMap.put("msg", "学生编号不正确");
			return "checkDormitory";
		}
		if (studentService.getStudentById(id) == null) {
			List studentList = studentService.getSudentsByDormitoryId(domitoryId);
			modelMap.put("studentList", studentList);
			modelMap.put("msg", "学生不存在");
			return "checkDormitory";
		}
		if (stdService.getStdByDormitoryIdAndStudentId(domitoryId, id) != null) {
			List studentList = studentService.getSudentsByDormitoryId(domitoryId);
			modelMap.put("studentList", studentList);
			modelMap.put("msg", "学生已经在该宿舍中了");
			return "checkDormitory";
		}
		if (stdService.getTopStdByDormitoryId(domitoryId)!=null &&  authority.equals("舍长")) {
			List studentList = studentService.getSudentsByDormitoryId(domitoryId);
			modelMap.put("studentList", studentList);
			modelMap.put("msg", "该宿舍只能有一个舍长");
			return "checkDormitory";
		}
		if (dormitory.getSize() >= studentService.getSudentsByDormitoryId(domitoryId).size()) {
			List studentList = studentService.getSudentsByDormitoryId(domitoryId);
			modelMap.put("studentList", studentList);
			modelMap.put("msg", "该宿舍已经满员");
			return "checkDormitory";
		}
		
		List studentList = studentService.getSudentsByDormitoryId(domitoryId);
		modelMap.put("studentList", studentList);
		std.setAuthority(authority);
		std.setDomitoryId(Integer.valueOf(domitoryId));
		std.setStudentId(Integer.valueOf(studentId));
		stdService.save(std);
		modelMap.put("msg", "添加成功");
		return "checkDormitory";
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
		String size = request.getParameter("size");
		Dormitory dormitory = new Dormitory();
		try {
			dormitory.setId(Integer.valueOf(id));
		} catch (Exception e) {
			modelMap.put("msg", "编号必须为数字");
			return "dormitoryAdd";
		}
		dormitory.setId(Integer.valueOf(size));
		dormitory.setName(name);
		dormitory.setPhone(phone);
		dormitory.setAddress(address);
		dormitoryService.save(dormitory);
		modelMap.put("msg", "添加成功");
		return "dormitoryAdd";
	}
}
