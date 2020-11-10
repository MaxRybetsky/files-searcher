package ru.job4j.exam;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import ru.job4j.exam.output.TestOutput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class FileSearchAppTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenSearchByName() throws IOException {
        folder.newFolder("folder"); // target folder
        folder.newFolder("folder1", "folder2"); // other folders
        File fileDest = folder.newFile("folder/file.txt"); // file to search
        folder.newFile("folder1/folder2/file2.txt"); // some other file
        File res = folder.newFile("result.txt");
        String name = fileDest.getName();
        String[] args = {"-d", folder.getRoot().toString(),
                "-n", name, "-f", "-o", res.getAbsolutePath()};
        FileSearchApp.changeOutput(new TestOutput());
        FileSearchApp.main(args);
        assertThat(readResultFile(res), is(
                List.of("folder\\file.txt")
        ));
    }

    @Test
    public void whenSearchByGlob() throws IOException {
        folder.newFolder("folder", "folder1", "folder2"); // target folder
        folder.newFile("folder/file.txt"); // file to search
        folder.newFile("folder/folder1/folder2/file2.txt"); // file to search
        folder.newFile("folder/folder1/folder2/file3.dat"); // some other file
        folder.newFile("folder/folder1/file4.java"); // some other file
        File res = folder.newFile("result.txt");
        String glob = "*.txt";
        String[] args = {"-d", folder.getRoot().toString() + "\\folder",
                "-n", glob, "-m", "-o", res.getAbsolutePath()};
        FileSearchApp.changeOutput(new TestOutput());
        FileSearchApp.main(args);
        assertThat(readResultFile(res), is(
                List.of("folder\\file.txt",
                        "folder\\folder1\\folder2\\file2.txt")
        ));
    }

    @Test
    public void whenSearchByRegex() throws IOException {
        folder.newFolder("folder", "folder1", "folder2"); // target folder
        folder.newFile("folder/file.txt"); // some other file
        folder.newFile("folder/folder1/folder2/file2.txt"); // file to search
        folder.newFile("folder/folder1/folder2/file.dat"); // some other file
        folder.newFile("folder/folder1/file4.java"); // file to search
        folder.newFile("folder/folder1/folder2/file5.class"); // file to search
        folder.newFile("folder/folder1/folder2/file.class"); // some other file
        File res = folder.newFile("result.txt");
        String regex = "file[0-9].*";
        String[] args = {"-d", folder.getRoot().toString() + "\\folder",
                "-n", regex, "-r", "-o", res.getAbsolutePath()};
        FileSearchApp.changeOutput(new TestOutput());
        FileSearchApp.main(args);
        assertThat(readResultFile(res), is(
                List.of("folder\\folder1\\file4.java",
                        "folder\\folder1\\folder2\\file2.txt",
                        "folder\\folder1\\folder2\\file5.class")
        ));
    }

    @Test
    public void whenBadArgsLength() {
        String[] args = {"-d", folder.getRoot().toString(),
                "-n", "*.txt", "-m", "result.txt"};
        TestOutput out = new TestOutput();
        FileSearchApp.changeOutput(out);
        FileSearchApp.main(args);
        assertThat(out.toString(),
                is("Not enough arguments!\r\n")
        );
    }

    @Test
    public void whenBadArgsTypeOfSearch() {
        String[] args = {"-d", folder.getRoot().toString(),
                "-n", "*.txt", "-g", "-o", "result.txt"};
        TestOutput out = new TestOutput();
        FileSearchApp.changeOutput(out);
        FileSearchApp.main(args);
        assertThat(out.toString(),
                is("Wrong argument: -g\r\n")
        );
    }

    @Test
    public void whenEmptyPatternOfSearch() {
        String[] args = {"-d", folder.getRoot().toString(),
                "-n", "", "-r", "-o", "result.txt"};
        TestOutput out = new TestOutput();
        FileSearchApp.changeOutput(out);
        FileSearchApp.main(args);
        assertThat(out.toString(),
                is("Incorrect parameter!\r\n")
        );
    }

    private List<String> readResultFile(File file) {
        List<String> result = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            in.lines().forEach(line -> result.add(folder.getRoot()
                    .toPath()
                    .relativize(Paths.get(line))
                    .toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}