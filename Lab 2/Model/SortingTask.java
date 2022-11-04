package Model;

import Container.Strategy;

import java.util.List;

public class SortingTask extends Task {
    private final List<Integer> arrayToSort;
    private final Strategy sortingStrategy;
    private AbstractSorter sorter;

    public SortingTask(String taskId, String description, List<Integer> array, Strategy sortingStrategy) {
        super(taskId, description);
        this.arrayToSort = array;
        this.sortingStrategy = sortingStrategy;
    }

    @Override
    public void execute() {
        if(sortingStrategy == Strategy.BUBBLE)
            sorter = new BubbleSort();
        else if(sortingStrategy == Strategy.MERGE)
            sorter = new MergeSort();
        sorter.sort(arrayToSort);
        System.out.println(arrayToSort.toString());
    }
}
