package abstraction;

public abstract class Component implements ITurnable
{
    private int id;
    private String name;
    private boolean isTurnedOn;

    public Component(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    private String getType()
    {
        return getClass().getName();
    }

    @Override
    public boolean isTurnedOn()
    {
        return isTurnedOn;
    }

    @Override
    public void on()
    {
        isTurnedOn = true;
    }

    @Override
    public void off()
    {
        isTurnedOn = false;
    }

    @Override
    public String toString()
    {
        return String.format("%s Component\n\tID: %d\n\tName: %s\n\tState: %s\n\t",
                               getType(), getId(), getName(), getStateString());
    }

    private String getStateString()
    {
        if (isTurnedOn) return "On";
        else return "Off";
    }
}
