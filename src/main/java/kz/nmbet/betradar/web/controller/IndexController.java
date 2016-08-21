package kz.nmbet.betradar.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import kz.nmbet.betradar.dao.service.MatchResultService;
import kz.nmbet.betradar.utils.MessageByLocaleService;

@Controller
public class IndexController {

	private static final Logger logger = LoggerFactory.getLogger(CashierController.class);

	@Autowired
	@Qualifier("localeResolver")
	private LocaleResolver localeResolver;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@RequestMapping({ "/" })
	public String home(Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("content", "index");
		localeResolver.setLocale(request, response, new Locale("ru_RU"));
		logger.info(messageByLocaleService.getMessage("paymentOrder.orderStatus.RECEIVED"));
		model.addAttribute("keywords", messageByLocaleService.getMessage("keywords.root"));
		model.addAttribute("description", messageByLocaleService.getMessage("description.root"));

		return "olimp";
	}

	@RequestMapping(value = "/changelang/ru")
	public String changelangRu(HttpServletRequest request, HttpServletResponse response) {
		localeResolver.setLocale(request, response, new Locale("ru_RU"));
		return "redirect:/";
	}

	@RequestMapping(value = "/changelang/en")
	public String changelangEn(HttpServletRequest request, HttpServletResponse response) {
		localeResolver.setLocale(request, response, new Locale("en_US"));
		return "redirect:/";
	}

	@RequestMapping("/nm/bonus")
	public String bonus(Model model) {
		model.addAttribute("content", "static/bonus");
		return "olimp";
	}

	@RequestMapping("/nm/support")
	public String support(Model model) {
		model.addAttribute("content", "static/support");
		return "olimp";
	}

	@RequestMapping("/nm/liveresults")
	public String liveresults(Model model) {
		model.addAttribute("content", "static/liveresults");
		model.addAttribute("keywords", messageByLocaleService.getMessage("keywords.liveresults"));
		model.addAttribute("description", messageByLocaleService.getMessage("description.liveresults"));
		return "olimp";
	}

	@RequestMapping("/nm/rules")
	public String rules(Model model) {
		model.addAttribute("content", "static/rules");
		return "olimp";
	}

	@RequestMapping("/nm/about")
	public String about(Model model) {
		model.addAttribute("content", "static/about");
		return "olimp";
	}
}
