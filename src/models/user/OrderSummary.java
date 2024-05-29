package models.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import models.Login;
import util.Connect;

public class OrderSummary {
	
	private Connect connect = Connect.getInstance();
	
	private Stage s;
	private Scene sc;
	
	// container
	private BorderPane bp;
	private VBox vb1,vb2;
	private GridPane gp;
	private HBox hb, hb1;
	
	// label
	private Label title_label, shoe_type_label, cleaning_type_label, total_label,
				  payment_method_label, notes_label;
	
	// textarea
	private TextArea notes_ta;
	
	// combobox
	private ComboBox<String> payment_method_cb;
	
	// button
	private Button back_btn, done_btn;
	
	
	private void initialize() {
		// container
		bp = new BorderPane();
		vb1 = new VBox(20);
		vb2 = new VBox(20);
		gp = new GridPane();
		hb = new HBox(30);
		hb1 = new HBox(325);
		
		sc = new Scene(bp,800, 700);
		
		// label
		title_label = new Label("Order Summary");
		shoe_type_label = new Label("Shoe Type\t\t: " + CleaningService.shoe_type_prices);
		cleaning_type_label = new Label("Cleaning Type\t: " + CleaningService.cleaning_service_type_prices);
		total_label = new Label("Total\t\t\t: " + CleaningService.total_price);
		CleaningService.total_price = 0;
		payment_method_label = new Label("Payment Method");
		notes_label = new Label("Notes");
		
		
		// combobox
		payment_method_cb = new ComboBox<>();
				
		// textarea
		notes_ta = new TextArea(CleaningService.notes);
		
		// button
		back_btn = new Button("Back");
		done_btn = new Button("Done");
		
			
	}
	
	private void set_borderpane() {
		bp.setCenter(vb1);
	}
	
	private void set_prompttext() {
		payment_method_cb.setPromptText("Choose Payment");
		
	}
	
	private void set_form() {
		vb1.getChildren().addAll(title_label, vb2, gp);
		vb2.getChildren().addAll(shoe_type_label, cleaning_type_label, total_label);
		
		hb.getChildren().addAll(payment_method_label, payment_method_cb);
		hb1.getChildren().addAll(back_btn, done_btn);
		
		gp.add(notes_label, 0, 0);
		gp.add(notes_ta, 0, 1);
		gp.add(hb, 0, 2);
		gp.add(hb1, 0, 3);
		
	}
	
	private void set_alignment() {
		vb1.setAlignment(Pos.CENTER);
		vb1.setPadding(new Insets(20,100,60,100));
		vb2.setPadding(new Insets(20));
		
		
		VBox.setMargin(title_label, new Insets(0, 0, 20,0));
		
		HBox.setMargin(payment_method_cb, new Insets(5,0,0,0));
		
		GridPane.setMargin(notes_ta, new Insets(-10,0,10,0));
		
		hb1.setPadding(new Insets(35,0,0,0));
		
		gp.setVgap(10);
		gp.setHgap(10);
		
		back_btn.setPadding(new Insets(10, 50, 10, 50));
		done_btn.setPadding(new Insets(10, 50, 10, 50));
		
		
	}
	 
	private void set_style() {
		bp.setStyle("-fx-background-color: #F5F5DC");
		title_label.setFont(Font.font("Verdana",FontWeight.EXTRA_BOLD, 32));
		
		shoe_type_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 28));
		cleaning_type_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 28));
		total_label.setFont(Font.font("Verdana",FontWeight.SEMI_BOLD, 28));
		payment_method_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 24));
		notes_label.setFont(Font.font("Varela", FontWeight.SEMI_BOLD, 14));
		
		vb2.setStyle("-fx-background-color: #FFFFFF");
		vb2.setBorder(new Border(new BorderStroke(Color.valueOf("Black"), BorderStrokeStyle.SOLID, null, null)));
		
		title_label.setTextFill(Color.web("#000080"));
		shoe_type_label.setTextFill(Color.web("#000080"));
		cleaning_type_label.setTextFill(Color.web("#000080"));
		total_label.setTextFill(Color.web("#000080"));
		payment_method_label.setTextFill(Color.web("#000080"));
		notes_label.setTextFill(Color.web("#000080"));
		back_btn.setTextFill(Color.web("#000080"));
		done_btn.setTextFill(Color.web("#000080"));
		
		back_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		done_btn.setStyle("-fx-font-family: Verdana; -fx-font-size: 14;");
		
		notes_ta.setMaxHeight(50);
		
		notes_ta.setWrapText(true);
		
		notes_ta.setEditable(false);
		notes_ta.setMouseTransparent(true);
		notes_ta.setFocusTraversable(false);	
	}
	
	private void set_combobox() {
		payment_method_cb.getItems().addAll("Debit", "Credit", "E-Banking", "Cash");
	}
	
	private void event_handler() {
		
		back_btn.setOnAction(e -> {
			CleaningService ocs = new CleaningService(s);
		});
		
		done_btn.setOnAction(e -> {
			String payment_methods = payment_method_cb.getValue();
			
			if(payment_methods == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Please choose payment method");
				alert.showAndWait();
			}
			else {
				String online_cleaning_service_id = getOnlineCleaningServiceId();
				String user_id = getUserId();
				String shoe_type_id = getShoeTypeId();
				String cleaning_type_id = getCleaningTypeId();
				String payment_method = payment_methods;
				String notes = CleaningService.notes;
				
				addToDB(online_cleaning_service_id, user_id, shoe_type_id, cleaning_type_id, payment_method, notes);
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Order Information!");
				alert.setContentText("Order success");
				alert.showAndWait();
				
				Home h = new Home(s);
			}
			
		});
		
	}
	
	private void addToDB(String online_cleaning_service_id, String user_id,String shoe_type_id, String cleaning_type_id, String payment_method, String notes) {
		String ocsId = online_cleaning_service_id;
		String userId = user_id;
		String shoeTypeId = shoe_type_id;
		String cleaningTypeId = cleaning_type_id;
		String paymentMethod = payment_method;
		String userNotes = notes;
		
		String query = "INSERT INTO onlinecleaningservice (OnlineCleaningServiceID, UserID, ShoeTypeID, CleaningTypeID, PaymentMethod, UserNotes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        
	     PreparedStatement preparedStatement;
		try {
			preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, ocsId);
			preparedStatement.setString(2, userId);
			preparedStatement.setString(3, shoeTypeId);
			preparedStatement.setString(4, cleaningTypeId);
			preparedStatement.setString(5, paymentMethod);
			preparedStatement.setString(6, userNotes);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getCleaningTypeId() {
		String cleaningType = CleaningService.cleaning_type;
		String query = "SELECT CleaningTypeID FROM cleaningtype WHERE CleaningTypeName = ?";
		String cleaningTypeId = null;
		
		try {
			PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, cleaningType);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				cleaningTypeId = result.getString("CleaningTypeID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cleaningTypeId;
		
	}
	
	public String getShoeTypeId() {
		String shoeType = CleaningService.shoe_type;
		String query = "SELECT ShoeTypeID FROM shoetype WHERE ShoeTypeName = ?";
		String shoeTypeId = null;
		
		try {
			PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, shoeType);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				shoeTypeId = result.getString("ShoeTypeID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return shoeTypeId;
		
	}
	
	public String getUserId() {
		String username = Login.username;
		String query = "SELECT UserID FROM msuser WHERE Username = ?";
		String userId = null;
		
		try {
			PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			
			while(result.next()) {
				userId = result.getString("UserID");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userId;
		
	}
	
	public String getOnlineCleaningServiceId() {
	    int transaction = countId();
	    int nextTransaction = transaction + 1;
	    String ID = String.format("%03d", nextTransaction);
	    String transactionId = "OS" + ID;
	    
	    return transactionId;
	    
	}
	
	public int countId() {
	    String query = "SELECT COUNT(*) FROM onlinecleaningservice";
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
	
	public OrderSummary(Stage s) {
		initialize();
		set_borderpane();
		set_prompttext();
		set_form();
		set_alignment();
		set_style();
		set_combobox();
		event_handler();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
		this.s.setResizable(false);
		
	}

}
