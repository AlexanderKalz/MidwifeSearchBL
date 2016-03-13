package de.drkalz.midwifesearchbl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.local.UserTokenStorageFactory;

import de.drkalz.midwifesearchbl.dataObjects.UserAddress;
import de.drkalz.midwifesearchbl.demand.MapRequest;
import de.drkalz.midwifesearchbl.offer.MidwifeArea;
import de.drkalz.midwifesearchbl.offer.ServiceActivity;
import de.drkalz.midwifesearchbl.offer.SetBlockedTime;

public class MainActivity extends AppCompatActivity {

    public final String APP_KEY = "FB722433-CE7D-F7A1-FF00-4A5A61834900";
    public final String API_KEY = "5E881F51-12A2-A115-FFA1-C2120C3B0B00";
    public final String APP_VERSION ="v1";
    public String currentUserId;
    public boolean isMidwife;
    ImageButton ibArea, ibTime, ibService, ibSearch;
    TextView tvAbwesenheit, tvArea, tvService, tvSearch;
    Button buLogout;
    TextView tvUser;
    StartApp sApp = StartApp.getInstance();

    protected void setButtons() {
        if (!sApp.isMidwife()) {
            sApp.setMidwife(false);
            tvAbwesenheit.setText("persönliche Daten ändern");
            tvArea.setText("Anfrage senden");
            tvSearch.setText("Suche Hebamme");
            tvService.setTextColor(Color.LTGRAY);
            ibService.setVisibility(View.INVISIBLE);
        } else {
            sApp.setMidwife(true);
            tvAbwesenheit.setText("Abwesenheiten planen");
            tvArea.setText("Gebiete festlegen");
            tvSearch.setText("Anfragen suchen");
            tvService.setText("Serviceportfolio ändern");
            tvService.setTextColor(Color.BLACK);
            ibService.setVisibility(View.VISIBLE);
        }
    }

    public void doSomeAction(View view) {

        int i = Integer.parseInt(view.getTag().toString());

        switch (i) {
            case 1:
                if (sApp.isMidwife()) {
                    Intent intent = new Intent(MainActivity.this, SetBlockedTime.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                    intent.putExtra("userPassword", sApp.getUserPassword());
                    intent.putExtra("changeData", true);
                    startActivity(intent);
                    finish();
                }
                break;
            case 2:
                if (sApp.isMidwife()) {
                    Intent intent = new Intent(MainActivity.this, MidwifeArea.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, MapRequest.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case 3:
                if (sApp.isMidwife()) {
                    Intent intent = new Intent(MainActivity.this, ServiceActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                }
                break;

            case 4:
                if (sApp.isMidwife()) {

                } else {

                }
                break;
            case 5:
                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {
                        sApp.setCurrentUser(null);
                        sApp.setUserAddress(null);
                        sApp.setMidwife(false);
                        sApp.setUserEmail("");
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), "Logout failed!", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ibArea = (ImageButton) findViewById(R.id.ib_Area);
        ibTime = (ImageButton) findViewById(R.id.ib_Abwesenheit);
        ibService = (ImageButton) findViewById(R.id.ib_Service);
        ibSearch = (ImageButton) findViewById(R.id.ib_search);
        buLogout = (Button) findViewById(R.id.bu_logout);
        tvAbwesenheit = (TextView) findViewById(R.id.tv_Abwesenheit);
        tvArea = (TextView) findViewById(R.id.tv_Area);
        tvService = (TextView) findViewById(R.id.tv_Service);
        tvSearch = (TextView) findViewById(R.id.tv_Search);
        tvUser = (TextView) findViewById(R.id.tv_User);

        Backendless.initApp(this, APP_KEY, API_KEY, APP_VERSION);
        sApp.setCurrentUser(Backendless.UserService.CurrentUser());
        tvUser.setText(sApp.getFullUsername());

        if (sApp.getCurrentUser() == null) {
            final String userToken = UserTokenStorageFactory.instance().getStorage().get();
            if (userToken != null && !userToken.equals("")) {
                currentUserId = Backendless.UserService.loggedInUser();
                Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Backendless.UserService.setCurrentUser(response);
                        sApp.setCurrentUser(Backendless.UserService.CurrentUser());
                        isMidwife = (boolean) response.getProperty("isMidwife");
                        sApp.setMidwife(isMidwife);
                        sApp.setUserEmail(response.getEmail());

                        String whereClause = "Users[Address].objectId='" + response.getObjectId() + "'";
                        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                        dataQuery.setWhereClause(whereClause);
                        Backendless.Persistence.of(UserAddress.class).find(dataQuery, new AsyncCallback<BackendlessCollection<UserAddress>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<UserAddress> response) {
                                for (UserAddress item : response.getCurrentPage()) {
                                    sApp.setUserAddress(item);
                                    tvUser.setText(sApp.getFullUsername());
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                            }
                        });
                        setButtons();
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), fault.getDetail(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                tvUser.setText(sApp.getFullUsername());
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (sApp.getCurrentUser() != null) {
            tvUser.setText(sApp.getFullUsername());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
