package Dao.Impl;

import Entity.CategoryBean;
import Dao.CategoryBeanDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import Utils.C3P0Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryBeanDaoImpl implements CategoryBeanDao {
	private QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());

	@Override
	public List<CategoryBean> getCategoryList() {
		// TODO Auto-generated method stub
		List<CategoryBean> categoryBeans = null;
		List<CategoryBean> childCategory = null;
		try {
			categoryBeans = runner.query("select * from t_mc_type where parentid=0 order by cid asc",
					new BeanListHandler<CategoryBean>(CategoryBean.class));
			for (CategoryBean categoryBean : categoryBeans) {
				int cid = categoryBean.getCid();
				childCategory = runner.query("select * from t_mc_type where parentid=" + cid + " order by cid asc",
						new BeanListHandler<CategoryBean>(CategoryBean.class));
				categoryBean.setChildCategory(childCategory);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}

		return categoryBeans;
	}


	@Override
	public void insertCategory(CategoryBean categoryBean) {
		// TODO Auto-generated method stub
		int cid = categoryBean.getCid();
		String classname = "'" + categoryBean.getClassname() + "'";
		int parentid = categoryBean.getParentid();

		String sql = "insert into t_mc_type (cid,classname,parentid) values(" + cid + "," + classname + "," + parentid
				+ ")";
		// System.out.println(sql);
		try {
			runner.update(sql, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCategory(String cid, String parentid) throws Exception {
		// TODO Auto-generated method stub
		Connection connection = null;
		try {
			connection = C3P0Util.getConnection();
			// 设置事务不自动提交
			connection.setAutoCommit(false);
			String sql1 = null;
			if (parentid.equals("0")) {
				// 删除的是大类别
				sql1 = "delete from t_mc_type where parentid = " + cid;
				runner.update(connection, sql1, null);
			}
			// 删除自身，无论大类小类都要删除自身
			String sql2 = "delete from t_mc_type where cid = " + cid;
			// System.out.println(sql1 + "\n" + sql2);
			runner.update(connection, sql2, null);

			connection.commit();
		} catch (Exception e) {
			// 处理事务中发生的异常,进行回滚
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new Exception("数据库持久层异常");
		} finally {
			C3P0Util.closeAll(connection, null, null);
		}

	}


	@Override
	public Map<Integer, String> getCategoryMap() {
		// TODO Auto-generated method stub
		Map<Integer, String> categoryMap = new HashMap<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			connection = C3P0Util.getConnection();
			statement = connection.createStatement();
			String sql = "select cid,classname from t_mc_type order by cid asc";
			resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				int cid = resultSet.getInt("cid");
				String classname = resultSet.getString("classname");
				categoryMap.put(cid, classname);
			}
			// System.out.println(categoryMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			C3P0Util.closeAll(connection, statement, resultSet);
		}
		return categoryMap;
	}



	@Override
	public void updateCategory(CategoryBean categoryBean) {
		// TODO Auto-generated method stub
		int cid = categoryBean.getCid();
		String classname = "'" + categoryBean.getClassname() + "'";
		int parentid = categoryBean.getParentid();

		String sql = "update t_mc_type set classname=" + classname + ",parentid=" + parentid + " where cid=" + cid + "";
		System.out.println("update sql="+sql);
		try {
			runner.update(sql, null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
