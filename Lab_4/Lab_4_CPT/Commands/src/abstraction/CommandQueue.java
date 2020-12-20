package abstraction;

import java.util.Comparator;
import java.util.PriorityQueue;

public class CommandQueue
{
    private PriorityQueue<Command> commands;

    public CommandQueue()
    {
        Comparator<Command> commandComparator = (o1, o2) -> o2.getExecutionTime().compareTo(o1.getExecutionTime());

        commands = new PriorityQueue<>(commandComparator);
    }

    public int size()
    {
        return commands.size();
    }

    public boolean add(Command command)
    {
        return commands.add(command);
    }

    public Command pool()
    {
        return commands.poll();
    }

    public Command peek()
    {
        return commands.peek();
    }

    @Override
    public String toString()
    {
        StringBuilder view = new StringBuilder();

        for (Command command : commands)
        {
            view.append(command.toString());
        }

        return view.toString();
    }
}
