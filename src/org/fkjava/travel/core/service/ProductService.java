package org.fkjava.travel.core.service;

import java.util.List;

import org.fkjava.travel.core.domain.Product;
import org.fkjava.travel.core.domain.ProductType;
import org.fkjava.travel.core.vo.IndexPage;
import org.fkjava.travel.core.vo.Result;
import org.fkjava.travel.core.vo.SearchPage;

public interface ProductService {

	/**
	 * 保存产品类型
	 * 
	 * @param type
	 * @return
	 */
	Result<ProductType> saveType(ProductType type);

	/**
	 * 查询所有产品类型，集合里面只返回第一级类型，下级类型通过表关联自动获取。
	 * 
	 * @return
	 */
	List<ProductType> getAllTypes();

	/**
	 * 根据ID获取一个产品类型的信息，用于修改的时候使用。
	 * 
	 * @param id
	 * @return
	 */
	ProductType getTypeById(String id);

	Result<Void> deleteType(String id);

	void save(Product product);

	IndexPage getIndex();

	SearchPage search(String typeId, Integer number);
}
