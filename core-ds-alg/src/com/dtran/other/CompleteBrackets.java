package com.dtran.other;

public class CompleteBrackets {
    public static String solution(String angles) {
        // Type your solution here
        // start with a coutner at 0
        // when a bracket opens, add 1
        // when a bracket closes, add -1
        // if we ever go to -1, add 1
        // if we get to the end, and there's still open, close as many as there's left
        int bracketCounter = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < angles.length(); i++) {
            if (angles.charAt(i) == '<') {
                bracketCounter += 1;
            } else {
                bracketCounter -= 1;
            }

            if (bracketCounter < 0) {
                sb.append('<');
            }

            sb.append(angles.charAt(i));
        }

        for (int i = 0; i < bracketCounter; i++) {
            sb.append('>');
        }
        return sb.toString();
    }
}
