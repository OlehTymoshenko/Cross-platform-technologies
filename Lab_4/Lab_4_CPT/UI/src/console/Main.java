package console;

import connections.ComponentConnector;

public class Main
{
    public static void main(String[] args)
    {
        ComponentConnector componentConnector = new ComponentConnector();
        ControlConsole console = new ControlConsole(System.in, System.out, componentConnector);

        console.update();
    }
}
