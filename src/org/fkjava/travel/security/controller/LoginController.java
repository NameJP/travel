package org.fkjava.travel.security.controller;

import org.fkjava.travel.core.domain.User;
import org.fkjava.travel.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("login")
// 放入Session的对象的名字，必须跟addObject方法的第一个参数的值相同！
// Spring MVC是基于AOP方式实现的，会在方法执行完成以后，自动把对象放入HttpSession对象中。
@SessionAttributes({ "user" })
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String login() {
		return "login";
	}

	// Spring MVC会自动调用WebDataBinder对象，把请求参数封装到User类型的对象里面，并且作为参数传入给方法！
	@PostMapping
	public ModelAndView login(User user) {
		ModelAndView mav = new ModelAndView("login");

		// 实际的工作中，登录相关的逻辑，也会封装到业务逻辑层里面。
		// 但是由于现在缺少很多的技术，没有必要封装。所以登录的逻辑直接在控制器写！
		User dbuser = this.userService.findUserByName(user.getName());
		if (dbuser == null) {
			// 根据登录名没有找到User对象，登录名写错了。
			mav.addObject("msg", "登录名不存在");
		} else {
			if (dbuser.getPassword().equals(user.getPassword())) {
				// 密码一致
				// 实际的项目中，一般密码不会直接比较是否相等。而是会采用一定的加密算法对原文进行加密，得到密文以后再按照特定的算法来比较是否匹配。

				// 登录成功以后，重定向到首页
				// 实际的项目中，往往会重定向到之前访问的页面
				mav.setViewName("redirect:/index.jsp");

				// 把对象放入Session，只需要把对象加入mav、model对象里面。并且对象的名称要在类上面使用@SessionAttributes注解标注起来！
				mav.addObject("user", dbuser);
			} else {
				mav.addObject("msg", "登录密码错误");
			}
		}
		return mav;
	}
}
