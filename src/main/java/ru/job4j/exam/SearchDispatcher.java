package ru.job4j.exam;

import ru.job4j.exam.searching.MainVisitor;
import ru.job4j.exam.searching.SearchByGlob;
import ru.job4j.exam.searching.SearchByName;
import ru.job4j.exam.searching.SearchByRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Choose right type of searching via Dispatcher pattern
 */
public class SearchDispatcher {
    /**
     * Search types dispatcher
     */
    private Map<String, Function<String, MainVisitor>> dispatcher =
            new HashMap<>();

    /**
     * Creates object and initializes dispatcher
     */
    public SearchDispatcher() {
        init();
    }

    /**
     * Initializes dispatcher by setting pairs into
     * {@link SearchDispatcher#dispatcher} where key
     * is input parameter of search and value is object
     * of class appropriating the search type
     */
    private void init() {
        dispatcher.put("-m", SearchByGlob::new);
        dispatcher.put("-f", SearchByName::new);
        dispatcher.put("-r", SearchByRegex::new);
    }

    /**
     * Returns visitor's object appropriate the search type
     * @param type Type of search
     * @param pattern Pattern of search
     * @return new visitor's object
     */
    public MainVisitor getVisitor(String type, String pattern) {
        if (!dispatcher.containsKey(type) || pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Bad type or pattern of search!");
        }
        return dispatcher.get(type).apply(pattern);
    }
}
