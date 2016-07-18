package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.EntityDao;
import com.mvc.entity.Dormitory;
import com.mvc.entity.Std;
import com.mvc.entity.Student;

@Service
public class StudentService {
	@Autowired
	private EntityDao entityDao;
	
	@Transactional
	public List<Object> getStudentList(){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Student.class.getSimpleName()).append(" a ");
		List<Object> list = entityDao.createQuery(sff.toString());
		return list;
	}
	
	@Transactional
	public Student getStudentById(Integer id){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Student.class.getSimpleName()).append(" a ")
		.append("where a.id=").append(id);
		List<Object> list = entityDao.createQuery(sff.toString());
		if (list.size()>0) {
			return (Student) list.get(0);
		} else {
			return null;
		}
	}
	

	
	@Transactional
	public List<Object> getSudentsByDormitoryId(Integer dormitoryId){
		StringBuffer sff = new StringBuffer();
		sff.append("select new map(c.id as id, c.name as name, b.authority as authority) from ").append(Dormitory.class.getSimpleName()).append(" a ,")
		.append(Std.class.getSimpleName()).append(" b ,")
		.append(Student.class.getSimpleName()).append(" c ")
		.append("where a.id=").append(dormitoryId).append(" and a.id=b.dormitoryId and b.studentId=c.id");
		List<Object> list = entityDao.createQuery(sff.toString());
		return list;
	}
	
	
	
	public void save(Student st){
		entityDao.save(st);
	}
	public void delete(Object obj){
		entityDao.delete(obj);
	}
}
