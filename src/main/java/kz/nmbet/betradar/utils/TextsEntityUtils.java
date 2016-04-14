package kz.nmbet.betradar.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sportradar.sdk.feed.lcoo.entities.TextEntity;
import com.sportradar.sdk.feed.lcoo.entities.TextsEntity;

import kz.nmbet.betradar.dao.domain.types.LocalizedEntity;

@Component
public class TextsEntityUtils {

	public static final String DEFAULT_LANG = "BET";
	public static final String EMPTY = "EMPTY";

	public static String getName(LocalizedEntity entity) {
		if (entity != null && StringUtils.isNotEmpty(entity.getNameRu())) {
			return entity.getNameRu();
		} else if (entity != null && StringUtils.isNotEmpty(entity.getNameEn())) {
			return entity.getNameEn();
		} else
			return EMPTY;
	}

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
