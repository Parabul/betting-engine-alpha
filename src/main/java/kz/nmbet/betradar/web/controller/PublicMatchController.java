package kz.nmbet.betradar.web.controller;

import kz.nmbet.betradar.dao.service.PublicMatchService;
import kz.nmbet.betradar.utils.MessageByLocaleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PublicMatchController {
	private static final Logger logger = LoggerFactory.getLogger(PublicMatchController.class);

	@Autowired
	private PublicMatchService matchService;

	@Autowired
	private MessageByLocaleService messageByLocaleService;

	@RequestMapping("/prematch")
	public String index(Model model) {
		model.addAttribute("sports", matchService.getActiveCategories());
		model.addAttribute("content", "prematch/index");

		return "template";
	}

	@RequestMapping("/prematch/{tournamentId}/")
	public String table(Model model, @PathVariable("tournamentId") Integer tournamentId) {
		model.addAttribute("matches", matchService.getMatchesByTournament(tournamentId));
		model.addAttribute("tournament", matchService.getTournament(tournamentId));
		model.addAttribute("content", "prematch/category");
		return "template";
	}

	@RequestMapping("/olimp/prematch")
	public String olimp(Model model) {
		model.addAttribute("sports", matchService.getActiveCategories());
		model.addAttribute("content", "prematch/index");

		return "prematch/olimp";
	}

	@RequestMapping("/olimp/selected")
	public String selected(Model model, @RequestParam(value = "matchIds", required = false) Integer[] matchIds) {
		model.addAttribute("content", "live/odds");
		return "prematch/olimp-category";
	}

}
