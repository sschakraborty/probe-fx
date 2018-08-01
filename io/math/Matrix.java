package io.math;

import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

public class Matrix {
    public static class Dimension {
        private int rows, cols;
        
        public Dimension(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
        }
        
        public int getRows() { return this.rows; }
        public int getColumns() { return this.cols; }
        
        public void setRows(int rows) {
            this.rows = rows;
        }
        
        public void setColumns(int cols) {
            this.cols = cols;
        }
        
        public boolean isAdditionCompatible(Dimension foo) {
            return this.rows == foo.getRows() && this.cols == foo.getColumns();
        }
        
        public boolean isMultiplicationCompatible(Dimension foo) {
            return this.cols == foo.getRows();
        }
        
        public int countElements() {
            return this.rows * this.cols;
        }
    }
    
    private static class DimensionMismatchException extends Exception {
        @Override
        public String getMessage() {
            return "Matrix dimension has no operation compatibility!";
        }
    }
    
    private Dimension dim;
    private ArrayList<Double> linear;
    
    public Matrix() {
        dim = new Dimension(3, 3);
        linear = new ArrayList<>(dim.countElements());
        for(int i = 0; i < dim.countElements(); i++) {
            linear.add(0.0);
        }
    }
    
    public Matrix(Dimension d) {
        if(d != null) {
            dim = d;
        } else {
            dim = new Dimension(3, 3);
        }
        linear = new ArrayList<>(dim.countElements());
        for(int i = 0; i < dim.countElements(); i++) {
            linear.add(0.0);
        }
    }
    
    public Dimension getDim() { return this.dim; }
    
    public Double get(int x, int y) {
        return linear.get(x * dim.getColumns() + y);
    }
    
    public void set(int x, int y, Double elem) {
        linear.set(x * dim.getColumns() + y, elem);
    }
    
    public void printPrettily() throws IOException {
        PrintWriter out = new PrintWriter(System.out);
        for(int i = 0; i < dim.getRows(); i++) {
            for(int j = 0; j < dim.getColumns(); j++) {
                out.print(get(i, j));
                out.print('\t');
            }
            out.println();
        }
        out.close();
    }
    
    public Matrix mul(Matrix foo) throws Exception {
        if(this.dim.isMultiplicationCompatible(foo.getDim())) {
            Matrix m = new Matrix(new Dimension(this.dim.getRows(), foo.getDim().getColumns()));
            for(int i = 0; i < this.dim.getRows(); i++) {
                for(int j = 0; j < foo.getDim().getColumns(); j++) {
                    Double elem = 0.0;
                    for(int k = 0; k < this.dim.getColumns(); k++) {
                        elem += this.get(i, k) * foo.get(k, j);
                    }
                    m.set(i, j, elem);
                }
            }
            return m;
        } else {
            throw new DimensionMismatchException();
        }
    }
    
    public Matrix hadMul(Matrix foo) throws Exception {
        if(this.dim.countElements() == foo.getDim().countElements()) {
            Matrix m = new Matrix(this.dim);
            for(int i = 0; i < this.dim.getRows(); i++) {
                for(int j = 0; j < this.dim.getColumns(); j++) {
                    m.set(i, j, this.get(i, j) * foo.get(i, j));
                }
            }
            return m;
        } else {
            throw new DimensionMismatchException();
        }
    }
}
