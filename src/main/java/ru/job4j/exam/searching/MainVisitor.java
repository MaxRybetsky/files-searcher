package ru.job4j.exam.searching;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * Using as super class for searching-by-specifying-parameters
 * classes. Accumulates matches to list and writes it to file or
 * returns as String. Implements special {@link FileVisitor}
 * interface
 *
 * @see SearchByGlob
 * @see SearchByName
 * @see SearchByRegex
 */
public class MainVisitor implements FileVisitor<Path> {
    /**
     * {@link List} for accumulating results of searching.
     */
    private final List<Path> result = new ArrayList<>();

    /**
     * Adds path to {@link MainVisitor#result}
     *
     * @param path Path to add to result list
     */
    public void addPath(Path path) {
        result.add(path);
    }

    /**
     * Gets string representation of
     * result
     *
     * @return Result of searching as String
     */
    public List<Path> getResult() {
        return result;
    }

    /**
     * Save result of searching to file with
     * specify file name. Doesn't do anything
     * if size of result is zero.
     *
     * @param fileName Name of file to save result
     * @return {@code true} if result was successfully
     * saved to file, otherwise returns {@code false}
     * @throws IOException if there was some errors with IO
     *                     operations
     */
    public boolean saveResultToFile(String fileName) throws IOException {
        if (result.size() == 0) return false;
        try (PrintWriter out = new PrintWriter(
                new FileOutputStream(new File(fileName)))) {
            result.forEach(out::println);
        }
        return true;
    }

    /**
     * These four methods are using for visiting directory by
     * {@link Files#walkFileTree} #}
     */
    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs){
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs){
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc){
        return FileVisitResult.SKIP_SUBTREE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc){
        return FileVisitResult.CONTINUE;
    }
}
