package entities.service;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {
	public static List<Department> findAll(){
		List<Department> list = new ArrayList<>();
		list.add(new Department(1, "Books"));
		list.add(new Department(1, "Computers"));
		return list;
	}
}
