/**
 * 
 */
package spelling;

import java.util.*;


/**
 * @author UC San Diego Intermediate MOOC team
 *
 */
public class NearbyWords implements SpellingSuggest {
	// THRESHOLD to determine how many words to look through when looking
	// for spelling suggestions (stops prohibitively long searching)
	// For use in the Optional Optimization in Part 2.
	private static final int THRESHOLD = 1000; 

	Dictionary dict;

	public NearbyWords (Dictionary dict) 
	{
		this.dict = dict;
	}

	/** Return the list of Strings that are one modification away
	 * from the input string.  
	 * @param s The original String
	 * @param wordsOnly controls whether to return only words or any String
	 * @return list of Strings which are nearby the original string
	 */
	public List<String> distanceOne(String s, boolean wordsOnly )  {
		   List<String> retList = new ArrayList<String>();
		   insertions(s, retList, wordsOnly);
		   substitution(s, retList, wordsOnly);
		   deletions(s, retList, wordsOnly);
		   return retList;
	}

	
	/** Add to the currentList Strings that are one character mutation away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void substitution(String s, List<String> currentList, boolean wordsOnly) {
		// for each letter in the s and for all possible replacement characters
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				// use StringBuffer for an easy interface to permuting the 
				// letters in the String
				StringBuilder sb = new StringBuilder(s);
				sb.setCharAt(index, (char)charCode);

				// if the item isn't in the list, isn't the original string, and
				// (if wordsOnly is true) is a real word, add to the list
				addWortToList(s, sb.toString(), currentList, wordsOnly);

			}
		}
	}
	
	/** Add to the currentList Strings that are one character insertion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void insertions(String s, List<String> currentList, boolean wordsOnly ) {
		for(int index = 0; index < s.length(); index++){
			for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
				StringBuilder sb = new StringBuilder(s);
				sb.insert(index, (char)charCode);
				addWortToList(s, sb.toString(), currentList, wordsOnly);
			}
		}
		for(int charCode = (int)'a'; charCode <= (int)'z'; charCode++) {
			addWortToList(s, s + (char) charCode, currentList, wordsOnly);
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param s The original String
	 * @param currentList is the list of words to append modified words 
	 * @param wordsOnly controls whether to return only words or any String
	 * @return
	 */
	public void deletions(String s, List<String> currentList, boolean wordsOnly) {
		StringBuilder sb = new StringBuilder(s);
		for (int i = 0; i < s.length(); ++i) {
			sb.deleteCharAt(i);
			addWortToList(s, sb.toString(), currentList, wordsOnly);
			sb = new StringBuilder(s);
		}
	}

	private void addWortToList(String word, String modifiedWord, List<String> currentList, boolean wordsOnly) {
		if(!currentList.contains(modifiedWord) &&
				(!wordsOnly||dict.isWord(modifiedWord)) &&
				!word.equals(modifiedWord)) {
			currentList.add(modifiedWord);
		}
	}

	/** Add to the currentList Strings that are one character deletion away
	 * from the input string.  
	 * @param word The misspelled word
	 * @param numSuggestions is the maximum number of suggestions to return 
	 * @return the list of spelling suggestions
	 */
	@Override
	public List<String> suggestions(String word, int numSuggestions) {
		Queue<String> queue = new LinkedList<String>();     // String to explore
		HashSet<String> visited = new HashSet<String>();   // to avoid exploring the same  
		List<String> retList = new LinkedList<String>();   // words to return

		queue.add(word);
		visited.add(word);

		while (!queue.isEmpty() && numSuggestions > 0) {
			String currWord = queue.poll();
			List<String> neighbours = distanceOne(currWord, true);
			for (String w : neighbours) {
				if (!visited.contains(w)) {
					queue.add(w);
					visited.add(w);
					if (dict.isWord(w) && numSuggestions > 0) {
						numSuggestions--;
						retList.add(w);
					}
				}
			}
		}

		return retList;

	}	

   public static void main(String[] args) {
	   //basic testing code to get started
	   String word = "i";
	   // Pass NearbyWords any Dictionary implementation you prefer
	   Dictionary d = new DictionaryHashSet();
	   DictionaryLoader.loadDictionary(d, "data/dict.txt");
	   NearbyWords w = new NearbyWords(d);
	   List<String> l = w.distanceOne(word, true);
	   System.out.println("One away word Strings for for \""+word+"\" are:");
	   System.out.println(l+"\n");

	   word = "tailo";
	   List<String> suggest = w.suggestions(word, 10);
	   System.out.println("Spelling Suggestions for \""+word+"\" are:");
	   System.out.println(suggest);

   }

}
