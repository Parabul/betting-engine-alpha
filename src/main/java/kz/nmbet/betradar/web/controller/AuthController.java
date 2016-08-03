package kz.nmbet.betradar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.web.beans.UserException;

@Controller
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("content", "auth/login");
		return "olimp";
	}

	@RequestMapping("/nm/registration")
	public String registration(Model model) {
		model.addAttribute("content", "auth/registration");
		return "olimp";
	}

	@RequestMapping("/nm/createuser")
	public String createuser(Model model, @RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password, @RequestParam(value = "password2") String password2)
			throws UserException {

		if (!password2.equals(password)) {
			throw new UserException("Пароли не совпдают");
		}
		userService.create(email, password, UserService.CLIENT_ROLES, null);
		return "redirect:/login";

	}

	@ExceptionHandler(UserException.class)
	public String userException(Model model, Exception e) {
		logger.error(e.getMessage(), e);
		model.addAttribute("errorMsg", e.getMessage());
		model.addAttribute("content", "auth/registration");
		return "olimp";
	}

}
