package views.tournament.handleTournamentStyle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import arena.tournament.tournamentStyle.ITournamentStyle;
import arena.tournament.tournamentStyle.TournamentStyleFactory;
import views.FXMLViewController;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * View controller for the Operators "Handle tournamentStyle" view. Sets up the TournamentStyle settings choices available, and sets
 * up routing for buttons to their corresponding "TournamentStyle" views.
 *
 * Extended ERROR handling for invalid input data needs to be fixed.
 */
public class HandleTournamentStyleController extends FXMLViewController implements Initializable{
    ITournamentStyle tournamentStyle;
    @FXML
    private VBox TournamentStyleWindow;

    @FXML
    private TextField name;

    @FXML
    private TextField size;

    @FXML
    private CheckBox groupsChoice;

    @FXML
    private TextField noOfGroups;

    @FXML
    private TextField rounds;

    @FXML
    private TextField groupWinners;

    @FXML
    private CheckBox eliminationChoice;

    @FXML
    private TextField bestOfGames;

    @FXML
    private TextArea description;

    @FXML
    private Button submit;

    @FXML
    private Text confirmationText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TournamentStyleWindow.setPadding(new Insets(15,0,0,30));
        submit.setOnAction(e->constructTournamentStyle());

    }

    public ITournamentStyle getNewTournamentStyle(){
        return tournamentStyle;
    }

    /**
     * constructTournamentStyle
     * Adds all the data from  the view to the class.
     */
    private void constructTournamentStyle(){
        tournamentStyle = TournamentStyleFactory.newTournamentStyle();
        tournamentStyle.setTournamentStyleName(name.getText());
        tournamentStyle.setTournamentStyleDescription(description.getText());
        tournamentStyle.setTournamentSize(Integer.parseInt(size.getText()));
        if(eliminationChoice.isSelected()) {
            tournamentStyle.initateEliminationSettings();
            tournamentStyle.getEliminationSettings().setBestOf(Integer.parseInt(bestOfGames.getText()));
            //TODO Se över fel som sker när user inte matar in all data Fråga Björn.
        }
        if(groupsChoice.isSelected()) {
            tournamentStyle.initateGroupSettings();
            tournamentStyle.getGroupSettings().setGroupAmount(Integer.parseInt(noOfGroups.getText()));
            tournamentStyle.getGroupSettings().setMaxWinners(Integer.parseInt(groupWinners.getText()));
            tournamentStyle.getGroupSettings().setRounds(Integer.parseInt(rounds.getText()));
            //TODO Se över fel som sker när user inte matar in all data Fråga Björn.
        }
        System.out.print(tournamentStyle.toString());
        if(!testTournamentStyle(tournamentStyle)){
            showWarningDialog();
            tournamentStyle = null;
        }
        if(tournamentStyle != null) {
            confirmationText.setText("Tournament Style is created");
            TournamentStyleFactory.addTournamentStyle(tournamentStyle);
        }
    }

    /**
     * tesTournamentStyle
     * Tests if the styles passes all the tests.
     * @param tournamentStyle
     * @return
     */
    private boolean testTournamentStyle(ITournamentStyle tournamentStyle) {
        boolean result = false;
        if(tournamentStyle.getGroupSettings() != null){
            result = groupSettingTest();
            if(result != true)
                return result;
        }
        if(tournamentStyle.getEliminationSettings() != null) {
            result= eliminationBestOf();
            if(result != true)
                return result;
        }
        if(result) return result;
        else {
            System.out.println("No choice is picked");
            return result;
        }
    }

    /**
     * EqualGroupSize checks if the groupsize is even if not return false
     * @return
     */
    private boolean equalGroupSize(){
        if (tournamentStyle.getTournamentSize() % tournamentStyle.getGroupSettings().getGroupAmount() != 0) {
            System.out.println("Tournamentsize and groupamount is not giving equal groupsize");
            return false;
        }
        return true;
    }

    /**
     * EliminationBestOF checks if the bestOf is not an even number.
     * @return
     */
    private boolean eliminationBestOf(){
        if (tournamentStyle.getEliminationSettings().getBestOf() % 2 == 0) {
            System.out.println("best of is uneven");
            return false;
        }
        return true;
    }

    /**
     * groupWinnerNotHigherThenSize tests to see that there is not more continuers(winners) in a group then the size of the group.
     * Based on the TournamentSize divided with the groupAmount is not less then the amount of maxWinners(continuers)
     * @return
     */
    private boolean groupWinnerNotHigherThenSize(){
        if(tournamentStyle.getTournamentSize()/ tournamentStyle.getGroupSettings().getGroupAmount() < tournamentStyle.getGroupSettings().getMaxWinners()) {
            System.out.println("The maxwinners is more then participants in group");
            return false;
        }
        return true;
    }

    /**
     * groupSettingTest test if all the other grouptest succeds.
     * @return
     */
    private boolean groupSettingTest(){
        if(groupWinnerNotHigherThenSize() && equalGroupSize()){
            return true;
        }
        else return false;

    }
    //TODO fixa felhanteringen för felaktig indata inte helt korrekt Fråga Björn
    //TODO Ge notering till användarn vad denne har gjort för fel.Fråga Björn
    private void showWarningDialog(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error!");
        alert.setHeaderText("New tournamentstyle contains errors.");
        alert.setContentText("Your arena.tournament style is not createt\n" +
                "Please check your input and re-submit to create\n" +
                "the arena.tournament style");
        alert.showAndWait();
    }

    @Override
    public void closeView() {

    }
}
