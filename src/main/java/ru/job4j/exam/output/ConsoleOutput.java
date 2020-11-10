package ru.job4j.exam.output;

/**
 * Writes information to console via
 * {@link System#out}
 */
public class ConsoleOutput implements OutputMessages {
    /**
     * Prints message to Console. If message
     * is {@code null} prints "null"
     *
     * @param msg Message to print
     */
    @Override
    public void println(Object msg) {
        String message = msg == null ? "null" :
                msg.toString();
        System.out.println(message);
    }

    /**
     * Prints error message with special
     * prompts
     *
     * @param message Error message to print
     */
    @Override
    public void printError(String message) {
        System.out.println(message);
        System.out.println("Example of correct request:");
        System.out.println("java -jar find.jar -d c:/ -n *.txt -m -o log.txt");
    }
}
