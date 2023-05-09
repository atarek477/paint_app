package lab7;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class MyPanel extends JPanel implements DrawingEngine {

    private ArrayList<Shape> shapes;
    private Shape selectedShape;
    private javax.swing.JComboBox<String> combBox;
    private boolean mouseReleased;
    private int caseCorner = -1;
    private ArrayList<Rectangle> smallrectdots;
    private int currentCorner;
    private boolean canDrag;

    public MyPanel(javax.swing.JComboBox<String> combBox) {

        this.combBox = combBox;
        shapes = new ArrayList<>();

        ClickListner clickListner = new ClickListner();
        this.addMouseListener(clickListner);

        DragListner dragListner = new DragListner();
        this.addMouseMotionListener(dragListner);
        mouseReleased = false;
        selectedShape = null;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Shape s : shapes) {
            s.draw(g);
        }

    }

    @Override
    public void addShape(Shape shape) {

        shapes.add(shape);

    }

    public ArrayList<Shape> getter() {
        return shapes;
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public Shape[] getShapes() {
        return shapes.toArray(Shape[]::new);
    }

    @Override
    public void refresh(Graphics canvas) {
        this.repaint();
    }

    public void save(Path path) {
        try {
            JsonArray ja = new JsonArray();
            for (Shape s : shapes) {
                ja.add(s.tojsonObject());
            }
            String jsonText = Jsoner.serialize(ja);
            Files.write(path, jsonText.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            Logger.getLogger(MyPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class ClickListner extends MouseAdapter 
    {

        @Override
        public void mousePressed(MouseEvent evt)
        {

            int i = 0;
            int caseOF = 0;
            if (selectedShape != null) {
                if (checkIfSelected(evt.getPoint())) 
                {
                    mouseReleased = true;
                    canDrag = false;
                    ((Resize) selectedShape).deletedots(getGraphics()); 
                    ((Resize) selectedShape).drawdots(getGraphics());
                    caseOF = 1; 

                    selectedShape = null;
                    repaint();
                }

                if (caseOF == 0) 
                {
                    Rectangle t[] = ((Resize) selectedShape).getRio();

                    for (Shape s : shapes) {
                        if (((Moveable) s).contains(evt.getPoint())) {
                            if (selectedShape != s) {
                                ((Resize) selectedShape).deletedots(getGraphics()); 
                                ((Resize) selectedShape).drawdots(getGraphics());  
                               
                                repaint();
                                
                                ((Resize) s).setDots();
                                ((Resize) s).drawdots(getGraphics());
                                ((Moveable) s).setDraggingPoint(evt.getPoint());
                                combBox.setSelectedIndex(i);
                                selectedShape = s;
                                ((Resize) selectedShape).setDots();
                                ((Resize) selectedShape).deletedots(getGraphics());
                                ((Resize) selectedShape).drawdots(getGraphics());
                                caseOF = 1;
                                canDrag = true;
                                mouseReleased = false;
                            } else {
                                ((Moveable) s).setDraggingPoint(evt.getPoint());    
                                
                                ((Resize) selectedShape).setDots();
                                ((Resize) selectedShape).drawdots(getGraphics());
                                combBox.setSelectedIndex(i);
                            }

                        }
                        i++; // counter for combox

                    }

                    if (caseOF == 0) {
                        for (int j = 0; j < t.length; j++) {
                            if (t[j].contains(evt.getPoint())) {
                                caseOF = 1;
                                

                                t[j].setDraggingPoint(evt.getPoint());
                            
                                currentCorner = j;
                                caseCorner = 1;
                                mouseReleased = false;
                                canDrag = true;
                            }
                        }
                    }
                }

            } else {
                for (Shape s : shapes) {
                    if (((Moveable) s).contains(evt.getPoint())) {
                        
                        selectedShape = s;
                        ((Resize) selectedShape).setDots();
                        ((Resize) selectedShape).drawdots(getGraphics());

                        ((Moveable) s).setDraggingPoint(evt.getPoint());
                        combBox.setSelectedIndex(i);
                        mouseReleased = false;
                        canDrag = true;
                    }
                    i++;

                }
            }
        }

        private boolean checkIfSelected(Point mousePoint) {
            Rectangle t[] = ((Resize) selectedShape).getRio();
            for (Rectangle rectangle : t) {
                if (rectangle.contains(mousePoint)) {
                    return false;
                }
            }
            for (Shape s : shapes) {
                if (((Moveable) s).contains(mousePoint)) {
                    return false;
                }
            }
            return true; // kda ana selected wla hga
        }

        @Override
        public void mouseReleased(MouseEvent evt) {

            if (mouseReleased == true) {
                
                caseCorner = 0;
                selectedShape = null;
            }

        }

    }

    private class DragListner extends MouseMotionAdapter // inner classe
    {

        @Override
        public void mouseDragged(MouseEvent evt) {
            if (selectedShape != null) {
                
                if (caseCorner == 1) {
                    Rectangle t[] = ((Resize) selectedShape).getRio();

                    ((Resize) selectedShape).drawdotsWhileDragging(getGraphics(), currentCorner, evt.getPoint());
                    repaint();
                    mouseReleased = true;
                } else {
                    ((Moveable) selectedShape).moveTo(evt.getPoint());
                    ((Moveable) selectedShape).setDraggingPoint(evt.getPoint());
                    repaint();
                    mouseReleased = true;
                }

            }
        }

    }
}
