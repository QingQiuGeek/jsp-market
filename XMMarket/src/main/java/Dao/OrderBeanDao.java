package Dao;

import Entity.*;

import java.util.List;
import java.util.Map;

public interface OrderBeanDao {


	void insert(UserBean regUserBean, CartBean cartBean, int mctypesize, String msg,List<ItemBean> itemList) throws Exception;


	int getMaxCid();

	
	List<OrderBean> getOrderList(int startIndex, int pageSize,String uid);

	int getOrderCount(String uid);

	
	OrderBean getOrderBeanByOrderId(String orderid);

	void updateOrderBean(OrderBean orderBean) throws Exception;

}
