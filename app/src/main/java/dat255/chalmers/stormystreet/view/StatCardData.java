package dat255.chalmers.stormystreet.view;

import android.graphics.Bitmap;

/**
 * @author Alexander Håkansson
 * @since 2015-09-24
 */
public class StatCardData {

    private final String value, suffix;
    private final Bitmap icon;
    
    public StatCardData(String value, String suffix, Bitmap icon) {
        this.value = value;
        this.suffix = suffix;
        this.icon = icon;
    }

    public String getValue() {
        return value;
    }

    public String getSuffix() {
        return suffix;
    }

    public Bitmap getIcon() {
        return icon;
    }
}
