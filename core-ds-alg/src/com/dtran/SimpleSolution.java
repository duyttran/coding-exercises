
import java.io.*;
import java.util.*;

/**

 if we are close to running out of memory, abandon the counting and switch to partitioning and sorting the file
 sorting the file per partition will be O(nlogn)
 to save from rereadin the input when we've deciding to abandon the in memory O(n) solution already
 implemented here, lets collect all of the data to potentially sort the first partition now
 after sorting a partition, flush the sorted partition to a new file-p-0001.txt, file-p-0002.txt, ...
 we will use 2n the space fo the original file, we can delete the old file afterwards to save space if we really want

 once we have all sorted partitions, we can read pointeres to all the files, and read the keys in lexigraphic
 order and print out when we see a new lexigraphically unique key
 this way we will only keep the counts for one key in memory at a time, and only one line from each
 file in memory at any given point of time

 */
public class SimpleSolution {

    private final class AggResult {
        long count;
        String max;
    }

    private static final int MAX_COUNT_FIT_IN_MEMORY = 2;
    private static final String PARTITION_FILE_FORMAT = "%s-p-%d";

    private Map<String, AggResult> inMemoryAggregatedResult;
    private List<String> partitionBuffer;

    public SimpleSolution() {
        inMemoryAggregatedResult = new HashMap<>();
        partitionBuffer = new ArrayList<>(MAX_COUNT_FIT_IN_MEMORY);
    }

    private void printAggResults(String fileName) throws IOException {
        long count = 0;
        int partitionNumber = 0;

        try (FileReader fr = new FileReader(fileName);
             BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = null;
            while ((line = br.readLine()) != null) {

                String[] kv = parseSingleResult(line);
                if (kv == null) {
                    continue;
                }
                count++;

                if (count < MAX_COUNT_FIT_IN_MEMORY) {
                    // Either insert the new key/value or update an existing result. this is our optimistic solution
                    // hoping everythign will fit in memory
                    updateAggResult(kv);
                    // To save from rereadin the input when we've deciding to abandon the in memory O(n) solution already
                    // implemented here, lets collect all of the data to potentially sort the first partition now
                    partitionBuffer.add(line);
                } else {
                    // if we are close to running out of memory, abandon counting and switch to partitioning and sorting the file
                    // sorting the file per partition will be O(nlogn)
                    if (count == MAX_COUNT_FIT_IN_MEMORY) {
                        // flush in memory count since its useless
                        inMemoryAggregatedResult.clear();
                    }
                    partitionBuffer.add(line);
                    if (partitionBuffer.size() == MAX_COUNT_FIT_IN_MEMORY) {
                        // after sorting a partition, flush the sorted partition to a new file-p-0001.txt, file-p-0002.txt, ...
                        flushPartitionBuffer(fileName, partitionNumber);
                        partitionNumber++;
                    }
                }
            }
        }

        if (count < MAX_COUNT_FIT_IN_MEMORY) {
            printInMemoryAggregation();
        } else {
            // flush one more time for leftover buffer
            if (partitionBuffer.size() > 0) {
                // after sorting a partition, flush the sorted partition to a new file-p-0001.txt, file-p-0002.txt, ...
                flushPartitionBuffer(fileName, partitionNumber);
                partitionNumber++;
            }
            printPartitionedAggregation(fileName, partitionNumber);
        }
    }

    private class BufferedReaderPointer {
        private BufferedReader br;
        private FileReader fr;
        private String currLine;
        public BufferedReaderPointer(String fileName) throws IOException {
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            currLine = br.readLine();
        }
    }

    private void printPartitionedAggregation(String fileNameRoot, int numPartitions) throws IOException {
        // once we have all sorted partitions, we can read pointeres to all the files, and read the keys in lexigraphic
        // order and print out when we see a new lexigraphically unique key
        // this way we will only keep the counts for one key in memory at a time, and only one line from each
        // file in memory at any given point of time
        PriorityQueue<BufferedReaderPointer> heap = new PriorityQueue<>(new Comparator<BufferedReaderPointer>() {
            @Override
            public int compare(BufferedReaderPointer bufferedReaderPointer, BufferedReaderPointer t1) {
                return bufferedReaderPointer.currLine.compareTo(t1.currLine);
            }
        });
        for (int i = 0; i < numPartitions; i++) {
            BufferedReaderPointer partitionPointer = new BufferedReaderPointer(String.format(PARTITION_FILE_FORMAT, fileNameRoot, i));
            heap.add(partitionPointer);
        }

        String currKey = null;
        String currValue = null;
        int currCount = 0;
        while (!heap.isEmpty()) {
            BufferedReaderPointer pointer = heap.poll();
            String[] keyValue = pointer.currLine.split(" ");
            if (currKey == null) {
                currKey = keyValue[0];
                currValue = keyValue[1];
                currCount++;
            } else if (currKey.equals(keyValue[0])) {
                currValue = keyValue[1];
                currCount++;
            } else {
                System.out.println(currKey + ": " + currCount + ", " + currValue);
                currKey = keyValue[0];
                currValue = keyValue[1];
                currCount = 1;
            }
            // add the next line to the heap
            pointer.currLine = pointer.br.readLine();
            if (pointer.currLine != null && pointer.currLine != "") {
                heap.add(pointer);
            }
        }
        if (currCount == 1) {
            System.out.println(currKey + ": " + currCount + ", " + currValue);
        }
    }

    private void flushPartitionBuffer(String fileName, int partitionNumber) throws IOException {
        partitionBuffer.sort(Comparator.naturalOrder());
        try (FileWriter fw = new FileWriter(String.format(PARTITION_FILE_FORMAT, fileName, partitionNumber));
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (String s : partitionBuffer) {
                bw.write(s);
                bw.newLine();
            }
        }
        partitionBuffer.clear();
    }

    private void printInMemoryAggregation() {
        // All done with aggregation, output the result.
        for (Map.Entry<String, AggResult> it: inMemoryAggregatedResult.entrySet()) {
            AggResult val = it.getValue();
            System.out.println(it.getKey() + ": " + val.count + ", " + val.max);
        }
    }

    private void updateAggResult(String[] kv) {
        AggResult agg = inMemoryAggregatedResult.get(kv[0]);
        if (agg == null) {
            // New key-value, add it to intermediate result.
            agg = new AggResult();
            agg.count = 1;
            agg.max = kv[1];
            inMemoryAggregatedResult.put(kv[0], agg);
        } else {
            // Update an existing entry.
            if (agg.max.compareTo(kv[1]) < 0) agg.max = kv[1];
            agg.count++;
        }
    }

    private String[] parseSingleResult(String line) throws IOException {
        // Parse the key value. It should be space separated.
        if (line.trim().length() == 0) return null;
        String[] kv = line.split(" ");
        if (kv.length != 2) {
            throw new IOException("Invalid data element: " + line);
        }
        return kv;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java SimpleSolution <input-file>");
            System.exit(1);
        }
        SimpleSolution solution = new SimpleSolution();
        solution.printAggResults(args[0]);
    }
}
