package org.fkjava.travel.core.controller;

import org.fkjava.travel.core.service.ProductService;
import org.fkjava.travel.core.vo.SearchPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("search")
public class SearchController {

	@Autowired
	private ProductService productService;

	@GetMapping
	public ModelAndView main(//
			@RequestParam(name = "typeId", required = false) String typeId, //
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer number) {

		ModelAndView mav = new ModelAndView("search/main");

		SearchPage page = this.productService.search(typeId, number);
		mav.addObject("searchPage", page);

		return mav;
	}
}
