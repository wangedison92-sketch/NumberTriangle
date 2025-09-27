import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the provided NumberTriangle class to be used in this coding task.
 *
 * Note: This is like a tree, but some nodes in the structure have two parents.
 *
 * The structure is shown below. Observe that the parents of e are b and c, whereas
 * d and f each only have one parent. Each row is complete and will never be missing
 * a node. So each row has one more NumberTriangle object than the row above it.
 *
 *                  a
 *                b   c
 *              d   e   f
 *            h   i   j   k
 *
 * Also note that this data structure is minimally defined and is only intended to
 * be constructed using the loadTriangle method, which you will implement
 * in this file. We have not included any code to enforce the structure noted above,
 * and you don't have to write any either.
 *
 *
 * See NumberTriangleTest.java for a few basic test cases.
 import java.io.*;
 import java.util.ArrayList;
 import java.util.List;

 /**
 * Provided NumberTriangle class for the task.
 */
public class NumberTriangle {

    private int root;

    private NumberTriangle left;
    private NumberTriangle right;

    public NumberTriangle(int root) {
        this.root = root;
    }

    public void setLeft(NumberTriangle left) {
        this.left = left;
    }

    public void setRight(NumberTriangle right) {
        this.right = right;
    }

    public int getRoot() {
        return root;
    }

    public void maxSumPath() {
    }

    public boolean isLeaf() {
        return right == null && left == null;
    }


    public int retrieve(String path) {
        NumberTriangle cur = this;
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == 'l') {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return cur.root;
    }

    public static NumberTriangle loadTriangle(String fname) throws IOException {
        InputStream inputStream = NumberTriangle.class.getClassLoader().getResourceAsStream(fname);
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found on classpath: " + fname);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        List<int[]> rows = new ArrayList<>();
        String line = br.readLine();
        while (line != null) {
            String trimmed = line.trim();
            if (!trimmed.isEmpty()) {
                String[] parts = trimmed.split("\\s+");
                int[] vals = new int[parts.length];
                for (int i = 0; i < parts.length; i++) {
                    vals[i] = Integer.parseInt(parts[i]);
                }
                rows.add(vals);
            }
            line = br.readLine();
        }
        br.close();


        NumberTriangle[] below = null;
        for (int r = rows.size() - 1; r >= 0; r--) {
            int[] vals = rows.get(r);
            NumberTriangle[] current = new NumberTriangle[vals.length];
            for (int c = 0; c < vals.length; c++) {
                NumberTriangle node = new NumberTriangle(vals[c]);
                if (below != null) {
                    node.setLeft(below[c]);
                    node.setRight(below[c + 1]);
                }
                current[c] = node;
            }
            below = current;
        }

        return below[0];
    }

    public static void main(String[] args) throws IOException {
        NumberTriangle mt = NumberTriangle.loadTriangle("input_tree.txt");
        mt.maxSumPath(); 
        System.out.println(mt.getRoot());
    }
}