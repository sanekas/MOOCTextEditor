package spelling;

import java.util.LinkedList;
import java.util.List;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private final List<String> dict;

    public DictionaryLL() {
        this.dict = new LinkedList<>();
    }

    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
        return !dict.contains(word.toLowerCase()) && dict.add(word.toLowerCase());
    }


    /** Return the number of words in the dictionary */
    public int size() {
        return dict.size();
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        return dict.contains(s.toLowerCase());
    }

    
}
