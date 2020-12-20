package abstraction;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Command<TReceiver> extends TimerTask
{
    protected IView sender;
    protected TReceiver receiver;

    private CommandQueue queue;
    private static Thread uiUpdateDispatcher;

    private Timer timer;

    private Timer getTimer()
    {
        if (timer == null)
        {
            timer = new Timer();
        }

        return timer;
    }

    private void stopTimer()
    {
        getTimer().cancel();
        timer = null;
    }

    public Date getExecutionTime()
    {
        return new Date(this.scheduledExecutionTime());
    }

    private String getType()
    {
        return getClass().getName();
    }

    public TReceiver getReceiver()
    {
        return receiver;
    }

    public IView getSender()
    {
        return sender;
    }

    public Command(IView sender, TReceiver receiver, CommandQueue queue)
    {
        this.sender = sender;
        this.receiver = receiver;
        this.queue = queue;

        if (uiUpdateDispatcher == null)
        {
            uiUpdateDispatcher = new Thread(new DispatcherUpdate(sender));
        }

        queue.add(this);
    }

    public void runWithDelay(long delay)
    {
        if (delay > 0)
        {
            getTimer().schedule(this, delay);
        }
        else if (delay == 0)
        {
            run();
        }
    }

    public void runWithDelayAndRepeat(long delay, long period)
    {
        getTimer().schedule(this, delay, period);
    }

    protected abstract void execute();

    @Override
    public void run()
    {
        if (queue.peek() == this)
        {
            stopTimer();
            queue.pool();
            execute();

            uiUpdateDispatcher.run();
        }
    }

    @Override
    public String toString()
    {
        return String.format("%s\n\tTime: %s\n\tDestination: %s\n",
                              getType(), getExecutionTime().toString(), receiver.getClass().toString());
    }
}
