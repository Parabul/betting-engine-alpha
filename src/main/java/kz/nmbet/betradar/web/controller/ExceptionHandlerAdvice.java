package kz.nmbet.betradar.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {
	@ExceptionHandler(value = Exception.class)
	public String exception(Model model, Exception exception, WebRequest request) {
		model.addAttribute("msg", exception.getMessage());
		return "common/403";
	}
}
