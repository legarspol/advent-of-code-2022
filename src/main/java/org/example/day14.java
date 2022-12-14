package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

public class day14 {

    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        HashMap<String, String> map = new HashMap<>();
        String[] fileString = new String(Files.readAllBytes(new File("day14").toPath())).split("\n");
        int maxX = 0;
        int maxY = 0;
        for (String s : fileString) {
            String[] steps = s.split(" -> ");

            for (int i = 0; i < steps.length - 1; i++) {

                int[] coord = Arrays.stream(steps[i].split(",")).mapToInt(Integer::valueOf).toArray();
                int[] coord2 = Arrays.stream(steps[i + 1].split(",")).mapToInt(Integer::valueOf).toArray();


                map.put(coord[0] + "," + coord[1], "#");
                while (coord[0] != coord2[0] || coord[1] != coord2[1]) {
                    if (maxX < coord[0]) {
                        maxX = coord[0];
                    }
                    if (maxY < coord[1]) {
                        maxY = coord[1];
                    }
                    int diffX = coord[0] - coord2[0];
                    int diffY = coord[1] - coord2[1];
                    if (diffX != 0) {
                        if (diffX > 0) {
                            coord[0]--;
                        } else {
                            coord[0]++;
                        }
                    } else if (diffY != 0) {
                        if (diffY > 0) {
                            coord[1]--;
                        } else {
                            coord[1]++;
                        }
                    }
                    map.put(coord[0] + "," + coord[1], "#");
                }
            }

        }
        System.out.println(map);
        System.out.println(maxX);
        System.out.println(maxY);


        boolean stillInMap = true;
        int i = 1;
        boolean reachedSource = false;
        while (!reachedSource) {

            int[] sand = new int[]{500, 0};
            while (true) {
                System.out.println(t(sand));

                String s = map.get(sand[0] + "," + (sand[1] + 1));
                if (s == null) {
                    sand[1]++;
                    if (sand[1] > maxY) {
                        stillInMap = false;
                        break;
                    }
                } else {
                    s = map.get((sand[0] - 1) + "," + (sand[1] + 1));
                    if (s == null) {
                        sand[0]--;
                    } else {
                        s = map.get((sand[0] + 1) + "," + (sand[1] + 1));
                        if (s == null) {
                            sand[0]++;
                        } else {
                            String key = (sand[0]) + "," + sand[1];
                            map.put(key, "o");
                            if (key.equals("500,0")) {
                                reachedSource = true;
                            }
                            System.out.println("sand #" + i + ": " + t(sand));
                            break;
                        }
                    }
                }
            }
            i++;
            System.out.println(i - 1);
        }
    }

    static String t(int[] s) {
        return s[0] + ", " + s[1];
    }
}


