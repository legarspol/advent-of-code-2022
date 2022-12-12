package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day7 {

    private static final List<Integer> sizes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String fileStrings = new String(Files.readAllBytes(new File("day7").toPath()));
        String[] lines = fileStrings.split("\n");
        String currentFolder = lines[0].replace("$ cd ", "");
        long sizeOfDir = findSizeOfDir(currentFolder, lines, 0);
        System.out.println(sizeOfDir);
        int min = Collections.min(sizes);
        System.out.println(min);
    }

    public static long findSizeOfDir(String name, String[] lines, int i0) {
        int size = 0;
        int i = i0;
        int depth = 0;
        for (; i < lines.length; i++) {
            if (lines[i].equals("$ cd ..")) {
                depth -= 1;
            } else if (depth == 0 && lines[i].equals("$ cd " + name)) {
                i++;
                i++;
                break;
            } else if (lines[i].startsWith("$ cd ")) {
                depth += 1;
            }
        }
        while (i < lines.length) {
            if (lines[i].startsWith("dir ")) {
                String dirName = lines[i].split(" ")[1];
                size += findSizeOfDir(dirName, lines, i);
            } else if (!lines[i].startsWith("$")) {
                size += Integer.parseInt(lines[i].split(" ")[0]);
            } else {
                break;
            }
            i++;
        }
        if (41609574 - size <= 40000000) {
            sizes.add(size);
        }
        return size;
    }
}
