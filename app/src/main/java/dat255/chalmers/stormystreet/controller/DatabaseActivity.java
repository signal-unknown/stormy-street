package dat255.chalmers.stormystreet.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.List;
import android.widget.EditText;

import dat255.chalmers.stormystreet.GlobalState;
import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.DataValue;

/**
 * Created by David Fogelberg on 2015-09-28.
 */
public class DatabaseActivity extends ListActivity {

    private SQLiteDataSource sqLiteDataSourceOpertions;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_data);


        sqLiteDataSourceOpertions = new SQLiteDataSource(this);
        sqLiteDataSourceOpertions.open();
        List dataValues = sqLiteDataSourceOpertions.getAllDataValues();
        List busTrips = ((GlobalState)getApplication()).getModel().getAllBusTrips();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , busTrips);
        setListAdapter(adapter);

    }
    public void saveBusData(View view) {
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();

        EditText text =(EditText) findViewById(R.id.editText1);
        EditText text2 = (EditText) findViewById(R.id.editText2);
        EditText text3 = (EditText) findViewById(R.id.editText3);
        DataValue value = new DataValue();
        value.addValues(text.getText().toString(), text2.getText().toString(), text3.getText().toString());
        sqLiteDataSourceOpertions.saveData(value);

        adapter.add(value);

    }
    public void deleteBusData(View view) {
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        DataValue value = null;
        if (getListAdapter().getCount() > 0) {

            value = (DataValue) getListAdapter().getItem(0);
            sqLiteDataSourceOpertions.deleteData(value);
            adapter.remove(value);
        }
    }
    @Override
            protected void onResume(){
            sqLiteDataSourceOpertions.open();
            super.onResume();
        }
    @Override
            protected void onPause(){

            sqLiteDataSourceOpertions.close();
            super.onPause();
        }


}
