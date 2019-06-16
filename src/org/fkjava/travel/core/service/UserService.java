package org.fkjava.travel.core.service;

import org.fkjava.travel.core.domain.User;
import org.fkjava.travel.core.vo.Result;
import org.springframework.data.domain.Page;

public interface UserService {

	/**
	 * 新增或者修改用户
	 * 
	 * @param user
	 * @return
	 */
	Result<User> save(User user);

	/**
	 * 根据用户名搜索用户
	 * 
	 * @param name
	 * @param number
	 * @return
	 */
	Page<User> findUser(String name, Integer number);

	/**
	 * 根据ID获取某个用户的详细信息
	 * 
	 * @param id
	 * @return
	 */
	User getById(String id);

	/**
	 * 根据登录名查询User对象，主要是为了给登录功能使用的
	 * 
	 * @param name
	 * @return
	 */
	User getByName(String name);

	Result<Void> delete(String id);

	User findUserByName(String name);
}
