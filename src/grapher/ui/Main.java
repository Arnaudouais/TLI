package grapher.ui;

import java.util.Iterator;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.SplitPane;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;

import grapher.fc.*;



public class Main extends Application {
	public void start(Stage stage) {
		BorderPane root = new BorderPane();
		SplitPane split = new SplitPane();
		GrapherCanvas canvas = new GrapherCanvas(getParameters());
		
		ListView<FunctionInfos> listeviou = new ListView<>();
		listeviou.setItems(canvas.fctInfo);
		listeviou.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		//SelectionModel<FunctionInfos> select = listeviou.getSelectionModel();
		//listeviou.setOnMouseClicked(new ListHandler(canvas));
		listeviou.getSelectionModel().getSelectedItems().addListener(new InvalidationListener(){
			public void invalidated(Observable obs){
				ObservableList<FunctionInfos> allItems = listeviou.getItems();
				ObservableList<FunctionInfos> selectedItems = listeviou.getSelectionModel().getSelectedItems();
				for(FunctionInfos fi : allItems){
					fi.setSelected(false);
				}
				for(FunctionInfos fi : selectedItems){
					fi.setSelected(true);
				}
				canvas.redraw();
			}
		});

		
		split.getItems().addAll(listeviou,canvas);
		split.setDividerPositions(0.25);
		root.setCenter(split);
		
		stage.setTitle("grapher");
		stage.setScene(new Scene(root));
		stage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}