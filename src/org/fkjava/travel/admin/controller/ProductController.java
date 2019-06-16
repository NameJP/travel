package org.fkjava.travel.admin.controller;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fkjava.travel.commons.DateEditor;
import org.fkjava.travel.core.domain.Product;
import org.fkjava.travel.core.domain.ProductType;
import org.fkjava.travel.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("product")
public class ProductController {

	// 获取日志记录器
	private Logger log = LogManager.getLogger(ProductController.class);
	@Autowired
	private ProductService productService;

	@GetMapping
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView("product/list");

		return mav;
	}

	@GetMapping("add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView("product/add");

		// 读取所有的一级类型，然后转换为JSON字符串
		List<ProductType> topTypes = this.productService.getAllTypes();
		String json = "[]";
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			json = objectMapper.writeValueAsString(topTypes);
		} catch (Exception ex) {
			// ex.printStackTrace();
			log.warn("无法把产品类型转换为JSON：" + ex.getMessage(), ex);
		}
		mav.addObject("json", json);

		return mav;
	}

	// 在调用方法之前执行的补充方法，预处理
	@InitBinder
	// 方法名无所谓！但是参数类型通常固定的：叫做WebDataBinder对象。
	public void init0(WebDataBinder binder) {
		// 注册自定义的数据类型转换器
		binder.registerCustomEditor(Date.class, new DateEditor("yyyy-MM-dd"));
	}

	// 如果请求参数里面有“[数字]”形式的名称，Spring MVC会认为这是一个集合。
	// 中括号里面的数字，就会被作为【集合的索引】来使用！
	@PostMapping
	public ModelAndView save(Product product) {

		this.productService.save(product);

		// 添加成功以后，回到产品列表页面
		ModelAndView mav = new ModelAndView("redirect:/admin/product");
		return mav;
	}
}
