package kz.nmbet.betradar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kz.nmbet.betradar.dao.service.AdminService;
import kz.nmbet.betradar.web.beans.StatBean;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(CashierController.class);

	@Autowired
	private AdminService adminService;

	@RequestMapping("/admin")
	public String index(Model model) {
		model.addAttribute("content", "admin/index");
		return "template";
	}

	@RequestMapping("/admin/data")
	@ResponseBody
	public StatBean data() {

		return adminService.getStatData();
	}

}
