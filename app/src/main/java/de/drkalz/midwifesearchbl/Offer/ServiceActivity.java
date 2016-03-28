package de.drkalz.midwifesearchbl.offer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.text.SimpleDateFormat;
import java.util.Locale;

import de.drkalz.midwifesearchbl.MainActivity;
import de.drkalz.midwifesearchbl.R;
import de.drkalz.midwifesearchbl.StartApp;
import de.drkalz.midwifesearchbl.dataObjects.ServicePortfolio;

public class ServiceActivity extends AppCompatActivity {


    final StartApp sApp = StartApp.getInstance();
    final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN);
    ServicePortfolio cuSerPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageButton addServices = (ImageButton) findViewById(R.id.ib_add);
        final CheckBox cb_belegeburt = (CheckBox) findViewById(R.id.cb_beleggeburt);
        final CheckBox cb_geburthge = (CheckBox) findViewById(R.id.cb_geburthge);
        final CheckBox cb_geburtsvorbereitung = (CheckBox) findViewById(R.id.cb_geburtsvorbereitung);
        final CheckBox cb_hausgeburt = (CheckBox) findViewById(R.id.cb_hausgeburt);
        final CheckBox cb_schwangerenvorsorge = (CheckBox) findViewById(R.id.cb_schwangerenvorsorge);
        final CheckBox cb_rueckbildung = (CheckBox) findViewById(R.id.cb_rueckbildung);
        final CheckBox cb_wochenbett = (CheckBox) findViewById(R.id.cb_wochenbett);
        final CheckBox cb_english = (CheckBox) findViewById(R.id.cb_englisch);
        final CheckBox cb_french = (CheckBox) findViewById(R.id.cb_french);
        final CheckBox cb_spanish = (CheckBox) findViewById(R.id.cb_spanish);
        final CheckBox cb_german = (CheckBox) findViewById(R.id.cb_german);

        cuSerPort = new ServicePortfolio();

        String whereClause = "Users[hasService].objectId='" + sApp.getUserID() + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        Backendless.Persistence.of(ServicePortfolio.class).find(dataQuery, new AsyncCallback<BackendlessCollection<ServicePortfolio>>() {
            @Override
            public void handleResponse(BackendlessCollection<ServicePortfolio> response) {
                if (response != null) {
                    for (ServicePortfolio item : response.getCurrentPage()) {
                        cuSerPort = item;
                        cb_belegeburt.setChecked(cuSerPort.getBelegGeburt());
                        cb_geburthge.setChecked(cuSerPort.getGeburtHge());
                        cb_geburtsvorbereitung.setChecked(cuSerPort.getGeburtsVorberetiung());
                        cb_hausgeburt.setChecked(cuSerPort.getHausGeburt());
                        cb_schwangerenvorsorge.setChecked(cuSerPort.getSchwangerenVorsorge());
                        cb_rueckbildung.setChecked(cuSerPort.getRueckbildungsKurs());
                        cb_wochenbett.setChecked(cuSerPort.getWochenBetreuung());
                        cb_english.setChecked(cuSerPort.getEnglish());
                        cb_french.setChecked(cuSerPort.getFrench());
                        cb_spanish.setChecked(cuSerPort.getSpanish());
                        cb_german.setChecked(cuSerPort.getGerman());
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        addServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cuSerPort.setBelegGeburt(cb_belegeburt.isChecked());
                cuSerPort.setGeburtHge(cb_geburthge.isChecked());
                cuSerPort.setGeburtsVorberetiung(cb_geburtsvorbereitung.isChecked());
                cuSerPort.setHausGeburt(cb_hausgeburt.isChecked());
                cuSerPort.setRueckbildungsKurs(cb_rueckbildung.isChecked());
                cuSerPort.setWochenBetreuung(cb_wochenbett.isChecked());
                cuSerPort.setSchwangerenVorsorge(cb_schwangerenvorsorge.isChecked());
                cuSerPort.setEnglish(cb_english.isChecked());
                cuSerPort.setFrench(cb_french.isChecked());
                cuSerPort.setSpanish(cb_spanish.isChecked());
                cuSerPort.setGerman(cb_german.isChecked());

                BackendlessUser saveUser = sApp.getCurrentUser();
                saveUser.setProperty("hasService", cuSerPort);
                Backendless.UserService.update(saveUser, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getApplicationContext(), "Services gespeichert!", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Services NICHT gespechert! \n" + fault.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}