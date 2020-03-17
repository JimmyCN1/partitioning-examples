import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class application {

    public static void main(String[] args) {
        List<Integer> longList = new ArrayList<>();

        for (int i = 0; i < 500000; i++) {
            longList.add(i);
        }


        partitionUsingPartitionClass(longList);
        partitionUsingJavaStreamAndGroupingCollector(longList);
    }

    private static void partitionUsingPartitionClass(List<Integer> longList) {
        long startTime = System.currentTimeMillis();

        Partition.ofSize(longList, 5000);

        long endTime = System.currentTimeMillis();

        System.out.println("partition using partition class: " + (endTime - startTime) + "ms");
    }

    private static void partitionUsingJavaStreamAndGroupingCollector(List<Integer> longList) {
        final int chunkSize = 5000;
        final AtomicInteger counter = new AtomicInteger();

        long startTime = System.currentTimeMillis();

        final Collection<List<Integer>> result = longList.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / chunkSize))
                .values();

        long endTime = System.currentTimeMillis();

        System.out.println("partition using partition class: " + (endTime - startTime) + "ms");
    }
}
