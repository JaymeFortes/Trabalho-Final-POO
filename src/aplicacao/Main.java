package aplicacao;

import dados.Drone;
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

    DronePessoal drone = new DronePessoal(1, 2, 4, 5);
    TransportePessoal transportePessoal1 = new TransportePessoal(1, "Cliente A", "Transporte de Pessoas", 20, -23.55052, -46.633309, -22.906847, -43.172896, Estado.PENDENTE, 2);

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("telaPrincipal.fxml"));
            Parent root = loader.load();

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
