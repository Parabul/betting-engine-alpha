package kz.nmbet.betradar.utils;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kz.nmbet.betradar.dao.domain.entity.GlUser;

@Component
public class SmsUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	public static final String login = "nmsia";
	public static final String password = "zowoxyfu";
	public static final String api_url = "https://www.smstraffic.ru/multi.php";

	RestTemplate restTemplate;
	HttpEntity<?> entity;

	@PostConstruct
	public void init() {
		restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.TEXT_XML_VALUE);
		entity = new HttpEntity<>(headers);
	}

	@Async
	public void send(String phones, String message) {
		logger.info(MessageFormat.format("send: {0},{1} ", phones, message));
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(api_url).queryParam("login", login)
				.queryParam("password", password).queryParam("rus", "5").queryParam("phones", phones)
				.queryParam("message", message);

		HttpEntity<String> resp = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity,
				String.class);

		logger.info(resp.getBody());
	}

	public void send(GlUser user, String message) {
		send(user.getEmail(), message);
	}

	public void send(List<GlUser> users, String message) {
		String phones = users.stream().map(user -> user.getEmail()).collect(Collectors.joining(","));
		send(phones, message);
	}
}
