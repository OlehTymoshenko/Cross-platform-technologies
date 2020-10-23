package lab3.src;

import lab3.lib.*;

/**
 * Класс предавляющий точку входа в приложение,
 * содержит метод main
 */
public class Main {
    public static void main(String[] args) {
        try {
            int tau = 13; // this is my variant
            // create field
            FileReader fr = new FileReader(args[0]);
            SignalParams sigParams;


            // read bytes from file
            byte[] signalVals = fr.getBytes();

            if (signalVals == null) {
                System.out.println("Can`t read signal values from file " + args[1]);
                return;
            }

            // calculate params
            sigParams = new SignalParams(signalVals);

            System.out.println(sigParams.toStringValues(tau));
        }
        catch (IndexOutOfBoundsException e) {
            System.out.println("You didnt pass an argument(file name)");
        }
        catch (Exception e) {
            System.out.println("Unexpected error\n" + e.getMessage());
        }
    }
}
