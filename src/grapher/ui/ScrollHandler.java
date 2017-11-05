package grapher.ui;

import java.awt.Event;

import javafx.event.EventHandler;
import javafx.scene.input.ScrollEvent;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;



public class ScrollHandler implements EventHandler<ScrollEvent> {

    GrapherCanvas canvas;
    
    public static final int D_DRAG = 5;


    public ScrollHandler(GrapherCanvas c){
        this.canvas = c;
    }


    @Override
    public void handle(ScrollEvent event) {
    	if(event.getDeltaY()>0){
    		canvas.zoom(new Point2D(event.getX(),event.getY()), D_DRAG);
    	}
    	else{
    		canvas.zoom(new Point2D(event.getX(),event.getY()), -D_DRAG);
    	}
    }
}
    
