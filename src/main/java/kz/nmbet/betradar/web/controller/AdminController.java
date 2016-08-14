package kz.nmbet.betradar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kz.nmbet.betradar.dao.service.AdminService;
import kz.nmbet.betradar.dao.service.PaymentService;
import kz.nmbet.betradar.web.beans.StatBean;

@Controller
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(CashierController.class);

	@Autowired
	private AdminService adminService;

	@Autowired
	private PaymentService paymentService;

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

	@RequestMapping("/admin/cashboxes")
	public String cashboxes(Model model) {
		model.addAttribute("content", "admin/cashboxes");
		model.addAttribute("cashboxes", paymentService.findAllCashboxs());
		return "template";
	}

	@RequestMapping("/admin/create/cashbox")
	public String createCashbox(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "title") String title, @RequestParam(name = "address") String address) {
		paymentService.createCashbox(title, address, amount);
		return "redirect:/admin/cashboxes";
	}

	@RequestMapping("/admin/delete/cashbox")
	public String deleteCashbox(Model model, @RequestParam(name = "id") Integer id) {
		paymentService.deleteCashbox(id);
		return "redirect:/admin/cashboxes";
	}

}
