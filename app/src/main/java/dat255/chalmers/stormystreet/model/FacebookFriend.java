package dat255.chalmers.stormystreet.model;

import java.net.URL;

/**
    @author Alexander Karlsson && Maxim Goretskyy

    Class describing FacebookFriends, they are identified by a name, id and their score/meterstraveled
    in the application.
 */
public class FacebookFriend {
    
    private String name, id;
    private int metersTraveled;


    public FacebookFriend(String name, String id, int metersTraveled){
        this.name = name;
        this.id = id;
        this.metersTraveled = metersTraveled;

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public int getMetersTraveled() {
        return metersTraveled;
    }

    public void setMetersTraveled(int metersTraveled) {
        this.metersTraveled = metersTraveled;
    }

    public String toString(){
        return "Name is " + this.name + " score is " + this.metersTraveled;
    }

}
