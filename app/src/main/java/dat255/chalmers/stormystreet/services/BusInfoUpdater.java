package dat255.chalmers.stormystreet.services;

import android.os.AsyncTask;

import dat255.chalmers.stormystreet.model.bus.IBus;
import dat255.chalmers.stormystreet.utilities.APIParser;

/**
 * @author Alexander Håkansson
 */
public class BusInfoUpdater extends AsyncTask<Integer, Void, IBus> {

    public interface IBusInfoListener {
        void busUpdated(IBus bus);
    }

    private final IBusInfoListener listener;

    public BusInfoUpdater(IBusInfoListener listener) {
        this.listener = listener;
    }

    @Override
    protected IBus doInBackground(Integer... params) {
        if (params.length > 0 && params[0] != null) {
            int vin = params[0];
            return getBusForVin(vin);
        } else {
            return null;
        }
    }

    private IBus getBusForVin(int vin) {
        return APIParser.getBusInfo(vin);
    }

    @Override
    protected void onPostExecute(IBus bus) {
        if (listener != null && bus != null) {
            listener.busUpdated(bus);
        }
    }
}
