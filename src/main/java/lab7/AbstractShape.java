package lab7;

import java.awt.Color;
import java.awt.Point;

public abstract class AbstractShape implements Shape,Moveable,Resize{
    
    private Point position;
    private Color color;
    private Color fillcolor;
    private Point draggingPoint;
    
    
    public AbstractShape(Point position) {
        this.position = position;
    }
     @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

   @Override
    public void setColor(java.awt.Color c) {
        this.color =c;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(java.awt.Color c) {
        this.fillcolor = c;
    }

    @Override
    public Color getFillColor() {
        return fillcolor;
    }  
    
    
    
    @Override
    public Point getDraggingPoint() {
        return draggingPoint;
    }

    @Override
    public void setDraggingPoint(Point draggingPoint) {
        this.draggingPoint = draggingPoint;
    }

    
    
}
