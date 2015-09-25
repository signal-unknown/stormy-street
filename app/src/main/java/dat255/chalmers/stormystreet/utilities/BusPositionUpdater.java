package dat255.chalmers.stormystreet.utilities;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

/**
 * Created by alexanderkarlsson on 15-09-25.
 */
public class BusPositionUpdater extends AsyncTask<Void,Void,Map<LatLng, String>>{

    @Override
    protected Map<LatLng, String> doInBackground(Void... params) {
        //TODO fetch positions, and map them to an identifier (probably registration nr)
        return null;
    }

    @Override
    protected void onPostExecute(Map<LatLng, String> map){
        //TODO handle positions
    }
}
