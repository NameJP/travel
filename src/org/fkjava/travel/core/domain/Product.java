package org.fkjava.travel.core.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Product {

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(length = 36)
	private String id;
	@Column(length = 500)
	private String name;
	@ManyToOne()
	@JoinColumn(name = "TYPE_ID")
	private ProductType type;
	// 价格通常不会只是一个简单的值，在实际的项目中，会结合【规则引擎】来实现
	// 不同的用户，优惠不同！
	// 考虑到简单起见，所以只考虑一个固定的价格。
	private Double price;
	// 行程天数
	private int travelDays;
	// 出发的行程日历
	// 加上级联操作，在保存产品的时候，就会同时把关联的日常计划保存起来
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "PRODUCT_ID")
	private List<ProductCalendar> startOuts;
	// 描述
	@Column(length = 2000)
	private String description;
	// 路线介绍
	@Column(length = 2000)
	private String intraduction;
	// 费用说明
	@Column(length = 2000)
	private String cost;

	// 集合映射
	@ElementCollection()
	private List<String> imageIds;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public int getTravelDays() {
		return travelDays;
	}

	public void setTravelDays(int travelDays) {
		this.travelDays = travelDays;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ProductCalendar> getStartOuts() {
		return startOuts;
	}

	public void setStartOuts(List<ProductCalendar> startOuts) {
		this.startOuts = startOuts;
	}

	public String getIntraduction() {
		return intraduction;
	}

	public void setIntraduction(String intraduction) {
		this.intraduction = intraduction;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public List<String> getImageIds() {
		return imageIds;
	}

	public void setImageIds(List<String> imageIds) {
		this.imageIds = imageIds;
	}
}
