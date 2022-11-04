package runners;

import Utils.Constants;

import java.time.LocalDateTime;

public class DelayTaskRunner extends AbstractTaskRunner{
    public DelayTaskRunner(TaskRunner runner) {
        super(runner);
    }

    @Override
    public void executeOneTask(){
        decorateDelayOneTask();
        try{
            Thread.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        runner.executeOneTask();
        decorateExecuteOneTask();
    }

    public void decorateDelayOneTask() {
        System.out.println("Task was delayed on: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }

    public void decorateExecuteOneTask() {
        System.out.println("Task was executed on: " + LocalDateTime.now().format(Constants.DATE_TIME_FORMATTER));
    }
}
