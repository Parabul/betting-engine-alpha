package kz.nmbet.betradar.web.controller;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import kz.nmbet.betradar.dao.domain.entity.GlBet;
import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.domain.views.OutrightOdd;
import kz.nmbet.betradar.dao.domain.views.ShortMatch;
import kz.nmbet.betradar.dao.service.CashierService;
import kz.nmbet.betradar.dao.service.PublicOutrightService;
import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.web.beans.ShortOdd;

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
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping({ "/cabinet/bet/{betId}" })
	public String bet(Model model, @PathVariable("betId") Integer betId) {
		model.addAttribute("bet", cashierService.getBet(betId));
		model.addAttribute("content", "cabinet/bet");
		return "template";
	}

	@RequestMapping("/cabinet/outright/bet/create")
	public String createBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddId") Integer oddId, Principal principal) {
		GlUser cashier = userService.findByEmail(principal.getName());
		GlBet bet = cashierService.createOutrightBet(oddId, amount, cashier);
		logger.info("createBet " + bet.getId());
		return MessageFormat.format("redirect:/cabinet/bet/{0}/", bet.getId() + "");
	}

	@RequestMapping("/cabinet/match/bet/create")
	public String createMatchBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddIds") String oddIds, @RequestParam(name = "preview") String preview,
			Principal principal) {
		GlUser cashier = userService.findByEmail(principal.getName());
		GlBet bet = cashierService.createMatchBets(oddIds, amount, cashier, preview);
		logger.info("createBet " + bet.getId());
		return MessageFormat.format("redirect:/cabinet/bet/{0}/", bet.getId() + "");
	}
	
	@RequestMapping("/cabinet/live/bet/create")
	public String createLiveBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddIds") String oddIds, @RequestParam(name = "preview") String preview,
			Principal principal) {
		GlUser cashier = userService.findByEmail(principal.getName());
		GlBet bet = cashierService.createLiveBets(oddIds, amount, cashier, preview);
		logger.info("createBet " + bet.getId());
		return MessageFormat.format("redirect:/cabinet/bet/{0}/", bet.getId() + "");
	}

	@RequestMapping({ "/cabinet/sports" })
	@ResponseBody
	public Map<Integer, String> getSports() {
		return cashierService.getSportEntities();
	}

	@RequestMapping({ "/cabinet/categories" })
	@ResponseBody
	public Map<Integer, String> getCategories(@RequestParam(name = "sportId") Integer sportId) {
		return cashierService.getCategoryEntities(sportId);
	}

	@RequestMapping({ "/cabinet/tournaments" })
	@ResponseBody
	public Map<Integer, String> getTournaments(@RequestParam(name = "categoryId") Integer categoryId) {
		return cashierService.getTournamentEntities(categoryId);
	}

	@RequestMapping({ "/cabinet/outrights" })
	@ResponseBody
	public Map<Integer, String> getOutrights(@RequestParam(name = "categoryId") Integer categoryId) {
		return cashierService.getOutrightEntities(categoryId);
	}

	@RequestMapping({ "/cabinet/outright/odds" })
	@ResponseBody
	public List<OutrightOdd> getOutrightOdds(@RequestParam(name = "outrightId") Integer outrightId) {
		return outrightService.findOutrightOdds(outrightId);
	}

	@RequestMapping({ "/cabinet/matches" })
	@ResponseBody
	public List<ShortMatch> getMatches(@RequestParam(name = "sportId") Integer sportId,
			@RequestParam(name = "q") String q) {
		return cashierService.getMatchesBySport(sportId, q);
	}

	@RequestMapping({ "/cabinet/live-matches" })
	@ResponseBody
	public List<ShortMatch> getLiveMatches(@RequestParam(name = "q") String q) {
		return cashierService.getLiveMatches(q);
	}

	@RequestMapping({ "/cabinet/matches/odds" })
	@ResponseBody
	public List<ShortOdd> getMatchOdds(@RequestParam(name = "matchId") Integer matchId) {
		return cashierService.getMatchOddEntities(matchId);
	}
	
	@RequestMapping({ "/cabinet/live-matches/odds" })
	@ResponseBody
	public List<ShortOdd> getLiveMatchOdds(@RequestParam(name = "matchId") Integer matchId) {
		return cashierService.getMatchLiveOdds(matchId);
	}

	@RequestMapping("/autologin/prematch")
	public String autologinPrematch(Model model, @RequestParam(name = "login") String login,
			@RequestParam(name = "cashierId") Integer cashierId, @RequestParam(name = "hash") String hash) {

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
		return "redirect:/cabinet/prematch";
	}
	
	@RequestMapping("/autologin/live")
	public String autologinLive(Model model, @RequestParam(name = "login") String login,
			@RequestParam(name = "cashierId") Integer cashierId, @RequestParam(name = "hash") String hash) {

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
		return "redirect:/cabinet/live";
	}

	@RequestMapping({ "/cabinet" })
	public String cabinet(Model model) {
		return "redirect:/cabinet/index";
	}

	@RequestMapping({ "/cabinet/prematch" })
	public String index(Model model) {
		model.addAttribute("sports", cashierService.getSportEntities());
		return "cabinet/template";
	}

	@RequestMapping({ "/cabinet/live" })
	public String live(Model model) {
		return "cabinet/live";
	}

}
