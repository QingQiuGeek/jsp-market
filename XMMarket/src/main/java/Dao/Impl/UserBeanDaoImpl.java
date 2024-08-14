package Dao.Impl;

import Entity.UserBean;
import Dao.UserBeanDao;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import Utils.C3P0Util;
import Utils.GlobalUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/*
 *  用户信息的持久层操作
 */
public class UserBeanDaoImpl implements UserBeanDao {

	private QueryRunner runner = new QueryRunner(C3P0Util.getDataSource());

	@Override
	public UserBean findByUserName(String userName) {
		// TODO Auto-generated method stub
		try {
			return (UserBean) runner.query("select * from t_user where username = ?",
					new BeanHandler<UserBean>(UserBean.class), userName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public UserBean findByUserNameAndPassword(String userName, String password) {
		// TODO Auto-generated method stub
		try {
			return (UserBean) runner.query("select * from t_user where username = ? and password = ? ",
					new BeanHandler<UserBean>(UserBean.class), userName, password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserBean getUserById(int userid) throws Exception {
		// TODO Auto-generated method stub
		UserBean userBean = null;
		String sql = "select * from t_user where uid = '" + userid + "'";
		try {
			userBean = (UserBean) runner.query(sql, new BeanHandler<UserBean>(UserBean.class));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
		return userBean;
	}


	@Override
	public void insertRegUser(UserBean userBean) {
		// TODO Auto-generated method stub
		try {
			System.out.println(userBean);
			runner.update(
					"insert into t_user(username,password,realname,phone,address)values(?,?,?,?,?)",
					userBean.getUsername(), userBean.getPassword(),  userBean.getRealname(), userBean.getPhone(),userBean.getAddress());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}


	@Override
	public void changePwd(String uid, String newpwd) {
		// TODO Auto-generated method stub
		try {
			// System.out.println(userBean);
			runner.update("update t_user set password=? where uid = ?", newpwd, uid);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public void updateRegUser(UserBean userBean) {
		// TODO Auto-generated method stub
		try {
			System.out.println(userBean);
			runner.update(
					"update t_user set realname=?,phone=?,address=? where uid = ?",
					 userBean.getRealname(), userBean.getPhone(), userBean.getAddress(), userBean.getUid());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
