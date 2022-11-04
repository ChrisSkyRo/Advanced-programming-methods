package Container;

import Model.Task;
import Utils.Constants;

public class StackContainer extends SupahContainer {
    private static StackContainer instance = null;

    private StackContainer() {
        tasks = new Task[Constants.INITIAL_TASK_SIZE];
        size = 0;
    }

    public static StackContainer getInstance(){
        if (instance == null)
            instance = new StackContainer();
        return instance;
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            --size;
            return tasks[size];
        }
        return null;
    }
}
