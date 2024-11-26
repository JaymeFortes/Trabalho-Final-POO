package aplicacao;

import dados.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TextInputDialog;
import servicos.DroneService;
import servicos.TransporteService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class ControleRelatorioGeral {

    @FXML
    private TextArea textAreaRelatorio, txtMensagem;

    @FXML
    private Button buttonSair, buttonVoltar, CSVButton;

    private TransporteService transporteService;
    private DroneService droneService;

    @FXML
    public void initialize() {
        if (textAreaRelatorio != null) {
            textAreaRelatorio.setEditable(false);
        }
        if (txtMensagem != null) {
            txtMensagem.setEditable(false);
        }

        if (buttonSair != null) {
            buttonSair.setOnAction(event -> System.exit(0));
        }

        if (buttonVoltar != null) {
            buttonVoltar.setOnAction(event -> voltarParaMenuPrincipal());
        }

        if (CSVButton != null) {
            CSVButton.setOnAction(event -> salvarTransportesEDronesEmCsv());
        }
    }

    public void setServicos(TransporteService transporteService, DroneService droneService) {
        this.transporteService = transporteService;
        this.droneService = droneService;
    }

    public void exibirRelatorio() {
        StringBuilder relatorio = new StringBuilder("Relatório de Transportes e Drones:\n\n");

        if (droneService == null || droneService.getDrones().isEmpty()) {
            relatorio.append("Nenhum drone cadastrado.\n");
        } else {
            relatorio.append("Drones:\n");
            for (Drone drone : droneService.getDrones()) {
                relatorio.append(drone.toString()).append("\n");
            }
        }

        if (transporteService == null || transporteService.getTransportes().isEmpty()) {
            relatorio.append("Nenhum transporte cadastrado.\n");
        } else {
            relatorio.append("Transportes:\n");
            for (Transporte transporte : transporteService.getTransportes()) {
                relatorio.append(transporte.toString()).append("\n");

                Drone droneAlocado = transporte.getDroneAlocado();
                if (droneAlocado != null) {
                    relatorio.append("Drone alocado: ").append(droneAlocado.toString()).append("\n");
                } else {
                    relatorio.append("Nenhum drone alocado.\n");
                }

                try {
                    relatorio.append("Custo do Transporte: R$ ")
                            .append(transporte.calculaCusto()).append("\n\n");
                } catch (IllegalStateException e) {
                    relatorio.append("Erro: ").append(e.getMessage()).append("\n\n");
                } catch (NullPointerException e) {
                    relatorio.append("Erro: Drone não está alocado corretamente para este transporte.\n\n");
                }
            }
        }

        textAreaRelatorio.setText(relatorio.toString());
    }

    public void exibirTodosTransportes() {
        StringBuilder relatorioTransportes = new StringBuilder();

        if (transporteService == null || transporteService.getTransportes().isEmpty()) {
            relatorioTransportes.append("Nenhum transporte cadastrado.\n");
        } else {
            relatorioTransportes.append("Transportes:\n");
            for (Transporte transporte : transporteService.getTransportes()) {
                relatorioTransportes.append(transporte.toString()).append("\n");
                if (transporte.getDroneAlocado() != null) {
                    relatorioTransportes.append("Drone alocado:\n")
                            .append(transporte.getDroneAlocado().toString()).append("\n");
                } else {
                    relatorioTransportes.append("Nenhum drone alocado.\n");
                }

                try {
                    relatorioTransportes.append("Custo do Transporte: R$ ")
                            .append(transporte.calculaCusto()).append("\n\n");
                } catch (IllegalStateException e) {
                    relatorioTransportes.append("Erro no cálculo do custo: ")
                            .append(e.getMessage()).append("\n\n");
                }
            }
        }

        txtMensagem.setText(relatorioTransportes.toString());
    }

    @FXML
    private void voltarParaMenuPrincipal() {
        Stage stage = (Stage) buttonVoltar.getScene().getWindow();
        stage.close();
    }

    private void salvarTransportesEDronesEmCsv() {
        if (transporteService == null || droneService == null) {
            mostrarErro("Serviços de transporte ou drone não foram inicializados.");
            return;
        }

        ObservableList<Transporte> transportes = transporteService.getTransportes();
        ObservableList<Drone> drones = droneService.getDrones();

        // Solicitar nome do arquivo ao usuário
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salvar Dados");
        dialog.setHeaderText("Digite o nome do arquivo (sem extensão):");
        dialog.setContentText("Nome do arquivo:");

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
            String nomeArquivoBase = resultado.get().trim();

            // Salvar transportes
            if (!salvarTransportes(nomeArquivoBase + "_transportes.csv", transportes)) {
                mostrarErro("Erro ao salvar os transportes!");
            }

            // Salvar drones
            if (!salvarDrones(nomeArquivoBase + "_drones.csv", drones)) {
                mostrarErro("Erro ao salvar os drones!");
            }

            // Mensagem de sucesso
            System.out.println("Dados salvos com sucesso!");
        } else {
            System.out.println("Operação cancelada ou nome inválido.");
        }
    }

    private boolean salvarTransportes(String nomeArquivo, ObservableList<Transporte> transportes) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("Número,Nome Cliente,Descrição,Peso,Latitude Origem,Longitude Origem,Latitude Destino,Longitude Destino,Situação,Drone Alocado,Acrescimos,Custo\n");
            for (Transporte transporte : transportes) {
                writer.write(transporte.getNumero() + "," +
                        transporte.getNomeCliente() + "," +
                        transporte.getDescricao() + "," +
                        transporte.getPeso() + "," +
                        transporte.getLatitudeOrigem() + "," +
                        transporte.getLongitudeOrigem() + "," +
                        transporte.getLatitudeDestino() + "," +
                        transporte.getLongitudeDestino() + "," +
                        transporte.getSituacao() + "," +
                        (transporte.getDroneAlocado() != null ? transporte.getDroneAlocado().getCodigo() : "Nenhum") + "," +
                        transporte.calculaAcrescimos() + "," +
                        transporte.calculaCusto() + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean salvarDrones(String nomeArquivo, ObservableList<Drone> drones) {
        try (FileWriter writer = new FileWriter(nomeArquivo)) {
            writer.write("Código,Tipo,Custo Fixo,Autonomia,Custo por Km,Adicionais\n");

            for (Drone drone : drones) {
                String informacaoEspecifica = "";

                // Verificando o tipo de drone e ajustando informações específicas
                if (drone instanceof DronePessoal) {
                    DronePessoal dronePessoal = (DronePessoal) drone;
                    informacaoEspecifica = "Qtd Pessoas: " + dronePessoal.getQtdPessoas();

                } else if (drone instanceof DroneCargaInanimada) {
                    DroneCargaInanimada droneCargaInanimada = (DroneCargaInanimada) drone;
                    informacaoEspecifica = "Peso Máximo: " + droneCargaInanimada.getPesoMaximo() + " kg";
                    informacaoEspecifica += "; Protegido: " + (droneCargaInanimada.isProtecao() ? "Sim" : "Não");

                } else if (drone instanceof DroneCargaViva) {
                    DroneCargaViva droneCargaViva = (DroneCargaViva) drone;
                    informacaoEspecifica = "Peso Máximo: " + droneCargaViva.getPesoMaximo() + " kg";
                    informacaoEspecifica += "; Climatizado: " + (droneCargaViva.isClimatizado() ? "Sim" : "Não");
                }

               writer.write(drone.getCodigo() + "," +
                        drone.getTipoDrone() + "," +
                        (drone.getCustoFixo()) + "," +
                        (drone.getAutonomia()) + "," + // Preservando a autonomia
                        (drone.calculaCustoKm()) + "," + // Custo por Km na coluna correta
                        informacaoEspecifica.replace(", ", "; ") + "\n");
            }

            return true; // Indica sucesso
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Indica falha
        }
    }





    private void mostrarErro(String mensagem) {
        System.err.println(mensagem);
    }

    // Método para alocar o drone ao transporte
    private boolean alocarDroneParaTransporte(Drone drone, Transporte transporte) {
        // Verifique se o drone já está alocado em algum transporte
        for (Transporte t : transporteService.getTransportes()) {
            if (t.getDroneAlocado() != null && t.getDroneAlocado().equals(drone)) {
                System.out.println("O drone " + drone.getCodigo() + " já está alocado a outro transporte.");
                return false;  // O drone já está alocado, não faz a alocação

            }
        }
        return true;
    }
}