package org.fkjava.travel.core.service.impl;

import java.util.List;

import org.fkjava.travel.core.dao.ProductDao;
import org.fkjava.travel.core.dao.ProductTypeDao;
import org.fkjava.travel.core.domain.Product;
import org.fkjava.travel.core.domain.ProductType;
import org.fkjava.travel.core.service.ProductService;
import org.fkjava.travel.core.vo.IndexPage;
import org.fkjava.travel.core.vo.Result;
import org.fkjava.travel.core.vo.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductTypeDao productTypeDao;
	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional
	public Result<ProductType> saveType(ProductType type) {
		Result<ProductType> result;

		if (StringUtils.isEmpty(type.getId())) {
			type.setId(null);
		}

		if (type.getParent() != null && StringUtils.isEmpty(type.getParent().getId())) {
			// 没有父类型的ID，认为该类型就是一级类型，没有上级。
			type.setParent(null);
		}

		// 确保在同级类型中，没有同名的其他类型
		ProductType old;
		if (type.getParent() == null) {
			old = this.productTypeDao.findByNameAndParentIsNull(type.getName());
		} else {
			old = this.productTypeDao.findByNameAndParent(type.getName(), type.getParent());
		}

		if (StringUtils.isEmpty(type.getId())) {
			// 新增
			if (old == null) {
				this.productTypeDao.save(type);
				result = Result.of(Result.STATUS_OK, "添加产品类型成功");
			} else {
				result = Result.of(Result.STATUS_ERROR, "添加产品类型失败，名称已被使用");
			}
		} else {
			if (old == null || type.getId().equals(old.getId())) {
				this.productTypeDao.save(type);
				result = Result.of(Result.STATUS_OK, "修改产品类型成功");
			} else {
				result = Result.of(Result.STATUS_ERROR, "修改产品类型失败，名称已被其他类型使用");
			}
		}
		return result;
	}

	@Override
	public List<ProductType> getAllTypes() {
		List<ProductType> types = this.productTypeDao.findByParentIsNull();
		return types;
	}

	@Override
	public ProductType getTypeById(String id) {
		return this.productTypeDao.getOne(id);
	}

	@Override
	public Result<Void> deleteType(String id) {
		ProductType type = new ProductType();
		type.setId(id);
		Pageable pageable = PageRequest.of(0, 3);
		// 1.检查是否有下级类型，如果有下级类型，不能删除！
		Page<ProductType> types = this.productTypeDao.findByParent(type, pageable);
		if (types.getTotalElements() > 0) {
			return Result.of(Result.STATUS_ERROR, "产品类型有下级类型，不能删除！");
		}
		// 2.检查类型是否有产品引用，有引用的时候不能删除！
		Page<Product> products = this.productDao.findByType(type, pageable);
		if (products.getTotalElements() > 0) {
			return Result.of(Result.STATUS_ERROR, "产品类型已经有产品关联，不能删除！");
		}
		// 3.执行删除
		this.productTypeDao.deleteById(id);

		return Result.of(Result.STATUS_OK, "产品类型删除成功！");
	}

	@Override
	public void save(Product product) {
		if (StringUtils.isEmpty(product.getId())) {
			product.setId(null);
		}
		this.productDao.save(product);
	}

	@Override
	public IndexPage getIndex() {
		IndexPage page = new IndexPage();

		// 查询所有一级类型
		// List<ProductType> topTypes = this.productTypeDao.findByParentIsNull();
		// 查询前面几条一级类型
		Pageable pageable = PageRequest.of(0, 15);
		Page<ProductType> topTypePage = this.productTypeDao.findByParentIsNull(pageable);
		List<ProductType> topTypes = topTypePage.getContent();

		page.setTopTypes(topTypes);

		// 查询热门推广

		// 查询首屏巨幅广告

		// 查询统计报表

		// 查询分类热门

		return page;
	}

	@Override
	public SearchPage search(String typeId, Integer number) {

		SearchPage page = new SearchPage();

		// 查询横幅的数据
		Pageable pageable = PageRequest.of(0, 15);
		Page<ProductType> topTypePage = this.productTypeDao.findByParentIsNull(pageable);
		List<ProductType> topTypes = topTypePage.getContent();

		page.setTopTypes(topTypes);

		// 查询产品列表
		// 如果有很多条件来查询数据的时候，需要使用动态条件来查询！
		// 动态查询可以在程序中灵活组装查询参数，Spring Data已经提供了对应的API。
		Page<Product> productPage;
		pageable = PageRequest.of(number, 10);
		if (StringUtils.isEmpty(typeId)) {
			// 查询所有类型的产品
			productPage = this.productDao.findAll(pageable);
		} else {
			// 根据类型查询
			ProductType type = this.getTypeById(typeId);
			page.setType(type);

			// 无法查询子类型的产品
			// productPage = this.productDao.findByType(type, pageable);

			// 获取所有的子类型、以及当前类型本身
			List<ProductType> types = type.getAllChilds();
			// 用于实现in查询，把子类的产品也一起查询出来
			productPage = this.productDao.findByTypeIn(types, pageable);
		}
		page.setProductPage(productPage);

		// 查询热门推广
		// ...

		return page;
	}
}
