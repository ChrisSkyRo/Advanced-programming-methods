import Container.Strategy;
import Model.MessageTask;
import Model.SortingTask;
import runners.DelayTaskRunner;
import runners.PrinterTaskRunner;
import runners.StrategyTaskRunner;
import runners.TaskRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    private static MessageTask[] getMessages() {
        MessageTask labHomework = new MessageTask("1",
                "two exercises", "for laboratory", "Michael", "Flo", LocalDateTime.now());

        MessageTask semHomework = new MessageTask("2",
                "three exercises", "for seminary", "Andrew", "Anna",LocalDateTime.now());

        MessageTask courseHomework = new MessageTask("3",
                "four exercises", "for course", "Diana", "Paula", LocalDateTime.now());

        return new MessageTask[]{labHomework, semHomework, courseHomework};
    }

    public static void run(String arg) {
        System.out.println("Strategy: " + arg);
        Strategy strategy = arg.equals("LIFO") ? Strategy.LIFO : Strategy.FIFO;
        MessageTask[] messages = getMessages();

        System.out.println("\nTask Runner: ");
        TaskRunner taskRunner = new StrategyTaskRunner(strategy);
        taskRunner.addTask(messages[0]);
        taskRunner.addTask(messages[1]);
        taskRunner.addTask(messages[2]);
        taskRunner.executeAll();

        System.out.println("\nPrinter Task Runner: ");
        PrinterTaskRunner printerTaskRunner = new PrinterTaskRunner(new StrategyTaskRunner(strategy));
        printerTaskRunner.addTask(messages[0]);
        printerTaskRunner.addTask(messages[1]);
        printerTaskRunner.addTask(messages[2]);
        printerTaskRunner.executeAll();

        System.out.println("\nDelay Task Runner: ");
        DelayTaskRunner delayTaskRunner = new DelayTaskRunner(new StrategyTaskRunner(strategy));
        delayTaskRunner.addTask(messages[0]);
        delayTaskRunner.addTask(messages[1]);
        delayTaskRunner.addTask(messages[2]);
        delayTaskRunner.executeAll();

        List<Integer> arr = new ArrayList<>();
        {
            arr.add(4);
            arr.add(17);
            arr.add(23);
            arr.add(2);
            arr.add(5);
            arr.add(1);
        } // Add integers to the array
        System.out.println("\nArray to sort: "+arr.toString());
        System.out.print("Sorted array: ");
        SortingTask st = new SortingTask("1", "Sort it", arr, Strategy.MERGE);
        st.execute();
    }
}
