package io.graphics;

import io.math.Matrix;

public class Point2D {
    private int x, tx;
    private int y, ty;
    
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
        this.tx = x;
        this.ty = y;
    }
    
    public void applyTransformationMatrix(Matrix tmat) throws Exception {
        Matrix seed = new Matrix(new Matrix.Dimension(3, 1));
        seed.set(0, 0, this.x * 1.0);
        seed.set(1, 0, this.y * 1.0);
        seed.set(2, 0, 1.0);
        if(tmat != null && tmat.getDim().isMultiplicationCompatible(seed.getDim())) {
            seed = tmat.mul(seed);
            this.tx = (int) seed.get(0, 0).doubleValue();
            this.ty = (int) seed.get(1, 0).doubleValue();
        } else {
            this.tx = this.x;
            this.ty = this.y;
        }
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public int getX() { return this.x; }
    public int getY() { return this.y; }
    
    public int getTX() {
        return tx;
    }
    
    public int getTY() {
        return ty;
    }
}
