package syllablecounter;

/**
 * Test the countSyllables method from WordCounter class.
 * 
 * @author Wongsathorn Panichkurkul
 *
 */
public class OOSyllableCounter {
	
	/**
	 * For test and print the result.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		WordCounter counter = new WordCounter();
		System.out.println(counter.countSyllables("Test"));
	}

}
