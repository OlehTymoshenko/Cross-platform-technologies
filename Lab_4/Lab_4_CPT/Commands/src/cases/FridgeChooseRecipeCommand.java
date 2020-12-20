package cases;

import abstraction.Command;
import abstraction.CommandQueue;
import abstraction.IView;
import cases.models.Recipe;

public class FridgeChooseRecipeCommand extends Command<Fridge>
{
    private int newRecipeId;

    public FridgeChooseRecipeCommand(IView iView, Fridge fridge, int newRecipeId, CommandQueue queue)
    {
        super(iView, fridge, queue);
        this.newRecipeId = newRecipeId;
    }

    @Override
    protected void execute()
    {
        receiver.chooseRecipe(newRecipeId);
    }
}
