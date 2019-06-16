package org.fkjava.travel.core.dao;

import org.fkjava.travel.core.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, String> {

	User findByName(String name);

	Page<User> findByNameLike(String name, Pageable pageable);

}
