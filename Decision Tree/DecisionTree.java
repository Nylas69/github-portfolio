import java.util.ArrayList;
import svm.SVM;

public class DecisionTree {

    private TreeNode root;
    private SVM svm;

    public DecisionTree(SVM svm) {
        this.svm = svm;
        this.root = null;
    }

    public void train() {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i < svm.ind.V.length; i++) {
            indices.add(i);
        }
        root = createTree(indices);
    }

    public float predict(float[] x) {
        TreeNode node = root;
        while (node.splitIndex >= 0) {
            if (x[node.splitIndex] < node.splitValue) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return node.prediction;
    }

    private TreeNode createTree(ArrayList<Integer> indices) {
        TreeNode node = new TreeNode();
        float[] M0 = new float[dim];
        float[] M1 = new float[dim];
        int k0 = 0, k1 = 0;
        for (int i = 0; i < indices.size(); i++) {
            int idx = indices.get(i);
            if (svm.ind.V[idx].cl.Y == 0) {
                k0++;
            } else {
                k1++;
            }
        }
        for (int j = 0; j < dim; j++) {
            for (int i = 0; i < indices.size(); i++) {
                int idx = indices.get(i);
                if (svm.ind.V[idx].cl.Y == 0) {
                    M0[j] += svm.ind.V[idx].X[j];
                } else {
                    M1[j] += svm.ind.V[idx].X[j];
                }
            }
            M0[j] /= k0;
            M1[j] /= k1;
        }
        float[] X0 = new float[dim];
        float[] w = new float[dim+1];
        for (int j = 0; j < dim; j++) {
            X0[j] = (M0[j] + M1[j]) / 2;
            w[j] = M1[j] - M0[j];
            w[dim] -= w[j] * X0[j];
        }
        node.prediction = getAccuracy(w);
        node.splitIndex = -1;
        node.splitValue = 0;
        node.leftChild = null;
        node.rightChild = null;
        return node;
    }

    private float getAccuracy(float[] w) {
        float correct = 0;
        for (int i = 0; i < svm.ind.V.length; i++) {
            float y = svm.ind.V[i].cl.Y;
            float dot = dotProduct(w, svm.ind.V[i].X);
            float prediction = Math.signum(dot);
            if (prediction == y) {
                correct++;
            }
        }
        return correct / svm.ind.V.length;
    }

    private float dotProduct(float[] a, float[] b) {
        float dot = 0;
        for (int i = 0; i < a.length; i++) {
            dot += a[i] * b[i];
        }
        dot += a[a.length-1];
        return dot;
    }

    private class TreeNode {
        int splitIndex;
        float splitValue;
        float prediction;
        TreeNode leftChild;
        TreeNode rightChild;

        public TreeNode() {
            this.splitIndex = -1;
            this.splitValue = 0;
            this.prediction = 0;
            this.leftChild = null;
            this.rightChild = null;
     }
   }
}