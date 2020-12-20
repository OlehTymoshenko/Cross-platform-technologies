package cases;

import abstraction.*;

public class TurnOnCommand extends Command<ITurnable>
{
    public TurnOnCommand(IView iView, ITurnable iTurnable, CommandQueue queue)
    {
        super(iView, iTurnable, queue);
    }

    @Override
    protected void execute()
    {
        receiver.on();
    }
}
