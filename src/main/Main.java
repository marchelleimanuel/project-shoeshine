package main;

import javafx.application.Application;
import javafx.stage.Stage;
import models.Login;

public class Main extends Application{
	
	@Override
	public void start(Stage s) throws Exception {
		s.setTitle("Shoeshine");
		Login login = new Login(s);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
