package factories;

import abstraction.Component;
import abstraction.ComponentFactory;
import cases.AirConditioner;

public class AirConditionerFactory extends ComponentFactory
{
    @Override
    protected Component create(String name)
    {
        return new AirConditioner(getAutoIncrementId(), name);
    }
}
