package syllablecounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * reads all the words from a URL or File and calls countSyllables. Output the
 * total number of words, syllables, and the elapsed time in seconds.
 * 
 * @author Wongsathorn Panichkurkul
 *
 */
public class Main {
	static final String DICT_URL = "http://se.cpe.ku.ac.th/dictionary.txt";
	static final double NANOSECONDS = 1.0E-9;

	/**
	 * read all words from URL ,calls countSyllables and print description.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		WordCounter counter = new WordCounter();
		BufferedReader reader;
		int countSyllables = 0;
		int countWord = 0;
		long startTime = 0;
		try {
			URL url = new URL(DICT_URL);
			InputStream in = url.openStream();
			reader = new BufferedReader(new InputStreamReader(in));
			startTime = System.nanoTime();
			while (true) {
				String word = reader.readLine();
				if (word == null)
					break;
				int syllables = counter.countSyllables(word);
				if (syllables != 0)
					countWord++;
				countSyllables += syllables;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		System.out.println("Reading words from " + DICT_URL);
		System.out.println("Counted " + countSyllables + " syllables in " + countWord + " words");
		System.out.printf("Elapse time: %.3f sec\n", (System.nanoTime() - startTime) * NANOSECONDS);
	}
}
