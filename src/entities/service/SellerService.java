package entities.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class SellerService {
	
	/*
	 * Load via List
	 * public static List<Seller> findAll(){
		List<Seller> list = new ArrayList<>();
		list.add(new Seller(1, "Books"));
		list.add(new Seller(1, "Computers"));
		return list;
	}*/
	private static SellerDao dao = DaoFactory.createSellerDao();
	
	public static List<Seller> findAll(){
		return dao.findAll();
	}
	public static void saveOrUpdate(Seller obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		}else {
			dao.update(obj);
		}
	}
	public void remove(Seller obj) {
		dao.deleteById(obj.getId());
	}
	
}
