package de.drkalz.midwifesearchbl;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import de.drkalz.midwifesearchbl.dataObjects.UserAddress;

public class RegisterActivity extends AppCompatActivity {

    final StartApp sApp = StartApp.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();

        final Boolean[] isMidwife = {false};
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final EditText cuFirstname = (EditText) findViewById(R.id.et_firstname);
        final EditText cuName = (EditText) findViewById(R.id.et_name);
        final EditText cuStreet = (EditText) findViewById(R.id.et_street);
        final EditText cuCity = (EditText) findViewById(R.id.et_city);
        final EditText cuCountry = (EditText) findViewById(R.id.et_country);
        final EditText cuZip = (EditText) findViewById(R.id.et_zip);
        final EditText cuTelefon = (EditText) findViewById(R.id.et_telefon);
        final EditText cuMobil = (EditText) findViewById(R.id.et_mobil);
        final EditText cuHomepage = (EditText) findViewById(R.id.et_homepage);
        final EditText cuEmail = (EditText) findViewById(R.id.et_email);
        final EditText cuPassword = (EditText) findViewById(R.id.et_password);
        final Switch cuIsMidwife = (Switch) findViewById(R.id.sw_isMidwife);

        Intent i = getIntent();
        cuEmail.setText(i.getStringExtra("userEmail"));
        cuPassword.setText(i.getStringExtra("userPassword"));
        final boolean changeData = i.getBooleanExtra("changeData", false);

        if (changeData) {
            UserAddress currentUser = sApp.getUserAddress();
            cuFirstname.setText(currentUser.getFirstname());
            cuName.setText(currentUser.getLastname());
            cuStreet.setText(currentUser.getStreet());
            cuZip.setText(Integer.toString(currentUser.getZip()));
            cuCity.setText(currentUser.getCity());
            cuCountry.setText(currentUser.getCountry());
            cuTelefon.setText(currentUser.getTelefon());
            cuMobil.setText(currentUser.getMobil());
            cuHomepage.setText(currentUser.getHomepage());
            cuIsMidwife.setChecked(sApp.isMidwife());
            cuEmail.setText(sApp.getUserEmail());
        }

        cuIsMidwife.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isMidwife[0] = true;
                    cuIsMidwife.setText("Ja");

                } else {
                    isMidwife[0] = false;
                    cuIsMidwife.setText("Nein");
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cuFirstname.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Vorname nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuName.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Nachname nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuStreet.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Straße nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuCity.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Stadt nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuCountry.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Land nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuZip.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Postleitzahl nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuTelefon.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Festnetz nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuEmail.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Email (Benutzername) nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cuPassword.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), "Passwort nicht eingetragen!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Snackbar.make(view, "Sie werden registriert!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    BackendlessUser user = new BackendlessUser();
                    user.setEmail(cuEmail.getText().toString());
                    user.setPassword(cuPassword.getText().toString());
                    user.setProperty("isMidwife", isMidwife[0]);

                    final UserAddress userAddress = new UserAddress();
                    userAddress.setFirstname(cuFirstname.getText().toString());
                    userAddress.setLastname(cuName.getText().toString());
                    userAddress.setStreet(cuStreet.getText().toString());
                    userAddress.setCity(cuCity.getText().toString());
                    userAddress.setCountry(cuCountry.getText().toString());
                    userAddress.setZip(Integer.parseInt(cuZip.getText().toString()));
                    userAddress.setTelefon(cuTelefon.getText().toString());
                    userAddress.setMobil(cuMobil.getText().toString());
                    userAddress.setHomepage(cuHomepage.getText().toString());
                    user.setProperty("Address", userAddress);

                    final String eMail = cuEmail.getText().toString();
                    final String passWord = cuPassword.getText().toString();
                    sApp.setUserEmail(eMail);
                    sApp.setUserPassword(passWord);
                    sApp.setUserAddress(userAddress);

                    if (!changeData) {
                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(getApplicationContext(), userAddress.getFirstname() + " " + userAddress.getLastname() + " wurde registriert!", Toast.LENGTH_LONG).show();
                                sApp.setCurrentUser(response);
                                Backendless.UserService.login(eMail, passWord, new AsyncCallback<BackendlessUser>() {
                                    @Override
                                    public void handleResponse(BackendlessUser response) {
                                        Toast.makeText(getApplicationContext(), "Login erfolgreich!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                    }

                                    @Override
                                    public void handleFault(BackendlessFault fault) {
                                        Toast.makeText(getApplicationContext(), "Der Login ging schief: \n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }, true);
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(getApplicationContext(), "Die Registrierung lief schief: \n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(getApplicationContext(), sApp.getFullUserName() + " wurde aktualisiert", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(getApplicationContext(), sApp.getFullUserName()
                                        + " nicht aktualisiert\n" + fault.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

            }
        });

    }




}
