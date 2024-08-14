
package Dao;

import Entity.UserBean;
import Utils.GlobalUtil.UserType;

import java.util.List;
import java.util.Map;

public interface UserBeanDao {

	UserBean findByUserName(String userName);
	
	UserBean findByUserNameAndPassword(String userName,String password);



	UserBean getUserById(int userid) throws Exception;


	void insertRegUser(UserBean userBean);


	void changePwd(String uid, String newpwd);


	void updateRegUser(UserBean userBean);
}
