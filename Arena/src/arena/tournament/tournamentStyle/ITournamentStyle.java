package arena.tournament.tournamentStyle;

import java.io.Serializable;

/**
 * Defines the type and style of arena.tournament to be played.
 *
 * Might describe the way a Tournament Map may be created and
 * other related aspect to setting up a new Tournament.
 */
public interface ITournamentStyle extends Serializable{

    String getTournamentStyleName();
    String getTournamentStyleDescription();
    GroupSettings getGroupSettings();
    EliminationSettings getEliminationSettings();
    int getTournamentSize();

    void setTournamentStyleName(String name);
    void setTournamentStyleDescription(String description);
    void initateGroupSettings();
    void initateEliminationSettings();
    void setTournamentSize(int size);

    //void setGroupRound(boolean value);
    //boolean getGroupRound();
    //void setEliminationRound(boolean value);
    //boolean getEliminationRound();


    //ITournamentStyle getTournamentStyle();
    // void addTournamentStyle(ITournamentStyle tournamentStyle);
    //void createMapPreference();
}
