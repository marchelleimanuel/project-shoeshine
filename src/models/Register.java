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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import util.Connect;

public class Register {
	private Connect connect = Connect.getInstance();
	
	private Scene sc;
	private Stage s;

	private BorderPane bp;
	private FlowPane fp;
	private GridPane gp;
	private VBox vbox;

	private Label titleLbl, usernameLbl, passwordLbl, pnumberLbl, emailLbl, genderLbl; 
	private Hyperlink loginLbl;
	private TextField usernameTf, pnumberTf, emailTf;
	private PasswordField passwordPf;
	private RadioButton maleBtn, femaleBtn;
	private ToggleGroup genderGroup;
	private Button submitBtn;

	public void init() {
		bp = new BorderPane();
		fp = new FlowPane();
		gp = new GridPane();
		vbox = new VBox(20);

		titleLbl = new Label("Register");

		usernameLbl = new Label("Username");
		usernameTf = new TextField();
		usernameTf.setPromptText("Input your username here");

		pnumberLbl = new Label("Phone Number");
		pnumberTf = new TextField();
		pnumberTf.setPromptText("Input your phone number here. Example: +6212312312312");

		emailLbl = new Label("Email");
		emailTf = new TextField();
		emailTf.setPromptText("Input your email here. Example: test@gmail.com");

		passwordLbl = new Label("Password");
		passwordPf = new PasswordField();
		passwordPf.setPromptText("Input your password here. Example: test123");

		genderLbl = new Label("Gender");
		genderGroup = new ToggleGroup();

		maleBtn = new RadioButton("Male");
		femaleBtn = new RadioButton("Female");

		submitBtn = new Button("Register");

		loginLbl = new Hyperlink("Already have an account? Login here!");

		sc = new Scene(bp, 800, 700);

	}

	public void arrangeScene() {
		bp.setTop(titleLbl);
		bp.setCenter(vbox);
		vbox.getChildren().addAll(titleLbl, gp, submitBtn, loginLbl);
		vbox.setAlignment(Pos.CENTER);

		gp.add(usernameLbl, 0, 0);
		gp.add(pnumberLbl, 0, 2);
		gp.add(emailLbl, 0, 4);
		gp.add(passwordLbl, 0, 6);
		gp.add(genderLbl, 0, 8);

		gp.add(usernameTf, 0, 1);
		gp.add(pnumberTf, 0, 3);
		gp.add(emailTf, 0, 5);
		gp.add(passwordPf, 0, 7);

		maleBtn.setToggleGroup(genderGroup);
		femaleBtn.setToggleGroup(genderGroup);

		fp.getChildren().addAll(maleBtn, femaleBtn);
		HBox genderBox = new HBox(20);
		HBox.setMargin(fp, new Insets(8));
		fp.setHgap(10);
		genderBox.getChildren().addAll(genderLbl, fp);
		gp.add(genderBox, 0, 8);

	}

	public void setStyle() {
		BorderPane.setAlignment(titleLbl, Pos.CENTER);
		BorderPane.setAlignment(submitBtn, Pos.CENTER);

		BorderPane.setMargin(submitBtn, new Insets(40));
		bp.setPadding(new Insets(50));

		gp.setAlignment(Pos.CENTER);
		gp.setVgap(10);

		usernameLbl.setMinWidth(400);
		passwordLbl.setMinWidth(400);

		bp.setStyle("-fx-background-color: #F5F5DC;");
		titleLbl.setStyle("-fx-font-weight: bold; -fx-text-fill: darkblue; -fx-font-size: 30;");
		usernameLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		pnumberLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		emailLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		passwordLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");
		genderLbl.setStyle("-fx-text-fill: darkblue; -fx-font-size: 20;");

		submitBtn.setStyle("-fx-text-fill: darkblue;-fx-font-size: 18;");
		loginLbl.setStyle("-fx-text-fill: purple;");

	}

	public void setEventListeners() {
		submitBtn.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if(e.getSource() == submitBtn) {
				String username = usernameTf.getText();
				String email = emailTf.getText();
				String password = passwordPf.getText();
				String phone_number = pnumberTf.getText();
				boolean male = maleBtn.isSelected();
				boolean female = femaleBtn.isSelected();
				String genderValue = null;
				String role = null;
				boolean isEmailValid = true;
				boolean isUsernameValid = true;
				boolean isPasswordValid = true;
				boolean isGenderValid = true;
				boolean isPhoneNumberValid = true;
				
				try {
					RadioButton value = (RadioButton) genderGroup.getSelectedToggle();
					genderValue = value.getText();	
				} catch(Exception ex){
					
				}
				
				role = "User";
				
				if(username.equals("")) {
					alert.setContentText("Please fill out your username");	
					alert.showAndWait();
					isUsernameValid = false;
				}
				else if(checkUsernameCredentials(username)) {
					alert.setContentText("Please choose a different username");
					alert.showAndWait();
					isUsernameValid = false;
				}
				else if(phone_number.equals("")) {
					alert.setContentText("Please fill out your phone number");
					alert.showAndWait();
					isPhoneNumberValid = false;
				}
				else if(!phone_number.startsWith("+62")) {
					alert.setContentText("Make sure your phone number starts with +62");
					alert.showAndWait();
					isPhoneNumberValid = false;
				}
				else if(!checkPhoneNumber(phone_number) || phone_number.length() > 15 || phone_number.length() < 13) {
					alert.setContentText("Invalid phone number!");
					alert.showAndWait();
					isPhoneNumberValid = false;
				}
				else if(checkPhoneNumberCredentials(phone_number)) {
					alert.setContentText("Please choose a different phone number");
					alert.showAndWait();
					isPhoneNumberValid = false;
				}
				else if(email.equals("")) {
					alert.setContentText("Please fill out your email");
					alert.showAndWait();
					isEmailValid = false;
				}
				else if(checkEmailCredentials(email)) {
					alert.setContentText("Please choose a different email");
					alert.showAndWait();
					isEmailValid = false;
				}
				else if((!email.endsWith(".com") && !email.endsWith(".ac.id") && !email.endsWith(".co.id")) || email.startsWith("@") || !email.contains("@")) {
					alert.setContentText("Invalid email");
					alert.showAndWait();
					isEmailValid = false;
				}
				else if(password.equals("")) {
					alert.setContentText("Please fill out your password");
					alert.showAndWait();
					isPasswordValid = false;
				}
				else if(password.length() < 8 || password.length() > 15) {
					alert.setContentText("Make sure your password has a length of 8 - 15 characters");
					alert.showAndWait();
					isPasswordValid = false;
				}
				else if(!isAlphanumeric(password)) {
					alert.setContentText("Password must contain alphabet and number");
					alert.showAndWait();
					isPasswordValid = false;
				}
				else if((!male && !female)) {
					alert.setContentText("Please fill out your gender");
					alert.showAndWait();
					isGenderValid = false;
				}	
				
				if(isUsernameValid && isEmailValid && isPasswordValid && isGenderValid && isPhoneNumberValid) {
					insertDataRegister(username, phone_number, email, password, genderValue, role);	
					
					Alert success = new Alert(AlertType.INFORMATION);
					success.setHeaderText("Registration");
					success.setContentText("Register Success");
					success.showAndWait();
										
	            	Login login = new Login(s);
				}
				
			}
			
			
		});
		
		loginLbl.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Login login = new Login(s);

			}
		});
	}
	
	public boolean checkPhoneNumber(String phoneNumber) {
		if(phoneNumber.equals("+62")) {
			return false;
		}
		else {
			String[] num = phoneNumber.split("\\+62");
			int numbers = 0;
			boolean isValid = false;
			
			for(int i = 0; i < num[1].length(); i++) {
				if(Character.isDigit(num[1].charAt(i))) {
					isValid = true;
				}
				else {
					isValid = false;
					return false;
				}
			}
			if(isValid == true) {
				return true;
			}
		}
		return false;
	}
	
	public boolean checkPhoneNumberCredentials(String phoneNumber) {
	    try {
	        String query = "SELECT * FROM msuser WHERE UserPhoneNumber = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, phoneNumber);

	        ResultSet result = preparedStatement.executeQuery();

	        return result.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean checkUsernameCredentials(String username) {
	    try {
	        String query = "SELECT * FROM msuser WHERE Username = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, username);

	        ResultSet result = preparedStatement.executeQuery();

	        return result.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public boolean checkEmailCredentials(String email) {
	    try {
	        String query = "SELECT * FROM msuser WHERE UserEmail = ?";
	        PreparedStatement preparedStatement = connect.getCon().prepareStatement(query);
	        preparedStatement.setString(1, email);

	        ResultSet result = preparedStatement.executeQuery();

	        return result.next(); 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public void insertDataRegister(String username, String phone_number, String email, String password, String gender, String role) {
	    int users = countUsers();
	    int nextUser = users + 1;
	    String ID = String.format("%03d", nextUser);
	    String userID = "US" + ID;
	    
		String query = "INSERT INTO msuser "
				+ "VALUES ('"+userID+"', '"+ username +"', '"+ phone_number +"', '"+ email +"', '"+ password +"', '"+ gender +"' , '"+ role +"')";
		
		connect.execUpdate(query);
	}
	
	public int countUsers() {
	    String query = "SELECT COUNT(*) FROM msuser";
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
	
	public boolean isAlphanumeric(String password) {
		boolean isAlphabetic = false;
		boolean isNumeric = false;
		boolean isAlphanumeric = false;
		
		for (int i = 0; i < password.length(); i++) {
			if(Character.isAlphabetic(password.charAt(i))) {
				isAlphabetic = true;
			}
			
			if(Character.isDigit(password.charAt(i))) {
				isNumeric = true;
			}
		}
		
		if(isAlphabetic == true && isNumeric == true) {
			isAlphanumeric = true;
			return true;
		}
		
		return false;
	}

	public Register(Stage s) {
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
