package tournament.tournamentStyle;

/**
 * Defines the settings for a tournaments group settings
 */

public class GroupSettings {
    private int groupsAmount; // The amount of groups in a tournament
    private int rounds;     // The amount of matches in a group play.
    private int maxWinners; // The amount that qualifies to continue the group round

    public void setGroupAmount(int input){
        this.groupsAmount = input;
    }
    public int getGroupAmount(){return this.groupsAmount;}

    public void setRounds(int input){this.rounds = input;}
    public int getRounds(){
        return this.rounds;
    }

    public void setMaxWinners(int value){
        this.maxWinners = value;
    }
    public int getMaxWinners(){
        return this.maxWinners;
    }
}
