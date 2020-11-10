package ru.job4j.exam.output;

/**
 * Interface for output operations
 *
 * @see ConsoleOutput
 * @see TestOutput
 */
public interface OutputMessages {
    /**
     * Prints message
     * @param msg Message to print
     */
    void println(Object msg);

    /**
     * Prints error with some instructions
     * @param message Error message to print
     */
    void printError(String message);
}
