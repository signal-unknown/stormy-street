package dat255.chalmers.stormystreet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import dat255.chalmers.stormystreet.R;

/**
 * @author Alexander Håkansson
 * @since 2015-09-24
 */
public class BusStatCard extends CardView {

    private TextView topText, bottomText;
    private ImageView icon;

    public BusStatCard(Context context) {
        super(context);
        init();
    }

    public BusStatCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BusStatCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.cardview_busstatcard, this);
        topText = (TextView) findViewById(R.id.busstatcard_topText);
        bottomText = (TextView) findViewById(R.id.busstatcard_bottomText);
        icon = (ImageView) findViewById(R.id.busstatcard_icon);
    }

    public void setTopText(String text) {
        topText.setText(text);
        topText.setVisibility(View.VISIBLE);
    }

    public void setBottomText(String text) {
        bottomText.setText(text);
        bottomText.setVisibility(View.VISIBLE);
    }

    public void setIcon(Drawable image) {
        icon.setImageDrawable(image);
        icon.setVisibility(View.VISIBLE);
    }

    public void setIcon(Bitmap image) {
        icon.setImageBitmap(image);
        icon.setVisibility(View.VISIBLE);
    }
}
