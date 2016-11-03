package views.PlayView;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import tests.RunMatch;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by August on 2016-11-02.
 */
public class PlayViewController implements Initializable{

    @FXML
    private BorderPane playViewWindow;

    @FXML
    private Button playMatchButton;

    @FXML
    private StackPane advertBottom;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        advertBottom.prefWidthProperty().bind(playViewWindow.widthProperty());
        advertBottom.prefHeightProperty().bind(playViewWindow.heightProperty().multiply(0.2));
        advertBottom.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        playMatchButton.setOnAction(e -> {
            RunMatch.runMatch(playViewWindow);
        });
    }
}