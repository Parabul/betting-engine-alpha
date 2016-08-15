package kz.nmbet.betradar.web.controller;

import java.security.Principal;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kz.nmbet.betradar.dao.service.ClientService;
import kz.nmbet.betradar.dao.service.PaymentService;
import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.web.beans.ShortBetInfo;
import kz.nmbet.betradar.web.beans.ShortOdd;

@Controller
@RequestMapping(path = "/client")
@Secured(value = { "ROLE_CLIENT" })
public class ClientController {

	private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ClientService clientService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserService userService;

	@RequestMapping("/live/bet/create")
	@ResponseBody
	public ShortBetInfo createLiveBet(Model model, @RequestParam(name = "amount") double amount,
			@RequestParam(name = "oddId") Integer oddId, Principal principal) {
		return clientService.createLiveBet(oddId, amount, userService.findByEmail(principal.getName()));
	}

	@RequestMapping("/live/oddInfo")
	@ResponseBody
	public String getLiveOddInfo(Model model, @RequestParam(name = "oddId") Integer oddId) {
		return clientService.getLiveOddInfo(oddId);
	}

	@RequestMapping("/prematch/oddInfo")
	@ResponseBody
	public  ShortOdd getPrematchOddInfo(Model model, @RequestParam(name = "oddId") Integer oddId) {
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
			@RequestParam(name = "oddId") Integer[] oddIds, Principal principal) {
		return clientService.createMatchBet(Arrays.asList(oddIds), amount, userService.findByEmail(principal.getName()));
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

}
