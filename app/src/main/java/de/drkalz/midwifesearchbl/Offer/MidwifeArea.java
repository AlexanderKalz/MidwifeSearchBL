package de.drkalz.midwifesearchbl.offer;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import de.drkalz.midwifesearchbl.R;
import de.drkalz.midwifesearchbl.StartApp;
import de.drkalz.midwifesearchbl.dataObjects.ServiceArea;

public class MidwifeArea extends FragmentActivity implements OnMapReadyCallback {

    final StartApp sApp = StartApp.getInstance();
    double radius = 5000.0;
    ArrayList<Marker> markerList = new ArrayList<>();
    ArrayList<ServiceArea> areaList = new ArrayList<>();
    ArrayList<Circle> circleList = new ArrayList<>();
    private GoogleMap mMap;

    public int getZoomLevel(Circle circle) {
        int zoom = 12;
        if (circle != null) {
            double radius = circle.getRadius();
            double scale = radius / 500;
            zoom = (int) (16 - Math.log(scale) / Math.log(2));
        }
        return zoom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midwife_area);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mMap = mapFragment.getMap();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        final LinearLayout radiusLayout = (LinearLayout) findViewById(R.id.ll_Radius);
        final EditText enterRadius = (EditText) findViewById(R.id.tv_Radius);
        radiusLayout.setVisibility(View.INVISIBLE);

        LatLng homeLatLng = new LatLng(52.518611, 13.408333);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 10));

        areaList.clear();
        markerList.clear();
        circleList.clear();

        String whereClause = "midwifeID='" + sApp.getCurrentUser().getObjectId() + "'";
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addRelated("servicePoint");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(ServiceArea.class).find(dataQuery, new AsyncCallback<BackendlessCollection<ServiceArea>>() {
            @Override
            public void handleResponse(BackendlessCollection<ServiceArea> response) {
                if (response.getCurrentPage() != null) {
                    int i = 1;
                    for (ServiceArea area : response.getCurrentPage()) {
                        areaList.add(area);
                        Double lat = area.getServicePoint().getLatitude();
                        Double lng = area.getServicePoint().getLongitude();
                        LatLng geoPoint = new LatLng(lat, lng);
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(geoPoint)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                                .title(sApp.getFullUsername() + " Nr. " + String.valueOf(i)));
                        i++;
                        markerList.add(marker);
                        radius = area.getRadius();
                        Circle circle = mMap.addCircle(new CircleOptions()
                                .center(new LatLng(lat, lng))
                                .radius(radius)
                                .strokeColor(Color.BLUE));
                        circleList.add(circle);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(geoPoint, getZoomLevel(circle) - 1));
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(MidwifeArea.this, "Es ist ein Fehler aufgetreten: \n" + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(final LatLng latLng) {

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA))
                        .title(sApp.getFullUsername() + " Nr. " + String.valueOf(markerList.size() + 1)));
                markerList.add(marker);

                radiusLayout.setVisibility(View.VISIBLE);

                enterRadius.setText("");
                enterRadius.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if (keyCode == KeyEvent.KEYCODE_ENTER) {
                            radius = Double.parseDouble(enterRadius.getText().toString()) * 1000;
                            Circle circle = mMap.addCircle(new CircleOptions()
                                    .center(latLng)
                                    .radius(radius)
                                    .strokeColor(Color.BLUE));
                            circleList.add(circle);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, getZoomLevel(circle) - 1));

                            GeoPoint newServicePoint = new GeoPoint();
                            newServicePoint.addCategory("serviceArea");
                            newServicePoint.addMetadata("userID", sApp.getUserID());
                            newServicePoint.addMetadata("isMidwife", "true");
                            newServicePoint.addMetadata("radius", radius);
                            newServicePoint.setLatitude(latLng.latitude);
                            newServicePoint.setLongitude(latLng.longitude);

                            final ServiceArea newArea = new ServiceArea();
                            newArea.setRadius(radius);
                            newArea.setMidwifeID(sApp.getCurrentUser().getObjectId());
                            newArea.setServicePoint(newServicePoint);
                            newArea.saveAsync(new AsyncCallback<ServiceArea>() {
                                @Override
                                public void handleResponse(ServiceArea response) {
                                    areaList.add(newArea);
                                    areaList.get(areaList.indexOf(newArea)).setObjectId(response.getObjectId());
                                    Toast.makeText(MidwifeArea.this, "Gebiet wurde gespeichert.", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(MidwifeArea.this, "Es trat ein Fehler auf: \n" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            radiusLayout.setVisibility(View.INVISIBLE);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                final int position = markerList.indexOf(marker);

                if (marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    marker.showInfoWindow();
                    new AlertDialog.Builder(MidwifeArea.this)
                            .setTitle("Gebiet löschen?")
                            .setMessage("Wollen Sie dieses Gebiet löschen oder den Radius ändern?")
                            .setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    GeoPoint toDeleteGeo = areaList.get(position).getServicePoint();
                                    Backendless.Geo.removePoint(toDeleteGeo, new AsyncCallback<Void>() {
                                        @Override
                                        public void handleResponse(Void response) {

                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {

                                        }
                                    });
                                    areaList.get(position).removeAsync(new AsyncCallback<Long>() {
                                        @Override
                                        public void handleResponse(Long response) {
                                            Circle removeCircle = circleList.get(position);
                                            removeCircle.remove();
                                            circleList.remove(position);
                                            Marker removeMarker = markerList.get(position);
                                            removeMarker.remove();
                                            markerList.remove(position);
                                            Toast.makeText(MidwifeArea.this, "Gebiet wurde gelöscht", Toast.LENGTH_SHORT).show();
                                        }

                                        @Override
                                        public void handleFault(BackendlessFault fault) {
                                            Toast.makeText(MidwifeArea.this, "Gebiet konnte nicht gelöscht werden: \n" + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                            .setNegativeButton("Radius ändern", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    radiusLayout.setVisibility(View.VISIBLE);
                                    enterRadius.setText("");
                                    enterRadius.setOnKeyListener(new View.OnKeyListener() {
                                        @Override
                                        public boolean onKey(View v, int keyCode, KeyEvent event) {
                                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                                Circle changeCircle = circleList.get(position);
                                                ServiceArea changeArea = areaList.get(position);
                                                radius = Double.parseDouble(enterRadius.getText().toString()) * 1000;
                                                Circle circle = mMap.addCircle(new CircleOptions()
                                                        .center(changeCircle.getCenter())
                                                        .radius(radius)
                                                        .strokeColor(Color.BLUE));
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(changeCircle.getCenter(), getZoomLevel(circle) - 1));
                                                changeArea.setRadius(radius);
                                                changeArea.saveAsync();
                                            }
                                            radiusLayout.setVisibility(View.INVISIBLE);
                                            return true;
                                        }
                                    });
                                }
                            })
                            .setNeutralButton("Abbrechen", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                return true;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
    }
}

