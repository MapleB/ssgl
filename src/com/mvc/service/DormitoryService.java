package com.mvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.EntityDao;
import com.mvc.entity.Dormitory;
import com.mvc.entity.Student;

@Service
public class DormitoryService {
	@Autowired
	private EntityDao entityDao;
	
	
	@Transactional
	public List<Object> getDormitoryList(){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Dormitory.class.getSimpleName()).append(" a ");
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
