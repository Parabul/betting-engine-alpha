package kz.nmbet.betradar.web.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import kz.nmbet.betradar.dao.service.MatchResultService;

@Controller
public class IndexController {

	@Autowired
	private MatchResultService matchResultService;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("content", "login");
		return "template";
	}

	@RequestMapping({ "/check/match" })
	@ResponseBody
	public List<Integer> check(@RequestParam(name = "matchId") Integer matchId) {
		return matchResultService.check(matchId);
	}

	@RequestMapping({ "/" })
	public String home(Model model) {
		// remoteStoreService.chech();
		model.addAttribute("content", "index");
		return "template";
	}

	@RequestMapping(value = "/changelang")
	public String welcome(HttpServletRequest request, HttpServletResponse response) {
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		localeResolver.setLocale(request, response, Locale.forLanguageTag("ru"));
		return "redirect:/";
	}
}
