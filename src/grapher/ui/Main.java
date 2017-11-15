package grapher.ui;

import java.util.Iterator;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.Separator;
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
		listeviou.setCellFactory(TextFieldListCell.forListView(new StringConverter<FunctionInfos>(){

			@Override
			public String toString(FunctionInfos object) {
				return object.toString();
			}

			@Override
			public FunctionInfos fromString(String string) {
				return new FunctionInfos(FunctionFactory.createFunction(string),false);
			}
			
		}));
		listeviou.setEditable(true);
		listeviou.setItems(canvas.fctInfo);
		listeviou.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listeviou.getSelectionModel().getSelectedItems().addListener(new InvalidationListener(){
			public void invalidated(Observable obs){
				ObservableList<FunctionInfos> allItems = listeviou.getItems();
				ObservableList<FunctionInfos> selectedItems = listeviou.getSelectionModel().getSelectedItems();
				
				for(FunctionInfos fi : allItems){
					fi.setSelected(false);
				}
				for(FunctionInfos fi : listeviou.getSelectionModel().getSelectedItems()){
					assert fi != null;
					fi.setSelected(true);
				}
				canvas.redraw();
			}
		});

		Button plus = new Button("+");
		plus.setPrefSize(40,30);
		Button moins = new Button("-");
		moins.setPrefSize(40,30);
		ToolBar toolbar = new ToolBar(plus,moins);
		toolbar.setPadding(new Insets(5,0,5,5));
		
		BorderPane borderLeft = new BorderPane();
		borderLeft.setTop(listeviou);
		borderLeft.setBottom(toolbar);
		
		//FENETRE POP UP
		final Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.initOwner(stage);
        VBox dialogVBox = new VBox();
        BorderPane bp = new BorderPane();
        Separator sp = new Separator();
        HBox h1 = new HBox();
        HBox h2 = new HBox(7);
        
        
        Label l1 = new Label("Expression");
        l1.setStyle("-fx-font-size: 20;");
        VBox v1 = new VBox();
        v1.setAlignment(Pos.CENTER);
        v1.getChildren().add(l1);
        v1.setPadding(new Insets(0,0,0,20));
        bp.setLeft(v1);
        
        
        Label l2 = new Label("?"); // TODO new image
        l2.setStyle("-fx-font-size: 40;");
        VBox v2 = new VBox();
        v2.setAlignment(Pos.CENTER);
        v2.getChildren().add(l2);
        v2.setPadding(new Insets(0,30,0,0));
        bp.setRight(v2);
        
        
        bp.setPrefSize(70, 90);
        
        TextField tf = new TextField();
        tf.setPrefWidth(100);
        h1.setAlignment(Pos.CENTER);
        h1.setPrefSize(70, 70);
        h1.getChildren().addAll(new Label("Nouvelle Expression :  "),tf);
        
        Button bOk = new Button("OK");
        bOk.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				canvas.addFct(tf.getText());
				popup.hide();
			}
        	
        });
        Button bCancel = new Button ("Annuler");
        h2.setPadding(new Insets(10,10,10,10));
        bCancel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				popup.hide();
			}
        	
        });
        h2.setAlignment(Pos.CENTER_RIGHT);
        h2.getChildren().addAll(bOk,bCancel);
        dialogVBox.getChildren().addAll(bp,sp,h1,h2);
        Scene popupScene = new Scene(dialogVBox, 300, 200);
        popup.setScene(popupScene);
		
		moins.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				canvas.removeFct(listeviou.getSelectionModel().getSelectedItems());
			}
			
		});
		
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popup.show();
            }
        });
        
        // MENU
        MenuItem menu1 = new MenuItem("Ajouter ...");
        menu1.setOnAction(plus.getOnAction());
        menu1.setAccelerator(new KeyCodeCombination(KeyCode.N,KeyCombination.CONTROL_DOWN));
        MenuItem menu2 = new MenuItem("Supprimer les fonction sélectionnées");
        menu2.setAccelerator(new KeyCodeCombination(KeyCode.BACK_SPACE,KeyCombination.CONTROL_DOWN));
        menu2.setOnAction(moins.getOnAction());
        final Menu menu = new Menu("Expression");
        menu.getItems().addAll(menu1,menu2);
        MenuBar mb = new MenuBar();
        mb.getMenus().addAll(menu);
        
        split.getItems().addAll(borderLeft,canvas);
		split.setDividerPositions(0.25);
		root.setCenter(split);
		root.setTop(mb);
		
        
		stage.setTitle("grapher");
		stage.setScene(new Scene(root,800,500));
		stage.show();
		
        
	}
	public static void main(String[] args) {
		launch(args);
	}
}