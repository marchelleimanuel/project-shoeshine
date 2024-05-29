package models.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Login;
import models.Product;
import util.Connect;

public class CleaningProduct {
	
	private Connect connect = Connect.getInstance();

	private Stage s;
	private Scene sc;
	
	// container
	private BorderPane bp;
	private VBox vb;
	private GridPane gp;
	
	// menubar
	private MenuBar menu_bar;
	private Menu menu;
	private MenuItem online_cleaning_service, cleaning_product, cart, log_out;
	
	// label
	private Label title_label, product_name_label, product_name, quantity_label, total_price_label, total_price;
	
	// spinner
	private Spinner<Integer> quantity_spinner;
	
	// tableview
	private TableView<Product> table;
	
	// vector
	private Vector<Product> products;
	
	// button
	private Button add_to_cart_btn;
	
	private void initialize() {
		bp = new BorderPane();
		vb = new VBox(20);
		gp = new GridPane();
		
		sc = new Scene(bp, 800, 700);
		
		// menubar
		menu_bar = new MenuBar();
		menu = new Menu("Menu");
		online_cleaning_service = new MenuItem("Online Cleaning Service");
		cleaning_product = new MenuItem("Cleaning Product");
		cart = new MenuItem("Cart");
		log_out = new MenuItem("Log Out");
		
		// label
		title_label = new Label("Cleaning Product");
		product_name_label = new Label("Product Name");
		product_name = new Label();
		quantity_label = new Label("Quantity");
		total_price_label = new Label("Total Price");
		total_price = new Label();
		
		// spinner
		quantity_spinner = new Spinner<>(1, 100, 1);
		
		// tableview
		table = new TableView<>();
		
		// vector
		products = new Vector<>();
		
		// button
		add_to_cart_btn = new Button("Add To Cart");
	}
	
	private void set_borderpane() {
		bp.setTop(menu_bar);
		bp.setCenter(vb);
	}
	
	private void set_menu_bar() {
		menu_bar.getMenus().add(menu);
		menu.getItems().addAll(online_cleaning_service, cleaning_product, cart, log_out);
	}
	
	private void set_table_view() {
		TableColumn<Product, String> product_name = new TableColumn<>("Product Name");
		product_name.setCellValueFactory(new PropertyValueFactory<>("product_name"));
		product_name.setMinWidth((bp.getWidth()/2));

		TableColumn<Product, Integer> product_price = new TableColumn<>("Product Price");
		product_price.setCellValueFactory(new PropertyValueFactory<>("product_price"));
		product_price.setMinWidth((bp.getWidth()/2));
		
		table.getColumns().addAll(product_name, product_price);
		
		try {
			refreshTable();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void set_form() {
		vb.getChildren().addAll(title_label, table, gp, add_to_cart_btn);
		
		gp.add(product_name_label, 0,0);
		gp.add(product_name, 1,0);
		gp.add(quantity_label, 0,1);
		gp.add(quantity_spinner, 1,1);
		gp.add(total_price_label, 0, 2);
		gp.add(total_price, 1, 2);
	}
	
	private void set_alignment() {
		vb.setPadding(new Insets(20, 40, 40, 40));
		
		vb.setAlignment(Pos.CENTER);
		
		gp.setVgap(10);
		
		add_to_cart_btn.setPadding(new Insets(10, 50, 10, 50));
	}
	
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		
		title_label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 32));
		product_name_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		product_name.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		quantity_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		total_price_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		total_price.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		
		title_label.setTextFill(Color.web("#000080"));
		product_name_label.setTextFill(Color.web("#000080"));
		product_name.setTextFill(Color.web("#000080"));
		quantity_label.setTextFill(Color.web("#000080"));
		total_price_label.setTextFill(Color.web("#000080"));
		total_price.setTextFill(Color.web("#000080"));
		
		add_to_cart_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		add_to_cart_btn.setTextFill(Color.web("#000080"));
		
		product_name_label.setMinWidth(150);
		
	}
	
	private void event_handler() {
		add_to_cart_btn.setOnAction(e -> {
			if(table.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please select the item you want to add!");
				alert.showAndWait();
			}
			else {				
				TableSelectionModel<Product> tableSelectionModel = table.getSelectionModel();
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				Product product = tableSelectionModel.getSelectedItem();
				
				
				String name = product.getProduct_name();
				String username = Login.username;
				int price = product.getProduct_price();
				int quantity = quantity_spinner.getValue();
				int total = price * quantity;
				
//				UserHomePage.cupName = cup.getName();
//				UserHomePage.cupPrice = cup.getPrice();
//				UserHomePage.quantity = amount.getValue();
				
            	addData(username, name, quantity);
				
            	refreshTable();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Item added successfully!");
				alert.showAndWait();
				
				product_name.setText(" ");
				total_price.setText(" ");
				quantity_spinner.getValueFactory().setValue(1);
			}
		});
		
		table.setOnMouseClicked(e -> {
			quantity_spinner.getValueFactory().setValue(1);
			TableSelectionModel<Product> tableSelectionModel = table.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Product product = tableSelectionModel.getSelectedItem();
			if(product != null) {
				int price = product.getProduct_price() * quantity_spinner.getValue();
				String new_price = Integer.toString(price);
				product_name.setText(product.getProduct_name());
				total_price.setText(new_price);
			}
		});
		
		quantity_spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
		    Product product = table.getSelectionModel().getSelectedItem();
		    
		    if(product != null) {
		    	int price = product.getProduct_price() * newValue.intValue();
		    	String new_price = Integer.toString(price);
		    	total_price.setText(new_price);		    	
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
	
	public void addData(String pengguna, String product_name, int quantity) {
	    String username = pengguna;
	    String productName = product_name;

	    String getUserID = "SELECT userID FROM msuser WHERE Username = ?";
	    String getProductID = "SELECT productID FROM msproduct WHERE ProductName = ?";
	    
	    try {
	        PreparedStatement getUserIDStatement = connect.getCon().prepareStatement(getUserID);
	        getUserIDStatement.setString(1, username);
	        ResultSet userResult = getUserIDStatement.executeQuery();

	        PreparedStatement getProductIDStatement = connect.getCon().prepareStatement(getProductID);
	        getProductIDStatement.setString(1, productName);
	        ResultSet cupResult = getProductIDStatement.executeQuery();

	        String userID = null;
	        String productID = null;

	        if (userResult.next()) {
	            userID = userResult.getString("userID");
	        }

	        if (cupResult.next()) {
	        	productID = cupResult.getString("productID");
	        }
	        String checkCartItemQuery = "SELECT * FROM cart WHERE userID = ? AND productID = ?";
	        PreparedStatement checkCartItemStatement = connect.getCon().prepareStatement(checkCartItemQuery);
	        checkCartItemStatement.setString(1, userID);
	        checkCartItemStatement.setString(2, productID);
	        ResultSet cartResult = checkCartItemStatement.executeQuery();

	        if (cartResult.next()) {
	            int existingQuantity = cartResult.getInt("quantity");
	            int newQuantity = existingQuantity + quantity;
	            String updateCartItemQuery = "UPDATE cart SET quantity = ? WHERE userID = ? AND productID = ?";
	            PreparedStatement updateCartItemStatement = connect.getCon().prepareStatement(updateCartItemQuery);
	            updateCartItemStatement.setInt(1, newQuantity);
	            updateCartItemStatement.setString(2, userID);
	            updateCartItemStatement.setString(3, productID);
	            updateCartItemStatement.executeUpdate();
	        } else {
	            String insertCartItemQuery = "INSERT INTO cart (userID, productID, quantity) VALUES (?, ?, ?)";
	            PreparedStatement insertCartItemStatement = connect.getCon().prepareStatement(insertCartItemQuery);
	            insertCartItemStatement.setString(1, userID);
	            insertCartItemStatement.setString(2, productID);
	            insertCartItemStatement.setInt(3, quantity);
	            insertCartItemStatement.executeUpdate();
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void getData() {
		products.removeAllElements();
		
		String query = "SELECT * FROM msproduct";
		connect.rs = connect.execQuery(query);
		
		try {
			while(connect.rs.next()) {
				String name = connect.rs.getString("ProductName");
				int price = connect.rs.getInt("ProductPrice");
				products.add(new Product(name,price));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void refreshTable() {
		getData(); 
		ObservableList<Product> regObs = FXCollections.observableArrayList(products);
		table.setItems(regObs);		
	}
	
	public CleaningProduct(Stage s) {
		initialize();
		set_borderpane();
		set_menu_bar();
		set_form();
		set_alignment();
		set_style();
		set_table_view();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
		this.s.setResizable(false);
	}
	
	
	
	

}
