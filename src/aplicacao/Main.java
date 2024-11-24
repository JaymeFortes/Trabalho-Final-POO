package aplicacao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("modeloDrone.fxml"));
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("modeloDrone.fxml"));
            FXMLLoader loader3 = new FXMLLoader(getClass().getResource("modeloDrone.fxml"));
            Parent root = loader1.load();
            primaryStage.setTitle("ACMEAirDrones");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}