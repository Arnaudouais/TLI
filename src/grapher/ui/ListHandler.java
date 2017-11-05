package grapher.ui;

import java.awt.Event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;



public class ListHandler implements EventHandler<MouseEvent> {

    GrapherCanvas canvas;

    public ListHandler(GrapherCanvas c){
        this.canvas = c;
    }


    @Override
    public void handle(MouseEvent event) {
    	canvas.redraw();
    	System.out.println("Caca");
    }
}
    
