package grapher.ui;

import java.awt.Event;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;



public class Handler implements EventHandler<MouseEvent> {

    GrapherCanvas canvas;

    Point2D p;

    public static final int D_DRAG = 5;

    enum State {IDLE,RIGHT_DRAG_OR_CLICK, SELECT_ZOOM, DRAG_OR_CLICK, WHEEL_ZOOM, DRAG}

    State state = State.IDLE;

    public Handler(GrapherCanvas c){
        this.canvas = c;
    }


    @Override
    public void handle(MouseEvent mouseEvent) {


        switch(state){

            case IDLE:
                    switch(mouseEvent.getEventType().getName()){
                        case "MOUSE_PRESSED":
                        	if(mouseEvent.isPrimaryButtonDown()){
                        		p = new Point2D(mouseEvent.getX(),mouseEvent.getY());
                        		state = State.DRAG_OR_CLICK;
                        	}
                        	else if(mouseEvent.isSecondaryButtonDown()){
                        		p = new Point2D(mouseEvent.getX(),mouseEvent.getY());
                        		state = State.RIGHT_DRAG_OR_CLICK;
                        	}
                        	break;
                        default:
                        	break;
                    }
                    break;
            case DRAG_OR_CLICK:
            	switch(mouseEvent.getEventType().getName()){
            	case "MOUSE_DRAGGED":
            		if(p.distance(new Point2D(mouseEvent.getX(),mouseEvent.getY()))>D_DRAG){
            			state = State.DRAG;
            			canvas.setCursor(Cursor.MOVE);
            		}
            		break;
            	case "MOUSE_RELEASED":
            		canvas.zoom(p, D_DRAG);
            		state = State.IDLE;
            		break;
            	}
            	break;
            case RIGHT_DRAG_OR_CLICK:
            	switch(mouseEvent.getEventType().getName()){
            	case "MOUSE_DRAGGED":
            		if(p.distance(new Point2D(mouseEvent.getX(),mouseEvent.getY()))>D_DRAG){
            			state = State.SELECT_ZOOM;
            		}
            		break;
            	case "MOUSE_RELEASED":
            		canvas.zoom(p, -D_DRAG);
            		state = State.IDLE;
            		break;
            	}
            	break;
            case DRAG:
            	switch(mouseEvent.getEventType().getName()){
            	case "MOUSE_DRAGGED":
            		canvas.translate(mouseEvent.getX() - p.getX(), mouseEvent.getY() - p.getY());
            		p = new Point2D(mouseEvent.getX(),mouseEvent.getY());
            		break;
            	case "MOUSE_RELEASED":
            		state = State.IDLE;
            		canvas.setCursor(Cursor.DEFAULT);
            		break;
            	}
            	break;
            case SELECT_ZOOM:
            	switch(mouseEvent.getEventType().getName()){
            	case "MOUSE_DRAGGED":
            		canvas.redraw();
            		canvas.getGraphicsContext2D().setLineDashes(10);
            		canvas.getGraphicsContext2D().strokeLine(p.getX(), p.getY(), mouseEvent.getX(), p.getY());
            		canvas.getGraphicsContext2D().strokeLine(p.getX(), p.getY(), p.getX(), mouseEvent.getY());
            		canvas.getGraphicsContext2D().strokeLine(mouseEvent.getX(), p.getY(), mouseEvent.getX(), mouseEvent.getY());
            		canvas.getGraphicsContext2D().strokeLine(p.getX(), mouseEvent.getY(), mouseEvent.getX(), mouseEvent.getY());
            		canvas.getGraphicsContext2D().setLineDashes(null);
            		break;
            	case "MOUSE_RELEASED":
            		canvas.zoom(p, new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            		state = State.IDLE;
            		break;
            	}
            	break;
            case WHEEL_ZOOM:
            	break;
        }
    }
}
    
