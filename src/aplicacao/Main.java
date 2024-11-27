package aplicacao;

import dados.DronePessoal;
import dados.Estado;
import dados.TransportePessoal;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicos.DroneService;
import servicos.TransporteService;

public class Main extends Application {
    private TransporteService transporteService;
    private DroneService droneService;



    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/janelas/telaPrincipal.fxml"));
            Parent root = loader.load();

            primaryStage.setTitle("ACMEAirDrones");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
