package models.admin;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Login;
import models.Product;
import models.user.Cart;
import models.user.CleaningProduct;
import models.user.CleaningService;
import util.Connect;

public class CleaningProductManagement {
	
	private Connect connect = Connect.getInstance();

	private Stage s;
	private Scene sc;
	
	// container
	private BorderPane bp;
	private VBox vb;
	private GridPane gp;
	private HBox hb;
	
	// menubar
	private MenuBar menu_bar;
	private Menu menu;
	private MenuItem cleaning_product_management, log_out;
	
	// label
	private Label title_label, product_name_label,product_price_label;
	
	// textfield
	private TextField product_name_tf, product_price_tf;
	
	// tableview
	private TableView<Product> table;
	
	// vector
	private Vector<Product> products;
	
	// button
	private Button add_product_btn, update_product_btn, delete_product_btn;
	
	private void initialize() {
		bp = new BorderPane();
		vb = new VBox(20);
		gp = new GridPane();
		hb = new HBox(40);
		
		sc = new Scene(bp, 800, 700);
		
		// menubar
		menu_bar = new MenuBar();
		menu = new Menu("Menu");
		cleaning_product_management = new MenuItem("Cleaning Product Management");
		log_out = new MenuItem("Log Out");
		
		// label
		title_label = new Label("Cleaning Product Management");
		product_name_label = new Label("Product Name");
		product_price_label = new Label("Product Price");
		
		// textfield
		product_name_tf = new TextField();
		product_price_tf = new TextField();
		
		// tableview
		table = new TableView<>();
		
		// vector
		products = new Vector<>();
		
		// button
		add_product_btn = new Button("Add Product");
		update_product_btn = new Button("Update Product");
		delete_product_btn = new Button("Delete Product");
		
	}
	
	private void set_borderpane() {
		bp.setTop(menu_bar);
		bp.setCenter(vb);
	}
	
	private void set_menu_bar() {
		menu_bar.getMenus().add(menu);
		menu.getItems().addAll(cleaning_product_management, log_out);
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
		vb.getChildren().addAll(title_label, table, gp, hb);
		
		hb.getChildren().addAll(add_product_btn, update_product_btn, delete_product_btn);
		
		gp.add(product_name_label, 0,0);
		gp.add(product_name_tf, 1,0);
		gp.add(product_price_label, 0,1);
		gp.add(product_price_tf, 1,1);
		
	}
	
	private void set_alignment() {
		vb.setPadding(new Insets(20, 40, 40, 40));
		
		vb.setAlignment(Pos.CENTER);
		gp.setAlignment(Pos.CENTER);
		
		gp.setVgap(15);
		
		GridPane.setMargin(product_price_tf, new Insets(10,0,40,0));
		GridPane.setMargin(product_price_label, new Insets(10,0,40,0));
		VBox.setMargin(hb, new Insets(0,0,0,25));
		
		add_product_btn.setPadding(new Insets(10, 50, 10, 50));
		update_product_btn.setPadding(new Insets(10, 50, 10, 50));
		delete_product_btn.setPadding(new Insets(10, 50, 10, 50));
	}
	
	private void set_prompt_text() {
		product_name_tf.setPromptText("Input product name here");
		product_price_tf.setPromptText("Input product price here");
	}
	
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		
		title_label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 32));
		product_name_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		product_price_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 16));
		
		title_label.setTextFill(Color.web("#000080"));
		product_name_label.setTextFill(Color.web("#000080"));
		product_price_label.setTextFill(Color.web("#000080"));
		
		add_product_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		add_product_btn.setTextFill(Color.web("#000080"));

		update_product_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		update_product_btn.setTextFill(Color.web("#000080"));
		
		delete_product_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		delete_product_btn.setTextFill(Color.web("#000080"));
		
		product_name_label.setMinWidth(150);
		product_name_tf.setMinWidth(350);
		product_price_tf.setMinWidth(350);
		
	}
	
	private void event_handler() {
		
		delete_product_btn.setOnAction(e -> {
			if(table.getSelectionModel().isEmpty()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Product Management");
				alert.setContentText("Please select a product from the table to be deleted");	
				alert.showAndWait();
			}
			else {
				Product selectedProduct = table.getSelectionModel().getSelectedItem();
				String productName = selectedProduct.getProduct_name();
				String query = "DELETE FROM msproduct WHERE ProductName = ?";
				PreparedStatement ps = connect.prepareStatement(query);
				try {
					ps.setString(1, productName);
					ps.execute();
					
					table.getItems().remove(selectedProduct);
				} catch (Exception e2) {
					e2.printStackTrace();
				}				
				
				refreshTable();
				resetTextField();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Product Management");
				alert.setContentText("Product Successfully Deleted");	
				alert.showAndWait();
			}
			
		});
		
		add_product_btn.setOnAction(e -> {
		    Alert alert = new Alert(AlertType.ERROR);
		    alert.setHeaderText("Product Management");

		    String productID = getProductID();
		    String productName = product_name_tf.getText();
		    String productPriceText = product_price_tf.getText();
		    int productPrice = 0;

		    boolean isProductNameValid = false;
		    boolean isPriceValid = false;

		    if (productName.isEmpty()) {
		        alert.setContentText("Please fill out the product name");
		        alert.showAndWait();
		        isProductNameValid = false;
		    } 
		    else if (productPriceText.isEmpty()) {
		        alert.setContentText("Please enter the price");
		        alert.showAndWait();
		        isPriceValid = false;
		    }
		    else if (checkProductNameCredentials(productName)) {
		        alert.setContentText("Product already exists");
		        alert.showAndWait();
		        isProductNameValid = false;
		    }
		    else {
				try {
				    productPrice = Integer.parseInt(productPriceText);

				    if (productPrice < 5000 || productPrice > 1000000) {
				        alert.setContentText("Product price must be between 5000 and 1000000 inclusive");
					    alert.showAndWait();
					    isPriceValid = false;
					    return;
				    }
				    isPriceValid = true;
				    isProductNameValid = true;
			    } catch (NumberFormatException ex) {
			        alert.setContentText("Invalid price format. Please enter a valid integer.");
			        alert.showAndWait();
			        isPriceValid = false;
			        return;
			    }
		    }

		    if (isProductNameValid == true && isPriceValid == true) {
		        Alert success = new Alert(AlertType.INFORMATION);
		        success.setHeaderText("Product Management");
		        success.setContentText("Product Successfully Added");
		        success.showAndWait();
		        table.getItems().add(new Product(productName, productPrice));
		        addProduct(productID, productName, productPrice);
		        refreshTable();
		        resetTextField();
		    }
		});
		
		update_product_btn.setOnAction(e -> {
		    if (table.getSelectionModel().isEmpty()) {
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setHeaderText("Product Management");
		        alert.setContentText("Please select a product from the table to be updated");
		        alert.showAndWait();
		    } else {
		        String newPriceText = product_price_tf.getText();
		        if (newPriceText.isEmpty()) {
		            Alert alert = new Alert(AlertType.ERROR);
		            alert.setHeaderText("Product Management");
		            alert.setContentText("Please enter the new price");
		            alert.showAndWait();
		            return;
		        }

		        int newPrice;
		        try {
		            newPrice = Integer.parseInt(newPriceText);
		            if(newPrice < 5000 || newPrice > 1000000) {
		            	Alert alert = new Alert(AlertType.ERROR);
			            alert.setHeaderText("Product Management");
			            alert.setContentText("Product price must be 5000 - 1000000");
			            alert.showAndWait();
			            return;
		            }
		        } catch (NumberFormatException ex) {
		            Alert alert = new Alert(AlertType.ERROR);
		            alert.setHeaderText("Product Management");
		            alert.setContentText("Invalid price format. Please enter a valid integer.");
		            alert.showAndWait();
		            return; 
		        }

		        Product selectedProduct = table.getSelectionModel().getSelectedItem();
		        String productName = selectedProduct.getProduct_name();

		        selectedProduct.setProduct_price(newPrice);
		        table.refresh(); 

		        String query = "UPDATE msproduct SET ProductPrice = ? WHERE ProductName = ?";
		        PreparedStatement ps = connect.prepareStatement(query);
		        try {
		            ps.setInt(1, newPrice);
		            ps.setString(2, productName);
		            ps.executeUpdate();
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        
		        resetTextField();
		        
		        Alert success = new Alert(AlertType.INFORMATION);
		        success.setHeaderText("Product Management");
		        success.setContentText("Product Successfully Updated");
		        success.showAndWait();
		    }
		});
		
		table.setOnMouseClicked(e -> {
			TableSelectionModel<Product> tableSelectionModel = table.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
			Product product = tableSelectionModel.getSelectedItem();
			
			if(tableSelectionModel.getSelectedItem() == null) {
				Alert alert = new Alert(AlertType.ERROR);
		        alert.setHeaderText("Product Management");
		        alert.setContentText("Please select a product from the table to be updated");
		        alert.showAndWait();
			}
			else {
				product_name_tf.setText(product.getProduct_name());
				String cupPrice = Integer.toString(product.getProduct_price());
				product_price_tf.setText(cupPrice);				
			}
			
		});			
		
		cleaning_product_management.setOnAction(e -> {
			CleaningProductManagement admin = new CleaningProductManagement(s);
		});
		
		log_out.setOnAction(e -> {
			Login log = new Login(s);
		});
	}
	
	public void addProduct(String productID, String productName, int productPrice) {
		
		String productId = productID;
		
		String query = "INSERT INTO msproduct (ProductID, ProductName, ProductPrice) VALUES (?, ?, ?)";
		
		try {
			PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, productId);
			preparedStatement.setString(2, productName);
			preparedStatement.setInt(3, productPrice);
			preparedStatement.executeUpdate();

		} catch(SQLException e ) {
			e.printStackTrace();
		}
	}
	
	public boolean checkProductNameCredentials(String productName) {
	    try {
	        String query = "SELECT * FROM msproduct WHERE ProductName = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, productName);

	        ResultSet result = preparedStatement.executeQuery();

	        return result.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public int countProduct() {
	    String query = "SELECT COUNT(*) FROM msproduct";
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
	
	public String getProductID() {
	    String availableID = findAvailableProductID();
	    if (availableID != null) {
	        return availableID;
	    } else {
	        int product = countProduct();        
	        int nextProduct = product + 1;
	        return "PR" + String.format("%03d", nextProduct);
	    }
	}

	private String findAvailableProductID() {
	    String query = "SELECT ProductID FROM msproduct";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        String lastProductID = null;

	        while (resultSet.next()) {
	            String currentProductID = resultSet.getString("ProductID");

	            if (lastProductID != null) {
	                // Check if there is a gap between lastCupID and currentCupID
	                int lastNumber = Integer.parseInt(lastProductID.substring(2));
	                int currentNumber = Integer.parseInt(currentProductID.substring(2));

	                if (currentNumber - lastNumber > 1) {
	                    // Found a gap, return the next available ID
	                    return "CU" + String.format("%03d", lastNumber + 1);
	                }
	            }

	            lastProductID = currentProductID;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
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
	
	public void resetTextField() {
		product_name_tf.setText("");
		product_price_tf.setText("");
		
		table.getSelectionModel().clearSelection();
	}
	
	public CleaningProductManagement(Stage s) {
		initialize();
		set_borderpane();
		set_menu_bar();
		set_form();
		set_alignment();
		set_style();
		set_prompt_text();
		set_table_view();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
		this.s.setResizable(false);
	}
	
	
	
	

}
