package com.mvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvc.dao.EntityDao;
import com.mvc.entity.Admin;

@Service
public class AdminService {
	
	@Autowired
	private EntityDao entityDao;

	public void save(Admin admin) {
		// TODO Auto-generated method stub
		
	}

	public void delete(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	public Admin getAdminByNameAndPwd(String name, String pwd) {
		// TODO Auto-generated method stub
		List<String> propertyNameList = new ArrayList<String>();
		List<String> propertyValueList = new ArrayList<String>();
		propertyNameList.add("name");
		propertyNameList.add("pwd");
		propertyValueList.add(name);
		propertyValueList.add(pwd);
		List<Object> list = entityDao.findByHQLCondition(Admin.class, propertyNameList, propertyValueList);
		if (list.size() > 0) {
			return (Admin) list.get(0);
		} else {
			return null;
		}

	}

}
