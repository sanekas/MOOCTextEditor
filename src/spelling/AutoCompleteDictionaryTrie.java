package spelling;

import java.util.*;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word) {
		if (word == null || word.isEmpty() || isWord(word)) {
			return false;
		}
		size++;
		addWord(word.toLowerCase(), root, 0);
		return true;
	}

	private void addWord(String word, TrieNode node, int index) {
		if (node.getText().equals(word) && !node.endsWord()) {
			node.setEndsWord(true);
			return;
		}
		if (!node.getValidNextCharacters().contains(word.charAt(index))) {
			TrieNode newNode = node.insert(word.charAt(index));
			if (index + 1 == word.length()) {
				newNode.setEndsWord(true);
			} else {
				addWord(word, newNode, ++index);
			}
		} else {
			addWord(word, node.getChild(word.charAt(index)), ++index);
		}
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size() {
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) {
		return !(s == null || s.isEmpty()) && isWord(s.toLowerCase(), root, 0);
	}

	private boolean isWord(String s, TrieNode node, int index) {
		if (node.getText().equals(s) && node.endsWord()) {
			return true;
		} else if (index < s.length()){
			TrieNode currNode = node.getChild(s.charAt(index));
			return currNode != null && isWord(s, currNode, ++index);
		} else {
			return false;
		}
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) {
     	if (prefix == null || numCompletions <= 0) {
     		return Collections.emptyList();
		}
		if (prefix.isEmpty()) {
			return bfs(root, 4);
		}
     	TrieNode prefixNode = getPrefixNode(prefix, 0);
		if (prefixNode == null) {
		 	return Collections.emptyList();
		}
    	 
         return bfs(prefixNode, numCompletions);
     }

     private TrieNode getPrefixNode(String prefix, int index) {
     	TrieNode tempNode = root;
     	while (true) {
     		if (tempNode.getText().equals(prefix)) {
     			return tempNode;
			} else {
				if (tempNode.getValidNextCharacters().contains(prefix.charAt(index))) {
					tempNode = tempNode.getChild(prefix.charAt(index));
					index++;
				} else {
					return null;
				}
			}
		}
     }

	 private List<String> bfs(TrieNode prefixNode, int numCompletions) {
	 	Queue<TrieNode> queue = new LinkedList<>();
		List<String> completions = new ArrayList<>();
 		queue.add(prefixNode);
		if (prefixNode.endsWord()) {
			completions.add(prefixNode.getText());
		}
		while (!queue.isEmpty() && completions.size() != numCompletions) {
			TrieNode currNode = queue.poll();
			if (currNode.endsWord()) {
				if (!completions.contains(currNode.getText())) {
					completions.add(currNode.getText());
				}
			}
			for (Character ch : currNode.getValidNextCharacters()) {
				queue.add(currNode.getChild(ch));
			}
		}
		Collections.sort(completions);
		return completions;
	 }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}