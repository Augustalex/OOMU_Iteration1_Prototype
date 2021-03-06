package arena.league;

import arena.games.game.IGame;
import arena.tournament.ITournament;
import arena.users.IPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the arena.league interface
 */
public class League implements ILeague{
    private String leagueName;
    private int leagueID;
    private ArrayList<IPlayer> playersInLeague = new ArrayList<>();
    private ArrayList<ITournament> tournamentsInLeague = new ArrayList<>();
    private IGame game;

    public League(String name, int id){
        this.leagueName = name;
        this.leagueID = id;
    }

    @Override
    public String getLeagueName() {
        return leagueName;
    }

    @Override
    public int getLeagueID() {
        return leagueID;
    }

    @Override
    public List<ITournament> getTournaments() {
        return tournamentsInLeague;
    }

    @Override
    public void printTournamentNames(){
        for(int i = 0; i < tournamentsInLeague.size(); i++) {
            System.out.println("Name of tournaments: " + tournamentsInLeague.get(i).getTournamentMetaInformation().getName());
        }
    }

    @Override
    public void setGame(IGame game) {
        this.game = game;
    }

    @Override
    public IGame getGame() {
        return this.game;
    }

    @Override
    public void printTournamentIDs(){
        for(int i = 0; i < tournamentsInLeague.size(); i++) {
            //System.out.println("ID's of tournaments: " + tournamentsInLeague.get(i).getTournamentID());
        }
    }

    @Override
    public void addTournamentToLeague(ITournament tournament){
        tournamentsInLeague.add(tournament);
    }

    @Override
    public void addPlayerToLeague(IPlayer player) {
        playersInLeague.add(player);
    }


    @Override
    public List<IPlayer> getPlayersInLeague() {
        return playersInLeague;
    }

    @Override
    public String toString(){
        return this.getLeagueName();
    }
}
