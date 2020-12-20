package console;

import abstraction.*;
import cases.*;
import connections.ComponentConnector;
import connections.ComponentType;
import factories.*;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControlConsole implements IView
{
    private BufferedReader cin;
    private PrintStream cout;
    private ComponentConnector componentConnector;
    private CommandQueue commandQueue;
    private boolean helpOn;

    public ControlConsole(InputStream cin, PrintStream cout, ComponentConnector componentConnector)
    {
        this.cin = new BufferedReader(new InputStreamReader(cin));
        this.cout = cout;
        this.componentConnector = componentConnector;
        this.commandQueue = new CommandQueue();
        helpOn = false;
    }

    @Override
    public void update()
    {
        printLine();
        printHeader();
        printLine();

        printKnownTypes();
        printLine();
        if (componentConnector.getComponents().size() != 0)
        {
            printLine();
            cout.println(componentConnector.getComponentsTextView());
            printLine();
        }
        if (commandQueue.size() != 0)
        {
            printTimers();
            printLine();
        }
        if (helpOn)
        {
            printUserCommands();
        }

        try
        {
            captureInput();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void printUserCommands()
    {
        cout.println(help);
    }

    private void printKnownTypes()
    {
        StringBuilder types = new StringBuilder();
        types.append("Known component types is: ");

        for (ComponentType type: ComponentType.values())
        {
            types.append(type).append(" ");
        }

        cout.println(types.toString());
    }

    private void printTimers()
    {
        cout.println(commandQueue.toString());
    }

    private void printLine()
    {
        cout.println("------------------------------------------------------------");
    }

    private void printHeader()
    {
        cout.println(String.format("Current added devices = %d " +
                        "Current scheduled tasks = %d", componentConnector.getComponents().size(), commandQueue.size()));
    }

    private static final String help = "Manipulation commands:\n" +
                                       "[addcmp *type* *name*] - Adds component of specified type with specified name\n" +
                                       "[rmcmp *id*] - Removes component with specified id\n" +
                                       "[tron [-t HH:mm:ss] *id*] - Turns on component on specified time\n" +
                                       "[trof [-t HH:mm:ss] *id*] - Turns off component on specified time" +
                                       "Fridge commands:\n" +
                                       "[frselrec *id_fridge* *id_recipe* - Select recipe by id\n" +
                                       "[frgetcurrec *id_fridge* - Get recipe for selected fridge\n" +
                                       "[frreciptslist *id_fridge* - List of recipes for the selected fridge\n" +
                                       "[help] - Show/hide this text on every action";

    private void captureInput() throws IOException
    {
        String userCommand = cin.readLine();
        String delimiter = " ";
        ArrayList<String> signatures = new ArrayList<>(Arrays.asList(userCommand.split(delimiter)));

        int delayKeyPosition = signatures.indexOf("-t");
        long delay = 0;
        try
        {
            if (delayKeyPosition != -1)
            {
                delay = parseDelay(signatures, delayKeyPosition);

                signatures.subList(delayKeyPosition, delayKeyPosition + 2).clear();
            }
        }
        catch (IOException e)
        {
            printWaitForInputAndUpdate("Wrong delay signature. Delay signature must be [-t HH:mm:ss]");
        }

        String commandType = signatures.get(0);
        signatures.remove(0);

        Command command = null;

        switch (commandType)
        {
            case "addcmp" :
            {
                createComponentCommand(signatures);
                break;
            }
            case "rmcmp" :
            {
                removeComponentCommand(signatures);
                break;
            }
            case "tron" :
            {
                command = createSwitcherCommand(signatures, true);
                break;
            }
            case "trof" :
            {
                command = createSwitcherCommand(signatures, false);
                break;
            }
            case "frselrec" : // select by id
            {
                command = createFridgeCommand(signatures);
                break;
            }
            case "frgetcurrec" : // get current recipe
            {
                getCurrentRecipe(signatures);
                break;
            }
            case "frreciptslist" :
            {
                getRecipesList(signatures);
                break;
            }
            case "help" :
            {
                helpOn = !helpOn;
                break;
            }
            default :
            {
                printWaitForInputAndUpdate("Unknown Command! Type *help* to display full commands list.");
                break;
            }
        }

        if (command != null)
        {
            command.runWithDelay(delay);
            update();
        }
    }

    private void createComponentCommand(ArrayList<String> signatures)
    {
        ComponentFactory factory = null;
        String type = signatures.get(0);
        String name = signatures.get(1);

        if (type.equals(ComponentType.AirConditioner.toString()))
        {
            factory = new AirConditionerFactory();
        }
        else if (type.equals(ComponentType.Kettle.toString()))
        {
            factory = new KettleFactory();
        }
        else if (type.equals(ComponentType.Fridge.toString()))
        {
            factory = new FridgeFactory();
        }
        else if (type.equals(ComponentType.Lighting.toString()))
        {
            factory = new LightingFactory();
        }
        else if (type.equals(ComponentType.TV.toString()))
        {
            factory = new TVFactory();
        }

        if (factory != null)
        {
            componentConnector.create(name, factory);
            update();
        }
        else
        {
            printWaitForInputAndUpdate("Wrong addcmp command signature. Must be > addcmp *type* *name*");
        }
    }

    private void removeComponentCommand(ArrayList<String> signatures)
    {
        Integer id = parseId(signatures.get(0));

        if (id == null || !componentConnector.remove(id))
        {
            printWaitForInputAndUpdate("Index value *" + signatures.get(0) + "* is not exist.");
        }
        else update();
    }

    private Command createSwitcherCommand(ArrayList<String> signatures, boolean state)
    {
        Integer id = parseId(signatures.get(0));
        Command command;

        if (id != null & componentConnector.hasIndex(id))
        {
            Component receiver = componentConnector.get(id);
            if (state)
            {
                command = new TurnOnCommand(this, receiver, commandQueue);
            }
            else
            {
                command = new TurnOffCommand(this, receiver, commandQueue);
            }
        }
        else
        {
            printWaitForInputAndUpdate("Index value *" + id + "* is not exist in component connector");
            command = null;
        }

        return command;
    }

    private Command createFridgeCommand(ArrayList<String> signatures)
    {
        Integer id = parseId(signatures.get(0));
        Command command;

        if (id != null & componentConnector.hasIndex(id)) {
            Component receiver = componentConnector.get(id);
            if ((receiver instanceof Fridge)) {
                if (signatures.size() >= 2) {
                    Integer recipeId = parseId(signatures.get(1));
                    command = new FridgeChooseRecipeCommand(this, (Fridge) receiver,
                            recipeId, commandQueue);
                }
                else {
                    printWaitForInputAndUpdate("Incorrect ID of new recipe");
                    command = null;
                }
            }
            else {
                printWaitForInputAndUpdate("Element with index " + id + "is not a Fridge");
                command = null;
            }
        }
        else {
            printWaitForInputAndUpdate("Index value *" + id + "* is not exist in component connector");
            command = null;
        }

        return command;
    }

    private void getCurrentRecipe(ArrayList<String> signatures)
    {
        Integer id = parseId(signatures.get(0));

        if (id != null & componentConnector.hasIndex(id))
        {
            Component receiver = componentConnector.get(id);
            if ((receiver instanceof Fridge))
            {
                printWaitForInputAndUpdate(((Fridge) receiver).getChosenRecipe().toString());
            }
            else
            {
                cout.println("Element with index " + id + "is not a Fridge");
            }
        }
        else
        {
            printWaitForInputAndUpdate("Index value *" + id + "* is not exist in component connector");
        }
    }
    private void getRecipesList(ArrayList<String> signatures)
    {
        Integer id = parseId(signatures.get(0));

        if (id != null & componentConnector.hasIndex(id))
        {
            Component receiver = componentConnector.get(id);
            if ((receiver instanceof Fridge))
            {
                printWaitForInputAndUpdate(((Fridge) receiver).getRecipes());
            }
            else
            {
                cout.println("Element with index " + id + "is not a Fridge");
            }
        }
        else
        {
            printWaitForInputAndUpdate("Index value *" + id + "* is not exist in component connector");
        }
    }


    private Integer parseId(String signature)
    {
        try
        {
            int index = Integer.parseInt(signature);
            if (index < 0)
            {
                throw new NumberFormatException();
            }
            else return index;
        }
        catch (NumberFormatException e)
        {
            printWaitForInputAndUpdate("*id* must be an integer positive value");
            return null;
        }
    }

    private long parseDelay(ArrayList<String> signatures, int delayKeyPosition) throws IOException
    {
        String time = signatures.get(delayKeyPosition + 1);
        SimpleDateFormat parser = new SimpleDateFormat("HH:mm:ss");

        try
        {
            Calendar runTime = new GregorianCalendar();
            runTime.setTime(parser.parse(time));

            Calendar fullRunTime = Calendar.getInstance();
            fullRunTime.set(Calendar.HOUR, runTime.get(Calendar.HOUR));
            fullRunTime.set(Calendar.MINUTE, runTime.get(Calendar.MINUTE));
            fullRunTime.set(Calendar.SECOND, runTime.get(Calendar.SECOND));

            return fullRunTime.getTimeInMillis() - System.currentTimeMillis();
        }
        catch (ParseException e)
        {
            throw new IOException();
        }
    }

    private void printWaitForInputAndUpdate(String string)
    {
        cout.println(string);
        waitForUserInput();
        update();
    }

    private void waitForUserInput()
    {
        cout.println("Press enter to continue...");

        try
        {
            cin.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
