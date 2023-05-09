package lab7;

import java.awt.Graphics;
import java.awt.Point;


public interface Resize {
    
    public Rectangle[] getRio();
    public void setRio(Rectangle[] rio);
    public void deletedots(Graphics canvas);
    public void setDots ();
    public void drawdots(Graphics canvas);
    public void drawdotsWhileDragging(Graphics canvas,int k, Point currentCorPoint);

    
}
