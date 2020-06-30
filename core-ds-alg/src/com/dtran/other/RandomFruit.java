package com.dtran.other;

import java.util.Random;
public class RandomFruit {

    public static class Range {
        int start;
        int end;
        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
    public static String grabFruit(String[] fruit, int[] freq) {
        // create a range map
        // do binary search across range map
        int sum = 0;
        Range[] ranges = new Range[fruit.length];
        for (int i = 0; i < fruit.length; i++) {
            ranges[i] = new Range(sum, sum + freq[i]);
            sum += freq[i];
        }
        Random random = new Random();
        int randomInt = 85;//random.nextInt(sum);
        System.out.println(randomInt);
        int range = binarySearchRange(ranges, 0, fruit.length, randomInt);
        return fruit[range];
    }

    public static int binarySearchRange(Range[] ranges, int start, int end, int randomInt) {
        if (end - start <= 1) {
            return start;
        }
        int mid = (end + start) / 2;
        if (randomInt > ranges[mid].end) {
            return binarySearchRange(ranges, mid + 1, end, randomInt);
        } else if (randomInt < ranges[mid].start){
            return binarySearchRange(ranges, start, mid, randomInt);
        } else {
            return mid;
        }
    }

    public static void main(String[] args) {
        String[] fruit = new String[]{"banana", "grape", "strawberry", "melon", "cherry"};
        int[] freq = new int[]{10, 40, 30, 10, 10};
        System.out.println(grabFruit(fruit, freq));
    }
}
