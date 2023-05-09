package lab7;

import java.awt.Point;

public interface Moveable {

    void setDraggingPoint(Point b);

    Point getDraggingPoint();

    boolean contains(Point b);

    void moveTo(Point b);

}
