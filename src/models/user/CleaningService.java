package models.user;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Login;

public class CleaningService {
	
	private Scene sc;
	private Stage s;
	
	private BorderPane bp;
	private VBox vb1, vb2;
	private GridPane gp;
	
	private MenuBar menu_bar;
	private Menu menu;
	private MenuItem online_cleaning_service, cleaning_product, cart, log_out;
	
	private Label title_label, username_label, shoe_type_label, cleaning_service_type_label, 
				  notes_label, shoe_type_price_label, 
				  cleaning_service_type_price_label,
				  shoe_type_price, cleaning_service_type_price;
	
	private TextField username_tf;
	private TextArea notes_ta;
	private ComboBox<String> shoe_type_cb, cleaning_service_type_cb;
	
	private Button next_btn;
	
	public static String shoe_type = null;
	public static String cleaning_type;
	
	public static int shoe_type_prices = 0;
	public static int cleaning_service_type_prices = 0;
	public static int courierPrice = 0;
	
	public static String courier = null;
	public static int total_price = 0;
	public static String notes;
	
	public void initialize() {
		// container
		bp = new BorderPane();
		vb1 = new VBox(10);
		vb2 = new VBox(20);
		gp = new GridPane();
		
		sc = new Scene(bp,800, 700);
		
		// menubar
		menu_bar = new MenuBar();
		menu = new Menu("Menu");
		online_cleaning_service = new MenuItem("Online Cleaning Service");
		cleaning_product = new MenuItem("Cleaning Product");
		cart = new MenuItem("Cart");
		log_out = new MenuItem("Log Out");
		
		// label
		title_label = new Label("Cleaning Service");
		username_label = new Label("Username");
		notes_label = new Label("Notes");
		
		shoe_type_label = new Label("Shoe Type");
		shoe_type_price_label = new Label("Price");
		shoe_type_price = new Label();
		
		cleaning_service_type_label = new Label("Cleaning Type");
		cleaning_service_type_price_label = new Label("Price");
		cleaning_service_type_price = new Label();
		
		
		// textfield
		username_tf = new TextField();
		
		// textarea
		notes_ta = new TextArea();
		
		// combobox
		shoe_type_cb = new ComboBox<>();
		cleaning_service_type_cb = new ComboBox<>();
		
		// button
		next_btn = new Button("Next");
	}
	
	private void set_borderpane() {
		bp.setTop(menu_bar);
		bp.setCenter(vb1);
	}
	
	private void set_menu_bar() {
		menu_bar.getMenus().add(menu);
		menu.getItems().addAll(online_cleaning_service, cleaning_product, cart, log_out);
	}
	
	private void set_prompttext() {
		username_tf.setPromptText("Input your username here");
		notes_ta.setPromptText("Specific details related to the cleaning service ");		
		shoe_type_cb.setPromptText("Choose one");
		cleaning_service_type_cb.setPromptText("Choose one");
		
	}
	
	private void set_form() {
		vb1.getChildren().addAll(title_label, vb2, next_btn);
		vb2.getChildren().addAll(username_label, username_tf, gp,notes_label, notes_ta);
		
		gp.add(shoe_type_label, 0, 0);
		gp.add(shoe_type_price_label, 1, 0);
		gp.add(shoe_type_cb, 0, 1);
		gp.add(shoe_type_price, 1, 1);
		
		gp.add(cleaning_service_type_label, 0, 3);
		gp.add(cleaning_service_type_price_label, 1, 3);
		gp.add(cleaning_service_type_cb, 0, 4);
		gp.add(cleaning_service_type_price, 1, 4);
		
		
	}
	
	private void set_alignment() {
		VBox.setMargin(title_label, new Insets(0, 0, 10, 0));
		VBox.setMargin(username_label, new Insets(0, 0 , -15, 0));
		VBox.setMargin(notes_label, new Insets(0, 0 , -15, 0));
		VBox.setMargin(next_btn, new Insets(0,0,0,465));
		
		GridPane.setMargin(shoe_type_label, new Insets(0, 0 , -10, 0));
		GridPane.setMargin(cleaning_service_type_label, new Insets(-10, 0 , -10, 0));
		
		GridPane.setMargin(shoe_type_price_label, new Insets(0, 0 , -10, 0));
		GridPane.setMargin(cleaning_service_type_price_label, new Insets(-10, 0 , -10, 0));
		
		vb1.setAlignment(Pos.CENTER);
		
		vb1.setPadding(new Insets(20,100,60,100));
		
		next_btn.setPadding(new Insets(10, 50, 10, 50));
		
		gp.setVgap(15);
		gp.setHgap(40);
		
	}
	
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		title_label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 32));
		
		username_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		shoe_type_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		shoe_type_price_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		shoe_type_price.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		cleaning_service_type_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		cleaning_service_type_price_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		cleaning_service_type_price.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));
		notes_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 16));		
		
		title_label.setTextFill(Color.web("#000080"));
		username_label.setTextFill(Color.web("#000080"));
		shoe_type_label.setTextFill(Color.web("#000080"));
		shoe_type_price_label.setTextFill(Color.web("#000080"));
		shoe_type_price.setTextFill(Color.web("#000080"));
		cleaning_service_type_label.setTextFill(Color.web("#000080"));
		cleaning_service_type_price_label.setTextFill(Color.web("#000080"));
		cleaning_service_type_price.setTextFill(Color.web("#000080"));
		notes_label.setTextFill(Color.web("#000080"));
		next_btn.setTextFill(Color.web("#000080"));
		
		next_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		
		shoe_type_cb.setMinWidth(150);
		cleaning_service_type_cb.setMinWidth(150);
		
		notes_ta.setWrapText(true);
	}
	
	private void set_combobox() {
		shoe_type_cb.getItems().addAll("Running Shoes", "Casual Shoes", "Formal Shoes", "Sneakers", "Boots");
		cleaning_service_type_cb.getItems().addAll("Standard Cleaning", "Deep Cleaning", "Premium Cleaning", "Express Cleaning", "Custom Cleaning");
	}
	
	private void event_handler() {
		CleaningService.total_price = 0;
		shoe_type_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
			shoe_type_prices = 0;
		    if(observable.getValue().equals("Running Shoes")) {
		    	shoe_type = "Running Shoes";
		    	shoe_type_prices = 10000; 
		    }
		    else if(observable.getValue().equals("Casual Shoes")) {
		    	shoe_type = "Casual Shoes";
		    	shoe_type_prices = 15000;
		    }
		    else if(observable.getValue().equals("Formal Shoes")) {
		    	shoe_type = "Formal Shoes";
		    	shoe_type_prices = 20000;
		    }
		    else if(observable.getValue().equals("Sneakers")) {
		    	shoe_type = "Sneakers";
		    	shoe_type_prices = 25000;
		    }
		    else if(observable.getValue().equals("Boots")) {
		    	shoe_type = "Boots";
		    	shoe_type_prices = 30000;
		    }
		    
		    String newPrice = Integer.toString(shoe_type_prices);
		    shoe_type_price.setText(newPrice);
		    total_price += shoe_type_prices;
//		    System.out.println("Total : " + total_price);
		});
		
		cleaning_service_type_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
			cleaning_service_type_prices = 0;
		    if(observable.getValue().equals("Standard Cleaning")) {
		    	cleaning_type = "Standard Cleaning";
		    	cleaning_service_type_prices = 50000; 
		    }
		    else if(observable.getValue().equals("Deep Cleaning")) {
		    	cleaning_type = "Deep Cleaning";
		    	cleaning_service_type_prices = 75000;
		    }
		    else if(observable.getValue().equals("Premium Cleaning")) {
		    	cleaning_type = "Premium Cleaning";
		    	cleaning_service_type_prices = 100000;
		    }
		    else if(observable.getValue().equals("Express Cleaning")) {
		    	cleaning_type = "Express Cleaning";
		    	cleaning_service_type_prices = 65000;
		    }
		    else if(observable.getValue().equals("Custom Cleaning")) {
		    	cleaning_type = "Custom Cleaning";
		    	cleaning_service_type_prices = 120000;
		    }
		    String newPrice = Integer.toString(cleaning_service_type_prices);
		    cleaning_service_type_price.setText(newPrice);
		    total_price += cleaning_service_type_prices;
//		    System.out.println("Total : " + total_price);
		});
		
		next_btn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			
			String username = username_tf.getText();
			String shoe_type = shoe_type_cb.getValue();
			String cleaning_service_type = cleaning_service_type_cb.getValue();
			notes = notes_ta.getText();
			if(username.isEmpty()) {
				alert.setContentText("Please fill out your username");
				alert.showAndWait();
			}
			else if(shoe_type == null) {
				alert.setContentText("Please choose shoe type");
				alert.showAndWait();
			}
			else if(cleaning_service_type == null) {
				alert.setContentText("Please choose cleaning service type");
				alert.showAndWait();
			}
			else if(notes.isEmpty()) {
				alert.setContentText("Please fill out your notes");
				alert.showAndWait();
			}
			else {
				OrderSummary cp = new OrderSummary(s);				
			}
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
		
		log_out.setOnAction(e -> {
			Login log = new Login(s);
		});
		
	}
	
	public CleaningService(Stage s) {
		initialize();
		set_borderpane();
		set_menu_bar();
		set_prompttext();
		set_form();
		set_alignment();
		set_style();
		set_combobox();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.setResizable(false);
		this.s.show();
	}

}
