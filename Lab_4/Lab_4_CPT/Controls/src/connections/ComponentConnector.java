package connections;

import abstraction.Component;
import abstraction.ComponentFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ComponentConnector
{
    private Map<Integer, Component> components;

    public ComponentConnector()
    {
        components = new HashMap<>();
    }

    public boolean connect(Component component)
    {
        if (!hasIndex(component.getId()))
        {
            components.put(component.getId(), component);
            return true;
        }
        else return false;
    }

    public boolean create(String name, ComponentFactory factory)
    {
        return connect(factory.createComponent(name));
    }

    public boolean remove(Integer id)
    {
        if (hasIndex(id))
        {
            components.remove(id);
            return true;
        }
        else return false;
    }

    public Collection<Component> getComponents()
    {
        return components.values();
    }

    public Component get(Integer id)
    {
        return components.get(id);
    }

    public boolean hasIndex(Integer index)
    {
        return components.containsKey(index);
    }

    public String getComponentsTextView()
    {
        StringBuilder view = new StringBuilder();

        for (Component component : getComponents())
        {
            view.append(component.toString());
        }

        return view.toString();
    }
}
