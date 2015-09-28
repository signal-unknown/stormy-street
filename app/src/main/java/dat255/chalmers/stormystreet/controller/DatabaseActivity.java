package dat255.chalmers.stormystreet.controller;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import java.util.List;

import dat255.chalmers.stormystreet.model.DataValue;

/**
 * Created by Ingemar on 2015-09-28.
 */
public class DatabaseActivity extends ListActivity {

    SQLiteDataSource sqLiteDataSourceOpertions;

    public DatabaseActivity(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        sqLiteDataSourceOpertions = new SQLiteDataSource(this);
        sqLiteDataSourceOpertions.open();
        List dataValues = sqLiteDataSourceOpertions.getAllDataValues();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1 , dataValues);
        setListAdapter(adapter);

    }
    private void saveBusData(View view) {

        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();

        // some text edit for testing  +????????????C/////// output

        DataValue value = sqLiteDataSourceOpertions.saveData("1010_1111_0111"); // test

        adapter.add(value);

    }
    private void deleteBusData(DataValue dataValue) {
        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        if (getListAdapter().getCount() > 0) {

            dataValue = (DataValue) getListAdapter().getItem(0);
            sqLiteDataSourceOpertions.deleteData(dataValue);
            adapter.remove(dataValue);
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
