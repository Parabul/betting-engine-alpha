package kz.nmbet.betradar.web.controller;

import kz.nmbet.betradar.dao.service.PublicMatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicMatchController {
	private static final Logger logger = LoggerFactory.getLogger(PublicMatchController.class);

	@Autowired
	private PublicMatchService matchService;

	@RequestMapping("/prematch")
	public String index(Model model) {
		model.addAttribute("sports", matchService.getActiveCategories());
		return "prematch/index";
	}

	@RequestMapping("/prematch/{categoryId}/category")
	public String table(Model model, @PathVariable("categoryId") Integer categoryId) {
		model.addAttribute("matches", matchService.getMatchesByCategory(categoryId));
		model.addAttribute("category", matchService.getCategory(categoryId));

		return "prematch/category";
	}
}
