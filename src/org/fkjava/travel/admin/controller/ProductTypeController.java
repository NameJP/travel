package org.fkjava.travel.admin.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fkjava.travel.core.domain.ProductType;
import org.fkjava.travel.core.service.ProductService;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("productType")
public class ProductTypeController {

	// 获取日志记录器
	private Logger log = LogManager.getLogger(ProductTypeController.class);
	@Autowired
	private ProductService productService;

	@GetMapping
	public ModelAndView index() {

		ModelAndView mav = new ModelAndView("productType/index");

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

	@PostMapping
	public String save(ProductType type) {

		this.productService.saveType(type);

		return "redirect:/admin/productType";
	}

	@DeleteMapping("{id}")
	@ResponseBody
	public Result<Void> delete(@PathVariable("id") String id) {
		return this.productService.deleteType(id);
	}
}
