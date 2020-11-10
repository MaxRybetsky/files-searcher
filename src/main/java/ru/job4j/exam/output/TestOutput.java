package ru.job4j.exam.output;

/**
 * Using for get info sent to console into
 * tests
 */
public class TestOutput implements OutputMessages {
    /**
     * Store output info
     */
    private StringBuilder buffer = new StringBuilder();

    /**
     * Appends output info to {@link TestOutput#buffer}
     *
     * @param msg Message to print
     */
    @Override
    public void println(Object msg) {
        buffer.append(msg)
                .append(System.lineSeparator());
    }

    /**
     * Send error message to {@link TestOutput#println}
     * without any comments
     *
     * @param message Error message to print
     */
    @Override
    public void printError(String message) {
        println(message);
    }

    /**
     * Get {@link TestOutput#buffer} in String
     * representation
     *
     * @return buffer value as String
     */
    @Override
    public String toString() {
        return buffer.toString();
    }
}
