package ru.job4j.exam;

import ru.job4j.exam.output.ConsoleOutput;
import ru.job4j.exam.output.OutputMessages;
import ru.job4j.exam.searching.MainVisitor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Main class to start process of searching files.
 * Takes input parameters (args in {@link FileSearchApp#main},
 * {@code newOut} in {@link FileSearchApp#changeOutput})
 * validate them and use for searching and show results to
 * users or for tests
 */
public class FileSearchApp {
    /**
     * Default value to show information about
     * results of searching or about process errors
     */
    private static OutputMessages out = new ConsoleOutput();

    /**
     * Takes arguments with search configuration, validate them
     * and start process of searching via visiting files in specifying
     * directory. Prints info about success of searching and about errors
     *
     * @param args Search configuration
     */
    public static void main(String[] args) {
        try {
            ArgumentsHandler argsHandler = new ArgumentsHandler();
            argsHandler.check(args);
            MainVisitor visitor = new SearchDispatcher().getVisitor(
                    argsHandler.getSearchType(),
                    argsHandler.getSearchPattern()
            );
            Files.walkFileTree(Paths.get(argsHandler.getDirectory()), visitor);
            if (visitor.saveResultToFile(argsHandler.getDest())) {
                out.println("Searching is ended! All matches are saved to "
                        + argsHandler.getDest());
            } else {
                out.println("Nothing founded!");
            }
        } catch (IOException ioe) {
            out.printError("IOException: " + ioe.getMessage());
        } catch (Exception e) {
            out.printError(e.getMessage());
        }
    }

    /**
     * Changes where the result is displayed.
     *
     * @param newOut New stream of showing results
     */
    public static void changeOutput(OutputMessages newOut) {
        out = newOut;
    }
}
