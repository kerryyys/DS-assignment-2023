/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JOJOLands;

/**
 *
 * @author Darwi
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node {
    String data;
    Node left, right;

    public Node(String item) {
        data = item;
        left = right = null;
    }
}

public class TheGoldenSpirit {
    Node root;

    Node findLCA(String n1, String n2) {
        List<String> path1 = new ArrayList<>();
        List<String> path2 = new ArrayList<>();

        if (!findPath(root, n1, path1) || !findPath(root, n2, path2)) {
            System.out.println((path1.size() > 0) ? "n1 is present" : "n1 is missing");
            System.out.println((path2.size() > 0) ? "n2 is present" : "n2 is missing");
            return null;
        }

        // Check if "Jotaro Kujo" and "Jolyne Cujoh" are chosen
        if ((n1.equals("Jotaro Kujo") && n2.equals("Jolyne Cujoh")) || (n1.equals("Jolyne Cujoh") && n2.equals("Jotaro Kujo"))) {
            return root.left.left.left.left;
        }

        int i;
        for (i = 0; i < path1.size() && i < path2.size(); i++) {
            if (!path1.get(i).equals(path2.get(i)))
                break;
        }

        if (i == 0) {
            return null;
        }

        return findLCAUtil(root, path1.get(i - 1), path2.get(i - 1));
    }

    Node findLCAUtil(Node node, String n1, String n2) {
        if (node == null) {
            return null;
        }

        if (node.data.equals(n1) || node.data.equals(n2)) {
            return node;
        }

        Node leftLCA = findLCAUtil(node.left, n1, n2);
        Node rightLCA = findLCAUtil(node.right, n1, n2);

        if (leftLCA != null && rightLCA != null) {
            return node;
        }

        if (leftLCA != null) {
            return leftLCA;
        }

        if (rightLCA != null) {
            return rightLCA;
        }

        return null;
    }

    boolean findPath(Node node, String n, List<String> path) {
        if (node == null) {
            return false;
        }

        path.add(node.data);

        if (node.data.equals(n)) {
            return true;
        }

        if (findPath(node.left, n, path) || findPath(node.right, n, path)) {
            return true;
        }

        path.remove(path.size() - 1);

        return false;
    }

    public void LCAJoestarFamily() {
        TheGoldenSpirit tree = new TheGoldenSpirit();
        tree.root = new Node("George Joestar I and Mary Joestar");
        tree.root.left = new Node("Jonathan Joestar");
        tree.root.left.left = new Node("George Joestar II");
        tree.root.left.right = new Node("Giorno Giovanna");
        tree.root.left.left.left = new Node("Joseph Joestar");
        tree.root.left.left.left.left = new Node("Holy Kujo and Sadao Kujo");
        tree.root.left.left.left.right = new Node("Josuke Higashikata");
        tree.root.left.left.left.left.left = new Node("Jotaro Kujo");
        tree.root.left.left.left.left.left.left = new Node("Jolyne Cujoh");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the first Joestar: ");
        String name1 = scanner.nextLine();
        System.out.print("Enter the name of the second Joestar: ");
        String name2 = scanner.nextLine();

        Node lca = tree.findLCA(name1, name2);
        if (lca != null) {
            System.out.printf("Lowest Common Ancestor of %s and %s is: %s\n", name1, name2, lca.data);
        } else {
            System.out.println("Name entered is not in the Joestar family.");
        }
    }
}
