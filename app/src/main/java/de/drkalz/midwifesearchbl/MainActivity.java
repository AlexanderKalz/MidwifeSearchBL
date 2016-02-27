package de.drkalz.midwifesearchbl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserTokenStorageFactory;

public class MainActivity extends AppCompatActivity {

    public final String APP_KEY = "FB722433-CE7D-F7A1-FF00-4A5A61834900";
    public final String API_KEY = "5E881F51-12A2-A115-FFA1-C2120C3B0B00";
    public final String APP_VERSION ="v1";
    public String currentUserId;
    public boolean isMidwife;
    public BackendlessUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ImageButton ibArea = (ImageButton) findViewById(R.id.ib_Area);
        final ImageButton ibTime = (ImageButton) findViewById(R.id.ib_Abwesenheit);
        final ImageButton ibService = (ImageButton) findViewById(R.id.ib_Service);
        final ImageButton ibSearch = (ImageButton) findViewById(R.id.ib_search);
        final Button buLogout = (Button) findViewById(R.id.bu_logout);
        final TextView tvUser = (TextView) findViewById(R.id.tv_User);

        Backendless.initApp(this, APP_KEY, API_KEY, APP_VERSION);
        currentUser = Backendless.UserService.CurrentUser();

        if (currentUser == null) {
            String userToken = UserTokenStorageFactory.instance().getStorage().get();
            if (userToken != null && !userToken.equals("")) {
                currentUserId = Backendless.UserService.loggedInUser();
                Backendless.UserService.findById(currentUserId, new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Backendless.UserService.setCurrentUser(response);
                        currentUser = Backendless.UserService.CurrentUser();
                        isMidwife = (boolean) currentUser.getProperty("isMidwife");
                    }
                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(getApplicationContext(), fault.getDetail(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
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
