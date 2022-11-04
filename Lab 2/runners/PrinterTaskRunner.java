package runners;

import Utils.Constants;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner runner) {
        super(runner);
    }

    public void executeOneTask() {
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    public void decorateExecuteOneTask() {
        System.out.println("Task was executed on: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }
}
