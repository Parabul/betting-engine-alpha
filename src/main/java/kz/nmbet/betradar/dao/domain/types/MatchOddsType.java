package kz.nmbet.betradar.dao.domain.types;

public enum MatchOddsType {
	handicap(01), correct_score(02), three_way(10), two_way(20), totals(60);

	private int type;

	MatchOddsType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static MatchOddsType find(int type) {
		for (MatchOddsType oddsType : values()) {
			if (oddsType.type == type)
				return oddsType;
		}
		throw new IllegalArgumentException("unknown MatchOddsType");

	}
}
