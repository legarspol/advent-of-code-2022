package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day15 {

    private static ArrayList<int[][]> points;

    static int distance(int[] s, int[] b) {
        return Math.abs(s[1] - b[1]) + Math.abs(s[0] - b[0]);
    }

    static class Test {
        String t(int[] s) {
            return s[0] + ", " + s[1];
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

        void test() throws IOException, InterruptedException {

            HashMap<String, String> map = new HashMap<>();

            String[] fileString = new String(Files.readAllBytes(new File("day15").toPath())).split("\n");
            System.out.println(Arrays.toString(fileString));
            points = new ArrayList<>();
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

                addToMax(new int[]{s[0] - distance, s[1] - distance});
                addToMax(new int[]{s[0] + distance, s[1] + distance});

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


            for (int[][] point : points) {
                int distance = distance(point[0], point[1]) + 1;
                for (int i = 0; i < distance; i++) {
                    int[] signal = point[0];
                    checkOutOfBound(signal[0] + distance - i, signal[1] + i);
                    checkOutOfBound(signal[0] + distance - i, signal[1] - i);
                    checkOutOfBound(signal[0] - distance + i, signal[1] - i);
                    checkOutOfBound(signal[0] - distance + i, signal[1] + i);
                }
            }
//
//                System.out.println(Arrays.toString(curr));
//                System.out.println(curr[0] * 4000000 + curr[1]);
//                System.out.print(".");
            return;
        }

        private void checkOutOfBound(int i, int i1) {
            if (i < 0 || i1 < 0 || i > 4000000 || i1 > 4000000)
                return;
            for (int[][] point : points) {
                int distance = distance(point[0], point[1]);
                if (distance(point[0], new int[]{i, i1}) <= distance) {
                    return;
                }
            }
            System.out.println(i + "- " + i1);
            System.out.println(i * 4000000 + i1);
// 116472624
        }
    }

    //
    public static void main(String[] args) throws IOException, InterruptedException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//

//        for (String arg : args) {
//
//            System.out.println(arg);
//        }
        new Test().test();
//        new Test().test(args[0]);
    }

}


