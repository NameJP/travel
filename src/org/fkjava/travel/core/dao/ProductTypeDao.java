package org.fkjava.travel.core.dao;

import java.util.List;

import org.fkjava.travel.core.domain.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeDao extends JpaRepository<ProductType, String> {

	// select * from ProductType where parent_id is null
	List<ProductType> findByParentIsNull();

	// select * from ProductType where name = ? and parent_id is null
	ProductType findByNameAndParentIsNull(String name);

	// select * from ProductType where name = ? parent_id = ?
	ProductType findByNameAndParent(String name, ProductType parent);

	Page<ProductType> findByParent(ProductType type, Pageable pageable);

	Page<ProductType> findByParentIsNull(Pageable pageable);

}
