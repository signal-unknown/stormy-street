package dat255.chalmers.stormystreet.view;

/**
 * @author Kevin Hoogendijk
 * @since 2015-10-14
 */
public class HighscoreCardData {
    private String name, id;
    private int score, place;

    public HighscoreCardData(int place, String name, String id, int score){
        this.name = name;
        this.id = id;
        this.score = score;
        this.place = place;
    }

    public int getPlace() {
        return place;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public String getId(){
        return id;
    }
}
