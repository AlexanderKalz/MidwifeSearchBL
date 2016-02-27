package de.drkalz.midwifesearchbl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final EditText userEmail = (EditText) findViewById(R.id.et_email);
        final EditText userPassword = (EditText) findViewById(R.id.et_password);
        final Button loginButton = (Button) findViewById(R.id.bu_login);
        TextView registerUser = (TextView) findViewById(R.id.tv_register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            int aufrufe;
            @Override
            public void onClick(View v) {
                Backendless.UserService.login(userEmail.getText().toString(), userPassword.getText().toString(), new AsyncCallback<BackendlessUser>() {
                    @Override
                    public void handleResponse(BackendlessUser response) {
                        Toast.makeText(getApplicationContext(), "User ist angemeldet", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        switch (fault.getCode()) {
                            case "3003":
                                if (aufrufe < 3) {
                                    Toast.makeText(getApplicationContext(), "Email oder Passwort falsch!", Toast.LENGTH_LONG).show();
                                    userEmail.setText("");
                                    userPassword.setText("");
                                    aufrufe++;
                                } else {
                                    aufrufe = 0;
                                    Toast.makeText(getApplicationContext(), "Sie werden zur Registrierungsseite weitergeleitet.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                                    i.putExtra("userEmail", userEmail.getText().toString());
                                    i.putExtra("userPassword", userPassword.getText().toString());
                                    startActivity(i);
                                    finish();
                                }

                                break;
                            case "3006":
                                Toast.makeText(getApplicationContext(), "Email oder Passwort eingeben!", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                }, true);
            }
        });

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Sie werden zur Registrierungsseite weitergeleitet.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                i.putExtra("userEmail", userEmail.getText().toString());
                i.putExtra("userPassword", userPassword.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

}
