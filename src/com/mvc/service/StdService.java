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
	public Std getStdByDormitoryIdAndStudentId(Integer dormitoryId, Integer studentId){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Std.class.getSimpleName()).append(" a ")
		.append("where a.dormitoryId=").append(dormitoryId).append(" and a.studentId=").append(studentId);
		List<Object> list = entityDao.createQuery(sff.toString());
		if (list.size()>0) {
			return (Std) list.get(0);
		}
		return null;
	}
	
	@Transactional
	public Std getTopStdByDormitoryId(Integer dormitoryId){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Std.class.getSimpleName()).append(" a ")
		.append("where a.dormitoryId=").append(dormitoryId).append(" and a.authority='舍长'");
		List<Object> list = entityDao.createQuery(sff.toString());
		if (list.size()>0) {
			return (Std) list.get(0);
		}
		return null;
	}

	
	public void save(Std std){
		entityDao.save(std);
	}
	public void delete(Object obj){
		entityDao.delete(obj);
	}
}
