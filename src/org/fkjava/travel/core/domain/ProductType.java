package org.fkjava.travel.core.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

// 产品类型
@Entity
public class ProductType {

	@Id
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@GeneratedValue(generator = "uuid2")
	@Column(length = 36)
	private String id;
	private String name;

	// 上级类型，没有上级类型的时候表示顶级类型
	@ManyToOne()
	@JoinColumn(name = "PARENT_ID") // 外键的列名
	@JsonIgnore // 转换为JSON的时候，忽略此属性
	private ProductType parent;
	// 下级类型
	// cascade = CascadeType.ALL当修改父类型的时候，子类型也会修改
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	@JsonProperty("children") // 指定生成的JSON属性名称
	private List<ProductType> childs;

	// 数据库本身已经存储了所有的下级类型
	// 该成员变量不是为了保存关系，而是为了递归产生所有子类型
	@Transient // 不要保存到数据库里面
	private List<ProductType> allChilds;

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

	public ProductType getParent() {
		return parent;
	}

	public void setParent(ProductType parent) {
		this.parent = parent;
	}

	public List<ProductType> getChilds() {
		return childs;
	}

	public void setChilds(List<ProductType> childs) {
		this.childs = childs;
	}

	// 通过递归算法把所有子类型、自己本身全部拼在一个集合里面。
	// 通过in查询就可以把所有子类型的数据全部查询出来。
	public List<ProductType> getAllChilds() {
		// 该方法完全是自己写的，Hibernate不会帮我们查询数据！
		allChilds = new LinkedList<>();

		// 获取当前类型的下级类型
		List<ProductType> childs = this.getChilds();
		childs.forEach(c -> {
			// 获取子类型的下级类型
			List<ProductType> types = c.getAllChilds();
			this.allChilds.addAll(types);
		});
		// 本身、当前类型加入集合
		allChilds.add(this);
		// 下级类型加入集合
		allChilds.addAll(childs);

		return allChilds;
	}
}
