package org.fkjava.travel.admin.controller;

import org.fkjava.travel.admin.domain.SystemSettings;
import org.fkjava.travel.admin.service.ConfigCenter;
import org.fkjava.travel.core.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
// 命名空间+@RequestMapping
// /admin/config
@RequestMapping("config")
public class ConfigController {

	@Autowired
	private ConfigCenter configCenter;

	@GetMapping
	public ModelAndView list(//
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer number, //
			@RequestParam(name = "key", required = false) String key) {

		ModelAndView mav = new ModelAndView("config/list");
		Page<SystemSettings> page = this.configCenter.findSystemSettings(key, number);
		mav.addObject("page", page);

		return mav;
	}

	@PostMapping
	public String save(SystemSettings set) {
		this.configCenter.saveSystemSettings(set);
		return "redirect:/admin/config";
	}

	// {}在Spring MVC里面被称之为路径参数，把路径的一部分字符串作为参数来使用。
	@DeleteMapping("{id}")
	@ResponseBody
	public Result<Void> delete(@PathVariable("id") String id) {
		this.configCenter.deleteSystemSettingsById(id);
		Result<Void> result = Result.of(Result.STATUS_OK, "删除成功");
		return result;
	}
}
