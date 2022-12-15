package org.example;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {


    static class Test {
        String t(int[] s) {
            return s[0] + ", " + s[1];
        }

        int distance(int[] s, int[] b) {
            return Math.abs(s[1] - b[1]) + Math.abs(s[0] - b[0]);
        }

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        void addToMax(int[] b) {

            if (b[0] > maxX)
                maxX = b[0];
            if (b[0] < minX)
                minX = b[0];
            if (b[1] > maxY)
                maxY = b[1];
            if (b[1] < minY)
                maxY = b[1];
        }

        void test() throws IOException {

            HashMap<String, String> map = new HashMap<>();

            String[] fileString = new String(Files.readAllBytes(new File("day15").toPath())).split("\n");
            System.out.println(Arrays.toString(fileString));
            ArrayList<int[][]> points = new ArrayList<>();
            for (String a : fileString) {

                String[] split = a.split(": closest beacon is at x=");
                System.out.println(Arrays.toString(split));
                int[] s = Arrays.stream(split[0]
                                .replace("Sensor at x=", "")
                                .replace(" y=", "")
                                .split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray();
                int[] b = Arrays.stream(split[1].split(", y=")).mapToInt(Integer::parseInt).toArray();
                System.out.println(Arrays.toString(s) + " " + Arrays.toString(b));

//                addToMax(b);
                int distance = distance(s, b);

                addToMax(new int[] { s[0] - distance, s[1] - distance});
                addToMax(new int[] { s[0] + distance, s[1] + distance});

                map.put(t(s), "S");
                points.add(new int[][]{s, b});

//                for (int x = s[0] - distance; x <= s[0] + distance; x++) {
//                    for (int y = s[1] - distance; y < s[1] + distance; y++) {
//                        int[] cur = new int[]{x, y};
//                        if (distance(s, b) <= distance) {
//                            map.put(t(cur), "#");
//                        }
//                    }
//                }
                map.put(t(b), "B");

            }

            int count = 0;
            for (int i = minX ; i < maxX; i++) {
                boolean isCovered = false;
                int[] curr = new int[]{i, 2000000};
                for (int j = 0; j < points.size(); j++) {
                    int[][] ints = points.get(j);
                    int distance = distance(ints[0], ints[1]);
                    if (distance(ints[0], curr) <= distance) {
                        isCovered = true;
                    }
                    if (arrayToPoint(ints[1]).equals(arrayToPoint(curr))) {
                        isCovered = false;
                        break;
                    }
                }
                if (isCovered) {
                    System.out.print("#");
                    count++;
                } else {
                    System.out.print(".");
                }
            }
            System.out.println(count);
//                for (int[] pari : map.keySet()) {
//                    int[] point = stringToArray(key);
//
//                    if (map.get(key).equals("S")) {
//                        if (distance(point, curr) )
//                    }
//                    String s = map.get(t());
//                    if (s != null && !s.equals("B")) {
//                        count++;
//                    }
//
//                    if (s == null) {
//                        s = ".";
//                    }
//                    System.out.print(s);
//                }
//            }
//            System.out.println(count);

        }

        int[] stringToArray(String s) {
            return Arrays.stream(s.split(","))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        Point arrayToPoint(int[] s) {
            return new Point(s[0], s[1]);
        }

    }


    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        new Test().test();
    }

}


