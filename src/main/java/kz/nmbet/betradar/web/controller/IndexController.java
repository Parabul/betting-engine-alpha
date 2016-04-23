package kz.nmbet.betradar.web.controller;

import kz.nmbet.betradar.dao.service.PublicOutrightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	@Autowired
	private PublicOutrightService outrightService;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("content", "login");
		return "template";
	}

	@RequestMapping({"/"})
	public String home(Model model) {
		model.addAttribute("content", "index");
		return "template";
	}
}
