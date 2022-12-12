package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashSet;

public class Day9 {

    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        HashSet<String> prevp = new HashSet<>();
        String fileString = new String(Files.readAllBytes(new File("day9").toPath()));
        String[] lines = fileString.split("\n");

        ArrayList<int[]> nodes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            nodes.add(new int[2]);
        }

        int[] H = nodes.get(0);


        prevp.add(nodes.get(8)[0] + "-" + nodes.get(8)[1]);
        for (String line : lines) {
            String[] s = line.split(" ");
            int steps = Integer.parseInt(s[1]);

            for (int i = 0; i < steps; i++) {
                switch (s[0]) {
                    case "L":
                        H[0] -= 1;
                        break;
                    case "R":
                        H[0] += 1;
                        break;
                    case "U":
                        H[1] += 1;
                        break;
                    case "D":
                        H[1] -= 1;
                        break;
                    default:
                        throw new IllegalArgumentException(s[0]);
                }

                for (int j = 1; j < nodes.size(); j++) {
                    int[] T = nodes.get(j);
                    int[] P = nodes.get(j - 1);
                    //move T
                    if (P[0] - T[0] == 2 && Math.abs(P[1] - T[1]) == 2) {
                        T[0] = P[0] - 1;
                        T[1] += (P[1] - T[1]) / 2;
                        System.out.println(P +  " " +  T);

                    } else if (P[0] - T[0] == -2 && Math.abs(P[1] - T[1]) == 2) {
                        T[0] = P[0] + 1;
                        T[1] += (P[1] - T[1]) / 2;
                        System.out.println(P +  " " +  T);
                    } else if (P[0] - T[0] == 2) {
                        T[0] = P[0] - 1;
                        T[1] = P[1];
                    } else if (P[0] - T[0] == -2) {
                        T[0] = P[0] + 1;
                        T[1] = P[1];
                    } else if (P[1] - T[1] == 2) {
                        T[1] = P[1] - 1;
                        T[0] = P[0];
                    } else if (P[1] - T[1] == -2) {
                        T[1] = P[1] + 1;
                        T[0] = P[0];
                    }
                    if (j == nodes.size() - 1) {
                        System.out.println(T[0] + " " + T[1]);
                        prevp.add(T[0] + "-" + T[1]);
                    }
                }
            }
        }
        System.out.println(prevp.size());
        System.out.println(prevp);

    }


    //
//        char[][] gridList = new char[lines.length][];
//        for (int i = 0; i < lines.length; i++) {
//            gridList[i] = lines[i].toCharArray();
//        }
//        List<Integer> max = new ArrayList<>();
//        int visible = 0;
//        for (int i = 0; i < gridList.length; i++) {
//            for (int j = 0; j < gridList[0].length; j++) {
//                max.add(countDistance(gridList, i, j));
//                System.out.println(i + " " + j + " " + countDistance(gridList, i, j));
//
//            }
//        }
//        System.out.println(Collections.max(max));


    public static int meth(int n) {
        if (n <= 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1 + meth(n - 1);
        } else if (n == 3) {
            return 1 + 1 + meth(n - 1);
        } else {
            return meth(n - 1) + meth(n - 2) + meth(n - 3);
        }

    }

    public static int countDistance(char[][] arr, int i, int j) {


        return lookLeft(arr, i, j) * lookRight(arr, i, j) * lookUp(arr, i, j)
                * lookDown(arr, i, j);
    }

    private static int lookLeft(char[][] arr, int i, int j) {
        int distance = 0;

        for (int k = j - 1; k >= 0; k--) {
            if (arr[i][k] < arr[i][j]) {
                distance++;
            } else {
                return distance + 1;
            }
        }
        return distance;
    }


    private static int lookRight(char[][] arr, int i, int j) {
        int distance = 0;
        for (int k = j + 1; k < arr[i].length; k++) {
            if (arr[i][k] < arr[i][j]) {
                distance++;
            } else {
                return distance + 1;
            }
        }
        return distance;
    }

    private static int lookUp(char[][] arr, int i, int j) {
        int distance = 0;

        for (int k = i - 1; k >= 0; k--) {
            if (arr[k][j] < arr[i][j]) {
                distance++;
            } else {
                return distance + 1;
            }
        }
        return distance;
    }


    private static int lookDown(char[][] arr, int i, int j) {
        int distance = 0;

        for (int k = i + 1; k < arr.length; k++) {
            if (arr[k][j] < arr[i][j]) {
                distance++;
            } else {
                return distance + 1;
            }
        }
        return distance;
    }
}


//if (gridList[i][j] > gridList[i - 1][j]
//                        || gridList[i][j] > gridList[i + 1][j]
//                        || gridList[i][j] > gridList[i][j + 1]
//                        || gridList[i][j] > gridList[i][j - 1]) {

//   List<Character> arr = new ArrayList<>();
//        for (int i = 0; i < 14; i++) {
//
//            arr.add(null);
//        }
//
//        for (int i = 0; i < day3.length(); i++) {
//            arr.set(i % 14, day3.charAt(i));
//            System.out.println(arr);
//            if (allDiff(arr)) {
//                System.out.println(i + 1);
//                return;
//            }
//        }
//
//    }
//
//    public static boolean allDiff(List<Character> arr) {
//        HashSet<Character> characters = new HashSet(arr);
////        System.out.println(characters.size());
//        return characters.size() == 14 && !characters.contains(null);
//    }

//  List<Stack<Character>> stacks = new ArrayList<>();
//        String[] parts = day3.split("\n\n");
//        String[] stackLines = parts[0].split("\n");
//
//        String stackLine = stackLines[stackLines.length - 1];
//        System.out.println(stackLine);
//        OptionalInt max = Arrays.stream(stackLine.split("   ")).map(String::trim).mapToInt(Integer::valueOf).max();
//        int maxStack = max.getAsInt();
//        System.out.println(maxStack);
//        for (int i = 0; i < maxStack; i++) {
//            stacks.add(new Stack<Character>());
//        }
//        for (int j = stackLines.length - 1; j >= 0; j--) {
//            String line = stackLines[j];
//            for (int i = 0; i < line.length(); i++) {
//                char c = line.charAt(i);
//                if (c >= 'A' && c <= 'Z') {
//                    System.out.println(i / 4 + " " + c);
//                    stacks.get(i / 4).push(c);
//                }
//            }
//        }
//        System.out.println(stacks);
////        System.out.println(stacks.get(0).pop());
//        String[] instructionsLine = parts[1].split("\n");
//        for (String line : instructionsLine) {
//            String replace = line.replace("move ", "")
//                    .replace("from ", "")
//                    .replace("to ", "");
//            List<Integer> collect = Arrays.stream(replace.split(" "))
//                    .mapToInt(Integer::parseInt)
//                    .boxed()
//                    .collect(Collectors.toList());
//            System.out.println(collect);
//            int source = collect.get(1) - 1;
//            int destination = collect.get(2) - 1;
//            Stack<Character> temp = new Stack();
//            for (int i = 0; i < collect.get(0); i++) {
//
//                Character crate = stacks.get(source).pop();
//                temp.push(crate);
//            }
//
//            while (!temp.isEmpty()) {
//
//                Character crate = temp.pop();
//                stacks.get(destination).push(crate);
//            }
//
//            System.out.println(stacks);
//
//        }
//        String output = "";
//        for (Stack<Character> stack :
//                stacks) {
//            if (!stack.empty()) {
//                output += stack.pop();
//            }
//        }
//        System.out.println(output);
//    }


//
//
//    int sum =
//            Arrays.stream(day3.split("\n"))
//
//                    .map(line -> {
//                                String[] works = line.split(",");
//                                List<Integer> boundsA = Arrays.stream(works[0].split("-")).map(Integer::parseInt).collect(Collectors.toList());
//                                List<Integer> boundsB = Arrays.stream(works[1].split("-")).map(Integer::parseInt).collect(Collectors.toList());
//                                boolean BisInA = boundsB.get(0) > boundsA.get(1) && boundsB.get(1) > boundsA.get(1);
//                                boolean AisInB = boundsB.get(0) < boundsA.get(0) && boundsB.get(1) < boundsA.get(0);
//                                if (BisInA || AisInB) {
//                                    return 0;
//                                }
//                                System.out.println("---");
//                                System.out.println(boundsA);
//                                System.out.println(boundsB);
//                                return 1;
//                            }
//                    ).mapToInt(Integer::valueOf)
//                    .sum();
//        System.out.println(sum);
//
//
//
//   String[] s = line.split(" ");
//                    switch (s[0]) {
//                        case ROCK:
//                            if (s[1].equals(LOSE)) // lose
//                                return 3 + 0;
//                            if (s[1].equals(DRAW))
//                                return 1 + 3;
//                            if (s[1].equals(WIN))
//                                return 2 + 6;
//                        case PAPER: //papier
//                            if (s[1].equals(LOSE))
//                                return 1 + 0;
//                            if (s[1].equals(DRAW))
//                                return 2 + 3;
//                            if (s[1].equals(WIN))
//                                return 3 + 6;
//                        case Ciseau:
//                            if (s[1].equals(LOSE))
//                                return 2 + 0;
//                            if (s[1].equals(DRAW))
//                                return 3 + 3;
//                            if (s[1].equals(WIN))
//                                return 1 + 6;
//                            default:
//                            throw new IllegalArgumentException();
//                    }
//
//                }).mapToInt(Integer::valueOf).sum();
//        System.out.println(sum);

//                        .map(a -> getIntStream(a).sum())
//                        .sorted((a, b) -> b - a)
//                        .limit(3)
//                        .mapToInt(Integer::valueOf)
//                        .sum());


//
//        System.out.println("it is reading");
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//            int elfMax = 0;
//            while (line != null && !line.equals("")) {
//                elfMax += Integer.parseInt(line);
//                line = br.readLine();
//            }
//            queue.add(elfMax);
//        }
//
//        System.out.println(queue);
//        System.out.println(queue.poll() + queue.poll() + queue.poll());
//
//        int AIM = 0;
//        int horizontal = 1;
//        int depth = 2;
//        int[] arr = new int[3];
//        br.lines().forEach(line -> {
//            String[] s = line.split(" ");
//            int value = Integer.parseInt(s[1]);
//
//            if (s[0].equals("forward")) {
//                arr[horizontal] += value;
//                arr[depth] += value *arr[AIM];
//            } else if (s[0].equals("up")) {
//                arr[AIM] -= value;
//            } else if (s[0].equals("down")) {
//                arr[AIM] += value;
//            }
//        });
//
//        int[] reduce = br.lines().reduce(new int[3], (values, strings) -> {
//
//            String[] s = strings.split(" ");
//            int value = Integer.parseInt(s[1]);
//            if (s[0].equals("forward")) {
//                return new int[]{values[AIM], values[horizontal] + value, values[depth] + value * values[AIM]};
//            } else if (s[0].equals("up")) {
//                return new int[]{values[AIM] - value, values[horizontal], values[depth]};
//            } else if (s[0].equals("down")) {
//                return new int[]{values[AIM] + value, values[horizontal], values[depth]};
//            }
//            return values;
//        }, (arr1, arr2) -> {
//            return new int[]{arr1[0] + arr2[0], arr1[1] + arr2[1], arr1[2] + arr2[2]};
//        });
//        System.out.println(reduce[depth] * reduce[horizontal]);
//    }

//    }


// int countIncrease = 0;
//        LinkedList<Integer> values = new LinkedList<>();
//        for (int i = 0; i < 3; i++) {
//            values.add(Integer.parseInt(br.readLine()));
//        }
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//
//            values.add(Integer.parseInt(line));
//            Iterator<Integer> iterator = values.iterator();
//            int i = 0;
//            int previousMean = 0;
//            int nextMean = 0;
//            for (Integer next : values) {
//                if (i == 0) {
//                    previousMean += next;
//                } else if (i == 3) {
//                    nextMean += next;
//                } else {
//                    previousMean += next;
//                    nextMean += next;
//                }
//                i++;
//            }
//            if (nextMean > previousMean) {
//                countIncrease++;
//            }
//            values.removeFirst();
//
//
//        }
//        System.out.println(values);
//        System.out.println(countIncrease);

//
//        Integer preValue = null;
//        for (String line = br.readLine(); line != null; line = br.readLine()) {
//
////            System.out.println(line);
//            int number = Integer.parseInt(line);
//            if (preValue != null) {
//                if (number > preValue) {
//                    countIncrease++;
//                }
//            }
//            preValue = number;
//        }