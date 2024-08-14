package Service.impl;

import Entity.UserBean;
import Dao.UserBeanDao;
import Dao.Impl.UserBeanDaoImpl;
import Service.UserBeanService;
import Utils.GlobalUtil.UserType;

import java.util.List;
import java.util.Map;

public class UserBeanServiceImpl implements UserBeanService {

	UserBeanDao userBeanDao = new UserBeanDaoImpl();

	@Override
	public UserBean login(UserBean user) {
		// TODO Auto-generated method stub
		return userBeanDao.findByUserNameAndPassword(user.getUsername(), user.getPassword());
	}


	@Override
	public UserBean getUserById(int userid) throws Exception {
		// TODO Auto-generated method stub
		if (userid<=0) {
			throw new Exception("用户id号错误");
		}
		return userBeanDao.getUserById(userid);
	}


	@Override
	public void insertRegUser(UserBean userBean) {
		// TODO Auto-generated method stub
		userBeanDao.insertRegUser(userBean);
	}


	@Override
	public void changePwd(String uid, String newpwd) {
		// TODO Auto-generated method stub
		userBeanDao.changePwd(uid,newpwd);
	}


	@Override
	public void updateRegUser(UserBean userBean) {
		// TODO Auto-generated method stub
		userBeanDao.updateRegUser(userBean);
	}


	@Override
	public UserBean findByUserName(String username) {
		// TODO Auto-generated method stub
		return userBeanDao.findByUserName(username);
	}

}
