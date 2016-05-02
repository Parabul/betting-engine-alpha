package kz.nmbet.betradar.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(value = Exception.class)
	public String exception(Model model, Exception exception, WebRequest request) {
		logger.error(exception.getMessage(), exception);
		model.addAttribute("msg", exception.getMessage());
		return "common/403";
	}
}
