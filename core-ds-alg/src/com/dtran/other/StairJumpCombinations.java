package com.dtran.other;

import java.util.HashMap;
import java.util.Map;

public class StairJumpCombinations {
    public static long dfsFindAllEndings(long totalStairs, long currStair, Map<Long, Long> memoized) {
        if (currStair > totalStairs) {
            return 0;
        } else if (currStair == totalStairs) {
            return 1;
        } else if (memoized.containsKey(currStair)) {
            return memoized.get(currStair);
        } else {
            long one = dfsFindAllEndings(totalStairs, currStair + 1L, memoized);
            long two = dfsFindAllEndings(totalStairs, currStair + 2L, memoized);
            long three = dfsFindAllEndings(totalStairs, currStair + 3L, memoized);
            memoized.put(currStair, one + two + three);
            return memoized.get(currStair);
        }
    }

    public static long solution(long n) {
        // you can jump 1 2 or 3 stairs, find all combinations

        // Type your solution here
        // try all combinations until you hit n stairs
        // can imagine a tree of possibilities
        // all that have possible endings or impossible endings
        // only return the possible endings
        // can do a dfs of all possible endings
//       EndingCount count = new EndingCount();
//       dfsFindAllEndings(n, 0, count);
//       return count.count;

        // there's really 3 combinations
        // for 3, there is [1, 1, 1], [1,2], [2,1], [3]
        // for 2, there is [1, 1], [2]
        // for 1, there is [1]

        // for 4, there is [1, 1, 1, 1], [1, 2, 1], [2, 1, 1], [3, 1],
        // [2, 2], [1, 1, 2]
        // memoized

        // [1, 2, 4,

        Map<Long, Long> memoized = new HashMap<>();
        return dfsFindAllEndings(n, 0, memoized);

    }
}
