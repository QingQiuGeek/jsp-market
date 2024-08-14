package Dao.Impl;

import Entity.ItemBean;
import Dao.ItemBeanDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import Utils.C3P0Util;
import Utils.GlobalUtil;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ItemBeanDaoImpl implements ItemBeanDao {

	private QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());

	public String getSearchSql(Map<String, Object> searchMap) {
		StringBuilder whereSQL = new StringBuilder();
		whereSQL.setLength(0);

		String maxid = String.valueOf(searchMap.get("maxid"));
		String minid = String.valueOf(searchMap.get("minid"));
		String p_itemname = String.valueOf(searchMap.get("p_itemname"));
		String searchKeyWord=String.valueOf(searchMap.get("searchKeyWord"));
		
		if (GlobalUtil.isNotNull(maxid) && maxid.equals("0") == false) {
			whereSQL.append(" and maxid = '" + maxid + "'");
		}
		if (GlobalUtil.isNotNull(minid) && minid.equals("0") == false) {
			whereSQL.append(" and minid = '" + minid + "'");
		}
		if (GlobalUtil.isNotNull(p_itemname)) {
			whereSQL.append("and  name like '%" + p_itemname + "%'");
		}
		if (GlobalUtil.isNotNull(searchKeyWord)) {
			whereSQL.append("and (name like '%" + searchKeyWord + "%' or description like '%"+searchKeyWord+"%') ");
		}
		
		return whereSQL.toString();
	}


	@Override
	public List<ItemBean> getItemList(int startIndex, int pageSize, Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		List<ItemBean> itemBeans = null;
		try {
			itemBeans = runner.query(
					"select * from t_mc where isdel=0 "+this.getSearchSql(searchMap)+" order by id desc limit " + startIndex + "," + pageSize + "",
					new BeanListHandler<ItemBean>(ItemBean.class));
			// System.out.println(itemBeans);
//			System.out.println("getItemList="+"select * from t_mc where isdel=0 "+ this.getSearchSql(searchMap)+" order by id desc limit " + startIndex + "," + pageSize + "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return itemBeans;
	}


	@Override
	public int getItemCount(Map<String, Object> searchMap) {
		// TODO Auto-generated method stub
		try {
			Long itemCountLong = runner.query("select count(id) from t_mc where isdel=0 "+this.getSearchSql(searchMap), new ScalarHandler<Long>());
			// sql语句count(列名)返回的并不是int类型，而是long型值，调整代码进行类型转换
//			System.out.println("getItemCount="+"select count(id) from t_mc where isdel =0 "+this.getSearchSql(searchMap));
			String tString = itemCountLong.toString();
			int itemCount = Integer.parseInt(tString);
			if (itemCount > 0) {
				return itemCount;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public ItemBean getItemById(String itemid) {
		// TODO Auto-generated method stub
		ItemBean itemBean = null;
		try {
			itemBean = runner.query("select * from t_mc where id = " + itemid + "",
					new BeanHandler<ItemBean>(ItemBean.class));
			//设置取出对象时，默认singleCount=1
			itemBean.setSingleCount(1);
			// System.out.println(itemBeans);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return itemBean;
	}

}
