package ru.job4j.exam.searching;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Searches file by regex
 */
public class SearchByRegex extends MainVisitor {
    /**
     * Path matcher for searches files by
     * specifying regex
     */
    private final PathMatcher pathMatcher;

    /**
     * Initialize path matcher with specify regex
     * value
     *
     * @param regex Regex to search file
     */
    public SearchByRegex(String regex) {
        pathMatcher = FileSystems.getDefault()
                .getPathMatcher("regex:" + regex);
    }

    /**
     * Using in visiting files via {@link java.nio.file.Files#walkFileTree}
     * method to search file by matching every file name with
     * {@link SearchByRegex#pathMatcher}.
     *
     * @param file  a reference to the file
     * @param attrs the file's basic attributes
     * @return the visit result
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
        if (pathMatcher.matches(file.getFileName())) {
            addPath(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
