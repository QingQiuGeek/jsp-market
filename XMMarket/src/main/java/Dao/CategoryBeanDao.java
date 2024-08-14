package Dao;

import Entity.CategoryBean;

import java.util.List;
import java.util.Map;

public interface CategoryBeanDao {
	
	List<CategoryBean> getCategoryList();

	void insertCategory(CategoryBean categoryBean);

	void deleteCategory(String cid, String parentid) throws Exception;

	Map<Integer, String> getCategoryMap();



	void updateCategory(CategoryBean categoryBean);
	
}
