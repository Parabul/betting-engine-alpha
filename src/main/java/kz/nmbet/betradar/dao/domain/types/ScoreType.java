package kz.nmbet.betradar.dao.domain.types;

public enum ScoreType {
	TYPE_1P("1P"), TYPE_1Q("1Q"), TYPE_2P("2P"), TYPE_2Q("2Q"), TYPE_3P("3P"), TYPE_3Q("3Q"), TYPE_WO("WO"), TYPE_4Q(
			"4Q"), TYPE_AP("AP"), TYPE_C("C"), TYPE_FT("FT"), TYPE_HT("HT"), TYPE_OT("OT"), TYPE_Set1(
					"Set1"), TYPE_Set2("Set2"), TYPE_Set3("Set3"), TYPE_Set4("Set4"), TYPE_Set5("Set5");
	private String type;

	ScoreType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ScoreType find(String type) {
		for (ScoreType oddsType : values()) {
			if (oddsType.type.equalsIgnoreCase(type))
				return oddsType;
		}
		throw new IllegalArgumentException("unknown TeamType");
	}
}
