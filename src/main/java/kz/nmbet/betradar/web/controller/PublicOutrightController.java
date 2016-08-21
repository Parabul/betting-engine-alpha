package kz.nmbet.betradar.web.controller;

import kz.nmbet.betradar.dao.service.PublicOutrightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicOutrightController {

	@Autowired
	private PublicOutrightService outrightService;

	@RequestMapping("/outright")
	public String index(Model model) {
		model.addAttribute("sports", outrightService.getActiveOutrights());
		model.addAttribute("content", "outright/index");
		return "redirect:/";
	}
	
	@RequestMapping("/outright/{outrightId}/")
	public String table(Model model, @PathVariable("outrightId") Integer outrightId) {
		model.addAttribute("odds", outrightService.findOutrightOdds(outrightId));
		model.addAttribute("outright", outrightService.findOne(outrightId));
		model.addAttribute("content", "outright/outright");
		return "redirect:/";
	}




}
