package org.fkjava.travel.core.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "`Order`")
public class Order {

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(length = 36)
	private String id;

	@ManyToOne()
	@JoinColumn(name = "USER_ID")
	private User user;
	// 订单生成时间
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "`time`")
	private Date time;
	// 保存枚举的原始索引
	// @Enumerated(EnumType.ORDINAL)
	// 保存枚举的对象名
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToMany
	@JoinColumn(name = "ORDER_ID")
	private List<OrderTravelHuman> humans;
	@ManyToOne
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	// 订单状态的枚举
	public static enum Status {
		/**
		 * 已经支付
		 */
		PAYMENTED,
		/**
		 * 未支付
		 */
		UNPYAMENT,
		/**
		 * 已经完成行程
		 */
		FINISHED,
		/**
		 * 已经取消
		 */
		CANCELED
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<OrderTravelHuman> getHumans() {
		return humans;
	}

	public void setHumans(List<OrderTravelHuman> humans) {
		this.humans = humans;
	}
}
