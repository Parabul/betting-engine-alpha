package kz.nmbet.betradar.web.controller;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.repository.GlSportEntityRepository;
import kz.nmbet.betradar.dao.service.CashierService;
import kz.nmbet.betradar.dao.service.UserService;
import kz.nmbet.betradar.web.beans.AccountInfo;

@ControllerAdvice
public class AccountAdvice {

	private static final Logger logger = LoggerFactory.getLogger(AccountAdvice.class);

	@Autowired
	private UserService userService;

	@Autowired
	private CashierService cashierService;

	@ModelAttribute(value = "accountInfo")
	public AccountInfo addUserAmount(Principal principal) {
		if (principal != null && StringUtils.isNotBlank(principal.getName())) {
			GlUser user = userService.findByEmail(principal.getName());
			if (user == null)
				return null;
			return new AccountInfo(user);
		}
		return null;
	}

	@ModelAttribute(value = "sideBarSports")
	public Map<Integer, String> getSportEntities() {
		return cashierService.getSportEntities();
	}

}
