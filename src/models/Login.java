package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
import javafx.stage.Stage;
import models.admin.CleaningProductManagement;
import models.user.Home;
import util.Connect;

public class Login {
	
	private Connect connect = Connect.getInstance();
	
	private Scene sc;
	private Stage s;

	private BorderPane bp;
	private FlowPane fp;
	private GridPane gp;
	private VBox vbox;

	private Label titleLbl, usernameLbl, passwordLbl; 
	private Hyperlink registerLbl;
	private TextField usernameTf;
	private PasswordField passwordPf;
	private Button submitBtn;
	
	public static String username;

	public void init() {
		bp = new BorderPane();
		fp = new FlowPane();
		gp = new GridPane();
		vbox = new VBox(20);

		titleLbl = new Label("Login");

		usernameLbl = new Label("Username");
		usernameTf = new TextField();
		usernameTf.setPromptText("Input your username here");

		passwordLbl = new Label("Password");
		passwordPf = new PasswordField();
		passwordPf.setPromptText("Input your password here");

		submitBtn = new Button("Login");

		registerLbl = new Hyperlink("Dont have an account yet? Register here!");

		sc = new Scene(bp, 800, 700);

	}

	public void arrangeScene() {
		bp.setTop(titleLbl);
		bp.setCenter(vbox);
		vbox.getChildren().addAll(titleLbl, gp, submitBtn, registerLbl);
		vbox.setAlignment(Pos.CENTER);

		gp.add(usernameLbl, 0, 0);
		gp.add(passwordLbl, 0, 2);

		gp.add(usernameTf, 0, 1);
		gp.add(passwordPf, 0, 3);

	}

	public void setStyle() {
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(submitBtn, Pos.CENTER);

		BorderPane.setMargin(submitBtn, new Insets(40));
		bp.setPadding(new Insets(150));

		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);

		usernameLbl.setMinWidth(400);
		passwordLbl.setMinWidth(400);

		bp.setStyle("-fx-background-color: #F5F5DC;");
		titleLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: darkblue; -fx-font-size: 30;");
		usernameLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		passwordLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		submitBtn.setStyle("-fx-text-fill: darkblue;-fx-font-size: 18;");
		registerLbl.setStyle("-fx-text-fill: purple;");
		
//		submitBtn.setBorder(new Border(new BorderStroke(Color.valueOf("Black"), BorderStrokeStyle.SOLID, null, null)));
	}

	public void setEventListeners() {
		submitBtn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if(usernameTf.getText().equals("")) {
				alert.setContentText("Please fill out your username");
				alert.showAndWait();
			}
			else if(passwordPf.getText().equals("")) {
				alert.setContentText("Please fill out your password");
				alert.showAndWait();
			}
			else {
				boolean validCredentials = checkCredentials(usernameTf.getText(), passwordPf.getText());
	            if (validCredentials) {
	            	// admin atau user
	            	String role = checkRole(usernameTf.getText());
	            	Login.username = usernameTf.getText();
	            	
	            	if(role.equals("User")) {
	            		Home user = new Home(s);
	            	}
	            	else {
	            		CleaningProductManagement admin = new CleaningProductManagement(s);
	            	}
	            	
	            } else {
	                alert.setContentText("Invalid username or password. Please try again.");
	                alert.showAndWait();
	            }
			}
		});
		
		registerLbl.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Register register = new Register(s);

			}
		});
	}
	
	public boolean checkCredentials(String username, String password) {
	    try {
	        String query = "SELECT * FROM msuser WHERE Username = ? AND UserPassword = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, username);
	        preparedStatement.setString(2, password);

	        ResultSet result = preparedStatement.executeQuery();

	        return result.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public String checkRole(String username) {
	    try {
	        String query = "SELECT UserRole FROM msuser WHERE Username = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, username);

	        ResultSet result = preparedStatement.executeQuery();

	        if(result.next()) {
	        	return result.getString("UserRole"); 
	        }
	        else {
	        	return null;
	        }
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	    
	}

	public Login(Stage s) {
		// TODO Auto-generated constructor stub
		init();
		arrangeScene();
		setStyle();
		setEventListeners();
		this.s = s;
		this.s.setScene(sc);
		this.s.show();
	}

}
