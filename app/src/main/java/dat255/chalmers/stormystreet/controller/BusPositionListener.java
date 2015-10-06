package dat255.chalmers.stormystreet.controller;

import com.google.android.gms.maps.model.LatLng;

import java.util.Map;

import dat255.chalmers.stormystreet.utilities.TimedAndAngledPosition;

/**
 * Interface for classes listening to the positions of different busses
 */
public interface BusPositionListener {
    public void updatePositions(Map<TimedAndAngledPosition, String> positions);
}
