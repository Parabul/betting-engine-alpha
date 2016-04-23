package kz.nmbet.betradar.web.controller;

import java.util.List;
import java.util.Map;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.views.OutrightOdd;
import kz.nmbet.betradar.dao.service.CashierService;
import kz.nmbet.betradar.dao.service.PublicOutrightService;
import kz.nmbet.betradar.dao.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CashierController {

	private static final Logger logger = LoggerFactory.getLogger(CashierController.class);

	@Autowired
	private CashierService cashierService;

	@Autowired
	private PublicOutrightService outrightService;

	@Autowired
	@Qualifier("bettingUserDetailsService")
	private UserDetailsService userDetailsService;

	@Autowired
	private UserService userService;

	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping({"/cabinet/sports"})
	@ResponseBody
	public Map<Integer, String> getSports() {
		return cashierService.getSportEntities();
	}

	@RequestMapping({"/cabinet/categories"})
	@ResponseBody
	public Map<Integer, String> getCategories(@RequestParam(name = "sportId") Integer sportId) {
		return cashierService.getCategoryEntities(sportId);
	}

	@RequestMapping({"/cabinet/tournaments"})
	@ResponseBody
	public Map<Integer, String> getTournaments(@RequestParam(name = "categoryId") Integer categoryId) {
		return cashierService.getTournamentEntities(categoryId);
	}

	@RequestMapping({"/cabinet/outrights"})
	@ResponseBody
	public Map<Integer, String> getOutrights(@RequestParam(name = "categoryId") Integer categoryId) {
		return cashierService.getOutrightEntities(categoryId);
	}

	@RequestMapping({"/cabinet/outright/odds"})
	@ResponseBody
	public List<OutrightOdd> getOutrightOdds(@RequestParam(name = "outrightId") Integer outrightId) {
		return outrightService.findOutrightOdds(outrightId);
	}

	@RequestMapping({"/cabinet/matches"})
	@ResponseBody
	public Map<Integer, String> getMatches(@RequestParam(name = "tournamentId") Integer tournamentId) {
		return cashierService.getMatchEntities(tournamentId);
	}

	@RequestMapping({"/cabinet/matches/odds"})
	@ResponseBody
	public Map<Integer, String> getMatchOdds(@RequestParam(name = "matchId") Integer matchId) {
		return cashierService.getMatchOddEntities(matchId);
	}

	@RequestMapping("/autologin")
	public String index(Model model, @RequestParam(name = "login") String login, @RequestParam(name = "cashierId") Integer cashierId,
			@RequestParam(name = "hash") String hash) {

		try {
			GlUser cashier = cashierService.autologin(login, cashierId, hash);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(cashier.getEmail(),
					userService.getCashierDefaultPassword(login));
			token.setDetails(userDetailsService.loadUserByUsername(login));
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (Exception e) {
			SecurityContextHolder.getContext().setAuthentication(null);
			logger.error(e.getMessage(), e);
			model.addAttribute("msg", e.getMessage());
			return "common/403";
		}
		return "redirect:/cabinet";
	}

	@RequestMapping({"/cabinet"})
	public String cabinet(Model model) {
		model.addAttribute("content", "cabinet/index");
		return "template";
	}
}
