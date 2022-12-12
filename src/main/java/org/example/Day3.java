package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day3 {

    static int findCommonLetter(List<String> lists) {
        List<Character> list1 = lists.get(0).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> list2 = lists.get(1).chars().mapToObj(c -> (char) c).collect(Collectors.toList());
        List<Character> list3 = lists.get(2).chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        List<Character> collect = list1.stream()
                .filter(i -> Collections.frequency(list2, i) >= 1 && Collections.frequency(list3, i) >= 1).collect(Collectors.toList());
        char c = collect.get(0);

        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 1;
        } else {
            return c - 'A' + 27;
        }
    }

    public static void main(String[] args) throws IOException {
        String day3 = new String(Files.readAllBytes(new File("day32").toPath()));

        String[] split = day3.split("\n");
        ArrayList<List<String>> allArrays = new ArrayList<>();
        for (int i = 0; i < split.length; i += 3) {
            allArrays.add(Arrays.asList(split[i], split[i + 1], split[i + 2]));
        }
        int sum = allArrays.stream()
                .map(Day3::findCommonLetter)
                .mapToInt(Integer::valueOf)
                .sum();
        System.out.println(sum);
    }
}