package objectmodelling;

import java.util.NoSuchElementException;



class Node {

    public final int contents;
    public final Node left, right;
    public Node parent;

    public Node(int contents, Node left, Node right) {
        System.out.println("Node");
        this.contents = contents;
        this.left = left;
        if (left != null) {
            left.parent = this;
        }
        this.right = right;
        if (right != null) {
            right.parent = this;
        }
    }

    public Node setParent(Node n) {
        return n.parent;
    }

    public Node setLeft(Node n) {
        return n.left;
    }

    public Node setRight(Node n) {
        return n.right;
    }

    CustomIterator inorderIterator() {
        return new InorderIterator(this);
    }

    CustomIterator preorderIterator() {
        return new PreorderIterator(this);
    }

    CustomIterator postorderIterator() {
        return new PostorderIterator(this);
    }

}

class PreorderIterator implements CustomIterator {
    /* 
     * 1) Display the data part of root element (or current element)
     * 2) Traverse the left subtree by recursively calling the pre-order function.
     * 3) Traverse the right subtree by recursively calling the pre-order function.
     */

    public Node current;

    public PreorderIterator(Node root) {
        // System.out.println("PreorderIterator");
        current = root;
    }

    @Override
    public boolean hasNext() {
        // System.out.println("PreorderIterator hasNext()");
        return current != null;
    }

    @Override
    public int next() {
           // System.out.println("PreorderIterator next()");

        int value;
        value = current.contents;

        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        /* 
         * Vratit contents variabilniho Nodu , potom pojd do leveho podstromu, 
         potom do praveho podstromu potom nahoru k Nodu ktery ma dite sprava
         * 
         */
        if (current.left != null) {
            current = current.left;
        } else if (current.right != null) {
            current = current.right;
        } else {
            Node parent = current.parent;
            Node child = current;
            while (parent != null && (parent.right == child || parent.right == null)) {
                child = parent;
                parent = parent.parent;
            }
            if (parent == null) {
                current = null;
            } else {
                current = parent.right;
            }
        }
        return value;
    }
}

class InorderIterator implements CustomIterator {
    /* 
     * 1) Traverse the left subtree by recursively calling the in-order function.
     * 2) Display the data part of root element (or current element).
     * 3) Traverse the right subtree by recursively calling the in-order function.
     */

    private Node current;

    public InorderIterator(Node root) {
        //System.out.println("InorderIterator");
        current = root;
        if (current == null) {
            return;
        }
        while (current.left != null) {
            current = current.left;
        }
    }

    @Override
    public boolean hasNext() {
        //  System.out.println("InorderIterator hasNext()");
        return current != null;
    }

    @Override
    public int next() {
        // System.out.println("InorderIterator next()");
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        int value;
        value = current.contents;

        /* Pojd doprava jednou, pak konstantne doleva potom nahoru a vrat contents */
        if (current.right != null) {
            current = current.right;
            while (current.left != null) {
                current = current.left;
            }
            return value;
        } else {
            while (true) {
                if (current.parent == null) {
                    current = null;
                    return value;
                }
                if (current.parent.left == current) {
                    current = current.parent;
                    return value;
                }
                current = current.parent;
            }
        }
    }
}

class PostorderIterator implements CustomIterator {
    /* 
     * 1) Traverse the left subtree by recursively calling the post-order function.
     * 2) Traverse the right subtree by recursively calling the post-order function.
     * 3) Display the data part of root element (or current element).
     */

    Node current;
    Node root;
    boolean wasHere = false;
    boolean out = false;

    public PostorderIterator(Node node) {
        //System.out.println("PostorderIterator");
        current = node;
        root = node;
    }

    public Node setNode(Node root) {
        while (root.left != null || root.right != null) {
            if (root.left != null) {
                root = root.left;
            } else if (root.right != null) {
                root = root.right;
            }
        }
        return root;
    }

    @Override
    public boolean hasNext() {
        //System.out.println("PostorderIterator hasNext()");
        if (out == true) {
            return false;
        }
        return !(current== null);
    }

    @Override
    public int next() {
        //System.out.println("PostorderIterator next()");
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        
      // int num = variableNode.contents;
        /* 
         * Uplne doleva, nebo doprava, nebo vrat contents, potom nahoru a 
         opakuj dej kontinealne
         * 
         */
        if (current == root && wasHere == false) {
            wasHere = true;
            current = setNode(current);
            if (current == root) {
                out = true;
            }
            return current.contents;
        }
        if (current.parent.right != null) {
            if (current == current.parent.right) {
                current = current.parent;
                if (current == root) {
                    out = true;
                }
                return current.contents;
            } else {
                current = setNode(current.parent.right);

                return current.contents;
            }
        } else {
            current = current.parent;
            if (current == root) {
                out = true;
            }
            return current.contents;
        }
    }
}


interface CustomIterator {
 boolean hasNext();
 int next();
}


