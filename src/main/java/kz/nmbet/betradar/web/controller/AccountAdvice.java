package kz.nmbet.betradar.web.controller;

import java.security.Principal;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.service.UserService;

@ControllerAdvice
public class AccountAdvice {

	private static final Logger logger = LoggerFactory.getLogger(AccountAdvice.class);

	@Autowired
	private UserService userService;

	@ModelAttribute(value = "userAmount")
	public Double addUserAmount(Principal principal) {
		if (principal != null && StringUtils.isNotBlank(principal.getName())) {
			GlUser user = userService.findByEmail(principal.getName());
			logger.info("userAmount => " + user.getAmount());
			return user.getAmount();
		}
		return null;
	}
}
