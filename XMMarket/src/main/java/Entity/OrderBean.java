package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class OrderBean implements Serializable {
	private Integer orderid;
	private String ouser;// 订单用户名

	private Timestamp odate;//下单日期
	private String paytype;// 付款方式
	private Integer mctypesize;// 商品种类数
	private Integer mcsize;// 商品总数量
	private BigDecimal totalprice;// 总价格
	private String msg;
	private String getname;// 收货人
	private String getaddress;// 收获地址
	private String getphone;// 收货人电话



	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public String getOuser() {
		return ouser;
	}

	public void setOuser(String ouser) {
		this.ouser = ouser;
	}

	public Timestamp getOdate() {
		return odate;
	}

	public void setOdate(Timestamp odate) {
		this.odate = odate;
	}

	public String getPaytype() {
		return paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype = paytype;
	}

	public Integer getMctypesize() {
		return mctypesize;
	}

	public void setMctypesize(Integer mctypesize) {
		this.mctypesize = mctypesize;
	}

	public Integer getMcsize() {
		return mcsize;
	}

	public void setMcsize(Integer mcsize) {
		this.mcsize = mcsize;
	}

	public BigDecimal getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(BigDecimal totalprice) {
		this.totalprice = totalprice;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getGetname() {
		return getname;
	}

	public void setGetname(String getname) {
		this.getname = getname;
	}

	public String getGetaddress() {
		return getaddress;
	}

	public void setGetaddress(String getaddress) {
		this.getaddress = getaddress;
	}

	public String getGetphone() {
		return getphone;
	}

	public void setGetphone(String getphone) {
		this.getphone = getphone;
	}
}
