package org.openjfx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private GridPane gridPane = new GridPane();
    private BorderPane borderPane = new BorderPane();
    private Label gameLabel = new Label("Tic-Tac-Toe");
    private Button restartButton = new Button("restart");
    private Button[] buttons = new Button[9];
    Font font = Font.font("Roboto", FontWeight.BOLD, 50);
    Background background = new Background(new BackgroundFill(Color.WHITESMOKE, CornerRadii.EMPTY, new Insets(10)));
    Background winbackground = new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, new Insets(5)));
    Border border = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1)));

    //game logics
    private boolean playerTurn = true;
    private int[] isButtonClicked = new int[9];
    private final int[][] winCase = {{0, 1, 2}
            , {3, 4, 5}
            , {6, 7, 8}
            , {0, 3, 6}
            , {1, 4, 7}
            , {2, 5, 8}
            , {0, 4, 8}
            , {2, 4, 6}};
    private int count = 0;


    @Override
    public void start(Stage stage) throws IOException {
        this.createGUI();
        this.handleEvent();
        Scene scene1 = new Scene(borderPane, 600, 700);
        stage.setTitle("Tic-Tac-Toe");
//        stage.setAlwaysOnTop(true);
        stage.setScene(scene1);
        stage.show();
    }

    //for creating gui
    private void createGUI() {
        //creating Title
        gameLabel.setFont(font);
        //creating restart button
        restartButton.setFont(font);
        restartButton.setContentDisplay(ContentDisplay.CENTER);
        restartButton.setBackground(background);
        restartButton.setBorder(border);
        //placing title and restart button
        borderPane.setTop(gameLabel);
        borderPane.setBottom(restartButton);
        //placing both in middle
        borderPane.setAlignment(gameLabel, Pos.CENTER);
        borderPane.setAlignment(restartButton, Pos.CENTER);
        borderPane.setPadding(new Insets(20, 20, 20, 20));
//        adding contents to grid pen/9 buttons
        int label = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = new Button("");
                button.setId(label + "");
                button.setFont(font);
                button.setPrefHeight(150);
                button.setPrefWidth(150);
                button.setContentDisplay(ContentDisplay.CENTER);
                button.setBackground(background);
                button.setBorder(border);
                gridPane.add(button, j, i);
                gridPane.setAlignment(Pos.CENTER);
                buttons[label] = button;
                label++;
            }
        }
        borderPane.setCenter(gridPane);
        restartButton.setDisable(true);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rules");
        alert.setHeaderText("Player 1 : X\n"
                + "Player 2 : 0\n"
                + "Player 1 moves first.");
        alert.setContentText("click 'ok' to continue");
        alert.showAndWait();

    }

    //    methods for handling event
    private void handleEvent() {
        Arrays.fill(isButtonClicked, 0);
//        restartButton clicked
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                count = 0;
                for (Button button :
                        buttons) {
                    button.setText("");
                    button.setBackground(background);
                    button.setBorder(border);
                    button.setFont(font);
                    button.setDisable(false);
                }
                Arrays.fill(isButtonClicked, 0);
                playerTurn = true;
                restartButton.setDisable(true);
            }
        });
        //for 9 buttons

        for (Button button :
                buttons) {
            button.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent actionEvent) {
                    restartButton.setDisable(false);
                    Button b = (Button) actionEvent.getSource();
                    int id = Integer.parseInt(b.getId());
                    if (playerTurn) {
                        if (isButtonClicked[id] == 0) {
                            isButtonClicked[id] = 1;
                            button.setText("X");
                            count++;
                        }
                    } else {
                        if (isButtonClicked[id] == 0) {
                            isButtonClicked[id] = 2;
                            button.setText("0");
                            count++;
                        }
                    }
                    if (isGameOver()) {
                        for (Button button1 :
                                buttons) {
                            button1.setBorder(null);
                        }
                        for (int i = 0; i < 9; i++) {
                            if (isButtonClicked[i] == 0) {
                                buttons[i].setDisable(true);
                            }

                        }
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setTitle("GAME OVER");
                        alert.setContentText((playerTurn ? "Player 1 (X)" : "Player 2 (0)") + " won the game.");
                        alert.getDialogPane().setStyle("-fx-font-size: 14pt;");
                        alert.showAndWait();

                    } else {
                        changeTurn();
                        if (count == 9) {
                            for (Button button1 :
                                    buttons) {
                                button1.setBorder(null);
                            }
                            for (int i = 0; i < 9; i++) {
                                if (isButtonClicked[i] == 0) {
                                    buttons[i].setDisable(true);
                                }

                            }
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("GAME OVER");
                            alert.setContentText("GAME IS DRAW");
                            alert.getDialogPane().setStyle("-fx-font-size: 14pt;");
                            alert.showAndWait();
                        }
                    }
                }
            });
        }
    }

    private void changeTurn() {
        playerTurn = !playerTurn;
    }

    public boolean isGameOver() {
        for (int i = 0; i < 8; i++) {

            if (isButtonClicked[winCase[i][0]] == isButtonClicked[winCase[i][1]] && isButtonClicked[winCase[i][1]] == isButtonClicked[winCase[i][2]] && isButtonClicked[winCase[i][0]] != 0) {
                buttons[winCase[i][0]].setBackground(winbackground);
                buttons[winCase[i][1]].setBackground(winbackground);
                buttons[winCase[i][2]].setBackground(winbackground);
                buttons[winCase[i][0]].setFont(new Font(65));
                buttons[winCase[i][1]].setFont(new Font(65));
                buttons[winCase[i][2]].setFont(new Font(65));
                return true;
            }


        }
        return false;
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}