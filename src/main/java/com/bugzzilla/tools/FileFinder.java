package com.bugzzilla.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class FileFinder {

    private PathMatcher matcher;

    public List<File> getFiles(String startingFrom, String pattern) throws Exception {

        try {
            matcher = FileSystems.getDefault().getPathMatcher("glob:" + pattern);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        Path root = Paths.get(startingFrom);
        List<File> files = new ArrayList<>();

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
                if (file.getFileName() != null && matcher.matches(file.getFileName())) {
                    files.add(file.toFile());
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
                if (file.equals(root)) {
                    return super.visitFileFailed(file, e);
                } else {
                    return FileVisitResult.SKIP_SUBTREE;
                }
            }
        });
        return files;
    }

}
