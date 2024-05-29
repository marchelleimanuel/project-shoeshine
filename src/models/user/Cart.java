package models.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

public class Cart {
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
	private Label title_label, payment_method_label, total_price_label, total_price;
	
	// combobox
	private ComboBox<String> payment_method_cb;	
	
	// tableview
	private TableView<Product> table;
	
	// vector
	private Vector<Product> products;
	
	// button
	private Button checkout_btn, delete_btn;
	
	// variables
	private int totalPrice = 0;
	private String payment_method = null;
	
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
		title_label = new Label("Customer Cart");
		payment_method_label = new Label("Payment Method");
		total_price_label = new Label("Total Price");
		total_price = new Label();
		
		// combobox
		payment_method_cb = new ComboBox<>();
		
		// tableview
		table = new TableView<>();
		
		// vector
		products = new Vector<>();
		
		// button
		checkout_btn = new Button("Checkout");
		delete_btn = new Button("Delete");
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
		product_name.setMinWidth(180);

		TableColumn<Product, Integer> product_price = new TableColumn<>("Product Price");
		product_price.setCellValueFactory(new PropertyValueFactory<>("product_price"));
		product_price.setMinWidth(180);
		  
		TableColumn<Product, Integer> quantity = new TableColumn<>("Quantity");
		quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		quantity.setMinWidth(180);

		TableColumn<Product, Integer> total = new TableColumn<>("Total");
		total.setCellValueFactory(new PropertyValueFactory<>("total_price"));
		total.setMinWidth(180); 
		
		table.getColumns().addAll(product_name, product_price, quantity, total);
		
		refreshTable();
		
	}
	
	private void set_form() {
		vb.getChildren().addAll(title_label, table, gp, delete_btn,checkout_btn);
		
		gp.add(payment_method_label, 0,0);
		gp.add(payment_method_cb, 1,0);
		gp.add(total_price_label, 0,1);
		gp.add(total_price, 1,1);
	}
	
	private void set_alignment() {
		vb.setPadding(new Insets(20, 40, 40, 40));
		
		vb.setAlignment(Pos.CENTER);
		
		gp.setVgap(10);
		
		checkout_btn.setPadding(new Insets(10, 50, 10, 50));
		delete_btn.setPadding(new Insets(10, 60, 10, 60));
	}
	
	private void set_combobox() {
		payment_method_cb.getItems().addAll("Debit", "Credit", "E-Banking", "Cash");
	}
	
	private void set_prompttext() {
		payment_method_cb.setPromptText("Choose one");		
	}
	
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		
		title_label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 32));
		payment_method_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		total_price_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		total_price.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		
		title_label.setTextFill(Color.web("#000080"));
		payment_method_label.setTextFill(Color.web("#000080"));
		total_price_label.setTextFill(Color.web("#000080"));
		total_price.setTextFill(Color.web("#000080"));
		
		checkout_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		delete_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		checkout_btn.setTextFill(Color.web("#000080"));
		delete_btn.setTextFill(Color.web("#000080"));
		
		payment_method_label.setMinWidth(150);
		
		payment_method_cb.setMinWidth(150);
		
	}
	
	private void event_handler() {
		
		if(!table.getItems().isEmpty()) {
			payment_method_cb.setDisable(false);
		}
		else {
			payment_method_cb.setDisable(true);
		}
		
		checkout_btn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if(table.getItems().isEmpty()) {
				alert.setHeaderText("Error");
				alert.setContentText("Your cart is empty!");	
				alert.showAndWait();
			}
			else if(payment_method_cb.getValue() == null) {
				alert = new Alert(AlertType.ERROR);
				alert.setContentText("Choose payment method!");
				alert.showAndWait();
			}
			else {
				
				String username = Login.username;
				String paymentMethod = payment_method;
				LocalDate currDate = LocalDate.now();
		        String formattedDate = currDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        String transactionId = getTransactionId();
				
				addDataTransactionHeader(transactionId, username, formattedDate, paymentMethod);	
		        addMultipleItemsToTransactionDetails(username, transactionId, products);
		        clearTableProducts();
		        
		        if(table.getItems().size() < 1) {
					payment_method_cb.setDisable(true);
					total_price.setText(" ");
				}
		        
				alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Checkout success!");
				alert.showAndWait();
				
				
				
			}
		});
		
		delete_btn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if(table.getItems().isEmpty()) {
				alert.setHeaderText("Error");
				alert.setContentText("Your cart is empty!");	
				alert.showAndWait();
			}
			else if(table.getSelectionModel().isEmpty()) {
				alert.setContentText("Please select item you want to delete!");
				alert.showAndWait();
			}
			else {
				Product selectedCart = table.getSelectionModel().getSelectedItem();
				String username = Login.username; 
				String query = "DELETE FROM cart WHERE UserID = (SELECT UserID FROM msuser WHERE Username = ?) AND ProductID IN (SELECT ProductID FROM msproduct WHERE ProductName = ?) AND Quantity = ?";
				PreparedStatement ps = connect.prepareStatement(query);
				try {
					ps.setString(1, username);
		            ps.setString(2, selectedCart.getProduct_name());
		            ps.setInt(3, selectedCart.getQuantity());
		            ps.execute();
		            
		            table.getItems().remove(selectedCart);
				} catch (Exception e2) {
					e2.printStackTrace();
				}				
				
				totalPrice = 0;
				refreshTable();
				
//				String newPrice = Integer.toString(courierPrice);
//				courier_price.setText(newPrice);
//				
//				int currTotalPrice = totalPrice;
//				currTotalPrice += courierPrice;
//				String newTotalPrice = Integer.toString(currTotalPrice);
//				total_price.setText(newTotalPrice);
				
				if(table.getItems().size() < 1) {
					payment_method_cb.setDisable(true);
					total_price.setText(" ");
				}
					
				alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Item deleted successfully!");
				alert.showAndWait();
			}
		});
		
		payment_method_cb.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if(observable.getValue() == "Debit") {
		    	payment_method = "Debit";
		    }
		    else if(observable.getValue() == "Credit") {
		    	payment_method = "Credit";
		    }
		    else if(observable.getValue() == "E-Banking") {
		    	payment_method = "E-Banking";
		    }
		    else if(observable.getValue() == "Cash") {
		    	payment_method = "Cash";
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
	
	public String getTransactionId() {
	    int transaction = countTransaction();
	    int nextTransaction = transaction + 1;
	    String ID = String.format("%03d", nextTransaction);
	    String transactionId = "TR" + ID;
	    
	    return transactionId;
	    
	}
	
	

	public int countTransaction() {
	    String query = "SELECT COUNT(*) FROM transactionheader";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        if (resultSet.next()) {
	            return resultSet.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 0; 
	}
	
	public void getData() {
	    products.removeAllElements();

	    String username = Login.username;
	    
	    String query = "SELECT ProductName, ProductPrice, Quantity, mc.ProductPrice * c.Quantity AS Total " +
	    			   "FROM cart c " +
	    			   "JOIN msproduct mc ON c.ProductID = mc.ProductID " +
	    			   "JOIN msuser mu ON c.UserID = mu.UserID " +
	    			   "WHERE mu.Username = ? ";

	    PreparedStatement ps = connect.prepareStatement(query);
	    try {
	    	ps.setString(1, username);
	        connect.rs = ps.executeQuery(); 
	        
	        while (connect.rs.next()) {
	            String productName = connect.rs.getString("ProductName");
	            int productPrice = connect.rs.getInt("ProductPrice");
	            int quantity = connect.rs.getInt("Quantity");
	            int total = connect.rs.getInt("Total");
	            totalPrice += total;
	            products.add(new Product(productName, productPrice, quantity, total));

	        }
	        String new_total_price = Integer.toString(totalPrice);
	        total_price.setText(new_total_price);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void addDataTransactionHeader(String transactionId, String pengguna, String currDate, String paymentMethod) {
		String transactionID = transactionId;
	    String username = pengguna;
	    String transactionDate = currDate;
	    String payment_method = paymentMethod;

	    String getUserID = "SELECT userID FROM msuser WHERE Username = ?";
	    
	    
	    try {
	        PreparedStatement getUserIDStatement = connect.getCon().prepareStatement(getUserID);
	        getUserIDStatement.setString(1, username);
	        ResultSet userResult = getUserIDStatement.executeQuery();

	        String userID = null;

	        if (userResult.next()) {
	            userID = userResult.getString("userID");
	        }
	        
	        String query = "INSERT INTO transactionheader (TransactionID, UserID, TransactionDate, PaymentMethod) " +
	                "VALUES (?, ?, ?, ?)";
	        
		     PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
		     preparedStatement.setString(1, transactionID);
		     preparedStatement.setString(2, userID);
		     preparedStatement.setString(3, transactionDate);
		     preparedStatement.setString(4, payment_method);
	
		     preparedStatement.executeUpdate();
		 } catch (SQLException e) {
		     e.printStackTrace();
		 }    
	}
	
	public void addMultipleItemsToTransactionDetails(String username, String transactionDetailsID, Vector<Product> items) {
        for (Product item : products) {
            String productName = item.getProduct_name();
            String getProductID = "SELECT ProductID FROM msproduct WHERE ProductName = ?";
            String getQuantity = "SELECT Quantity FROM cart c " +
                    "JOIN msuser mu ON c.UserID = mu.UserID " +
                    "WHERE ProductID = ? AND Username = ?";
            try {
                PreparedStatement getProductIDStatement = connect.getCon().prepareStatement(getProductID);
                getProductIDStatement.setString(1, productName);
                ResultSet productIDResult = getProductIDStatement.executeQuery();

                String productID = null;

                if (productIDResult.next()) {
                	productID = productIDResult.getString("ProductID");
                }

                PreparedStatement getQuantityStatement = connect.getCon().prepareStatement(getQuantity);
                getQuantityStatement.setString(1, productID);
                getQuantityStatement.setString(2, username);
                ResultSet quantityResult = getQuantityStatement.executeQuery();

                int quantity = 0;
                if (quantityResult.next()) {
                    quantity = quantityResult.getInt("Quantity");
                }

                String query2 = "INSERT INTO transactiondetail " +
                        "(TransactionID, ProductID, Quantity) " +
                        "VALUES (?, ?, ?)";
                
                PreparedStatement preparedStatement = connect.getCon().prepareStatement(query2);
                preparedStatement.setString(1, transactionDetailsID);
                preparedStatement.setString(2, productID);
                preparedStatement.setInt(3, quantity);
                preparedStatement.addBatch();
                preparedStatement.executeBatch();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void clearTableProducts() {
		table.getItems().clear();
		
		String username = Login.username;
		String getUserID = "SELECT UserID FROM msuser WHERE Username = ?";
		
		String query = "DELETE FROM cart WHERE UserID = ?";
		
		try {
			PreparedStatement preparedStatement = connect.getCon().prepareStatement(getUserID);
			preparedStatement.setString(1, username);
			ResultSet userIDResult = preparedStatement.executeQuery();
			
			String userId = null;
			
			if(userIDResult.next()) {
				userId = userIDResult.getString("UserID");
			}
			
			preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, userId);
			preparedStatement.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void refreshTable() {
		getData(); 
		ObservableList<Product> regObs = FXCollections.observableArrayList(products);
		table.setItems(regObs);		
	}
	
	
	public Cart(Stage s) {
		initialize();
		set_borderpane();
		set_menu_bar();
		set_table_view();
		set_form();
		set_alignment();
		set_combobox();
		set_prompttext();
		set_style();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
		this.s.setResizable(false);
	}
	
	
	
	

}
