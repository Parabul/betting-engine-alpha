package kz.nmbet.betradar.dao.domain.types;

import org.apache.commons.lang3.StringUtils;

public enum TeamType {
	HOME(1), AWAY(2);

	private int type;

	TeamType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static TeamType find(String type) {
		if (StringUtils.isNumeric(type))
			return find(Integer.valueOf(type));
		return null;
	}

	public static TeamType find(int type) {
		for (TeamType oddsType : values()) {
			if (oddsType.type == type)
				return oddsType;
		}
		throw new IllegalArgumentException("unknown TeamType");
	}

}
