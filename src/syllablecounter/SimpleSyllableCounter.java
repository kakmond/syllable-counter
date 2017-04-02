package syllablecounter;

/**
 * A SimpleSyllableCounter class that count syllables in a String.
 * 
 * @author Wongsathorn Panichkurkul
 *
 */
public class SimpleSyllableCounter {

	private final String CONSONANT = "bcdfghjklmnpqrstvwxyz";
	private final String VOVEL_OR_Y = "aeiouy";
	private final String VOVEL = "aeiou";

	/**
	 * returns the number of syllables in the String. If the string parameter is
	 * not a word then return 0.
	 * 
	 * @param word is the word that you want to count.
	 * @return the number of syllables if string parameter is not a word then
	 *         return 0.
	 */
	public int countSyllables(String word) {
		word = word.toLowerCase().trim();
		int syllables = 0;
		char c = ' ';
		State state = State.START; // State is an enum of the states

		for (int k = 0; k < word.length(); k++) {
			c = word.charAt(k);
			if (c == '\'')
				continue; // ignore apostrophe
			switch (state) {
			// process character c using state machine
			case START:
				if (isVowelOrY(c)) {
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (isLetter(c)) {
					state = State.CONSONANT;
				} else
					state = State.NONWORD;
				break;
			case SINGLE_VOWEL:
				if (isVowel(c)) {
					if ((k == word.length() - 1) && c == 'e' && syllables > 0) {
					} else
						state = State.MULTIVOWEL;
				} else if (isLetter(c)) {
					state = State.CONSONANT;
				} else if (c == '-')
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				break;
			case HYPHEN:
				if (isVowelOrY(c)) {
					state = State.SINGLE_VOWEL;
					syllables++;
				} else if (isLetter(c)) {
					state = State.CONSONANT;
				} else if (c == '-') { /* stay in HYPHEN state */
				} else
					state = State.NONWORD;
				break;
			case MULTIVOWEL:
				if (isVowel(c)) { /* stay in MULTIVOWEL state */
				} else if (isLetter(c))
					state = State.CONSONANT;
				else if (c == '-')
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				break;
			case CONSONANT:
				if (isVowelOrY(c)) {
					if ((k == word.length() - 1) && c == 'e' && syllables > 0) {
					} else {
						state = State.SINGLE_VOWEL;
						syllables++;
					}
				} else if (isLetter(c)) { /* stay in CONSONANT state */
				} else if (c == '-')
					state = State.HYPHEN;
				else
					state = State.NONWORD;
				break;
			case NONWORD:
				syllables = 0;
				break;
			}
		}
		return syllables;
	}

	/**
	 * Check char parameter is a vowel or not (not include 'y').
	 * 
	 * @param c is char that you want to check.
	 * @return true if char parameter is a vowel ,false otherwise.
	 */
	private boolean isVowel(char c) {
		return VOVEL.indexOf(c) != -1;
	}

	/**
	 * Check char parameter is a letter or not.
	 * 
	 * @param c is char that you want to check.
	 * @return true if char parameter is a letter ,false otherwise.
	 */
	private boolean isLetter(char c) {
		return CONSONANT.indexOf(c) != -1;
	}

	/**
	 * Check char parameter is a vowel or not (include 'y').
	 * 
	 * @param c is char that you want to check.
	 * @return true if char parameter is a letter ,false otherwise.
	 */
	private boolean isVowelOrY(char c) {
		return VOVEL_OR_Y.indexOf(c) != -1;
	}
}
