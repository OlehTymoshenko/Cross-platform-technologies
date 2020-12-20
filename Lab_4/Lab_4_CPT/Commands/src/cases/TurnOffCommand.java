package cases;

import abstraction.*;

public class TurnOffCommand extends Command<ITurnable>
{
    public TurnOffCommand(IView iView, ITurnable iTurnable, CommandQueue queue)
    {
        super(iView, iTurnable, queue);
    }

    @Override
    protected void execute()
    {
        receiver.off();
    }
}
