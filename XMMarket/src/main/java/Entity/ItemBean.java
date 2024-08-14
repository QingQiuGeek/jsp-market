package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ItemBean implements Serializable {
	private int singleCount;

	private String minname = "";// 小类名称

	public String getMinname() {
		return minname;
	}

	public void setMinname(String minname) {
		this.minname = minname;
	}

	private BigDecimal singlePrice;

	public BigDecimal getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}

	public int getSingleCount() {
		return singleCount;
	}

	public void setSingleCount(int singleCount) {
		this.singleCount = singleCount;
	}

	private int id;// 编号
	private String name;// 名称
	private String description;// 描述
	private BigDecimal price;// 价格
	private String filepath = "";// 图片路径
	private String filename = "";// 图片名称
	private int isdel;// 是否删除（缺货）
	private int maxid;// 大类id
	private int minid;// 小类id
	private String maxname = "";// 大类名称

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getIsdel() {
		return isdel;
	}

	public void setIsdel(int isdel) {
		this.isdel = isdel;
	}

	public int getMaxid() {
		return maxid;
	}

	public void setMaxid(int maxid) {
		this.maxid = maxid;
	}

	public int getMinid() {
		return minid;
	}

	public void setMinid(int minid) {
		this.minid = minid;
	}

	public String getMaxname() {
		return maxname;
	}

	public void setMaxname(String maxname) {
		this.maxname = maxname;
	}
}
