package models.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Home {

	private Stage s;
	private Scene sc;
	
	// container
	private BorderPane bp;
	private VBox vb;
	
	// menubar
	private MenuBar menu_bar;
	private Menu menu;
	private MenuItem online_cleaning_service, cleaning_product, cart;
	
	
	// button
	private Button online_cleaning_service_btn, shoe_cleaning_products_btn;
	
	// image
	private Image image;
	private ImageView image_view;
	
	private void initialize() {
		
		bp = new BorderPane();
		vb = new VBox(30);
		
		sc = new Scene(bp, 800, 700);
		
		// menubar
		menu_bar = new MenuBar();
		menu = new Menu("Menu");
		online_cleaning_service = new MenuItem("Online Cleaning Service");
		cleaning_product = new MenuItem("Cleaning Product");
		cart = new MenuItem("Cart");
		
		// button
		online_cleaning_service_btn = new Button("Cleaning Service");
		shoe_cleaning_products_btn = new Button("Shoe Cleaning Products");
		
		// image
		image = new Image("file:\\Users\\marchelle\\Downloads\\kuliah\\Semester 3\\Shoeshine.png");
		image_view = new ImageView(image);
	}
	
	private void set_borderpane() {
//		bp.setTop(menu_bar);
		bp.setCenter(vb);
	}
	
//	private void set_menu_bar() {
//		menu_bar.getMenus().add(menu);
//		menu.getItems().addAll(online_cleaning_service, cleaning_product, cart);
//	}
	
	private void set_image() {
		image_view.setFitWidth(250);
		image_view.setFitHeight(250);
		image_view.setPreserveRatio(true);
	}
	
	private void set_vbox() {
		vb.getChildren().addAll(image_view, online_cleaning_service_btn, shoe_cleaning_products_btn);			
		
	}
	
	private void set_alignment() {
		vb.setAlignment(Pos.CENTER);
	}
	
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		
		online_cleaning_service_btn.setStyle("-fx-font-family: Verdana; -fx-font-weight: bold; -fx-font-size: 25;");
		shoe_cleaning_products_btn.setStyle("-fx-font-family: Verdana; -fx-font-weight: bold; -fx-font-size: 25;");
		
		online_cleaning_service_btn.setTextFill(Color.web("#000080"));
		shoe_cleaning_products_btn.setTextFill(Color.web("#000080"));
		
		online_cleaning_service_btn.setPadding(new Insets(35,65,35,65));
		shoe_cleaning_products_btn.setPadding(new Insets(35,15,35,15));
		
		online_cleaning_service_btn.setFocusTraversable(false);
		shoe_cleaning_products_btn.setFocusTraversable(false);
	}
	
	private void event_handler() {
		
		online_cleaning_service_btn.setOnAction(e -> {
			CleaningService ocs = new CleaningService(s);
		});
		
		shoe_cleaning_products_btn.setOnAction(e -> {
			CleaningProduct cp = new CleaningProduct(s);
		});
		
		online_cleaning_service.setOnAction(e -> {
			CleaningService ocs = new CleaningService(s);
		});
		
		cleaning_product.setOnAction(e -> {
			CleaningProduct cp = new CleaningProduct(s);
		});
		
		cart.setOnAction(e -> {
			Cart cart = new Cart(s);
		});
		
		
	}
	
	public Home(Stage s) {
		initialize();
		set_borderpane();
//		set_menu_bar();
		set_image();
		set_vbox();
		set_alignment();
		set_style();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
		this.s.setResizable(false);
	}

}
