package Advent2021.HelperClasses;

import java.util.ArrayList;

public class Matrix {
    private ArrayList<ArrayList<Integer>> matrix;
    private int rows;
    private int columns;

    // A Matrix is given in the form {row1, row2, ..., rowN}
    public Matrix(int[][] values) {
        rows = values.length;
        columns = values[0].length;
        matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                matrix.get(i).add(values[i][j]);
            }
        }
    }

    public Matrix(ArrayList<ArrayList<Integer>> values) {
        rows = values.size();
        columns = values.get(0).size();
        matrix = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                matrix.get(i).add(values.get(i).get(j));
            }
        }
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        matrix = new ArrayList<>();

        for (int i = 0; i < rows; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < columns; j++) {
                matrix.get(i).add(0);
            }
        }
    }

    public Matrix(Matrix A) {
        Matrix temp = new Matrix(A.matrix);
        this.matrix = temp.matrix;
        this.rows = temp.rows;
        this.columns = temp.columns;
    }

    public Matrix() {
        matrix = new ArrayList<>();
        rows = 0;
        columns = 0;
    }

    public void set(int row, int col, int val) {
        matrix.get(row).set(col, val);
    }

    public void setCol(int index, Vector v) throws Exception {
        if (v.getDimension() != columns)
            throw new Exception("Cannot set column - differing length");

        for (int r = 0; r < rows; r++)
            matrix.get(r).set(index, v.getVal(r));
    }

    public int numRows() {
        return rows;
    }

    public int numCols() {
        return columns;
    }

    public Vector getRow(int index) {
        return new Vector(matrix.get(index));
    }

    public Vector getCol(int index) {
        Vector v = new Vector();
        for (int r = 0; r < rows; r++)
            v.append(matrix.get(r).get(index));

        return v;
    }

    public static Matrix multiply(Matrix A,  Matrix B) throws Exception {
        if (A.numCols() != B.numRows())
            throw new Exception("Cannot compute product of these matrices");

        Matrix res = new Matrix(A.rows, B.columns);

        for (int i = 0; i < A.rows; i++) {
            Vector r = A.getRow(i);
            for (int j = 0; j < B.columns; j++) {
                Vector c = B.getCol(j);
                res.set(i, j, Vector.dot(r, c));
            }
        }

        return res;
    }

    public static Vector multiply(Matrix A, Vector b) throws Exception {
        if (A.numCols() != b.getDimension())
            throw new Exception("Cannot compute matrix-vector product");

        Vector res = new Vector(b.getDimension());

        for (int i = 0; i < A.numCols(); i++) {
            res = Vector.add(res, A.getCol(i).scale(b.getVal(i)));
        }

        return res;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(String.format("%5d", matrix.get(i).get(j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
