package Container;

import Model.Task;
import Utils.Constants;

public class QueueContainer extends SupahContainer {
    private static QueueContainer instance = null;

    private QueueContainer() {
        tasks = new Task[Constants.INITIAL_TASK_SIZE];
        size = 0;
    }

    public static QueueContainer getInstance(){
        if (instance == null)
            instance = new QueueContainer();
        return instance;
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            --size;
            Task t = tasks[0];
            System.arraycopy(tasks, 1, tasks, 0, size);
            return t;
        }
        return null;
    }
}
