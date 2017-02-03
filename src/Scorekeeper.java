
public class Scorekeeper {
	enum ScoreTypes {
		DECK_TO_FOUNDATION, DECK_TO_REG, REG_TO_FOUNDATION, FOUNDATION_TO_REG, NOTHING
	}

	public int keepScore(int fromDeck, int toDeck, int currentScore,
			int cardsFlipped) {
		ScoreTypes scoreType = determineScoreType(fromDeck, toDeck);
		switch (scoreType) {
		case DECK_TO_FOUNDATION:
			currentScore += 10;
			break;
		case DECK_TO_REG:
			currentScore += 5;
			break;
		case REG_TO_FOUNDATION:
			currentScore += 10;
			break;
		case FOUNDATION_TO_REG:
			currentScore -= 15;
			break;
		default:
			break;
		}
		return currentScore + (cardsFlipped * 5);
	}

	public ScoreTypes determineScoreType(int fromDeck, int toDeck) {
		if (fromDeck == 11) {
			if (toDeck >= 7 && toDeck <= 10) {
				return ScoreTypes.DECK_TO_FOUNDATION;
			} else if (toDeck >= 0 && toDeck <= 6) {
				return ScoreTypes.DECK_TO_REG;
			} else
				return ScoreTypes.NOTHING;
		} else if (fromDeck <= 10 && fromDeck >= 7) {
			if (toDeck >= 7 && toDeck <= 10) {
				return ScoreTypes.NOTHING;
			} else if (toDeck >= 0 && toDeck <= 6) {
				return ScoreTypes.FOUNDATION_TO_REG;
			} else
				return ScoreTypes.NOTHING;
		} else {
			if (toDeck >= 7 && toDeck <= 10) {
				return ScoreTypes.REG_TO_FOUNDATION;
			} else if (toDeck >= 0 && toDeck <= 6) {
				return ScoreTypes.NOTHING;
			} else
				return ScoreTypes.NOTHING;
		}
	}
}
