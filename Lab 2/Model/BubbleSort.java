package Model;

import java.util.Collections;
import java.util.List;

public class BubbleSort extends AbstractSorter{
    public void sort(List<Integer> arrayToSort){
        boolean swapped;
        do{
            swapped = false;
            for(int i = 0; i < arrayToSort.size()-1; i++)
                if(arrayToSort.get(i) > arrayToSort.get(i+1)){
                    swapped = true;
                    Collections.swap(arrayToSort, i, i+1);
                }
        }while(swapped);
    }
}
