package users;

import league.ILeague;
import league.League;
import tournament.ITournament;
import tournament.TournamentFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements interface IPlayer
 */
public class Player implements IPlayer{

    private ArrayList<ILeague> leagues = new ArrayList<>();
    private String playerName;


    public Player(){

    }
    public static IPlayer newMockPlayer(){

        IPlayer player = new Player().createMockPlayerAugust();
        ILeague league = new League("mock league", 1);


        league.addTournamentToLeague(TournamentFactory.newTournamentMock("Coca Cola Tournament"));
        league.addTournamentToLeague(TournamentFactory.newTournamentMock());
        league.addTournamentToLeague(TournamentFactory.newTournamentMock("Ostbågar Tournament"));
        player.getLeagues().add(league);
        return player;
    }

    @Override
    public ArrayList<ILeague> getLeagues() {
        return leagues;
    }

    public IPlayer createMockPlayerAugust(){
        IPlayer player = new Player();
        player.setName("August");
        return player;
    }


    @Override
    public List<ITournament> getAvailibleTournaments(){
        return leagues.stream()
                .flatMap(l -> l.getTournaments().stream())
                .collect(Collectors.toList());
    }

    @Override
    public void notify(String message) {
        System.out.println("Notifiaction");
    }

    @Override
    public String getName() {
        return playerName;
    }

    @Override
    public void setName(String name){
        this.playerName = name;
    }


}
