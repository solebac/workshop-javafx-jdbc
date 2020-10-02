package entities.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {
	
	/*
	 * Load via List
	 * public static List<Department> findAll(){
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, "Books"));
		list.add(new Department(1, "Computers"));
		return list;
	}*/
	private static DepartmentDao dao = DaoFactory.createDepartmentDao();
	
	public static List<Department> findAll(){
		return dao.findAll();
	}
	public static void saveOrUpdate(Department obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	
}
