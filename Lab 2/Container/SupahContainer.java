package Container;

import Model.Task;
import Utils.Constants;

public abstract class SupahContainer implements Container {
    protected Task[] tasks;
    protected int size;

    public SupahContainer() {
        tasks = new Task[Constants.INITIAL_TASK_SIZE];
        size = 0;
    }

    public int size() {
        return size;
    }
    public boolean isEmpty() {
        return size == 0;
    }

    public abstract Task remove();

    public void add(Task task) {
        if (tasks.length == size) {
            Task[] t = new Task[tasks.length * 2];
            System.arraycopy(tasks,0,t,0,tasks.length);
            tasks = t;
        }
        tasks[size] = task;
        ++size;
    }
}
