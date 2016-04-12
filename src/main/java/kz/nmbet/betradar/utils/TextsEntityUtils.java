package kz.nmbet.betradar.utils;

import java.util.List;

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

	public String getDefaultValue(List<TextsEntity> collection) {
		for (TextsEntity entity : collection)
			for (TextEntity texts : entity.getTexts()) {
				if (DEFAULT_LANG.equals((texts.getLanguage()))) {
					return texts.getValue();
				}
			}
		return null;
	}

	public String getCDefaultValue(List<TextEntity> collection) {
		for (TextEntity texts : collection) {
			if (DEFAULT_LANG.equals((texts.getLanguage()))) {
				return texts.getValue();
			}
		}
		return null;
	}
}
