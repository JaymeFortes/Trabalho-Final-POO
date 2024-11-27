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

import java.io.*;
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

    public void salvarTransportesEDronesEmCsv() {
        if (transporteService == null || droneService == null) {
            mostrarErro("Serviços de transporte ou drone não foram inicializados.");
            return;
        }

        ObservableList<Transporte> transportes = transporteService.getTransportes();
        ObservableList<Drone> drones = droneService.getDrones();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Salvar Dados");
        dialog.setHeaderText("Digite o nome do arquivo (sem extensão):");
        dialog.setContentText("Nome do arquivo:");

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent() && !resultado.get().trim().isEmpty()) {
            String nomeArquivoBase = resultado.get().trim();


            if (!salvarTransportes(nomeArquivoBase + "_transportes.csv", transportes)) {
                mostrarErro("Erro ao salvar os transportes!");
            }

            if (!salvarDrones(nomeArquivoBase + "_drones.csv", drones)) {
                mostrarErro("Erro ao salvar os drones!");
            }

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

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void mostrarErro(String mensagem) {
        System.err.println(mensagem);
    }


    @FXML
    public void CarregarDados() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Carregar Dados de Simulação");
        dialog.setHeaderText("Digite o nome do arquivo (sem extensão):");
        dialog.setContentText("Nome do arquivo:");

        Optional<String> nomeArquivoOptional = dialog.showAndWait();

        if (nomeArquivoOptional.isPresent() && !nomeArquivoOptional.get().trim().isEmpty()) {
            String nomeArquivoBase = nomeArquivoOptional.get().trim();

            boolean sucessoDrones = carregarDrones(nomeArquivoBase + "-DRONES.csv");
            boolean sucessoTransportes = carregarTransportes(nomeArquivoBase + "-TRANSPORTES.csv");

            if (!sucessoDrones && !sucessoTransportes) {
                System.err.println("Erro: Nenhum dos arquivos foi carregado com sucesso.");
                mostrarErro("Erro ao carregar os arquivos de drones e transportes.");
            } else {
                System.out.println("Dados carregados com sucesso.");
            }
        } else {
            System.out.println("Nome do arquivo não fornecido.");
        }
    }

    private boolean carregarDrones(String nomeArquivo) {
        System.out.println("Carregando drones do arquivo: " + nomeArquivo);
        InputStream inputStream = getClass().getResourceAsStream("/resources/" + nomeArquivo);
        if (inputStream == null) {
            System.err.println("Arquivo de drones não encontrado: " + nomeArquivo);
            return false;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                try {
                    String[] dados = linha.split(";");
                    int tipo = Integer.parseInt(dados[0]);
                    int codigo = Integer.parseInt(dados[1]);
                    double custoFixo = Double.parseDouble(dados[2]);
                    double autonomia = Double.parseDouble(dados[3]);

                    Drone drone;
                    if (tipo == 1) {
                        int qtdPessoas = Integer.parseInt(dados[4]);
                        drone = new DronePessoal(codigo, custoFixo, autonomia, qtdPessoas);
                    } else if (tipo == 2) {
                        double pesoMaximo = Double.parseDouble(dados[4]);
                        boolean protegido = Boolean.parseBoolean(dados[5]);
                        drone = new DroneCargaInanimada(codigo, custoFixo, autonomia, pesoMaximo, protegido);
                    } else if (tipo == 3) {
                        double pesoMaximo = Double.parseDouble(dados[4]);
                        boolean climatizado = Boolean.parseBoolean(dados[5]);
                        drone = new DroneCargaViva(codigo, custoFixo, autonomia, pesoMaximo, climatizado);
                    } else {
                        throw new IllegalArgumentException("Tipo de drone desconhecido: " + tipo);
                    }

                    droneService.getDrones().add(drone);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar drone: " + linha);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de drones: " + e.getMessage());
            return false;
        }
    }

    private boolean carregarTransportes(String nomeArquivo) {
        System.out.println("Carregando transportes do arquivo: " + nomeArquivo);
        InputStream inputStream = getClass().getResourceAsStream("/resources/" + nomeArquivo);
        if (inputStream == null) {
            System.err.println("Arquivo de transportes não encontrado: " + nomeArquivo);
            return false;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String linha = br.readLine();
            while ((linha = br.readLine()) != null) {
                try {
                    String[] dados = linha.split(";");
                    int tipo = Integer.parseInt(dados[0]);
                    int numero = Integer.parseInt(dados[1]);
                    String nomeCliente = dados[2];
                    String descricao = dados[3];
                    double peso = Double.parseDouble(dados[4]);
                    double latOrigem = Double.parseDouble(dados[5]);
                    double longOrigem = Double.parseDouble(dados[6]);
                    double latDestino = Double.parseDouble(dados[7]);
                    double longDestino = Double.parseDouble(dados[8]);


                    Transporte transporte;
                    if (tipo == 3) {
                        double tempMinima = Double.parseDouble(dados[9]);
                        double tempMaxima = Double.parseDouble(dados[10]);
                        transporte = new TransporteCargaViva(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, tempMinima, tempMaxima);
                    } else if (tipo == 2) {
                        boolean cargaPerigosa = Boolean.parseBoolean(dados[9]);
                        transporte = new TransporteCargaInanimada(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, cargaPerigosa);
                    } else if (tipo == 1) {
                        int qtdPessoas = Integer.parseInt(dados[9]);
                        transporte = new TransportePessoal(numero, nomeCliente, descricao, peso, latOrigem, longOrigem, latDestino, longDestino, Estado.PENDENTE, qtdPessoas);
                    } else {
                        throw new IllegalArgumentException("Tipo de transporte desconhecido: " + tipo);
                    }

                    transporteService.getTransportes().add(transporte);
                } catch (Exception e) {
                    System.err.println("Erro ao carregar transporte: " + linha);
                }
            }
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de transportes: " + e.getMessage());
            return false;
        }
    }
}