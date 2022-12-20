package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Score: " + new Test().test("day16"));
    }



  static   class Test {
        HashMap<String, Node> map;
        List<Integer> scores = new ArrayList<>();

        int test(String pathname) throws IOException, InterruptedException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
            map = new HashMap<>();
            String file = new String(Files.readAllBytes(new File(pathname).toPath()));
            String[] fileString = file.split("\n");
            Pattern pattern = Pattern.compile("Valve ([A-Z]*) has flow rate=(\\d+); \\w+ \\w+ to \\w+ (.*)");

            for (String s : fileString) {
                Matcher matcher = pattern.matcher(s);
                matcher.lookingAt();
                String name = matcher.group(1);
                map.put(name, new Node(name, Integer.parseInt(matcher.group(2)), matcher.group(3).split(", ")));
            }
            System.out.println(map);
            System.out.println();

            generateCombinations(new ArrayList<>(),
                    map.values().stream().filter(a -> a.rate != 0).collect(Collectors.toList()));
            System.out.println("count "+ count);
            return scores.stream().max(Comparator.comparingInt(a -> a)).get();
//        return 0;
        }

        long count = 0;
        private void generateCombinations(ArrayList<Node> program, List<Node> collect) {


            if (collect.isEmpty()  || measureRunningTimeOf(program) > 30) {
                count++;
                System.out.println(count);
                testThisSolution(program);
            } else {
                for (Node node : collect) {
                    ArrayList<Node> nodes = new ArrayList<>(program);
                    nodes.add(node);

                    List<Node> newCollect = new ArrayList<Node>(collect);
                    newCollect.remove(node);
                    generateCombinations(nodes, newCollect);
                }
            }
        }

        private int measureRunningTimeOf(ArrayList<Node> program) {
            int runningTime = 0;
            Node pos = map.get("AA");
            for (Node node: program ) {
                runningTime += distance(pos, node).size() + 1;
                pos = node;
            }
            return runningTime;
        }

        private int testThisSolution(List<Node> program) {
            Node currentNode = map.get("AA");

            int TOTAL_TIME = 30;
            ArrayList<Node> openedValves = new ArrayList<>();
            //Find the most exciting one
//        System.out.println(map.size());
//        System.out.println(map.values().size());
            int score = 0;
            for (int i = 1; i <= TOTAL_TIME; i++) {
//            System.out.println();
//            System.out.println("== Minute " + i + " ==");

                if (openedValves.size() == 0) {
//                System.out.println("No valves are open.");
                } else {
                    List<String> openedValvesName = openedValves.stream().map(a -> a.name).collect(Collectors.toList());
                    Integer reduce = openedValves.stream().map(a -> a.rate).reduce(0, Integer::sum);
                    score += reduce;
//                System.out.println("Valves " + openedValvesName + " are open, releasing " + reduce + " pressure. score=" + score);
                }
                if (program.size() == 0)
                    continue;

//            List<String> collect = Arrays.stream(map.keySet().toArray()).map(a -> (String) a).collect(Collectors.toList());
//            Node finalCurrentNode = currentNode;
//            Optional<SimpleEntry<Node, Integer>> max = collect.stream()
//                    .map(a -> map.get(a))
//                    .map(n -> {
//                        int distance = distance(finalCurrentNode, n).size();
//                        int value = openedValves.contains(n) ? 0 : n.rate * 10 / (distance + 1);
//                        return new SimpleEntry<Node, Integer>(n, value);
//                    })
//                    .max(Comparator.comparingInt(SimpleEntry::getValue));
                Node destination = program.get(0);
//            System.out.println("We want to go to: " + destination);


                //open it
                List<String> path = distance(currentNode, destination);
//            System.out.println(path);
                if (path.size() == 0) {
                    if (openedValves.contains(currentNode)) {
//                    System.out.println("valve already opened. Do nothing.");
                    } else {
//                    System.out.println("We arrived. We open the valve.");
                        openedValves.add(currentNode);
                        program.remove(0);
                    }
                } else {
                    currentNode = map.get(path.get(0));

//                System.out.println("We are now at " + currentNode);
                }
            }
            scores.add(score);
//        System.out.println(program.stream().map(a -> a.name).collect(Collectors.toList()) + " " + score);
            return score;
        }

        Map<String, ArrayList<String>> memo = new HashMap<>();
        private List<String> distance(Node currentNode, Node n) {
            ArrayList<String> strings = memo.get(currentNode.name + "-" + n.name);
            if (strings != null) {
                return new ArrayList<>(strings);
            }

            Queue<SimpleEntry<String, ArrayList<String>>> queue = new LinkedList<>();
            HashMap<String, List<String>> distance = new HashMap<>();

            queue.add(new SimpleEntry<>(currentNode.name, new ArrayList<>()));
            distance.put(currentNode.name, new ArrayList<>());

            while (!queue.isEmpty()) {
                SimpleEntry<String, ArrayList<String>> pair = queue.poll();
                String poll = pair.getKey();
                Node node = map.get(poll);
                for (String s : node.link) {
                    if (distance.get(s) == null) {
                        ArrayList<String> past = new ArrayList<>(pair.getValue());
                        past.add(s);
                        queue.add(new SimpleEntry<>(s, past));
                        distance.put(s, past);
                    }
                }
            }
            List<String> strings1 = distance.get(n.name);
            memo.put(currentNode.name + "-" + n.name, new ArrayList<>(strings1));
            return strings1;
        }
    }

   static class Node {
        public String name;
        public int rate;
        public String[] link;


        Node(String name, int rate, String[] link) {
            this.name = name;
            this.rate = rate;
            this.link = link;
        }

        @Override
        public String toString() {
            return "<" + name + " rate:" + rate + ", " + Arrays.toString(link) + ">";
        }

        @Override
        public boolean equals(Object obj) {
            Node obj1 = (Node) obj;
            return obj1.name.equals(name);
        }
    }


}

