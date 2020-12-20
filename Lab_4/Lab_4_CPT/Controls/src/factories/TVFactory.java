package factories;

import abstraction.Component;
import abstraction.ComponentFactory;
import cases.TV;

public class TVFactory extends ComponentFactory
{
    @Override
    protected Component create(String name)
    {
        return new TV(getAutoIncrementId(), name);
    }
}
