package com.dtran.other;

public class MaxStockProfit {
    public static long solution(long[] prices) {
        // Type your solution here
        // n*nlogn
        // go from the back, keep track of the highest number
        // and your current minus that, keep track of the max profit

        if (prices.length <= 1) {
            return 0;
        }

        long maxAfter = prices[prices.length - 1];
        long maxProfit = Long.MIN_VALUE;
        for (int i = prices.length - 2; i >= 0; i--) {
            long profit = maxAfter - prices[i];
            if (maxProfit < profit) {
                maxProfit = profit;
            }
            if (maxAfter < prices[i]) {
                maxAfter = prices[i];
            }
        }
        return maxProfit;
    }
}
