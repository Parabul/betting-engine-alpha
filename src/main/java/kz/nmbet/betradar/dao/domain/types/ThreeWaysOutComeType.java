package kz.nmbet.betradar.dao.domain.types;


public enum ThreeWaysOutComeType {
	HOME("1"), AWAY("2"), DRAW("X");

	private String type;

	ThreeWaysOutComeType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ThreeWaysOutComeType find(String type) {
		for (ThreeWaysOutComeType oddsType : values()) {
			if (oddsType.type.equalsIgnoreCase(type))
				return oddsType;
		}
		throw new IllegalArgumentException("unknown TeamType");
	}

}
