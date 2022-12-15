package org.example;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Main {
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

        void test(String arg) throws IOException, InterruptedException {

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

//            int count = 0;
            ArrayList<Worker> workers = new ArrayList<>();

//            for (int i = 0; i < 10; i++) {
            int i = Integer.parseInt(arg);
            System.out.println(" starting line: " + i);
                for (int j = 0; j < 10; j++) {
                    Worker worker = new Worker();
                    worker.x = i * (4000000 / 10);
                    worker.y = j * (4000000 / 10);
                    worker.size = 4000000 / 10;
                    worker.points = points;
                    System.out.println("Starting " + worker.x + "," + worker.y + "  " + worker.size);
                    workers.add(worker);
                    worker.start();
                }
//            }
            for (Worker worker : workers) {
                worker.join();

                System.out.print("j");
            }

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

    static class Worker extends Thread {
        int x, y, size;
        ArrayList<int[][]> points;


        @Override
        public void run() {
            super.run();
            extracted();
            System.out.println("hello");
        }

        private void extracted() {
            for (int k = y; k <= y + size; k++) { // y
                if (x == 0 && y ==0){
                    System.out.println(k);
                }
                for (int i = x; i <= x + size; i++) { //x

                    boolean isCovered = false;
                    int[] curr = new int[]{i, k};
                    for (int j = 0; j < points.size(); j++) {
                        int[][] ints = points.get(j);
                        int distance = distance(ints[0], ints[1]);
                        if (distance(ints[0], curr) <= distance) {
                            isCovered = true;
                        }
//                        if (arrayToPoint(ints[1]).equals(arrayToPoint(curr))) {
//                            isCovered = false;
//                            break;
//                        }
                    }
                    if (isCovered) {
//                        System.out.print("#");
//                        count++;
                    } else {
                        System.out.println(Arrays.toString(curr));
                        System.out.println(curr[0] * 4000000 + curr[1]);
                        System.out.print(".");
                        return;
                    }
                }
//                System.out.println(k);
            }
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//

        for (String arg : args) {

            System.out.println(arg);
        }
        new Test().test(args[0]);
    }

}


