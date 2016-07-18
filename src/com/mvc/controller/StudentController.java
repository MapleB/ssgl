package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mvc.entity.Std;
import com.mvc.entity.Student;
import com.mvc.service.StudentService;

@Controller
@RequestMapping("/student.do")
public class StudentController {
	protected final transient Log log = LogFactory
	.getLog(StudentController.class);
	@Autowired
	private StudentService studentService;
	public StudentController(){
		
	}
	
	@RequestMapping
	public String load(ModelMap modelMap){
		List<Object> list = studentService.getStudentList();
		modelMap.put("studentList", list);
		modelMap.put("msg", "");
		return "student";
	}
	
	@RequestMapping(params = "method=goStudentAdd")
	public String goStudentAdd(HttpServletRequest request, ModelMap modelMap) throws Exception{
		modelMap.put("msg", "");
		return "studentAdd";
	}
	
	@RequestMapping(params = "method=save")
	public String save(HttpServletRequest request, ModelMap modelMap){
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String classes = request.getParameter("classes");
		Student st = new Student();
		try{
			st.setId(Integer.valueOf(id));
		}
		catch(Exception e){
			log.error(e.getMessage());
			modelMap.put("msg", "编号必须为数字");
			return "studentAdd";
		}
		st.setAge(age);
		st.setClasses(classes);
		st.setGender(gender);
		st.setName(name);
		st.setPhone(phone);
		studentService.save(st);
		modelMap.put("msg", "添加成功");
		return "studentAdd";
	}
	
	
	
	@RequestMapping(params = "method=del")
	public String del(@RequestParam("id") String id, HttpServletResponse response, ModelMap modelMap){
		try{
			Student st = new Student();
			st.setId(Integer.valueOf(id));
			studentService.delete(st);
		}
		catch(Exception e){
			log.error(e.getMessage());
			e.printStackTrace();
		}
		List<Object> list = studentService.getStudentList();
		modelMap.put("studentList", list);
		modelMap.put("msg", "删除成功");
		return "student";
	}
}
