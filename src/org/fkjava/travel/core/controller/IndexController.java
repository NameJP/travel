package org.fkjava.travel.core.controller;

import org.fkjava.travel.core.service.ProductService;
import org.fkjava.travel.core.vo.IndexPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("index")
public class IndexController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ModelAndView main() {

		ModelAndView mav = new ModelAndView("index/main");

		IndexPage page = this.productService.getIndex();
		mav.addObject("indexPage", page);

		return mav;
	}
}
