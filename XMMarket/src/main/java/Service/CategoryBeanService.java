package Service;

import Entity.CategoryBean;

import java.util.List;

public interface CategoryBeanService {
	List<CategoryBean> getCategoryList();

	void insertCategory(CategoryBean categoryBean);

	void deleteCategory(String cid, String parentid) throws Exception;

	void updateCategory(CategoryBean categoryBean);
}
