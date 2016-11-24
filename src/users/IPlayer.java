package users;

import league.ILeague;
import tournament.ITournament;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the IUser type IPlayer.
 */
public interface IPlayer extends IUser {

    /**
     * Method for getting a list of leagues
     * availible to the player.
     * @return List
     */
    List<ILeague> getLeagues();

    /**
     * Method for getting a list of tournaments
     * availile to the player.
     * @return List
     */
    List<ITournament> getAvailibleTournaments();

    /**
     * Method for setting a name to the player.
     * @param name
     */
    void setName(String name);

}