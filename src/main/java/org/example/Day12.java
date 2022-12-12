package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Day12 {

    static class Cell {
        int value;
        int fastest;

        public Cell(int value, int fastest) {
            this.value = value;
            this.fastest = fastest;
        }
    }

    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        String fileString = new String(Files.readAllBytes(new File("day12").toPath()));

        String[] strings = fileString.split("\n");
        ArrayList<ArrayList<Cell>> arr = new ArrayList<>();
        int x = 0;
        int y = 0;
        Cell end = null;
        for (String s : strings) {
            ArrayList<Cell> line = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                Cell cell = new Cell(s.charAt(i), Integer.MAX_VALUE);
                line.add(cell);
                if (s.charAt(i) == 'S') {
//                    x = i;
//                    y = arr.size();
                    cell.value = 'a';
                } else if (s.charAt(i) == 'E') {
//                    end = cell;
                    cell.value = 'z';
                    x = i;
                    y = arr.size();

                }
            }
            arr.add(line);
        }

        searchEnd(x, y, arr, 0, 'z');
        System.out.println(min);
    }

    static int min = Integer.MAX_VALUE;
    public static void searchEnd(int x, int y, ArrayList<ArrayList<Cell>> arr, int step, int prevValue) {
        if (step > min) {
            return;
        }
        System.out.println(step);
        if (y < 0 || y >= arr.size()) {
            return;
        }
        if (x < 0 || x >= arr.get(y).size()) {
            return;
        }
        Cell cell = arr.get(y).get(x);
        if ( prevValue - cell.value  > 1) {
            return;
        }
        if (cell.fastest <= step) {
            return;
        }
        cell.fastest = step;
        if (cell.value == 'a') {
            if (step< min) {
                min = step;
            }
        }
        searchEnd(x - 1, y, arr, step + 1, cell.value);
        searchEnd(x + 1, y, arr, step + 1, cell.value);
        searchEnd(x, y - 1, arr, step + 1, cell.value);
        searchEnd(x, y + 1, arr, step + 1, cell.value);
    }
}

