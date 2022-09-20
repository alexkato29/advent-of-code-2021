package Advent2021.HelperClasses;

import java.util.ArrayList;

public class Vector {
    private ArrayList<Integer> vector;
    private int dimension;

    public Vector(int[] values) {
        dimension = values.length;
        vector = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            vector.add(values[i]);
        }
    }

    public Vector(String[] values) {
        dimension = values.length;
        vector = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            vector.add(Integer.parseInt(values[i]));
        }
    }

    public Vector(ArrayList<Integer> values) {
        dimension = values.size();
        vector = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            vector.add(values.get(i));
        }
    }

    public Vector(int dimension) {
        this.dimension = dimension;
        vector = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            vector.add(0);
        }
    }

    public Vector(Vector v) {
        vector = v.getVector();
        dimension = v.getDimension();
    }

    public Vector() {
        dimension = 0;
        vector = new ArrayList<>();
    }

    public void append(int value) {
        vector.add(value);
        dimension++;
    }

    public Vector copy() {
        return new Vector(this);
    }

    public Vector scale(int scalar) {
        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, getVal(i) * scalar);
        }

        return this;
    }

    public static Vector add(Vector u, Vector v) throws Exception {
        if (u.dimension != v.dimension)
            throw new Exception("Cannot compute sum of two differing length vectors");

        Vector res = new Vector();

        for (int i = 0; i < u.getDimension(); i++) {
            res.append(u.getVal(i) + v.getVal(i));
        }

        return res;
    }

    public static int dot(Vector u, Vector v) throws Exception {
        if (u.dimension != v.dimension)
            throw new Exception("Cannot compute dot product of two differing length vectors");

        int res = 0;

        for (int i = 0; i < u.dimension; i++)
            res += u.getVal(i) * v.getVal(i);

        return res;
    }

    public ArrayList<Integer> getVector() {
        return (ArrayList<Integer>) vector.clone();
    }

    public int getVal(int index) {
        return vector.get(index);
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector))
            return false;

        Vector v = (Vector) o;

        for (int i = 0; i < getDimension(); i++) {
            if (getVal(i) != v.getVal(i))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("<");
        for (int val : vector) {
            sb.append(val);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(">");

        return sb.toString();
    }
}
