package lab7;

import java.nio.file.Path;
import java.util.ArrayList;


public interface DrawingEngine {
public void addShape(Shape shape);
public void removeShape(Shape shape);
public Shape[] getShapes();
public void refresh(java.awt.Graphics canvas);
public void save(Path path) ;
public ArrayList<Shape> getter();

}
