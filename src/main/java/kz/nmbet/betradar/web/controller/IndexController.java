package kz.nmbet.betradar.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kz.nmbet.betradar.dao.service.MatchResultService;
import kz.nmbet.betradar.dao.service.RemoteStoreService;

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
}
