package views.TournamentInformationView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import views.FXMLViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Simon on 11/11/2016.
 */
public class TournamentInformationViewController extends FXMLViewController {

    private FXMLViewController currentContentController = null;

    @FXML
    private VBox tourInfo;

    @FXML
    private Label activeGamesLabel;

    @FXML
    private ListView tournamentsList;

    public TournamentInformationViewController(){
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        viewTournamentList();

    }

    public void viewTournamentList(){
        activeGamesLabel.setText("Active Games");

        ObservableList<String> tournaments = tournamentsList.getItems();

        tournaments.add("Coke Tournament");
        tournaments.add("Championship 2016");
        tournaments.add("'Unleash the August");

        tournamentsList.setItems(tournaments);
        tournamentsList.setPrefWidth(10);
    }

    @Override
    public void closeView() {
        closeCurrentContentController();
    }

    private void closeCurrentContentController(){
        if(this.currentContentController != null)
            this.currentContentController.closeView();
    }
}