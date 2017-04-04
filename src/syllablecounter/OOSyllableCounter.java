package syllablecounter;

/**
 * A OOSyllableCounter class that count syllables in a String by using the OO approach.
 * 
 * @author Wongsathorn Panichkurkul
 *
 */
public class OOSyllableCounter {
	private final State START = new StartState();
	private final State SINGLEVOWEL = new SingleVowelState();
	private final State MULTIVOWEL = new MultiVowelState();
	private final State CONSONANT = new Consonant();
	private final State NONWORD = new NonWordState();
	private final State HYPHEN = new Hyphen();
	private final State E = new EcheckState();
	private State state;
	private int syllableCount;
	private int length;
	private int position;

	/**
	 * returns the number of syllables in the String. If the string parameter is
	 * not a word then return 0.
	 * 
	 * @param word is the word that you want to count.
	 * @return the number of syllables if string parameter is not a word then return 0.
	 */
	public int countSyllables(String word) {
		syllableCount = 0;
		position = 0;
		setState(START);
		word = word.trim().toLowerCase();
		length = word.length();
		for (int i = 0; i < length; i++) {
			char c = word.charAt(i);
			if (c == '\'')
				continue;
			state.handleChar(c);
			position++;
		}
		return this.syllableCount;
	}

	/**
	 * Set or change the state.
	 * 
	 * @param newstate is the State that you want to set to.
	 */
	private void setState(State newstate) {
		if (newstate != state)
			newstate.enterState();
		state = newstate;
	}

	/**
	 * State abstract class for each states.
	 * 
	 */
	abstract class State {
		private final String ALL_CONSONANT = "bcdfghjklmnpqrstvwxyz";
		private final String VOVEL_OR_Y = "aeiouy";
		private final String VOVEL = "aeiou";

		/**
		 * for check character that has a vowel or letter or non-word or
		 * hyphen and handle into the next State.
		 * 
		 * @param c is character that you want to handle.
		 */
		public abstract void handleChar(char c);

		/**
		 * to perform an action in state.
		 */
		public void enterState() {
			/* default is to do nothing */ }

		/**
		 * Check char parameter is the last character and 'e' or not.
		 * 
		 * @param c is char that you want to check.
		 * @return true if char parameter is the last character and 'e' ,false
		 *         otherwise.
		 */
		public boolean isLastE(char c) {
			return (c == 'e' && position == (length - 1));
		}

		/**
		 * Check char parameter is a vowel or not (not include 'y').
		 * 
		 * @param c is char that you want to check.
		 * @return true if char parameter is a vowel ,false otherwise.
		 */
		public boolean isVowel(char c) {
			return this.VOVEL.indexOf(c) != -1;
		}

		/**
		 * Check char parameter is a letter or not.
		 * 
		 * @param c is char that you want to check.
		 * @return true if char parameter is a letter ,false otherwise.
		 */
		public boolean isLetter(char c) {
			return this.ALL_CONSONANT.indexOf(c) != -1;
		}

		/**
		 * Check char parameter is a vowel or not (include 'y').
		 * 
		 * @param c is char that you want to check.
		 * @return true if char parameter is a letter ,false otherwise.
		 */
		public boolean isVowelOrY(char c) {
			return this.VOVEL_OR_Y.indexOf(c) != -1;
		}
	}

	/**
	 * StartState is a State that check the first character at the first time
	 * and handle into the next State.
	 * 
	 */
	class StartState extends State {
		@Override
		public void handleChar(char c) {
			if (isLastE(c))
				setState(E);
			else if (isVowelOrY(c))
				setState(SINGLEVOWEL);
			else if (isLetter(c))
				setState(CONSONANT);
			else
				setState(NONWORD);
		}
	}

	/**
	 * SingleVowelState is a State that the character has a vowel (include y)
	 * handle into the next State.
	 *
	 */
	class SingleVowelState extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isLastE(c))
				setState(E);
			else if (isVowel(c))
				setState(MULTIVOWEL);
			else if (isLetter(c))
				setState(CONSONANT);
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

		/**
		 * @see State#enterState()
		 */
		public void enterState() {
			syllableCount++;
		}
	}

	/**
	 * MultiVowelState is a State that the character has a vowel and handle into
	 * the next State.
	 *
	 */
	class MultiVowelState extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowel(c)) {
			} else if (isLetter(c))
				setState(CONSONANT);
			else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}
	}

	/**
	 * Consonant is a State that the character is a letter and handle into the
	 * next State.
	 * 
	 */
	class Consonant extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isLastE(c))
				setState(E);
			else if (isVowelOrY(c))
				setState(SINGLEVOWEL);
			else if (isLetter(c)) {
			} else if (c == '-')
				setState(HYPHEN);
			else
				setState(NONWORD);
		}

	}

	/**
	 * Hyphen is a State that the character is a hyphen and handle into the next
	 * State.
	 * 
	 */
	class Hyphen extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			if (isVowelOrY(c))
				setState(SINGLEVOWEL);
			else if (isLetter(c))
				setState(CONSONANT);
			else if (c == '-') {
			} else
				setState(NONWORD);
		}
	}

	/**
	 * NonWordState is a State that the character is not vowel and letter and
	 * set syllableCount to 0.
	 *
	 */
	class NonWordState extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			// do nothing.
		}

		/**
		 * @see State#enterState()
		 */
		public void enterState() {
			syllableCount = 0;
		}
	}

	/**
	 * EcheckState is a last State that the last character is 'e' and check
	 * condition.
	 *
	 */
	class EcheckState extends State {
		/**
		 * @see State#handleChar(char)
		 */
		@Override
		public void handleChar(char c) {
			// do nothing.
		}

		/**
		 * @see State#enterState()
		 */
		public void enterState() {
			if (syllableCount == 0)
				syllableCount++;
		}
	}
}
