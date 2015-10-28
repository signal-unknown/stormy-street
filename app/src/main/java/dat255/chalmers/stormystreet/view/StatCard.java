package dat255.chalmers.stormystreet.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dat255.chalmers.stormystreet.R;

/**
 * This view is a card that represents data from an ElectriCity 
 *
 * @author Alexander Håkansson
 * @since 2015-09-24
 */
public class StatCard extends CardView {

    private TextView topText, bottomText;
    private ImageView icon;

    public StatCard(Context context) {
        super(context);
        init();
    }

    public StatCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatCard(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if (text != null) {
            topText.setText(text);
            topText.setVisibility(View.VISIBLE);
        }
    }

    public void setBottomText(String text) {
        if (text != null) {
            bottomText.setText(text);
            bottomText.setVisibility(View.VISIBLE);
        }
    }

    public void setIcon(Drawable image) {
        if (image != null) {
            icon.setImageDrawable(image);
            icon.setVisibility(View.VISIBLE);
        }
    }

    public void setIcon(Bitmap image) {
        if (image != null) {
            icon.setImageBitmap(image);
            icon.setVisibility(View.VISIBLE);
        }
    }

    public void setLarge() {
        topText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 56);
        bottomText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
    }
}
