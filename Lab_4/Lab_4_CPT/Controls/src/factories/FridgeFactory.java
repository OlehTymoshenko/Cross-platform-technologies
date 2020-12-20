package factories;

import abstraction.Component;
import abstraction.ComponentFactory;
import cases.Fridge;

public class FridgeFactory extends ComponentFactory
{
    @Override
    protected Component create(String name)
    {
        return new Fridge(getAutoIncrementId(), name);
    }
}
