package de.drkalz.midwifesearchbl.demand;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.drkalz.midwifesearchbl.R;
import de.drkalz.midwifesearchbl.StartApp;
import de.drkalz.midwifesearchbl.dataObjects.Request;

public class MapRequest extends FragmentActivity implements OnMapReadyCallback {

    final StartApp sApp = StartApp.getInstance();
    Date newDate;
    Double lat, lng;
    CalendarView dateOfBirth;
    CheckBox sendRequest;
    String requestID;
    private GoogleMap mMap;

    public void saveRequest() {
        Request newRequest = new Request();
        newRequest.setDateOfBirth(newDate);
        newRequest.setOwnerId(sApp.getUserID());
        newRequest.setMidwifeID("");

        newRequest.saveAsync(new AsyncCallback<Request>() {
            @Override
            public void handleResponse(Request response) {
                requestID = response.getObjectId();
                Toast.makeText(getApplicationContext(), "Request gesendet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_request);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mMap = mapFragment.getMap();

        GeoPoint homeGeopoint = (GeoPoint) sApp.getCurrentUser().getProperty("homeGeoPoint");
        lat = homeGeopoint.getLatitude();
        lng = homeGeopoint.getLongitude();
        LatLng geoPoint = new LatLng(lat, lng);
        mMap.addMarker(new MarkerOptions()
                .position(geoPoint)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                .title("Homepoint of " + sApp.getFullUsername()));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, 10));
        dateOfBirth = (CalendarView) findViewById(R.id.cv_dateOfBirth);
        sendRequest = (CheckBox) findViewById(R.id.cb_sendRequest);
        requestID = "";

        String whereClause = "ownerId='" + sApp.getUserID() + "'";
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);

        Backendless.Persistence.of(Request.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Request>>() {
            @Override
            public void handleResponse(BackendlessCollection<Request> response) {
                if (response.getCurrentPage() != null) {
                    for (Request request : response.getCurrentPage()) {
                        newDate = new Date(request.getDateOfBirth().getTime());
                        requestID = request.getObjectId();
                        dateOfBirth.setDate(request.getDateOfBirth().getTime());
                        sendRequest.setChecked(true);
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });

        dateOfBirth.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String puffer = String.valueOf(dayOfMonth) + "/" + String.valueOf(month + 1)
                        + "/" + String.valueOf(year);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    newDate = dateFormat.parse(puffer);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (sendRequest.isChecked() && !requestID.matches("")) {
                    Request updateRequest = new Request();
                    updateRequest.setObjectId(requestID);
                    updateRequest.setDateOfBirth(newDate);
                    updateRequest.setOwnerId(sApp.getUserID());
                    updateRequest.saveAsync(new AsyncCallback<Request>() {
                        @Override
                        public void handleResponse(Request response) {
                            Toast.makeText(MapRequest.this, "Die Terminänderung wurde gespeichert.", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }
            }
        });

        sendRequest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && requestID.matches("")) {
                    saveRequest();
                } else if (!isChecked && !requestID.matches("")) {
                    Request deleteRequest = new Request();
                    deleteRequest.setObjectId(requestID);
                    deleteRequest.removeAsync(new AsyncCallback<Long>() {
                        @Override
                        public void handleResponse(Long response) {
                            Toast.makeText(getApplicationContext(), "Request wurde gelöscht", Toast.LENGTH_SHORT).show();
                            requestID = "";
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                        }
                    });
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
