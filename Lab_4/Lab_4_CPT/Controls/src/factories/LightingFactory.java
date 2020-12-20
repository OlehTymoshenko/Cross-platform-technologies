package factories;

import abstraction.Component;
import abstraction.ComponentFactory;
import cases.Lighting;

public class LightingFactory extends ComponentFactory
{
    @Override
    protected Component create(String name)
    {
        return new Lighting(getAutoIncrementId(), name);
    }
}
