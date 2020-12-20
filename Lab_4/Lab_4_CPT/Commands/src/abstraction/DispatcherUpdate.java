package abstraction;

public class DispatcherUpdate implements Runnable
{
    private IView view;

    public DispatcherUpdate(IView view)
    {
        this.view = view;
    }

    @Override
    public void run()
    {
        view.update();
    }
}
