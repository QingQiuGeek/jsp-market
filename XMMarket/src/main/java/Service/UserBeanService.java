package Service;

import Entity.UserBean;
import Utils.GlobalUtil.UserType;

import java.util.List;
import java.util.Map;

public interface UserBeanService {
	UserBean login(UserBean user);
	UserBean getUserById(int userid) throws Exception;

	void insertRegUser(UserBean userBean);


	void changePwd(String uid, String newpwd);


	void updateRegUser(UserBean userBean);


	UserBean findByUserName(String username);
}
