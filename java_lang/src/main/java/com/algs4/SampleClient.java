package com.algs4;

/**
 * Created by abelard on 8/30/17.
 * Algorithm 4th edition p384
 */
public class SampleClient {
    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st;
        st = new SequentialSearchST<String, Integer>();

        for(int i = 0; !StdIn.isEmpty();i++)
        {
            String key = StdIn.readString();
            System.out.println("key " + key);
            st.put(key,i);
        }

        for(String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
