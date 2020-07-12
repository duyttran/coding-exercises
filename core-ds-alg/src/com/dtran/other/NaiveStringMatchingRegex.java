package com.dtran.other;

/*


return boolean value if a match exists.

1

contains full string

2

hel.o => asdfheldoasdfds

3

."0 or 1", query = "hell?o" => "helo" , "hello"

 */
public class NaiveStringMatchingRegex {

    public static boolean exactMatch(String input, String regex) {
        for (int i = 0; i < input.length() - regex.length() + 1; i++) {
            for (int j = 0; j < regex.length(); j++) {
                if (input.charAt(i + j) != regex.charAt(j)) {
                    break;
                }
                if (j == regex.length() - 1 && input.charAt(i + j) == regex.charAt(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean partialMatch(String input, String regex) {
        for (int i = 0; i < input.length() - regex.length() + 1; i++) {
            for (int j = 0; j < regex.length(); j++) {
                if (regex.charAt(j) != '.' && input.charAt(i + j) != regex.charAt(j)) {
                    break;
                }
                if (j == regex.length() - 1 && (input.charAt(i + j) == regex.charAt(j) || regex.charAt(j) == '.')) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean evaluateChar(char a, char b) {
        if (b == '.') {
            return true;
        } else {
            return a == b;
        }
    }

    public static boolean isOptional(String regex) {
        return regex.length() > 1 && regex.charAt(1) == '?';
    }

    public static boolean tryOptionalMatch(String input, String regex) {
        if (isOptional(regex)) {
            if (regex.length() == 2) {
                return true;
            }
            return tryOptionalMatch(input, regex.charAt(0) + regex.substring(2)) || tryOptionalMatch(input, regex.substring(2));
        } else if (regex.length() == 1) {
            return evaluateChar(input.charAt(0), regex.charAt(0));
        } else if (!evaluateChar(input.charAt(0), regex.charAt(0))) {
            return false;
        } else {
            return tryOptionalMatch(input.substring(1), regex.substring(1));
        }
    }

    public static boolean optionalMatch(String input, String regex) {
        for (int i = 0; i < input.length(); i++) {
            if (tryOptionalMatch(input.substring(i), regex)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(exactMatch("abcdef", "abc"));
//        System.out.println(exactMatch("abcdef", "def"));
//        System.out.println(exactMatch("abcdef", "deg"));
//
//
//        System.out.println(partialMatch("abcdef", "ab."));
//        System.out.println(partialMatch("abcdef", "d.f"));
//        System.out.println(partialMatch("abcdef", "d.g"));

//        System.out.println(optionalMatch("abcdef", "abc"));
//        System.out.println(optionalMatch("abcdef", "def"));
//        System.out.println(optionalMatch("abcdef", "deg"));
//        System.out.println(optionalMatch("abcdef", "ab."));
//        System.out.println(optionalMatch("abcdef", "d.f"));
//        System.out.println(optionalMatch("abcdef", "d.g"));
        System.out.println(optionalMatch("abcdef", "ab?c"));
        System.out.println(optionalMatch("abcdef", "ae?c"));
        System.out.println(optionalMatch("abcdef", "g?ef"));
        System.out.println(optionalMatch("ab", "g?b"));

    }
}
