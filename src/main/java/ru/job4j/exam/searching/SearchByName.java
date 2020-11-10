package ru.job4j.exam.searching;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Searches file by name
 */
public class SearchByName extends MainVisitor {
    /**
     * Name of file to search
     */
    private final String name;

    /**
     * Initialize name of file to search
     *
     * @param name Name of file
     */
    public SearchByName(String name) {
        this.name = name;
    }

    /**
     * Using in visiting files via {@link java.nio.file.Files#walkFileTree}
     * method to search file by compating every file name with current
     * {@link SearchByName#name} value.
     *
     * @param file  a reference to the file
     * @param attrs the file's basic attributes
     * @return the visit result
     */
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
        if (file.getFileName().toString().equals(name)) {
            addPath(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
