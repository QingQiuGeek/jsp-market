package Service.impl;

import Entity.CategoryBean;
import Dao.CategoryBeanDao;
import Dao.Impl.CategoryBeanDaoImpl;
import Service.CategoryBeanService;

import java.util.List;

public class CategoryBeanServiceImpl implements CategoryBeanService {
	
	
	CategoryBeanDao categoryBeanDao = new CategoryBeanDaoImpl();

	@Override
	public List<CategoryBean> getCategoryList() {
		// TODO Auto-generated method stub
		return categoryBeanDao.getCategoryList();
	}


	@Override
	public void insertCategory(CategoryBean categoryBean) {
		// TODO Auto-generated method stub
		categoryBeanDao.insertCategory(categoryBean);
	}

	@Override
	public void deleteCategory(String cid, String parentid) throws Exception {
		// TODO Auto-generated method stub
		if (cid==null||cid.equals("")) {
			throw new Exception("删除的类别编号为空，无法执行删除操作");
		}else {
			categoryBeanDao.deleteCategory(cid,parentid);
		}
	}



	@Override
	public void updateCategory(CategoryBean categoryBean) {
		// TODO Auto-generated method stub
		categoryBeanDao.updateCategory(categoryBean);
	}

}
