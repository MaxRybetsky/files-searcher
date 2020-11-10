package ru.job4j.exam.searching;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Searches file by globe
 */
public class SearchByGlob extends MainVisitor {
    /**
     * Path matcher for searches files by
     * specifying globe
     */
    private final PathMatcher pathMatcher;

    /**
     * Initialize path matcher with specify glob
     *
     * @param glob Glob to search file
     */
    public SearchByGlob(String glob) {
        pathMatcher = FileSystems.getDefault()
                .getPathMatcher("glob:" + glob);
    }

    /**
     * Using in visiting files via {@link java.nio.file.Files#walkFileTree}
     * method to search file by matching every file name with
     * {@link SearchByGlob#pathMatcher}.
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
