package Factory;

import Container.Container;
import Container.StackContainer;
import Container.QueueContainer;
import Container.Strategy;

public class TaskContainerFactory implements Factory{
    private static TaskContainerFactory instance = null;

    private TaskContainerFactory() {}

    public static TaskContainerFactory getInstance() {
        if (instance == null)
            instance = new TaskContainerFactory();
        return instance;
    }

    public Container createContainer(Strategy strategy) {
        if (strategy == Strategy.LIFO) {
            return StackContainer.getInstance();
        }
        if(strategy == Strategy.FIFO){
            return QueueContainer.getInstance();
        }
        return null;
    }
}
