package kz.nmbet.betradar.utils;

import org.springframework.stereotype.Component;

import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

@Component
public class TextsEntityUtils {

	public static final String DEFAULT_LANG = "BET";

	public String getDefaultValue(TextsEntity entity) {
		for (TextEntity texts : entity.getTexts()) {
			if (DEFAULT_LANG.equals((texts.getLanguage()))) {
				return texts.getValue();
			}
		}
		return null;
	}
}
