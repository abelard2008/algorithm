package com.algs4;

/**
 * Created by abelard on 8/31/17.
 * algs4 p398
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }

        public int size() {
            return size(root);
        }

        public int size(Node x) {
            if(x == null) return 0;
            else return x.N;
        }

        public Value get(Node x, Key key) {
            if(x == null) return null;
            int cmp = key.compareTo(x.key);
            if(cmp < 0) return get(x.left, key);
            else if(cmp > 0) return get(x.right, key);
            else return x.val;
        }
        public Value get(Key key) {
            return get(root, key);
        }

        public Node put(Node x, Key key, Value val) {
            if(x == null) return new Node(key, val, 1);
            int cmp = key.compareTo(x.key);
            if(cmp < 0) x.left = put(x.left, key, val);
            else if(cmp > 0) x.right = put(x.right, key, val);
            else x.val = val;
            x.N = size(x.left) + size(x.right) + 1;

            return x;
        }
        public void put(Key key, Value val) {
            root = put(root, key, val);
        }

        public Node min(Node x) {
            if(x.left == null) return x;
            return min(x.left);
        }

        public Key min() {
            return min(root).key;
        }

        public Node floor(Node x, Key key) {
            if(x == null) return null;
            int cmp = key.compareTo(x.key);
            if(cmp == 0) return x;
            if(cmp < 0) return floor(x.left, key);
            Node t = floor(x.right, key);
            if(t != null) return t;
            else return x;
        }

        private Node select(Node x, int k) {
            if(x == null) return null;
            int t = size(x.left);
            if(t > k) return select(x.left, k);
            else if(t < k) return select(x.right, k - t -1);
            else return x;
        }

        public Key select(int k) {
            return select(root, k).key;
        }

        public int rank(Key key) {
            return rank(key, root);
        }

        private int rank(Key key, Node x) {
            if(x == null) return 0;
            int cmp = key.compareTo(x.key);
            if(cmp < 0) return rank(key, x.left);
            else if(cmp > 0) return 1 + size(x.left) + rank(key, x.right);
            else return size(x.left);
        }
    }
}
