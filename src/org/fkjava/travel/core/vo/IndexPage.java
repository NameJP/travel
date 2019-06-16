package org.fkjava.travel.core.vo;

import java.util.List;

import org.fkjava.travel.core.domain.ProductType;

public class IndexPage {

	// 一级类型
	private List<ProductType> topTypes;

	public List<ProductType> getTopTypes() {
		return topTypes;
	}

	public void setTopTypes(List<ProductType> topTypes) {
		this.topTypes = topTypes;
	}
}
