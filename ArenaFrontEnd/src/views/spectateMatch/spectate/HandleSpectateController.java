package views.spectateMatch.spectate;

import arena.games.game.IGame;
import arena.games.gameManager.ArenaGameManager;
import arena.league.ILeague;
import arena.tournament.ITournament;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import spectate.SpectateTable;
import views.FXMLViewController;
import views.tournament.extendedTournamentView.ExtendedTournamentViewController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * View controller for the Operators "Handle tournamentStyle" view. Sets up the TournamentStyle settings choices available, and sets
 * up routing for buttons to their corresponding "TournamentStyle" views.
 *
 * Extended ERROR handling for invalid input data needs to be fixed.
 */
public class HandleSpectateController extends FXMLViewController implements Initializable {

    @FXML
    private TableView tableTournaments;
    @FXML
    private TableColumn leagueCol;
    @FXML
    private TableColumn tourNameCol;

    @FXML
    private TableColumn gameCol;
    @FXML
    private TableView tableGames;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configureView();
    }

    /**
     *  Handles the events and startup config for the the view.
     */
    private void configureView() {
        setTableGames();
        tableGames.setOnMousePressed(new EventHandler<MouseEvent>() {

            /**  Handles the events for a mouseclick on the tableGame
             *   If a user clicks a valid row the user will be shown a new table with more information
             *   About the league and tournaments that belongs to that game.
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    GameSettings gameSettings = (GameSettings) row.getItem();
                    setTableView(gameSettings.getGameInfo());
                }
            }
        });

        tableTournaments.setOnMousePressed(new EventHandler<MouseEvent>() {
            /**
             * Handles the mouseclick onto a row in the tableTournamentView.
             * The clicking on a row will trigger another event.
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    LeagueSettings league = (LeagueSettings) row.getItem();
                    System.out.println(league.getTournament());
                    try {
                        newView(league.getTournament());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Adds all the games availables in the arena to the tableGames
     */
    private void setTableGames(){

        tableGames.refresh();
        ObservableList<GameSettings> gameList = FXCollections.observableArrayList();

        for(IGame game : ArenaGameManager.get().getAllGames()){
            gameList.add(new GameSettings(game));
            System.out.println(game.getGameInformation().getGameName());
        }

        gameCol.setCellValueFactory(new PropertyValueFactory<GameSettings, String>("gameName"));
        tableGames.setItems(gameList);
        tableGames.getColumns().clear();
        tableGames.getColumns().addAll(gameCol);
    }

    /**
     * Launches a pop up that will show the TournamentLobby for Spectator
     * @param tournament
     * @throws IOException
     */
    private void newView(ITournament tournament) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tournament/extendedTournamentView/ExtendedTournamentView.fxml"));
        loader.setControllerFactory(c -> new ExtendedTournamentViewController(tournament));
        Parent parent = loader.load();
        stage.setTitle("Tournament Lobby");
        stage.setScene(new Scene(parent, 1600, 600));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    /**
     * setTableView initates the table that shows the tournaments and leagues for a certain game.
     * @param game
     */
    private void setTableView(IGame game) {
        ObservableList<LeagueSettings> tournamentsList = FXCollections.observableArrayList();

        for(ILeague league : SpectateTable.get().getLeagues())
            if(game.getGameInformation().getGameName() == league.getGame().getGameInformation().getGameName()){
                for (ITournament tournament : league.getTournaments()) {
                    tournamentsList.add(new LeagueSettings(league, tournament, league.getGame()));
                }
        }
        tableTournaments.setVisible(true);
        leagueCol.setCellValueFactory(new PropertyValueFactory<LeagueSettings, ILeague>("league"));
        tourNameCol.setCellValueFactory(new PropertyValueFactory<LeagueSettings, ITournament>("tournament"));
        tableTournaments.setItems(tournamentsList);
        tableTournaments.getColumns().clear();
        tableTournaments.getColumns().addAll(leagueCol, tourNameCol);
    }

    @Override
    public void closeView() {

    }

    /**
     *  Inner class that holds the information about a league, tournament and what game it belongs to
     *  The class is used within the view tableTournaments
     */
    public static class LeagueSettings {

        private ITournament tournament;
        private ILeague league;
        private IGame game;

        public LeagueSettings(ILeague league, ITournament tournament, IGame game){
            this.tournament = tournament;
            this.league = league;
            this.game = game;
        }

        public ILeague getLeague(){
            return this.league;
        }

        public ITournament getTournament() {
            return this.tournament;
        }
    }

    /**
     * A inner class that holds the information about a game.
     * The class is being used for the table viewGames.
     */
    public static class GameSettings{
        private IGame gameInfo;
        private String gameName;

        public GameSettings(IGame game){
            this.gameInfo = game;
            this.gameName = game.getGameInformation().getGameName();
        }

        public String getGameName(){
            return this.gameName;
        }
        public IGame getGameInfo(){
            return this.gameInfo;}
        }
}
