package org.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
//        System.out.println("Hello world!");
//        BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream("day3")));
//
        String fileString = new String(Files.readAllBytes(new File("day13").toPath()));

        String[] pairs = fileString.split("\n\n");
        int score = 0;
        for (int i = 0; i < pairs.length; i++) {
            String[] split = pairs[i].split("\n");
            ArrayList<Node> nodes = sToArray(split[0]);
            ArrayList<Node> nodes1 = sToArray(split[1]);
            System.out.println();
//            isSameString(split[0], displayArray(nodes));
//            isSameString(split[1], displayArray(nodes1));
            if (compareLists(nodes, nodes1) <= 0) {
                System.out.println(i + 1);
                score += i + 1;
            }
        }
        System.out.println(score);
    }


    static class Node {
        int value;
        ArrayList<Node> array;

        public Node(int value, ArrayList array) {
            this.value = value;
            this.array = array;
        }
    }

    /**
     * return true if right order, false otherwise.
     *
     * @param nodes
     * @param nodes1
     * @return
     */
    private static int compareLists(ArrayList<Node> nodes, ArrayList<Node> nodes1) {
        int i = 0;
        for (; i < nodes.size(); i++) {
            if (i >= nodes1.size()) {
                return 1;
            } else {
                int compare = compare(nodes.get(i), nodes1.get(i));
                if (compare != 0) {
                    return compare;
                }
            }
        }
        if(nodes1.size() >  nodes.size()) {
            return -1;
        }
        return 0;
    }

    private static int compare(Node node, Node node1) {
        if (node.array == null && node1.array == null) {
            return node.value - node1.value;
        } else if (node.array != null && node1.array != null) {
            return compareLists(node.array, node1.array);
        } else {
            return compare(nodeToArray(node), nodeToArray(node1));
        }
    }

    private static Node nodeToArray(Node node) {
        if (node.array != null) {
            return node;
        } else {
            ArrayList<Node> nodes = new ArrayList<>();
            nodes.add(node);
            return new Node(0, nodes);
        }
    }

    private static ArrayList<Node> sToArray(String s) {
        ArrayList<Node> nodes = new ArrayList<>();
        for (int i = 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= '0' && c <= '9') {
                int j = i;
                for (; j < s.length() && s.charAt(j) >= '0' && s.charAt(j) <= '9'; j++) {

                }
                int num = Integer.parseInt(s.substring(i, j));
                nodes.add(new Node(num, null));
                i = j - 1;
            } else if (c == ',') {
                continue;
            } else if (c == ']') {
                return nodes;
            } else if (c == '[') {
                nodes.add(new Node(0, sToArray(s.substring(i))));
                int count = 1;
                while (count != 0) {
                    i++;
                    c = s.charAt(i);
                    if (c == '[') {
                        count++;
                    } else if (c == ']') {
                        count--;
                    }

                }
            }
        }
        return nodes;
    }

    public static void isSameString(String s1, String s2) {
        if (!s1.equals(s2)) {
            System.out.println("not the same: " + s1 + " " + s2);
        }
    }

    public static String displayArray(ArrayList<Node> nodes) {
        String out = "[";
        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            if (i != 0) {

                out += ",";
            }
            if (n.array != null) {
                out += displayArray(n.array);
            } else {
                out += n.value;
            }
        }
        out += "]";
        return out;
    }
}


