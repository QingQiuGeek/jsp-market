package Service.impl;

import Entity.ItemBean;
import Dao.CategoryBeanDao;
import Dao.ItemBeanDao;
import Dao.Impl.CategoryBeanDaoImpl;
import Dao.Impl.ItemBeanDaoImpl;
import Service.ItemBeanService;

import java.util.List;
import java.util.Map;


public class ItemBeanServiceImpl implements ItemBeanService {

	ItemBeanDao itemBeanDao = new ItemBeanDaoImpl();
	CategoryBeanDao categoryBeanDao = new CategoryBeanDaoImpl();


	@Override
	public List<ItemBean> getItemList(int startIndex, int pageSize, Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		List<ItemBean> itemList = itemBeanDao.getItemList(startIndex, pageSize, searchMap);
		int maxid, minid;
		String maxname, minname;
		Map<Integer, String> categoryMap = categoryBeanDao.getCategoryMap();
		if (itemList != null && itemList.size() > 0) {
			for (ItemBean itemBean : itemList) {
				maxid = itemBean.getMaxid();
				minid = itemBean.getMinid();
				maxname = categoryMap.get(maxid);
				minname = categoryMap.get(minid);
				itemBean.setMaxname(maxname);
				itemBean.setMinname(minname);
			}
		} 
		return itemList;
	}

	@Override
	public int getItemCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		return itemBeanDao.getItemCount(searchMap);
	}


	@Override
	public ItemBean getItemById(String itemid) {
		// TODO Auto-generated method stub
		ItemBean itemBean = itemBeanDao.getItemById(itemid);
		if (itemBean != null) {
			Map<Integer, String> categoryMap = categoryBeanDao.getCategoryMap();
			int maxid, minid;
			String maxname, minname;
			maxid = itemBean.getMaxid();
			minid = itemBean.getMinid();
			maxname = categoryMap.get(maxid);
			minname = categoryMap.get(minid);
			itemBean.setMaxname(maxname);
			itemBean.setMinname(minname);
		}
		return itemBean;
	}

}
