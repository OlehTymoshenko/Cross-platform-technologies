package factories;

import abstraction.Component;
import abstraction.ComponentFactory;
import cases.Kettle;

public class KettleFactory extends ComponentFactory
{
    @Override
    protected Component create(String name)
    {
        return new Kettle(getAutoIncrementId(), name);
    }
}
