package dat255.chalmers.stormystreet.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.model.MainModel;
import dat255.chalmers.stormystreet.utilities.APIParser;

/**
 * Service that constantly updates the currenttip.
 * This is to ensure the model always has the right distance even though the bustrip is not saved
 * to the database
 * @author Kevin Hoogendijk
 * @since 2015-10-05
 */
public class CurrentTripService extends IntentService{
    private HandlerThread hThread;
    private boolean isFinished = false;
    private static final int DELAY_TIME = 1000;

    public static final String ACTION_TURN_ON = "ACTION_TURN_ON";
    public static final String ACTION_TURN_OFF = "ACTION_TURN_OFF";

    public CurrentTripService() {
        super("dat255.chalmers.stormystreet.services.WifiService");
    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CurrentTripService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if((intent.getAction()).equals(ACTION_TURN_OFF)){
            isFinished = true;
            this.stopSelf();
        }else if((intent.getAction()).equals(ACTION_TURN_ON)){
            runUpdate();
        }
    }

    public void runUpdate(){
        hThread = new HandlerThread("CurrentTripService");
        hThread.start();
        Handler mHandler = new Handler(hThread.getLooper());
        Runnable mRun = new Runnable() {
            @Override
            public synchronized void run() {
                while(!isFinished) {
                    updateCurrentTrip();
                    try {
                        hThread.sleep(DELAY_TIME);
                    } catch (InterruptedException e) {
                        Log.d("CurrentTrip", "interrupted");
                    }
                }
            }
        };
        mHandler.post(mRun);
    }

    /**
     * Each time it is updated it fetches the score from the electricity api
     * and parses it into a distance
     * the model gets updated with the new distance
     */
    public void updateCurrentTrip(){
        Log.d("CurrentTripService", "running update");
        MainModel model = ((GlobalState) getApplication()).getModel();
        if(model.getCurrentTrip() != null){
            try {
                long startDistance = Long.parseLong(APIParser.getBusResource(model.getCurrentTrip().getCurrentVinNumber(), model.getCurrentTrip().getTimestamp() - 60000, model.getCurrentTrip().getTimestamp(), APIParser.BusResource.TOTAL_VEHICLE_DISTANCE_VALUE));
                long endDistance = Long.parseLong(APIParser.getBusResource(model.getCurrentTrip().getCurrentVinNumber(), System.currentTimeMillis() - 60000, System.currentTimeMillis(), APIParser.BusResource.TOTAL_VEHICLE_DISTANCE_VALUE));
                model.setCurrentTripDistance((endDistance-startDistance)*5);
            }catch(APIParser.NoBusResourceException ex){
                isFinished = true;
            }
        }

    }
}