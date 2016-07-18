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
	public Dormitory getDormitoryById(Integer id){
		StringBuffer sff = new StringBuffer();
		sff.append("select a from ").append(Dormitory.class.getSimpleName()).append(" a ")
		.append("where a.id=").append(id);
		List<Object> list = entityDao.createQuery(sff.toString());
		if (list.size()>0) {
			return (Dormitory) list.get(0);
		} else {
			return null;
		}
	}
	
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
