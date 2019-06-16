package org.fkjava.travel.core.service.impl;

import org.fkjava.travel.core.dao.UserDao;
import org.fkjava.travel.core.domain.User;
import org.fkjava.travel.core.service.UserService;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public Result<User> save(User user) {

		User old = this.userDao.findByName(user.getName());
		if (StringUtils.isEmpty(user.getId())) {
			user.setId(null);
		}

		Result<User> result;
		if (StringUtils.isEmpty(user.getId())) {
			// 新增用户
			if (old != null) {
				result = Result.of(Result.STATUS_ERROR, "用户名 " + user.getName() + " 已经被占用，无法使用！", user);
			} else {
				this.userDao.save(user);
				result = Result.of(Result.STATUS_OK, "用户新增成功");
			}
		} else {
			// 修改用户
			if (old == null || user.getId().equals(old.getId())) {
				// 相同的用户ID，可以修改
				this.userDao.save(user);
				result = Result.of(Result.STATUS_OK, "用户修改成功");
			} else {
				result = Result.of(Result.STATUS_ERROR, "新的用户名被其他用户占用，无法修改");
			}
		}

		return result;
	}

	@Override
	public Page<User> findUser(String name, Integer number) {
		Pageable pageable = PageRequest.of(number, 8);

		Page<User> page;
		if (!StringUtils.isEmpty(name)) {
			// 一般模糊搜索是使用Solr（搜索引擎）来搜索的
			// 但是我们没有使用搜索引擎，所以只能使用非常低效的数据库like查询
			name = "%" + name + "%";
			page = this.userDao.findByNameLike(name, pageable);
		} else {
			page = this.userDao.findAll(pageable);
		}
		return page;
	}

	@Override
	public User getById(String id) {
		// 一般根据ID获取的对象，都会加入高速缓存技术，让查询的效率更高。
		// 高速缓存可以使用Hibernate的二级缓存，也可以使用Redis，或者基于Spring Cache技术来实现。
		return this.userDao.getOne(id);
	}

	@Override
	public User getByName(String name) {
		return this.userDao.findByName(name);
	}

	@Override
	public Result<Void> delete(String id) {
		this.userDao.deleteById(id);

		return Result.of(Result.STATUS_OK, "删除用户成功");
	}

	@Override
	public User findUserByName(String name) {
		return this.userDao.findByName(name);
	}
}
