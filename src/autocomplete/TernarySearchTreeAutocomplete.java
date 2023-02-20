package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of th≤≥le tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {

        for (CharSequence term : terms) {
            if (term.length() > 0) {
                overallRoot = put(overallRoot, term, 0);
            }
        }
    }

    private Node put(Node x, CharSequence key, int d) {

        char c = key.charAt(d);
        if (x == null) {
            x = new Node(c);
        }
        if (c < x.data) {
            x.left = put(x.left, key, d);
        } else if (c > x.data) {
            x.right = put(x.right, key, d);
        } else if (d < key.length() - 1) {
            x.mid = put(x.mid, key, d + 1);
        } else if (d == key.length()-1) {
            x.isTerm = true;
        }
        return x;
    }

//    private char add(CharSequence key) {
//        if (key == null) {
//            throw new IllegalArgumentException("calls add() with null argument");
//        }
//        if (key.length() == 0) {
//            throw new IllegalArgumentException("key must have length >= 1");
//        }
//        Node x = get(overallRoot, key, 0);
//        if (x == null) {
//            return 0;
//        }
//        return x.data;
//    }


    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        if (prefix == null && prefix.length()== 0) {
            return result;
        }

        Node node = get(overallRoot, prefix, 0);
        if (node == null) {
            return result;
        } else if(node.isTerm) {
            result.add(prefix);
        }
        collect(node.mid, new StringBuilder(prefix), result);
        return result;
    }

    private void collect(Node x, StringBuilder prefix, List<CharSequence> list) {
        if (x != null) {
            collect(x.left, prefix, list);
            prefix.append(x.data);
            if(x.isTerm) {
                list.add(prefix.toString());
            }
            collect(x.mid, prefix, list);
            prefix.deleteCharAt(prefix.length() - 1);
            collect(x.right, prefix, list);
        }
    }

    private Node get(Node x, CharSequence prefix, int d) {
        if (x == null) {
            return null;
        }
        char c = prefix.charAt(d);
        if (c < x.data) {
            return get(x.left, prefix, d);
        } else if (c > x.data) {
            return get(x.right, prefix, d);
        } else if (d < prefix.length() - 1) {
            return get(x.mid, prefix, d+1);
        } else {
            return x;
        }
    }


    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node<Value> {
        private char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data) {
            this.data = data;
            this.isTerm = false;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
