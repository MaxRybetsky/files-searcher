package ru.job4j.exam;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Handler of input arguments in {@link FileSearchApp#main}.
 * Validate arguments and save important setting info to
 * array. Uses Dispatcher pattern
 */
public class ArgumentsHandler {
    /**
     * Argument dispatcher
     */
    private final Map<Integer, Function<String, Boolean>> dispatcher =
            new HashMap<>();

    /**
     * Storage of settings. Order of settings is important:
     * 0 - directory to search
     * 1 - pattering of searching files
     * 2 - search type (by glob, regex, or name)
     * 3 - name of file where results of searching will saved
     */
    private final String[] settings = new String[4];

    /**
     * Creates handler object and initializes dispatcher
     */
    public ArgumentsHandler() {
        init();
    }

    /**
     * Initializes {@link ArgumentsHandler#dispatcher} with
     * arguments data. Also filling the settings array and
     * validate arguments.
     */
    private void init() {
        dispatcher.put(0, "-d"::equals);
        dispatcher.put(1, str -> {
            settings[0] = str;
            return checkInputParameters(str);
        });
        dispatcher.put(2, "-n"::equals);
        dispatcher.put(3, str -> {
            settings[1] = str;
            return checkInputParameters(str);
        });
        dispatcher.put(4, str -> {
            boolean result = "-m".equals(str)
                    || "-f".equals(str)
                    || "-r".equals(str);
            settings[2] = result ? str : "";
            return result;
        });
        dispatcher.put(5, "-o"::equals);
        dispatcher.put(6, str -> {
            settings[3] = str;
            return checkInputParameters(str);
        });
    }

    /**
     * Get directory where we need to search file
     *
     * @return Destination directory
     */
    public String getDirectory() {
        return settings[0];
    }

    /**
     * Get search pattern (glob,
     * name of file or regex)
     *
     * @return Search pattern
     */
    public String getSearchPattern() {
        return settings[1];
    }

    /**
     * Get search type:<pre>
     *  -m - by glob,
     *  -f - by name,
     *  -r - by regex</pre>
     *
     * @return Search type
     */
    public String getSearchType() {
        return settings[2];
    }

    /**
     * Get name of file to save result
     *
     * @return name of result file
     */
    public String getDest() {
        return settings[3];
    }

    /**
     * Validate input parameters to empty or
     * {@code null} value.
     *
     * @param param Parameter to check
     * @return {@code true} if parameter is valid,
     * otherwise - {@code false}
     */
    private boolean checkInputParameters(String param) {
        if ("".equals(param) || param == null) {
            throw new IllegalArgumentException("Incorrect parameter!");
        }
        return true;
    }

    /**
     * Iterates over every input argument, running
     * process of handling and validation
     *
     * @param args Input arguments
     * @throws IllegalArgumentException If argument was incorrect or
     *                                  number of arguments was less than needed
     */
    public void check(String[] args) throws IllegalArgumentException {
        if (args.length < 7) {
            throw new IllegalArgumentException("Not enough arguments!");
        }
        for (int i = 0; i < args.length; i++) {
            if (!dispatcher.get(i).apply(args[i])) {
                throw new IllegalArgumentException("Wrong argument: " + args[i]);
            }
        }
    }
}
