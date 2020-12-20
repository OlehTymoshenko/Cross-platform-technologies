package abstraction;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class ComponentFactory
{
    private static final AtomicInteger autoIncrementId = new AtomicInteger(0);

    protected int getAutoIncrementId()
    {
        return autoIncrementId.getAndIncrement();
    }

    public Component createComponent(String name)
    {
        return create(name);
    }

    protected abstract Component create(String name);
}
