package Service.impl;

import Entity.CartBean;
import Entity.ItemBean;
import Service.CartService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class CartServiceImpl implements CartService{


	@Override
	public CartBean updateCart(CartBean cartBean) {
		// TODO Auto-generated method stub
		List<ItemBean> itemList = cartBean.getItemList();
		int totalCount=0;
		BigDecimal totalPrice=new BigDecimal(0.00);
		String totalPrice_format="0.00";
		for(ItemBean itemBean:itemList){
			int singleCount = itemBean.getSingleCount();
			BigDecimal itemPrice=itemBean.getPrice();
			BigDecimal singlePrice=itemPrice.multiply(new BigDecimal(singleCount));
			itemBean.setSinglePrice(singlePrice);
			totalCount+=singleCount;
			totalPrice=totalPrice.add(singlePrice);
		}
		DecimalFormat decimalFormat=new DecimalFormat("#0.00");
		totalPrice_format=decimalFormat.format(totalPrice);
		cartBean.setTotalCount(totalCount);
		cartBean.setTotalPrice(totalPrice);
		cartBean.setTotalPrice_format(totalPrice_format);
		return cartBean;
	}

}
