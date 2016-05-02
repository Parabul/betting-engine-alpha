package kz.nmbet.betradar.utils;

import org.springframework.stereotype.Component;

@Component
public interface MessageByLocaleService {

	public String getMessage(String id);
}