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
public class StdService {
	@Autowired
	private EntityDao entityDao;
	
	
	@Transactional
	public List<Object> getSudentsByDormitoryId(Integer dormitoryId){
		StringBuffer sff = new StringBuffer();
		sff.append("select c from ").append(Dormitory.class.getSimpleName()).append(" a ,")
		.append(Std.class.getSimpleName()).append(" b ,")
		.append(Student.class).append(" c ")
		.append("where a.id=").append(dormitoryId).append(" and a.id=b.dormitortId and b.studentId=c.id");
		List<Object> list = entityDao.createQuery(sff.toString());
		return list;
	}
	
	public void save(Dormitory dm){
		entityDao.save(dm);
	}
	public void delete(Object obj){
		entityDao.delete(obj);
	}
}
