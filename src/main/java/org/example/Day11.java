package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
//        String fileString = new String(Files.readAllBytes(new File("day10").toPath()));

        ArrayList<Monkey> monkeys = new ArrayList<>();

//        monkeys.add(new Monkey(
//                Arrays.asList(79, 98),
//                a -> a * 19,
//                23,
//                2,
//                3));
//        monkeys.add(new Monkey(
//                Arrays.asList(54, 65, 75, 74),
//                a -> a + 6,
//                19,
//                2,
//                0));
//        monkeys.add(new Monkey(
//                Arrays.asList(79, 60, 97),
//                a -> a * a,
//                13,
//                1,
//                3));
//        monkeys.add(new Monkey(
//                Arrays.asList(74),
//                a -> a + 3,
//                17,
//                0,
//                1));


        monkeys.add(new Monkey(
                Arrays.asList(80),
                a -> a * 5,
                2,
                4,
                3));

        monkeys.add(new Monkey(
                Arrays.asList(75, 83, 74),
                a -> a + 7,
                7,
                5,
                6));
        monkeys.add(new Monkey( //2
                Arrays.asList(86, 67, 61, 96, 52, 63, 73),
                a -> a + 5,
                3,
                7,
                0));


        monkeys.add(new Monkey(
                Arrays.asList(85, 83, 55, 85, 57, 70, 85, 52),
                a -> a + 8,
                17,
                1,
                5));
        monkeys.add(new Monkey(
                Arrays.asList(67, 75, 91, 72, 89),
                a -> a + 4,
                11,
                3,
                1));
        monkeys.add(new Monkey(
                Arrays.asList(66, 64, 68, 92, 68, 77),
                a -> a * 2,
                19,
                6,
                2));
        monkeys.add(new Monkey(
                Arrays.asList(97, 94, 79, 88),
                a -> a * a,
                5,
                2,
                7));
        monkeys.add(new Monkey(
                Arrays.asList(77, 85),
                a -> a + 6,
                13,
                4,
                0));

        for (int i = 0; i < 10000; i++) { // rounds
            if (i == 1 || i == 20 || i % 1000 == 0) {
                System.out.println("round " + i);
                monkeys.forEach(a -> System.out.println(a.inspected));
                System.out.println();
            }

            for (int monkeyNumber = 0; monkeyNumber < monkeys.size(); monkeyNumber++) { // monkeys
                Monkey monkey = monkeys.get(monkeyNumber);
                for (int k = 0; k < monkey.items.size(); k++) {
                    Double item = monkey.items.get(k);
//                    monkey.items.remove(item);
                    Double afterbored = monkey.operation.exec(item);
                    Integer nextMonkey = (afterbored % 9699690) % monkey.test == 0 ? monkey.monkeyTrue : monkey.monkeyFalse;
                    monkeys.get(nextMonkey).items.add(afterbored % 9699690); //9,699,690
                    //28,537,348,205
                    monkey.inspected++;
                }
                monkey.items.clear();
            }


        }
        System.out.println();
        System.out.println();
        monkeys.forEach(a -> System.out.println(a.inspected));
        System.out.println();
        double[] doubles = monkeys.stream().mapToDouble(a -> a.inspected).sorted().toArray();
        System.out.println(doubles[doubles.length - 1] * doubles[doubles.length - 2]);

//        limit.forEach(a -> System.out.println("" + a));
//        double sum = limit.reduce(1, (a, b) -> a * b);
//        System.out.println(sum);
    }

    interface Operation {
        Double exec(Double old);
    }

    static class Monkey {
        List<Double> items;
        Operation operation;
        Integer test;
        Integer monkeyTrue;
        Integer monkeyFalse;

        long inspected = 0;

        public Monkey(List<Integer> items, Operation operation, Integer test, Integer monkeyTrue, Integer monkeyFalse) {
            this.items = items.stream().map(Double::valueOf).collect(Collectors.toList());
            this.operation = operation;
            this.test = test;
            this.monkeyTrue = monkeyTrue;
            this.monkeyFalse = monkeyFalse;
        }
    }
}


//   String fileString = ("noop\n" +
////                "addx 3\n" +
////                "addx -5");
//        String[] lines = fileString.split("\n");
////        String[] lines = .split("\n");
//
//        int strength = 0;
//        int pendingInstruction = 0;
//        int instructionLine = 0;
//        int x = 1;
//        int buffer = 0;
//        int i = 1;
//        for (; i >= 0; i++) {
//
//
////            if (i == 20 || (i + 20) % 40 == 0) {
////                strength += x * i;
////            }
////            System.out.println(i + " " +x + " " + strength);
//
//
//            if (pendingInstruction == 0) {
//
//                if (instructionLine == lines.length)
//                    break;
//                if (instructionLine < lines.length) {
//                    String[] s = lines[instructionLine].split(" ");
//                    instructionLine++;
//                    if (s[0].equals("noop")) {
//                        pendingInstruction = 1;
//                    } else if (s[0].equals("addx")) {
//                        buffer = Integer.parseInt(s[1]);
//                        pendingInstruction += 2;
//                    }
//                }
//            }
//
//            int pos = (i-1) % 40;
//            if (pos == 0)
//                System.out.println("");
//            if (pos == x || pos == x - 1 || pos == x + 1) {
//                System.out.print("#");
//            } else {
//                System.out.print(".");
//            }
//
//            pendingInstruction--;
//            if (pendingInstruction == 0) {
//                x += buffer;
//                buffer = 0;
////                System.out.println("cycle" + i + " x" + x);
//            }
//
//        }
//        System.out.println(i);
//        System.out.println(x);
//        System.out.println(strength);