package kz.nmbet.betradar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kz.nmbet.betradar.dao.service.PublicLiveService;
import kz.nmbet.betradar.utils.MessageByLocaleService;

@Controller
public class PublicLiveController {
	private static final Logger logger = LoggerFactory
			.getLogger(PublicLiveController.class);

	@Autowired
	private PublicLiveService liveService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@RequestMapping("/live")
	public String index(Model model) {
		model.addAttribute("matches", liveService.getLiveMathes());
		model.addAttribute("content", "live/index");

		return "template";
	}

	@RequestMapping("/nm/live")
	public String olimp(Model model) {
		model.addAttribute("matches", liveService.getLiveMathes());
		return "live/olimp";
	}

	@RequestMapping("/nm/live/odds/")
	public String tableOlimp(Model model,
			@RequestParam(value = "matchIds") Integer[] matchIds) {
		model.addAttribute("matches", liveService.getActiveOdds(matchIds));
		return "live/olimp-odds";
	}

	@RequestMapping("/nm/live/odds/one")
	public String updateOlimp(Model model,
			@RequestParam(value = "matchId") Integer matchId) {
		model.addAttribute("match", liveService.getActiveOdds(matchId));

		return "live/olimp-one";
	}

	@RequestMapping("/live/odds/")
	public String table(Model model,
			@RequestParam(value = "matchIds") Integer[] matchIds) {
		model.addAttribute("matches", liveService.getActiveOdds(matchIds));
		model.addAttribute("content", "live/odds");
		return "template";
	}

	@RequestMapping("/live/odds/one")
	public String update(Model model,
			@RequestParam(value = "matchId") Integer matchId) {
		model.addAttribute("match", liveService.getActiveOdds(matchId));

		return "live/one";
	}
}
