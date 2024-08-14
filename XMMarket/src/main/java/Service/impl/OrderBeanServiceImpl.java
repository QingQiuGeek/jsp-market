package Service.impl;

import Entity.*;
import Dao.OrderBeanDao;
import Dao.Impl.OrderBeanDaoImpl;
import Service.OrderBeanService;

import java.util.List;
import java.util.Map;

public class OrderBeanServiceImpl implements OrderBeanService{
	OrderBeanDao orderBeanDao =new OrderBeanDaoImpl();
	

	@Override
	public void insert(UserBean regUserBean,CartBean cartBean, int mctypesize, String msg,List<ItemBean> itemList) throws Exception {
		// TODO Auto-generated method stub
		orderBeanDao.insert(regUserBean,cartBean,mctypesize,msg,itemList);
	}

	
	@Override
	public List<OrderBean> getOrderList(int startIndex, int pageSize,String uid) {
		// TODO Auto-generated method stub
		return orderBeanDao.getOrderList(startIndex,pageSize,uid);
	}

	@Override
	public int getOrderCount(String uid) {
		// TODO Auto-generated method stub
		return orderBeanDao.getOrderCount(uid);
	}



	@Override
	public OrderBean getOrderBeanByOrderId(String orderid) {
		// TODO Auto-generated method stub
		return orderBeanDao.getOrderBeanByOrderId(orderid);
	}


	@Override
	public void updateOrderBean(OrderBean orderBean) throws Exception {
		// TODO Auto-generated method stub
		orderBeanDao.updateOrderBean(orderBean);
	}

	
}
