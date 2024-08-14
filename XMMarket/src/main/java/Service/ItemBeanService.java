package Service;

import Entity.ItemBean;

import java.util.List;
import java.util.Map;


public interface ItemBeanService {


	List<ItemBean> getItemList(int startIndex,int pageSize, Map<String, Object> searchMap);


	int getItemCount(Map<String, Object> searchMap);

	ItemBean getItemById(String itemid);

}
