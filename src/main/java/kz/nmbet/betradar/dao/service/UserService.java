package kz.nmbet.betradar.dao.service;

import java.text.MessageFormat;
import java.util.Arrays;

import kz.nmbet.betradar.dao.domain.entity.GlUser;
import kz.nmbet.betradar.dao.repository.UserRepository;
import kz.nmbet.betradar.web.beans.UserException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class UserService {

	public static final String ADMIN_ROLES = "USER, ADMIN";
	public static final String CASHIER_ROLES = "USER, CASHIER";
	public static final String CLIENT_ROLES = "USER, CLIENT";
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public GlUser findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public GlUser findByCashierId(Integer cashierId) {
		return userRepository.findByCashierId(cashierId);
	}

	public String getCashierDefaultPassword(String email) {
		return DigestUtils.md5DigestAsHex(email.getBytes()).substring(0, 6);
	}

	@Transactional
	public GlUser create(String email, String password, String roles, Integer cashierId) throws UserException {
		GlUser puser = findByEmail(email);
		if (puser != null)
			throw new UserException("Недоспустимый номер телефона");
		GlUser user = new GlUser();
		user.setEmail(email);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		if (password == null)
			password = getCashierDefaultPassword(email);
		logger.info(MessageFormat.format("new user {0} with password {1}", email, password));
		user.setPassword(passwordEncoder.encode(password));
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		user.setRoles(Arrays.asList(roles));
		user.setCashierId(cashierId);
		user.setAmount(500.0d);
		user.setDefaultBetAmount(50.0d);
		user.setFastBetEnabled(false);
		return userRepository.save(user);
	}

	@Transactional
	public void withdraw(double amount, GlUser user) {
		if (user.getAmount() < amount) {
			throw new IllegalArgumentException();
		} else {
			user.setAmount(user.getAmount() - amount);
		}
		userRepository.save(user);
	}

	@Transactional
	public void updateDefaultBetAmount(Double amount, String email) {
		userRepository.updateDefaultBetAmount(amount, email);
	}
	
	@Transactional
	public void updateFastBetEnabled(Boolean enabled, String email) {
		userRepository.updateFastBetEnabled(enabled, email);
	}
}
