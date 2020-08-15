
import java.io.*;
import java.util.*;

/**
 * This is no longer just a simple solution. This will attempt a simple in memory solution. If the counts don't
 * fit in memory (configurable by a maxCountFitInMemory) we will flush sorted partitions of the file and then
 * print the aggregated results by reading through the sorted partitions in order.
 */
public class SimpleSolution {

  private final class AggResult {
    long count;
    String max;
  }

  private static final String PARTITION_FILE_FORMAT = "%s-p-%d";

  private int maxCountFitInMemory;
  private Map<String, AggResult> inMemoryAggregatedResult;
  private List<String> partitionBuffer;

  public SimpleSolution(int maxCountFitInMemory) {
    this.maxCountFitInMemory = maxCountFitInMemory;
    this.inMemoryAggregatedResult = new HashMap<>();
    this.partitionBuffer = new ArrayList<>(maxCountFitInMemory);
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

        if (count < maxCountFitInMemory) {
          // This is our optimistic solution hoping everything will fit in memory with O(n) time complexity
          updateAggResult(kv);
          // To save from rereading the input file when we've deciding to abandon the optimistic solution
          // lets collect all of the data to potentially sort and flush the first partition now
          partitionBuffer.add(line);
        } else {
          // This is our O(nlogn) time complexity solution
          // We will partitioning the file and flush sorting  partitions to disk first.
          if (count == maxCountFitInMemory) {
            // flush in memory count since its useless
            inMemoryAggregatedResult.clear();
          }
          partitionBuffer.add(line);
          if (partitionBuffer.size() == maxCountFitInMemory) {
            flushPartitionBuffer(fileName, partitionNumber);
            partitionNumber++;
          }
        }
      }
    }

    if (count < maxCountFitInMemory) {
      // this is our print method if we were able to fit everything in memory
      printInMemoryAggregation();
    } else {
      // flush one more time for leftover buffer
      if (partitionBuffer.size() > 0) {
        // after sorting a partition, flush the sorted partition to a new file-p-0001.txt, file-p-0002.txt, ...
        flushPartitionBuffer(fileName, partitionNumber);
        partitionNumber++;
      }
      // this is our print method if we weren't able to fit everything in memory and had to flush partitions to disk
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
    // once we have all sorted partitions, we can keep all the pointers to the files in a heap sorted by the current
    // line pointer and read the keys in lexigraphic order and print out when we see a new lexigraphically unique key
    // this way we will only keep the counts for one key in memory at a time, and only one line from each
    // file in memory at any given point of time. we shouldn't have any memory problems anymore.
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
        // initial case
        currKey = keyValue[0];
        currValue = keyValue[1];
        currCount++;
      } else if (currKey.equals(keyValue[0])) {
        currValue = keyValue[1];
        currCount++;
      } else {
        // print only when it changes
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
    // print the last aggregated count
    System.out.println(currKey + ": " + currCount + ", " + currValue);
  }

  private void flushPartitionBuffer(String fileName, int partitionNumber) throws IOException {
    // sorts partition buffer and flushes to a partition file with name "<fileName>-p-<partitionNumber>"
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
    // this will do the O(n) solution that fits in memory
//    SimpleSolution solution = new SimpleSolution(5);

    // this will do the O(nlogn) solution that will sort partitions, and read sorted files in order.
    // this will split the simple solution into 2 partitions
    SimpleSolution solution = new SimpleSolution(2);
    solution.printAggResults(args[0]);
  }
}
