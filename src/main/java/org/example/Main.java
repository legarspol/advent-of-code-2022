package org.example;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Score: " + new Test().test("day16t"));
    }
}

class Node {
    public String name;
    public int rate;
    public String[] link;

    public boolean isOpened = false;

    Node(String name, int rate, String[] link) {
        this.name = name;
        this.rate = rate;
        this.link = link;
    }

    @Override
    public String toString() {
        return "<" + name + " rate:" + rate + ", " + Arrays.toString(link) + ">";
    }
}


class Test {
    HashMap<String, Node> map;


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
//            System.out.println(s);
            String name = matcher.group(1);
//            System.out.println(name);
            map.put(name, new Node(name, Integer.parseInt(matcher.group(2)), matcher.group(3).split(", ")));
        }
        System.out.println(map);
        System.out.println();


        Node currentNode = map.get("AA");

        int TOTAL_TIME = 30;
        ArrayList<Node> openedValves = new ArrayList<>();
        //Find the most exciting one
//        System.out.println(map.size());
//        System.out.println(map.values().size());
        int score = 0;
        for (int i = 1; i <= TOTAL_TIME; i++) {
            System.out.println();
            System.out.println("== Minute " + i + " ==");

            if (openedValves.size() == 0) {
                System.out.println("No valves are open.");
            } else {
                List<String> openedValvesName = openedValves.stream().map(a -> a.name).collect(Collectors.toList());
                Integer reduce = openedValves.stream().map(a -> a.rate).reduce(0, Integer::sum);
                score += reduce;
                System.out.println("Valves " + openedValvesName + " are open, releasing " + reduce + " pressure. score=" + score);
            }

            List<String> collect = Arrays.stream(map.keySet().toArray()).map(a -> (String) a).collect(Collectors.toList());
            Node finalCurrentNode = currentNode;
            Optional<SimpleEntry<Node, Integer>> max = collect.stream()
                    .map(a -> map.get(a))
                    .map(n -> {
                        int distance = distance(finalCurrentNode, n).size();
                        int value = n.isOpened ? 0 : n.rate * 10 / (distance + 1);
                        return new SimpleEntry<Node, Integer>(n, value);
                    })
                    .max(Comparator.comparingInt(SimpleEntry::getValue));
            System.out.println("We want to go to: " + max.get());

            //open it
            List<String> path = distance(currentNode, max.get().getKey());
            System.out.println(path);
            if (path.size() == 0) {
                if (currentNode.isOpened) {
                    System.out.println("valve already opened. Do nothing.");
                } else {
                    System.out.println("We arrived. We open the valve.");
                    currentNode.isOpened = true;
                    openedValves.add(currentNode);
                }
            } else {
                currentNode = map.get(path.get(0));

                System.out.println("We are now at " + currentNode);
            }
        }
        return score;
    }


    private List<String> distance(Node currentNode, Node n) {
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
        return distance.get(n.name);
    }
}

