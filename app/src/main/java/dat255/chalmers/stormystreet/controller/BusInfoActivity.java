package dat255.chalmers.stormystreet.controller;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import dat255.chalmers.stormystreet.R;
import dat255.chalmers.stormystreet.model.CurrentTrip;

/**
 * Created by DavidF on 2015-10-01.
 */
public class BusInfoActivity extends Activity {

     public TextView info;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_info);

      info = (TextView) findViewById(R.id.BusInfoView);

        info.setText("lol");
        new Thread(new UpdateInfo()).start();

    }
    public void getInfo() {

        Random rnd = new Random();

        int randomID = rnd.nextInt(4);

        switch(randomID) {

            case 0:
                info.setText("BUS INFO HERE!");
                break;

            case 1:
                info.setText("Plattformen bygger på mjukvara från Ericsson och kan skalas upp utanför demoarenan." +
                        " Det innebär att nya datakällor inom eller utom ElectriCity kan läggas till kontinuerligt under 2015-2018 och även efter det, om plattformen fortlever. " +
                        "Utvecklingen av ElectriCitys innovationsplattform kan därmed lägga grunden till en regional innovationsplattform som stärker innovation" +
                        " och samverkan i regionen i allmänhet och i framtida samverkansprojekt i synnerhet. ");
                break;

            case 2:
                info.setText("Framtagandet av plattformen sker i ett samverkansprojekt finansierat av Regionutvecklingsnämnden, Västra Götalandsregionen.\n" +
                        "\n" +
                        "Parter i projektet är Chalmers, Cybercom, Ericsson, Icomera, Interactive Swedish ICT, Keolis, Lindholmen Science Park, Pilotfish, Viktoria Swedish ICT, Volvo Bussar och Västtrafik.");
                break;

            case 3:
                info.setText("Innovationsplattformen tillhandahåller framförallt information om de el- och hybridbussarna som trafikerar linje 55 samt om dess hållplatser, " +
                        "men även om kollektivtrafiken i Västra Götalandsregionen i allmänhet. " +
                        "Informationen kan bl.a användas som underlag för att skapa koncept och prototyper till Electricity Innovation Challenge 2015");
                break;


        }

    }
    class UpdateInfo implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 10; i++) {

                try {

                    Thread.sleep(2000);  // 2 second
                    runOnUiThread(new Runnable() {   // for modifying view
                        @Override
                        public void run() {

                            getInfo();

                        }
                    });

                } catch (InterruptedException e) {

                    e.printStackTrace();

                }
                // do stuff here
                Log.d("Bus info: ", "....................................." );

            }
        }

    }

}
