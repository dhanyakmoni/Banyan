package org.example;

import java.util.*;

/**
 * Task: Generate a list of valid words from a character diagram
 * Given a diagram of letters and connections between the letters, generate a list of all
 * valid words that can be formed without reusing any letter.
 *
 *          p-> r, n, a, o1
 *          o1-> p, n, a, p1
 *          p1-> o1, a, c
 *          c-> o, n, a, p1
 *          o-> r, n, a, c
 *          r-> p ,n ,o
 *          n-> r, p, a, o, o1, c
 *          a-> n, p, o1, p1, c, 0
 *
 * For this example, valid words include "can", "pan", "popcorn", "corn", "cap", etc. Invalid
 * words would include "coop" (the two o's are not connected), "papa" (the a can not be
 * reused).
 * Assume you are provided a method
 * boolean isWord(String test)
 * That returns true if the test argument is a valid word and false otherwise. (You do not
 * have to implement this method although it may be useful to implement some version of
 * it for your testing purposes).
 * Requirements:
 * ● Build a data structure that represents the diagram structure. It should be flexible
 * enough to allow creation of any structure, not just the sample.
 * ● Write a main program that generates the structure above and then generates a
 * list of valid words from the program.
 * ● Output the results of the program in alphabetical order with a count of the
 * number of times the same word was generated (using different characters in the
 * diagram). For example, the word "pan" could be generated in two different ways
 * in the sample diagram.
 * Evaluation Criteria:
 */
public class WordsGraph {

    private List<Character> vertices = new ArrayList<>();
    public static List<String> meaningfulWords = Arrays.asList("on","no","pan","can","popcorn","pop","corn","nap",
            "select", "tea","set");
    public WordsGraph(List<Character> vertices) {
        this.vertices = vertices;
    }

    /**
     * @param graph
     * @return
     *
     * sample input
     * Map<Integer, List<Integer>> wordGraph = new HashMap<>();
     * wordGraph.put(0, Arrays.asList(5,6,7,1));
     * wordGraph.put(1, Arrays.asList(0,6,7,2));
     * each key uniquely represents a character
     */
    public Map<String, Integer> generateWordsFromGraph(Map<Integer, List<Integer>> graph) {
        Map<String, Integer> wordAndCountMap = new TreeMap<>();
        for (Integer charIndex : graph.keySet()) {
            //visited array tracks if we have visited a node already or not
            boolean[] visited = new boolean[graph.keySet().size()];
            visited[charIndex] = true;
            StringBuilder sb = new StringBuilder();
            sb.append(this.vertices.get(charIndex));
            generateWord(graph, visited, sb, wordAndCountMap, charIndex);
        }
        return wordAndCountMap;
    }

    /**
     * generates words start with a character represented by charIndex
     *
     * @param wordGraph
     * @param visited
     * @param sb
     * @param wordCountMap
     * @param charIndex
     */
    private void generateWord(Map<Integer, List<Integer>> wordGraph, boolean[] visited, StringBuilder sb,
                                     Map<String, Integer> wordCountMap, Integer charIndex) {
        String currWord = sb.toString();
        if (sb.length() > 1 && isWord(currWord)) {
            if (wordCountMap.containsKey(currWord)) {
                wordCountMap.put(currWord, wordCountMap.get(currWord) + 1);
            } else {
                wordCountMap.put(currWord, 1);
            }
        }
        List<Integer> neighbors = wordGraph.get(charIndex);
        for (Integer neighbor : neighbors) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                sb.append(this.vertices.get(neighbor));
                generateWord(wordGraph, visited, sb, wordCountMap, neighbor);
                sb.deleteCharAt(sb.length() - 1);
                visited[neighbor] = false;
            }
        }
    }

    /**
     * checks if the word is meaningful
     * @param word
     * @return
     */
    private boolean isWord(String word) {
        return meaningfulWords.contains(word); //if this method returns truw, you will see all possible words from the graph
    }

    public static void main(String args[]) {

        //........................................0   1   2   3   4   5   6   7 -> list index, each index represents a character in the below list
        List<Character> vertices = Arrays.asList('p','o','p','c','o','r','n','a');
        WordsGraph graph = new WordsGraph(vertices);

        //this is a mapping of each character to its neighbor
        // For e.g. 0(p) -> 5(r) ,6(n) ,7(a), 1(o) means p is connected to the nodes r n a and o
        Map<Integer, List<Integer>> wordGraph = new HashMap<>();
        wordGraph.put(0, Arrays.asList(5,6,7,1));
        wordGraph.put(1, Arrays.asList(0,6,7,2));
        wordGraph.put(2, Arrays.asList(1,7,3));
        wordGraph.put(3, Arrays.asList(4,6,7,2));
        wordGraph.put(4, Arrays.asList(5,6,7,3));
        wordGraph.put(5, Arrays.asList(0,6,4));
        wordGraph.put(6, Arrays.asList(4,5,0,1,7,3));
        wordGraph.put(7, Arrays.asList(6,0,1,2,3,4));

        System.out.println(graph.generateWordsFromGraph(wordGraph).toString());

        //.........................................0   1   2   3   4   5   6
        List<Character> vertices1 = Arrays.asList('s','e','t','l','e','c','a');
        wordGraph = new HashMap<>();
        wordGraph.put(0, Arrays.asList(1,3));
        wordGraph.put(1, Arrays.asList(0,2,3,6));
        wordGraph.put(2, Arrays.asList(1,5));
        wordGraph.put(3, Arrays.asList(1,4));
        wordGraph.put(4, Arrays.asList(3,5));
        wordGraph.put(5, Arrays.asList(4,2));
        wordGraph.put(6, Arrays.asList(1));

        graph = new WordsGraph(vertices1);
        System.out.println(graph.generateWordsFromGraph(wordGraph).toString());
    }
}
