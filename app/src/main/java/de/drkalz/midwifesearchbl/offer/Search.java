package de.drkalz.midwifesearchbl.offer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.BackendlessGeoQuery;
import com.backendless.geo.GeoPoint;
import com.backendless.geo.Units;
import com.backendless.persistence.BackendlessDataQuery;
import com.backendless.persistence.QueryOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import de.drkalz.midwifesearchbl.R;
import de.drkalz.midwifesearchbl.StartApp;
import de.drkalz.midwifesearchbl.dataObjects.BlockedTime;
import de.drkalz.midwifesearchbl.dataObjects.Request;
import de.drkalz.midwifesearchbl.dataObjects.ServiceArea;

public class Search extends FragmentActivity implements OnMapReadyCallback {

    final StartApp sApp = StartApp.getInstance();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Suche die Angebotsgebiete der aktuellen Hebamme
        String whereClause = "midwifeID='" + sApp.getCurrentUser().getObjectId() + "'";
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.addRelated("servicePoint");
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(whereClause);
        dataQuery.setQueryOptions(queryOptions);
        Backendless.Persistence.of(ServiceArea.class).find(dataQuery, new AsyncCallback<BackendlessCollection<ServiceArea>>() {
            @Override
            public void handleResponse(BackendlessCollection<ServiceArea> serviceAreas) {
                if (serviceAreas.getCurrentPage() != null) {
                    for (ServiceArea area : serviceAreas.getCurrentPage()) {
                        // Suche in Abhängigkeit des Radius des aktuell geladenen Gebiets die Nachfrager aus den Geolocations 'homeGeoPoint'
                        double lat = area.getServicePoint().getLatitude();
                        double lng = area.getServicePoint().getLongitude();
                        double radius = area.getRadius();
                        BackendlessGeoQuery geoQuery = new BackendlessGeoQuery(lat, lng, radius, Units.METERS);
                        geoQuery.addCategory("homeGeoPoint");
                        String metaData = "isMidwife = 'false'";
                        geoQuery.setWhereClause(metaData);
                        geoQuery.setIncludeMeta(true);
                        Backendless.Geo.getPoints(geoQuery, new AsyncCallback<BackendlessCollection<GeoPoint>>() {
                            @Override
                            public void handleResponse(BackendlessCollection<GeoPoint> geoPoints) {
                                if (geoPoints.getCurrentPage() != null) {
                                    for (final GeoPoint customer : geoPoints.getCurrentPage()) {
                                        // Nur von den 'homeGeoPoints', die nicht von einer Hebamme sind, werden die userID's geladen und die zugehörigen Geburtsdaten geladen
                                        String whereClause = "ownerId='" + customer.getMetadata("userID") + "'";
                                        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
                                        dataQuery.setWhereClause(whereClause);
                                        Backendless.Persistence.of(Request.class).find(dataQuery, new AsyncCallback<BackendlessCollection<Request>>() {
                                            @Override
                                            public void handleResponse(BackendlessCollection<Request> requests) {
                                                if (requests.getCurrentPage() != null) {
                                                    //
                                                    for (Request geburtsDatum : requests.getCurrentPage()) {
                                                        boolean available = true;
                                                        // Lade die Abwesenheitszeiten der aktuellen Hebamme
                                                        for (BlockedTime abwesenheit : sApp.getBlockedTimes()) {
                                                            if (geburtsDatum.getDateOfBirth().after(abwesenheit.getStartOfBlock())
                                                                    && geburtsDatum.getDateOfBirth().before(abwesenheit.getEndOfBlock())) {
                                                                available = false;
                                                            }
                                                        }
                                                        if (available) {
                                                            LatLng geoPoint = new LatLng(customer.getLatitude(), customer.getLongitude());
                                                            mMap.addMarker(new MarkerOptions()
                                                                    .position(geoPoint)
                                                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                                                    .title("Nachfrage"));
                                                        }
                                                    }
                                                }
                                            }

                                            @Override
                                            public void handleFault(BackendlessFault fault) {
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                            }
                        });
                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);

        LatLng homeLatLng = new LatLng(52.518611, 13.408333);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, 10));

    }
}
