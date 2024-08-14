package Entity;

import java.io.Serializable;
import java.util.Date;


public class UserBean implements Serializable{
	private Integer uid;
	private String username;
	private String password;
	private String realname;

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private String phone;
	private String address;



	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}


	public String getPhone() {
		return phone;
	}


	public String getAddress() {
		return address;
	}

}
