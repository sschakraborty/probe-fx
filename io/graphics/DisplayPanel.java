package io.graphics;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import io.math.Matrix;

public class DisplayPanel extends JPanel {
    private ArrayList<Point2D> pointList = new ArrayList<>(250);
    private Matrix transformation = new Matrix(new Matrix.Dimension(3, 3));
    
    private double scaleFactorX = 1.12;
    private double scaleFactorY = 1.12;

    public DisplayPanel() {
        setSize(700, 500);
        
        for(int i = 0; i < transformation.getDim().getRows(); i++) {
            for(int j = 0; j < transformation.getDim().getColumns(); j++) {
                transformation.set(i, j, (i == j) ? 1.0 : 0.0);
            }
        }
        
        for(int i = 0; i < 250; i++) {
            pointList.add(new Point2D((int) (Math.random() * 200), (int) (Math.random() * 200)));
        }
        
        addMouseListener(new MouseListener() {
            int sx, sy;
            
            @Override
            public void mousePressed(MouseEvent evt) {
                sx = evt.getX();
                sy = evt.getY();
            }
            
            @Override
            public void mouseReleased(MouseEvent evt) {
                sx = evt.getX() - sx;
                sy = evt.getY() - sy;
                
                try {
                    translate(sx, sy);
                    repaint();
                } catch(Exception e) {
                    System.err.println(e.getMessage());
                }
            }
            
            @Override
            public void mouseExited(MouseEvent evt) {
            }
            
            @Override
            public void mouseEntered(MouseEvent evt) {
            }
            
            @Override
            public void mouseClicked(MouseEvent evt) {
            }
        });
        
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent evt) {
                try {
                    if(evt.getWheelRotation() > 0) {
                        scaleDown(evt.getX(), evt.getY());
                    } else {
                        scaleUp(evt.getX(), evt.getY());
                    }
                    repaint();
                } catch(Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });
    }
    
    public void translate(int dx, int dy) throws Exception {
        Matrix trMat = new Matrix(new Matrix.Dimension(3, 3));
        
        trMat.set(0, 0, 1.0); trMat.set(0, 1, 0.0); trMat.set(0, 2, dx * 1.0);
        trMat.set(1, 0, 0.0); trMat.set(1, 1, 1.0); trMat.set(1, 2, dy * 1.0);
        trMat.set(2, 0, 0.0); trMat.set(2, 1, 0.0); trMat.set(2, 2, 1.0);
        
        transformation = trMat.mul(transformation);
        
        for(Point2D p : pointList) {
            p.applyTransformationMatrix(transformation);
        }
    }
    
    public void scaleUp(int x, int y) throws Exception {
        Matrix trMat = new Matrix(new Matrix.Dimension(3, 3));
        
        trMat.set(0, 0, scaleFactorX); trMat.set(0, 1, 0.0); trMat.set(0, 2, - x * scaleFactorX + x);
        trMat.set(1, 0, 0.0); trMat.set(1, 1, scaleFactorY); trMat.set(1, 2, - y * scaleFactorY + y);
        trMat.set(2, 0, 0.0); trMat.set(2, 1, 0.0); trMat.set(2, 2, 1.0);
        
        transformation = trMat.mul(transformation);
        
        for(Point2D p : pointList) {
            p.applyTransformationMatrix(transformation);
        }
    }
    
    public void scaleDown(int x, int y) throws Exception {
        Matrix trMat = new Matrix(new Matrix.Dimension(3, 3));
        
        trMat.set(0, 0, 1 / scaleFactorX); trMat.set(0, 1, 0.0); trMat.set(0, 2, - x * (1 / scaleFactorX) + x);
        trMat.set(1, 0, 0.0); trMat.set(1, 1, 1 / scaleFactorY); trMat.set(1, 2, - y * (1 / scaleFactorY) + y);
        trMat.set(2, 0, 0.0); trMat.set(2, 1, 0.0); trMat.set(2, 2, 1.0);
        
        transformation = trMat.mul(transformation);
        
        for(Point2D p : pointList) {
            p.applyTransformationMatrix(transformation);
        }
    }
    
    public void paint(Graphics graphics) {
        graphics.clearRect(0, 0, getWidth(), getHeight());
        graphics.drawLine(getWidth() >> 1, 0, getWidth() >> 1, getHeight());
        graphics.drawLine(0, getHeight() >> 1, getWidth(), getHeight() >> 1);
        
        for(Point2D p : pointList) {
            graphics.drawOval(p.getTX(), p.getTY(), 2, 2);
        }
    }
}
