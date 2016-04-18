package kz.nmbet.betradar.dao.domain.types;

public enum ScoreType {

	championship_outrights(30), short_term_outrights(40), podium_finish(50);

	private int type;

	ScoreType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static ScoreType find(int type) {
		for (ScoreType oddsType : values()) {
			if (oddsType.type == type)
				return oddsType;
		}
		throw new IllegalArgumentException("unknown OutrightOddsType");
	}
}
