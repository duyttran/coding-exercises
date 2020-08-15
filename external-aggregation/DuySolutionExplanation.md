I've implemented my solution in SimpleSolution.java and refactored but still kept intact the naive solution already provided that fits all counts in memory.

For the solution that does not fit in memory, we will do the following:
1. sort and flush partitions of the original file to disk
2. add to a heap BufferedReaders to all partitions
3. the heap will be sorted by the current line being read from each BufferedReader
4. we can then continually poll from the heap and then advance the BufferedReader and put the BufferedReader back on the heap. This allows us to read the key value pairs in order, so that we count accurately across all partitions.

We can configure when we think we'll run out of memory by setting `maxCountsInMemory` in the initialization of SimpleSolution, which in the implementation will toggle between the O(n) solution and O(nlogn) solution that flushes sorted partitions to disk.

What is missing from this is a more optimal partitioning method and estimation of when we are actually going to run out of memory. The partitioning and estimation of memory here is simply a hardcoded value for how many lines we will allow to be read. 
