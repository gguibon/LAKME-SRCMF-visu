package cnrs.lattice.srcmf.view;

import java.awt.Dimension;
import java.awt.Toolkit;

import cnrs.lattice.srcmf.beans.MaltFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StartApp extends Application {
	public static final String DEFAULT_JQUERY_MIN_VERSION = "1.12.0";
	public static final String JQUERY_LOCATION = Browser.class.getResource("/html/js/jquery-1.12.0.min.js")
			.toExternalForm();
	final FileChooser fileChooser = new FileChooser();
	public static MaltFormat maltFormat = new MaltFormat();
	public static Stage primaryStage;
	public static Scene scene1;
	public static StackPane root1;
	@Override
	public void start(Stage stage) throws Exception {
		primaryStage = stage;
		Browser browser = new Browser("/html/srcmf-lakme-viewer.html");
		StackPane root = new StackPane();
        root.getChildren().add(browser);
        root1 = root;
        
        MenuBar menuBar = new MenuBar();
        
        // --- Menu File
        Menu menuFile = new Menu("File");
 
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
 
        // --- Menu View
        Menu menuView = new Menu("View");
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
		Scene scene = new Scene(root, width, height, Color.web("#666970"));
		//((VBox) scene.getRoot()).getChildren().addAll(menuBar);
		scene1 = scene;
		stage.setScene(scene);
		stage.setTitle("LAKME-SRCMF-visu");
		stage.show();

		primaryStage.setOnCloseRequest(e -> Platform.exit());
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}


