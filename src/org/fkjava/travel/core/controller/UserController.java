package org.fkjava.travel.core.controller;

import org.fkjava.travel.core.domain.User;
import org.fkjava.travel.core.service.UserService;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

// 所有的Controller、Action，都是为了接收用户的请求、调用业务逻辑层代码、返回视图给浏览器的。
@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ModelAndView list(//
			// @RequestParam(name = "pageNumber", defaultValue = "0")
			// @RequestParam表示接收请求参数，相当于request.getParameter("pageNumber")
			// defaultValue = "0"表示在没有该参数的时候，默认值为0
			// 收到的参数值，Spring MVC会自动进来类型转换
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer number//
	) {

		// 前缀 + user/list + .jsp
		ModelAndView mav = new ModelAndView("user/list");

		Page<User> page = this.userService.findUser(null, number);
		mav.addObject("page", page);

		return mav;
	}

	@PostMapping
	public String save(User user, Model model) {
		Result<User> result = this.userService.save(user);
		if (result.getStatus() == Result.STATUS_ERROR) {
			model.addAttribute("result", result);
			return "user/add";
		} else {
			return "redirect:/core/user";
		}
	}

	// 在REST风格的WEB服务中，一个URL表示一个确定的对象
	// http://127.0.0.1:8080/travel/core/user/xxxxxx
	// {}表示一个路径参数，在括号中的字符串表示参数名
	// 浏览器不能直接往服务器发DELETE请求
	// 所有的按钮、链接，默认都只能发出GET和POST请求
	// 在HTTP协议中，除了GET、POST以外，还有DELETE、PUT、HEAD、OPTIONS等请求，这些请求都要通过AJAX发送，或者使用表单模拟
	// GET : 获取
	// DELETE : 删除数据
	// POST : 新增或修改
	// PUT : 新增
	// HEAD : 只是返回响应头，没有响应体
	// OPTIONS : 返回可用选项的一个特殊响应
	@DeleteMapping("{id}")
	@ResponseBody
	public Result<Void> delete(//
			@PathVariable("id") String id) {
		Result<Void> result = this.userService.delete(id);
		return result;
	}
}
