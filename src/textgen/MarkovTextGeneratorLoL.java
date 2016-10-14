package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		if (sourceText != null && sourceText.length() > 0) {
			String[] words = generateWords(sourceText);
			if (words.length > 0) {
				starter = words[0].trim();
				String prevWord = starter;
				for (int i = 1; i < words.length; ++i) {
					ListNode prevWordNode = new ListNode(prevWord);
					if (wordList.contains(prevWordNode)) {
						int ind = wordList.indexOf(prevWordNode);
						wordList.get(ind).addNextWord(words[i].trim());
					} else {
						prevWordNode.addNextWord(words[i].trim());
						wordList.add(prevWordNode);
					}
					prevWord = words[i].trim();
				}
				wordList.get(wordList.size() - 1).addNextWord(starter);

			}
		}
	}

	private String[] generateWords(String sourceText) {
		List<String> listOfWords = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile("[a-zA-Z]+");
		Matcher m = tokSplitter.matcher(sourceText);

		while (m.find()) {
			listOfWords.add(m.group());
		}
		String[] words = new String[listOfWords.size()];
		listOfWords.toArray(words);
		return words;
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		if (numWords != 0) {
			String currWord = starter.trim();
			StringBuilder result = new StringBuilder(currWord);
			if (currWord.length() > 0) {
				result.append(" ");
				while (numWords > 1) {
					ListNode currNode = new ListNode(currWord);
					int ind = wordList.indexOf(currNode) >= 0 ? wordList.indexOf(currNode) : 0;
					String randomWord = wordList.get(ind).getRandomNextWord(rnGenerator);
					result.append(randomWord).append(" ");
					currWord = randomWord;
					numWords--;

				}
			}
			return result.toString().trim();
		} else {
			return "";
		}
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		wordList.clear();
		starter = "";
		train(sourceText);
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
	    // The random number generator should be passed from
	    // the MarkovTextGeneratorLoL class
	    return nextWords.get(generator.nextInt(nextWords.size()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ListNode)) return false;

		ListNode listNode = (ListNode) o;

		return getWord().equals(listNode.getWord());

	}

	@Override
	public int hashCode() {
		return getWord().hashCode();
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


