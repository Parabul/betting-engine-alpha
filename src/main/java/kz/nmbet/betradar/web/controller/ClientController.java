package kz.nmbet.betradar.web.controller;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.service.ClientService;
import kz.nmbet.betradar.dao.service.PaymentService;
import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.security.BettingUserDetailsService;
import kz.nmbet.betradar.web.beans.ShortBetInfo;
import kz.nmbet.betradar.web.beans.ShortOdd;

@Controller
@RequestMapping(path = "/client")
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;

	@Autowired
	@Qualifier("bettingUserDetailsService")
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@RequestMapping("/live/bet/create")
	@ResponseBody
	public ShortBetInfo createLiveBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddId") Integer oddId, Principal principal) {
		return clientService.createLiveBet(oddId, amount, userService.findByEmail(principal.getName()));
	}

	@RequestMapping("/live/bets/create")
	@ResponseBody
	public ShortBetInfo createLiveBets(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddIds[]") List<Integer> oddIds, Principal principal) {
		return clientService.createLiveBet(oddIds, amount, userService.findByEmail(principal.getName()));
	}

	@RequestMapping("/live/oddInfo")
	@ResponseBody
	public ShortOdd getLiveOddInfo(Model model, @RequestParam(name = "oddId") Integer oddId) {
		return clientService.getLiveOddInfo(oddId);
	}

	@RequestMapping("/prematch/oddInfo")
	@ResponseBody
	public ShortOdd getPrematchOddInfo(Model model, @RequestParam(name = "oddId") Integer oddId) {
		return clientService.getPrematchOddInfo(oddId);
	}

	@RequestMapping("/prematch/bet/create")
	@ResponseBody
	public ShortBetInfo createMatchBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddId") Integer oddId, Principal principal) {
		return clientService.createMatchBet(oddId, amount, userService.findByEmail(principal.getName()));
	}

	@RequestMapping("/prematch/bets/create")
	@ResponseBody
	public ShortBetInfo createMatchBets(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddIds[]") List<Integer> oddIds, Principal principal) {
		return clientService.createMatchBet(oddIds, amount, userService.findByEmail(principal.getName()));
	}

	@RequestMapping("/history")
	public String history(Model model, Principal principal,
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "size", defaultValue = "10") Integer size) {
		Pageable pageable = new PageRequest(page, size);

		model.addAttribute("bets", clientService.getHistory(userService.findByEmail(principal.getName()), pageable));
		model.addAttribute("content", "client/history");
		return "olimp";
	}

	@RequestMapping("/account")
	public String account(Model model, Principal principal) {
		model.addAttribute("content", "client/account");
		model.addAttribute("cashboxes", paymentService.findAllCashboxs());
		return "olimp";
	}

	@RequestMapping("/settings/change/password")
	public String changePassword(Model model, Principal principal, @RequestParam(name = "password") String password,
			@RequestParam(name = "password2") String password2) {
		try {
			if (!password.equals(password2)) {
				throw new IllegalArgumentException("Пароли не совпадают");
			}
			GlUser user = userService.changePassword(userService.findByEmail(principal.getName()), password);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
					password);
			token.setDetails(userDetailsService.loadUserByUsername(user.getEmail()));
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			model.addAttribute("msg", "Пароль успешно изменен");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("errorMsg", "Ошибка: " + e.getMessage());
		}

		model.addAttribute("content", "client/settings");
		return "olimp";
	}

	@RequestMapping("/settings/change/phone")
	public String changePhone(Model model, Principal principal, @RequestParam(name = "phone") String phone,
			@RequestParam(name = "password") String password) {
		try {
			GlUser user = userService.changePhone(userService.findByEmail(principal.getName()), phone);
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),
					password);
			token.setDetails(userDetailsService.loadUserByUsername(user.getEmail()));
			Authentication authentication = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			model.addAttribute("msg", "Телефон успешно изменен");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			model.addAttribute("errorMsg", "Ошибка: " + e.getMessage());
		}

		model.addAttribute("content", "client/settings");
		return "olimp";
	}

	@RequestMapping("/settings")
	public String settings(Model model, Principal principal) {
		model.addAttribute("content", "client/settings");
		return "olimp";
	}

	@RequestMapping("/withdrawal")
	public String withdrawal(Model model, Principal principal) {
		model.addAttribute("paymentOrders",
				paymentService.findPaymentOrders(userService.findByEmail(principal.getName())));
		model.addAttribute("cashboxes", paymentService.findAllCashboxs());

		model.addAttribute("content", "client/withdrawal");
		return "olimp";
	}

	@RequestMapping("/create/withdrawal")
	public String createwithdrawal(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "cashboxId") Integer cashboxId, Principal principal) {
		paymentService.createPaymentOrder(userService.findByEmail(principal.getName()), amount, cashboxId);
		return "redirect:/client/withdrawal";
	}

	@RequestMapping("/save/defaultBetAmount")
	@ResponseBody
	public String createwithdrawal(Model model, @RequestParam(name = "defaultBetAmount") double defaultBetAmount,
			Principal principal) {
		userService.updateDefaultBetAmount(defaultBetAmount, principal.getName());
		return "OK";
	}

	@RequestMapping("/save/fastBetEnabled")
	@ResponseBody
	public String updateFastBetEnabled(Model model, @RequestParam(name = "fastBetEnabled") boolean fastBetEnabled,
			Principal principal) {
		userService.updateFastBetEnabled(fastBetEnabled, principal.getName());
		return "OK";
	}

}
