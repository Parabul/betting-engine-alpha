package kz.nmbet.betradar.web.controller;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.service.PublicOutrightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PublicOutrightController {

	@Autowired
	private PublicOutrightService outrightService;

	@RequestMapping("/outright")
	public String index(Model model) {
		model.addAttribute("sports", outrightService.getActiveOutrights());
		model.addAttribute("content", "outright/index");
		return "template";
	}
	
	@RequestMapping("/outright/{outrightId}/")
	public String table(Model model, @PathVariable("outrightId") Integer outrightId) {
		model.addAttribute("odds", outrightService.findOutrightOdds(outrightId));
		model.addAttribute("outright", outrightService.findOne(outrightId));
		model.addAttribute("content", "outright/outright");
		return "template";
	}


	@RequestMapping("/bet/create")
	@ResponseBody
	public String createBet(Model model,
			@RequestParam(name = "amount") double amount,
			@RequestParam(name = "odd_id") Integer oddId) {
		GlBet bet = outrightService.createBet(oddId, amount);
		return bet.toString();
	}

	@RequestMapping("/bet/check")
	@ResponseBody
	public String checkBet(Model model,
			@RequestParam(name = "odd_id") Integer oddId) {
		GlBet bet = outrightService.checkBet(oddId);
		return bet.toString();
	}

}
