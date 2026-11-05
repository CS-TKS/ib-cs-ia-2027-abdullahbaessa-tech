package app;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;

public class MainApp extends Application {
    private DatabaseManager db = new DatabaseManager();
    private RecommendationEngine engine = new RecommendationEngine();
    private Player currentPlayer;

    private TextField nameField;
    private TextField heightField;
    private TextField weightField;
    private TextField ageField;
    private TextField speedField;
    private TextField agilityField;
    private Label errorLabel;

    private Label bmiLabel;
    private Label categoryLabel;
    private Label positionLabel;
    private Label dietLabel;
    private ListView<String> trainingList;
    private LineChart<String, Number> progressChart;
    private ListView<String> profilesList;
    private List<Player> displayedProfiles = new ArrayList<Player>();

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        try {
            db.connect();
            db.createTables();

            TabPane tabs = new TabPane();
            tabs.getTabs().add(buildInputTab());
            tabs.getTabs().add(buildRecommendationsTab());
            tabs.getTabs().add(buildProgressTab());
            tabs.getTabs().add(buildProfilesTab());

            stage.setTitle("Football Coaching App");
            stage.setScene(new Scene(tabs, 700, 500));
            stage.show();
            refreshProfiles();
        } catch (SQLException e) {
            showError("Database Error", e.getMessage());
        }
    }

    private Tab buildInputTab() {
        GridPane grid = new GridPane();
        nameField = new TextField();
        heightField = new TextField();
        weightField = new TextField();
        ageField = new TextField();
        speedField = new TextField();
        agilityField = new TextField();
        errorLabel = new Label();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Height (cm):"), 0, 1);
        grid.add(heightField, 1, 1);
        grid.add(new Label("Weight (kg):"), 0, 2);
        grid.add(weightField, 1, 2);
        grid.add(new Label("Age:"), 0, 3);
        grid.add(ageField, 1, 3);
        grid.add(new Label("Speed (km/h):"), 0, 4);
        grid.add(speedField, 1, 4);
        grid.add(new Label("Agility (1-10):"), 0, 5);
        grid.add(agilityField, 1, 5);

        Button saveButton = new Button("Save Player");
        saveButton.setOnAction(e -> savePlayerFromForm());
        grid.add(saveButton, 1, 6);
        grid.add(errorLabel, 1, 7);

        Tab tab = new Tab("Input");
        tab.setContent(grid);
        return tab;
    }

    private Tab buildRecommendationsTab() {
        VBox box = new VBox();
        bmiLabel = new Label("BMI:");
        categoryLabel = new Label("BMI category:");
        positionLabel = new Label("Position:");
        trainingList = new ListView<String>();
        dietLabel = new Label("Diet advice:");

        box.getChildren().add(new Label("Recommendations"));
        box.getChildren().add(bmiLabel);
        box.getChildren().add(categoryLabel);
        box.getChildren().add(positionLabel);
        box.getChildren().add(new Label("Training plan:"));
        box.getChildren().add(trainingList);
        box.getChildren().add(dietLabel);

        Tab tab = new Tab("Recommendations");
        tab.setContent(box);
        return tab;
    }

    private Tab buildProgressTab() {
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Date");
        yAxis.setLabel("BMI");
        progressChart = new LineChart<String, Number>(xAxis, yAxis);
        progressChart.setTitle("Player Progress");

        Tab tab = new Tab("Progress");
        tab.setContent(progressChart);
        return tab;
    }

    private Tab buildProfilesTab() {
        VBox box = new VBox();
        profilesList = new ListView<String>();

        Button loadButton = new Button("Load Profile");
        loadButton.setOnAction(e -> loadSelectedProfile());
        Button newButton = new Button("New Profile");
        newButton.setOnAction(e -> {
            currentPlayer = null;
            clearFields();
        });

        box.getChildren().add(new Label("Saved Profiles"));
        box.getChildren().add(profilesList);
        box.getChildren().add(loadButton);
        box.getChildren().add(newButton);

        Tab tab = new Tab("Profiles");
        tab.setContent(box);
        return tab;
    }

    private boolean validateInputs() {
        if (nameField.getText().trim().isEmpty()) {
            errorLabel.setText("Enter a name.");
            return false;
        }
        try {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int age = Integer.parseInt(ageField.getText());
            double speed = Double.parseDouble(speedField.getText());
            int agility = Integer.parseInt(agilityField.getText());

            if (height <= 0 || weight <= 0 || age <= 0 || speed <= 0) {
                errorLabel.setText("Height, weight and age must be positive. Speed must be greater than zero.");
                return false;
            }
            if (agility < 1 || agility > 10) {
                errorLabel.setText("Agility must be from 1 to 10.");
                return false;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Enter valid numbers in all number fields.");
            return false;
        }
        errorLabel.setText("");
        return true;
    }

    private void savePlayerFromForm() {
        if (validateInputs()) {
            try {
                Player player = new Player(nameField.getText(),
                        Double.parseDouble(heightField.getText()),
                        Double.parseDouble(weightField.getText()),
                        Integer.parseInt(ageField.getText()),
                        Double.parseDouble(speedField.getText()),
                        Integer.parseInt(agilityField.getText()));

                db.savePlayer(player);
                Session session = new Session(player.getPlayerId(),
                        LocalDate.now().toString(), player.getBMI());
                db.saveSession(session);

                currentPlayer = player;
                refreshRecommendations();
                refreshProgress();
                refreshProfiles();
                errorLabel.setText("Player saved.");
            } catch (SQLException e) {
                showError("Database Error", e.getMessage());
            }
        }
    }

    private void refreshRecommendations() {
        if (currentPlayer != null) {
            bmiLabel.setText("BMI: " + currentPlayer.getBMI());
            categoryLabel.setText("BMI category: " + currentPlayer.getBMICategory());
            positionLabel.setText("Position: " + engine.recommendPosition(currentPlayer));
            String[] plan = engine.generateTrainingPlan(currentPlayer);
            trainingList.setItems(FXCollections.observableArrayList(plan));
            dietLabel.setText("Diet advice: " + engine.recommendDiet(currentPlayer));
        }
    }

    private void refreshProgress() {
        if (currentPlayer != null) {
            try {
                progressChart.getData().clear();
                List<Session> sessions = db.getSessions(currentPlayer.getPlayerId());
                XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
                series.setName(currentPlayer.getName());

                for (int i = 0; i < sessions.size(); i++) {
                    Session session = sessions.get(i);
                    XYChart.Data<String, Number> point =
                            new XYChart.Data<String, Number>(session.getDate(), session.getBmi());
                    series.getData().add(point);
                }
                progressChart.getData().add(series);
            } catch (SQLException e) {
                showError("Database Error", e.getMessage());
            }
        }
    }

    private void refreshProfiles() {
        try {
            displayedProfiles = db.getAllPlayers();
            List<String> names = new ArrayList<String>();
            for (int i = 0; i < displayedProfiles.size(); i++) {
                Player player = displayedProfiles.get(i);
                names.add(player.getName() + " (ID: " + player.getPlayerId() + ")");
            }
            profilesList.setItems(FXCollections.observableArrayList(names));
        } catch (SQLException e) {
            showError("Database Error", e.getMessage());
        }
    }

    private void loadSelectedProfile() {
        int selectedIndex = profilesList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            showError("No Profile Selected", "Select a profile first.");
        } else {
            try {
                int playerId = displayedProfiles.get(selectedIndex).getPlayerId();
                currentPlayer = db.getPlayer(playerId);
                if (currentPlayer != null) {
                    nameField.setText(currentPlayer.getName());
                    heightField.setText(String.format("%.1f", currentPlayer.getHeight()));
                    weightField.setText(String.format("%.1f", currentPlayer.getWeight()));
                    ageField.setText(String.valueOf(currentPlayer.getAge()));
                    speedField.setText(String.format("%.1f", currentPlayer.getSpeed()));
                    agilityField.setText(String.valueOf(currentPlayer.getAgility()));
                    refreshRecommendations();
                    refreshProgress();
                }
            } catch (SQLException e) {
                showError("Database Error", e.getMessage());
            }
        }
    }

    private void clearFields() {
        nameField.clear();
        heightField.clear();
        weightField.clear();
        ageField.clear();
        speedField.clear();
        agilityField.clear();
        errorLabel.setText("");
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
