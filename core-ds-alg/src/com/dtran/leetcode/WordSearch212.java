package com.dtran.leetcode;

import java.util.*;
import java.util.stream.Collectors;

public class WordSearch212 {
    public static class Node {
        Node parent = null;
        Map<Character, Node> children = new HashMap<>();
        boolean isWordEnd = false;
        char letter;
    }

    public List<String> findWords(char[][] board, String[] words) {
        // start from each spot, create all possible words, and compare with dictionary of words
        // nk start points of length nk, O(n^2 * k^2) with lookup in the dictionary of O(1)
        // keep track of visited 2d array that you have to reset as you go along words so you don't
        // visit the same space twice

        // can improve by exiting early for each letter...
        // or ending early by length
        // but i think you still have to traverse the whole 2d array
        // worse case if the full dictionary is present, we have to explore every combination and every space..
        // so it would be nk start points wtih nk lengths of strings from each start point...

        // exiting early
        // can have a map for each letter index
        // with a tree for each one
        // so organize the words list into trees based length
        // so will exit as early as possible once we've gotten to a leaf with no children

        // this means we have to create a tree nodes, that can access parent and children
        // a map that has the root of each tree
        // iterate through starts, and for each start, iterate sequentially along with the 2d array and the word map tree to know when to exit early

        Map<Character, Node> wordTree = buildWordTree(words);

        int x = board.length;
        int y = board[0].length;
        boolean[][] visited = new boolean[x][y];

        Set<String> returnWords = new HashSet<>();
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (wordTree.containsKey(board[i][j])) {
                    tryAllWords(board, i, j, visited, wordTree.get(board[i][j]), returnWords);
                }
            }
        }
        return returnWords.stream().collect(Collectors.toList());
    }

    public Map<Character, Node> buildWordTree(String[] words) {
        Map<Character, Node> wordTree = new HashMap<Character, Node>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            char curr = word.charAt(0);
            Node node = wordTree.get(curr);
            if (node == null) {
                node = new Node();
                node.letter = curr;
                wordTree.put(curr, node);
            }

            if (1 == word.length()) {
                node.isWordEnd = true;
                continue;
            }

            Node prevNode = node;
            for (int j = 1; j < word.length(); j++) {
                Node nextNode = prevNode.children.get(word.charAt(j));
                if (nextNode == null) {
                    nextNode = new Node();
                    nextNode.letter = word.charAt(j);
                    nextNode.parent = prevNode;
                    prevNode.children.put(word.charAt(j), nextNode);
                }
                if (j == word.length() - 1) {
                    nextNode.isWordEnd = true;
                }
                prevNode = nextNode;
            }
        }
        return wordTree;
    }

    public void tryAllWords(char[][] board, int i, int j, boolean[][] visited, Node parentNode, Set<String> returnWords) {
        visited[i][j] = true;
        if (parentNode.isWordEnd) {
            returnWords.add(getWord(parentNode));
        }

        // try right
        if (i + 1 < board.length && !visited[i + 1][j] && parentNode.children.containsKey(board[i+1][j])) {
            tryAllWords(board, i + 1, j, visited, parentNode.children.get(board[i + 1][j]), returnWords);
        }

        // try left
        if (i - 1 >= 0 && !visited[i - 1][j] && parentNode.children.containsKey(board[i-1][j])) {
            tryAllWords(board, i - 1, j, visited, parentNode.children.get(board[i - 1][j]), returnWords);
        }

        // try down
        if (j - 1 >= 0 && !visited[i][j-1] && parentNode.children.containsKey(board[i][j-1])) {
            tryAllWords(board, i, j - 1, visited, parentNode.children.get(board[i][j-1]), returnWords);
        }

        // try up
        if (j + 1 < board[0].length && !visited[i][j+1] && parentNode.children.containsKey(board[i][j+1])) {
            tryAllWords(board, i, j + 1, visited, parentNode.children.get(board[i][j+1]), returnWords);
        }
        visited[i][j] = false;
    }

    public String getWord(Node node) {
        Node curr = node;
        List<Character> word = new ArrayList<Character>();
        while (curr.parent != null) {
            word.add(curr.letter);
            curr = curr.parent;
        }
        word.add(curr.letter);
        StringBuilder sb = new StringBuilder();
        for (int i = word.size() - 1; i >= 0; i--) {
            sb.append(word.get(i));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        char[][] board = {
            {'o', 'a', 'a', 'n'},
            {'e', 't', 'a', 'e'},
            {'i', 'h', 'k', 'r'},
            {'i', 'f', 'l', 'v'},
        };
        String[] words = {"oath","pea","eat","rain"};
        WordSearch212 solution = new WordSearch212();
        List<String> foundWords = solution.findWords(board, words);
        foundWords.stream()
            .forEach(word -> System.out.println(word));
    }
}