package org.fkjava.travel.core.vo;

import java.util.List;

import org.fkjava.travel.core.domain.Product;
import org.fkjava.travel.core.domain.ProductType;
import org.springframework.data.domain.Page;

public class SearchPage {
	// 一级类型
	private List<ProductType> topTypes;
	// 根据类型的ID得到的产品类型对象
	private ProductType type;
	// 查询出来的一页产品列表
	private Page<Product> productPage;

	public List<ProductType> getTopTypes() {
		return topTypes;
	}

	public void setTopTypes(List<ProductType> topTypes) {
		this.topTypes = topTypes;
	}

	public ProductType getType() {
		return type;
	}

	public void setType(ProductType type) {
		this.type = type;
	}

	public Page<Product> getProductPage() {
		return productPage;
	}

	public void setProductPage(Page<Product> productPage) {
		this.productPage = productPage;
	}
}
