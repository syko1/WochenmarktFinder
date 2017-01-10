package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.mylocation.SimpleLocationOverlay;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pujan on 06.01.17.
 */
//Offline Karte mit Default Marker, 1 Marker pro Bezirk
public class OnlineActivity extends AppCompatActivity implements LocationListener, View.OnClickListener {

    private Button btn_gps;
    private LocationManager locationManger;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111;
    private GeoPoint geoPoint;
    private MapView mMapView;
    private MapController mc;
    private SimpleLocationOverlay mMyLocationOverlay;
    private ScaleBarOverlay mScaleBarOverlay;
    ArrayList<OverlayItem> overlayItemArray;
    //reference of FilterActivity
    FilterActivity filter = new FilterActivity();
    //is disrict selected?
    private Boolean bBChecked = false;
    private Boolean cWChecked = false;
    private Boolean fKChecked = false;
    private Boolean kChecked = false;
    private Boolean lChecked = false;
    private Boolean mHChecked = false;
    private Boolean mChecked = false;
    private Boolean nKChecked = false;
    private Boolean pChecked = false;
    private Boolean rDChecked = false;
    private Boolean sChecked = false;
    private Boolean sZChecked = false;
    private Boolean tSChecked = false;
    private Boolean tKChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //------Jparser
        itemList = new ArrayList<>();
        itemListBrandenburg = new ArrayList<>();
        itemListCharlottenburgWilmersdorf = new ArrayList<>();
        itemListFriedrichshainKreuzberg = new ArrayList<>();
        itemListKlaistow = new ArrayList<>();
        itemListLichtenberg = new ArrayList<>();
        itemListMarzahnHellersdorf = new ArrayList<>();
        itemListMitte = new ArrayList<>();
        itemListNeukölln = new ArrayList<>();
        itemListPankow = new ArrayList<>();
        itemListReinickendorf = new ArrayList<>();
        itemListSpandau = new ArrayList<>();
        itemListSteglitzZehlendorf = new ArrayList<>();
        itemListTempelhofSchöneberg = new ArrayList<>();
        itemListTreptowKöpenick = new ArrayList<>();

        lv = (ListView) findViewById(R.id.btn_list);

        new OnlineActivity.GetItems().execute();
        //---------

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants.setUserAgentValue(BuildConfig.APPLICATION_ID);

        mMapView = (MapView) findViewById(R.id.map);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);

        mMapView.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);

        mc = (MapController) mMapView.getController();
        mc.setZoom(11);

        geoPoint = new GeoPoint(52.520007, 13.404954);
        mc.animateTo(geoPoint);

        overlayItemArray = new ArrayList<OverlayItem>();
        //// Add the Array to the IconOverlay
        ItemizedIconOverlay<OverlayItem> itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlayItemArray, null);

        // Add the overlay to the MapView
        mMapView.getOverlays().add(itemizedIconOverlay);

        btn_gps = (Button) findViewById(R.id.btn_gps);
        btn_gps.setOnClickListener(this);

        //show only the selected districts referring to FilterActivity (checkboxes)
        //check if user comes from FilterActivity
        bBChecked = false;
        cWChecked = false;
        bBChecked = false;
        cWChecked = false;
        fKChecked = false;
        kChecked = false;
        lChecked = false;
        mHChecked = false;
        mChecked = false;
        nKChecked = false;
        pChecked = false;
        rDChecked = false;
        sChecked = false;
        sZChecked = false;
        tSChecked = false;
        tKChecked = false;


        if (filter.comesFromFilter == true){
            //check if districts are selected or not
            if (filter.bBurg.isChecked()) bBChecked = true;
            else bBChecked = false;

            if (filter.cBurgWilmers.isChecked()) cWChecked = true;
            else cWChecked = false;

            if (filter.fHainKberg.isChecked()) fKChecked = true;
            else fKChecked = false;

            if (filter.klaistow.isChecked()) kChecked = true;
            else kChecked = false;

            if (filter.lBerg.isChecked()) lChecked = true;
            else lChecked = false;

            if (filter.mZahnHdorf.isChecked()) mHChecked = true;
            else mHChecked = false;

            if (filter.mitte.isChecked()) mChecked = true;
            else mChecked = false;

            if (filter.nKölln.isChecked()) nKChecked = true;
            else nKChecked = false;

            if (filter.pankow.isChecked()) pChecked = true;
            else pChecked = false;

            if (filter.rDorf.isChecked()) rDChecked = true;
            else rDChecked = false;

            if (filter.spandau.isChecked()) sChecked = true;
            else sChecked = false;

            if (filter.sLitzZdorf.isChecked()) sZChecked = true;
            else sZChecked = false;

            if (filter.tHofSberg.isChecked()) tSChecked = true;
            else tSChecked = false;

            if (filter.tTowKnick.isChecked()) tKChecked = true;
            else tKChecked = false;
        }
    }

    public void addMarket(GeoPoint center) {
        Marker marker = new Marker(mMapView);
        marker.setPosition(center);

        mMapView.getOverlays().add(marker);
        mMapView.invalidate();
    }

    public void onLocationChanged(Location location) {
        double latitude=52.520007;
        double longitude=13.404954;
        GeoPoint center = new GeoPoint(location.getLatitude(), location.getLongitude());
        mc.animateTo(center);
        mc.setZoom(20);
        addMarket(center);



    }
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    public void onProviderEnabled(String provider) {}
    public void onProviderDisabled(String provider) {}
    public void onDestroy() {
        super.onDestroy();
        if (locationManger != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManger.removeUpdates(this);
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_gps:
                boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
                if (!hasPermissionLocation) {
                    ActivityCompat.requestPermissions(OnlineActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_ACCESS_FINE_LOCATION);
                }

                locationManger = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                locationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);


                break;
        }

    }

    //----------------------------


    private String TAG = OnlineActivity.class.getSimpleName();
    private ListView lv;

    //ArrayList for bezirke
    ArrayList<HashMap<String, String>> itemListBrandenburg;
    ArrayList<HashMap<String, String>> itemListCharlottenburgWilmersdorf;
    ArrayList<HashMap<String, String>> itemListFriedrichshainKreuzberg;
    ArrayList<HashMap<String, String>> itemListKlaistow;
    ArrayList<HashMap<String, String>> itemListLichtenberg;
    ArrayList<HashMap<String, String>> itemListMarzahnHellersdorf;
    ArrayList<HashMap<String, String>> itemListMitte;
    ArrayList<HashMap<String, String>> itemListNeukölln;
    ArrayList<HashMap<String, String>> itemListPankow;
    ArrayList<HashMap<String, String>> itemListReinickendorf;
    ArrayList<HashMap<String, String>> itemListSpandau;
    ArrayList<HashMap<String, String>> itemListSteglitzZehlendorf;
    ArrayList<HashMap<String, String>> itemListTempelhofSchöneberg;
    ArrayList<HashMap<String, String>> itemListTreptowKöpenick;
    ArrayList<HashMap<String, String>> itemList;

    private class GetItems extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(OnlineActivity.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://www.berlin.de/sen/wirtschaft/service/maerkte-feste/wochen-troedelmaerkte/index.php/index/all.json?q=";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray index = jsonObj.getJSONArray("index");

                    // looping through All items
                    // create markers for all districts
                    for (int i = 0; i < index.length(); i++) {

                        JSONObject c = index.getJSONObject(i);
                        String id = c.getString("id");
                        String bezirk = c.getString("bezirk");
                        String location = c.getString("location");
                        String latitude = c.getString("latitude");
                        String longitude = c.getString("longitude");
                        String tage = c.getString("tage");
                        String zeiten = c.getString("zeiten");
                        String betreiber = c.getString("betreiber");
                        String email = c.getString("email");
                        String web = c.getString("www");
                        String bemerkungen = c.getString("bemerkungen");

                        // tmp hash map for single items
                        //note: double bezirke names are divided with "-" in json
                        HashMap<String, String> items = new HashMap<>();
                        HashMap<String, String> itemsBrandenburg = new HashMap<>();
                        HashMap<String, String> itemsCharlottenburgWilmersdorf = new HashMap<>();
                        HashMap<String, String> itemsFriedrichshainKreuzberg = new HashMap<>();
                        HashMap<String, String> itemsKlaistow = new HashMap<>();
                        HashMap<String, String> itemsLichtenberg = new HashMap<>();
                        HashMap<String, String> itemsMarzahnHellersdorf = new HashMap<>();
                        HashMap<String, String> itemsMitte = new HashMap<>();
                        HashMap<String, String> itemsNeukölln = new HashMap<>();
                        HashMap<String, String> itemsPankow = new HashMap<>();
                        HashMap<String, String> itemsReinickendorf = new HashMap<>();
                        HashMap<String, String> itemsSpandau = new HashMap<>();
                        HashMap<String, String> itemsSteglitzZehlendorf = new HashMap<>();
                        HashMap<String, String> itemsTempelhofSchöneberg = new HashMap<>();
                        HashMap<String, String> itemsTreptowKöpenick = new HashMap<>();

                        if (bezirk.equals("Brandenburg") ) {
                            if (bBChecked == true) {
                                itemsBrandenburg.put("id", id);
                                itemsBrandenburg.put("bezirk", bezirk);
                                itemsBrandenburg.put("location", location);
                                itemsBrandenburg.put("latitude", latitude);
                                itemsBrandenburg.put("longitude", longitude);
                                itemsBrandenburg.put("tage", tage);
                                itemsBrandenburg.put("zeiten", zeiten);
                                itemsBrandenburg.put("betreiber", betreiber);
                                itemsBrandenburg.put("email", email);
                                itemsBrandenburg.put("www", web);
                                itemsBrandenburg.put("bemerkungen", bemerkungen);
                                //replace "," with "." in "latitude" and "longitude" for correct parsing
                                String lati = latitude.replaceAll(",", ".");
                                Double lat = Double.parseDouble(lati);
                                String lngi = longitude.replaceAll(",", ".");
                                Double lng = Double.parseDouble(lngi);
                                //set markers
                                OverlayItem bBurgItem = new OverlayItem("Brandenburg", "Berlin",
                                        new GeoPoint(lat, lng));
                                overlayItemArray.add(bBurgItem);
                            }
                        }

                        if ( bezirk.equals("Charlottenburg-Wilmersdorf")  ) {
                            if (cWChecked == true) {
                                itemsCharlottenburgWilmersdorf.put("id", id);
                                itemsCharlottenburgWilmersdorf.put("bezirk", bezirk);
                                itemsCharlottenburgWilmersdorf.put("location", location);
                                itemsCharlottenburgWilmersdorf.put("latitude", latitude);
                                itemsCharlottenburgWilmersdorf.put("longitude", longitude);
                                itemsCharlottenburgWilmersdorf.put("tage", tage);
                                itemsCharlottenburgWilmersdorf.put("zeiten", zeiten);
                                itemsCharlottenburgWilmersdorf.put("betreiber", betreiber);
                                itemsCharlottenburgWilmersdorf.put("email", email);
                                itemsCharlottenburgWilmersdorf.put("www", web);
                                itemsCharlottenburgWilmersdorf.put("bemerkungen", bemerkungen);

                                //replace "," with "." in "latitude" and "longitude" for correct parsing
                                String lati = latitude.replaceAll(",", ".");
                                Double lat = Double.parseDouble(lati);
                                String lngi = longitude.replaceAll(",", ".");
                                Double lng = Double.parseDouble(lngi);
                                //set markers
                                OverlayItem cBurgWdorfItem = new OverlayItem("Charlottenburg-Wilmersdorf", "Berlin",
                                        new GeoPoint(lat, lng));
                                overlayItemArray.add(cBurgWdorfItem);
                            }
                        }
                            if (bezirk.equals("Friedrichshain-Kreuzberg")) {
                                if(fKChecked == true) {
                                    itemsFriedrichshainKreuzberg.put("id", id);
                                    itemsFriedrichshainKreuzberg.put("bezirk", bezirk);
                                    itemsFriedrichshainKreuzberg.put("location", location);
                                    itemsFriedrichshainKreuzberg.put("latitude", latitude);
                                    itemsFriedrichshainKreuzberg.put("longitude", longitude);
                                    itemsFriedrichshainKreuzberg.put("tage", tage);
                                    itemsFriedrichshainKreuzberg.put("zeiten", zeiten);
                                    itemsFriedrichshainKreuzberg.put("betreiber", betreiber);
                                    itemsFriedrichshainKreuzberg.put("email", email);
                                    itemsFriedrichshainKreuzberg.put("www", web);
                                    itemsFriedrichshainKreuzberg.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem fHainKbergItem = new OverlayItem("Friedrichshain-Kreuzberg", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(fHainKbergItem);
                                }
                            }

                            if (bezirk.equals("Klaistow")) {
                                if(kChecked == true) {
                                    itemsKlaistow.put("id", id);
                                    itemsKlaistow.put("bezirk", bezirk);
                                    itemsKlaistow.put("location", location);
                                    itemsKlaistow.put("latitude", latitude);
                                    itemsKlaistow.put("longitude", longitude);
                                    itemsKlaistow.put("tage", tage);
                                    itemsKlaistow.put("zeiten", zeiten);
                                    itemsKlaistow.put("betreiber", betreiber);
                                    itemsKlaistow.put("email", email);
                                    itemsKlaistow.put("www", web);
                                    itemsKlaistow.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem klaistowItem = new OverlayItem("Klaistow", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(klaistowItem);
                                }
                            }

                            if (bezirk.equals("Lichtenberg")) {
                                if(lChecked == true) {
                                    itemsLichtenberg.put("id", id);
                                    itemsLichtenberg.put("bezirk", bezirk);
                                    itemsLichtenberg.put("location", location);
                                    itemsLichtenberg.put("latitude", latitude);
                                    itemsLichtenberg.put("longitude", longitude);
                                    itemsLichtenberg.put("tage", tage);
                                    itemsLichtenberg.put("zeiten", zeiten);
                                    itemsLichtenberg.put("betreiber", betreiber);
                                    itemsLichtenberg.put("email", email);
                                    itemsLichtenberg.put("www", web);
                                    itemsLichtenberg.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem lBergItem = new OverlayItem("Lichtenberg", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(lBergItem);
                                }
                            }

                            if (bezirk.equals("Marzahn-Hellersdorf")) {
                                if(mHChecked == true) {
                                    itemsMarzahnHellersdorf.put("id", id);
                                    itemsMarzahnHellersdorf.put("bezirk", bezirk);
                                    itemsMarzahnHellersdorf.put("location", location);
                                    itemsMarzahnHellersdorf.put("latitude", latitude);
                                    itemsMarzahnHellersdorf.put("longitude", longitude);
                                    itemsMarzahnHellersdorf.put("tage", tage);
                                    itemsMarzahnHellersdorf.put("zeiten", zeiten);
                                    itemsMarzahnHellersdorf.put("betreiber", betreiber);
                                    itemsMarzahnHellersdorf.put("email", email);
                                    itemsMarzahnHellersdorf.put("www", web);
                                    itemsMarzahnHellersdorf.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem mZahnHdorfItem = new OverlayItem("Marzahn-Hellersdorf", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(mZahnHdorfItem);
                                }
                            }

                            if (bezirk.equals("Mitte")) {
                                if(mChecked == true) {
                                    itemsMitte.put("id", id);
                                    itemsMitte.put("bezirk", bezirk);
                                    itemsMitte.put("location", location);
                                    itemsMitte.put("latitude", latitude);
                                    itemsMitte.put("longitude", longitude);
                                    itemsMitte.put("tage", tage);
                                    itemsMitte.put("zeiten", zeiten);
                                    itemsMitte.put("betreiber", betreiber);
                                    itemsMitte.put("email", email);
                                    itemsMitte.put("www", web);
                                    itemsMitte.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem mitteItem = new OverlayItem("Mitte", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(mitteItem);
                                }
                            }

                            if (bezirk.equals("Neukölln")) {
                                if(nKChecked == true) {
                                    itemsNeukölln.put("id", id);
                                    itemsNeukölln.put("bezirk", bezirk);
                                    itemsNeukölln.put("location", location);
                                    itemsNeukölln.put("latitude", latitude);
                                    itemsNeukölln.put("longitude", longitude);
                                    itemsNeukölln.put("tage", tage);
                                    itemsNeukölln.put("zeiten", zeiten);
                                    itemsNeukölln.put("betreiber", betreiber);
                                    itemsNeukölln.put("email", email);
                                    itemsNeukölln.put("www", web);
                                    itemsNeukölln.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem nKöllnItem = new OverlayItem("Neukölln", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(nKöllnItem);
                                }
                            }

                            if (bezirk.equals("Reinickendorf")) {
                                if(rDChecked == true) {
                                    itemsReinickendorf.put("id", id);
                                    itemsReinickendorf.put("bezirk", bezirk);
                                    itemsReinickendorf.put("location", location);
                                    itemsReinickendorf.put("latitude", latitude);
                                    itemsReinickendorf.put("longitude", longitude);
                                    itemsReinickendorf.put("tage", tage);
                                    itemsReinickendorf.put("zeiten", zeiten);
                                    itemsReinickendorf.put("betreiber", betreiber);
                                    itemsReinickendorf.put("email", email);
                                    itemsReinickendorf.put("www", web);
                                    itemsReinickendorf.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem rDorfItem = new OverlayItem("Reinickendorf", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(rDorfItem);
                                }
                            }

                            if (bezirk.equals("Pankow")) {
                                if(pChecked == true) {
                                    itemsPankow.put("id", id);
                                    itemsPankow.put("bezirk", bezirk);
                                    itemsPankow.put("location", location);
                                    itemsPankow.put("latitude", latitude);
                                    itemsPankow.put("longitude", longitude);
                                    itemsPankow.put("tage", tage);
                                    itemsPankow.put("zeiten", zeiten);
                                    itemsPankow.put("betreiber", betreiber);
                                    itemsPankow.put("email", email);
                                    itemsPankow.put("www", web);
                                    itemsPankow.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem pankowItem = new OverlayItem("Pankow", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(pankowItem);
                                }
                            }

                            if (bezirk.equals("Spandau")) {
                                if(sChecked == true) {
                                    itemsSpandau.put("id", id);
                                    itemsSpandau.put("bezirk", bezirk);
                                    itemsSpandau.put("location", location);
                                    itemsSpandau.put("latitude", latitude);
                                    itemsSpandau.put("longitude", longitude);
                                    itemsSpandau.put("tage", tage);
                                    itemsSpandau.put("zeiten", zeiten);
                                    itemsSpandau.put("betreiber", betreiber);
                                    itemsSpandau.put("email", email);
                                    itemsSpandau.put("www", web);
                                    itemsSpandau.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem spandauItem = new OverlayItem("Spandau", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(spandauItem);
                                }
                            }

                            if (bezirk.equals("Steglitz-Zehlendorf")) {
                                if(sZChecked == true) {
                                    itemsSteglitzZehlendorf.put("id", id);
                                    itemsSteglitzZehlendorf.put("bezirk", bezirk);
                                    itemsSteglitzZehlendorf.put("location", location);
                                    itemsSteglitzZehlendorf.put("latitude", latitude);
                                    itemsSteglitzZehlendorf.put("longitude", longitude);
                                    itemsSteglitzZehlendorf.put("tage", tage);
                                    itemsSteglitzZehlendorf.put("zeiten", zeiten);
                                    itemsSteglitzZehlendorf.put("betreiber", betreiber);
                                    itemsSteglitzZehlendorf.put("email", email);
                                    itemsSteglitzZehlendorf.put("www", web);
                                    itemsSteglitzZehlendorf.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem sLitzZdorfItem = new OverlayItem("Steglitz-Zehlendorf", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(sLitzZdorfItem);
                                }
                            }
                            //1 markt has no values for "latitude" and "longitude" in Json
                            if( bezirk.equals("Tempelhof-Schöneberg") && !id.equals("303")  ){
                                itemsTempelhofSchöneberg.put("id", id);
                                itemsTempelhofSchöneberg.put("bezirk", bezirk);
                                itemsTempelhofSchöneberg.put("location", location);
                                itemsTempelhofSchöneberg.put("latitude", latitude);
                                itemsTempelhofSchöneberg.put("longitude", longitude);
                                itemsTempelhofSchöneberg.put("tage", tage);
                                itemsTempelhofSchöneberg.put("zeiten", zeiten);
                                itemsTempelhofSchöneberg.put("betreiber", betreiber);
                                itemsTempelhofSchöneberg.put("email", email);
                                itemsTempelhofSchöneberg.put("www", web);
                                itemsTempelhofSchöneberg.put("bemerkungen", bemerkungen);
                                //replace "," with "." in "latitude" and "longitude" for correct parsing
                                String lati = latitude.replaceAll("," , ".");
                                Double lat = Double.parseDouble(lati);
                                String lngi = longitude.replaceAll("," , ".");
                                Double lng = Double.parseDouble(lngi);
                                //set markers
                                OverlayItem tHofSbergItem = new OverlayItem("Tempelhof-Schöneberg", "Berlin",
                                        new GeoPoint(lat, lng) );
                                overlayItemArray.add(tHofSbergItem);
                             }

                            if (bezirk.equals("Treptow-Köpenick") ) {
                                if(tKChecked == true) {
                                    itemsTreptowKöpenick.put("id", id);
                                    itemsTreptowKöpenick.put("bezirk", bezirk);
                                    itemsTreptowKöpenick.put("location", location);
                                    itemsTreptowKöpenick.put("latitude", latitude);
                                    itemsTreptowKöpenick.put("longitude", longitude);
                                    itemsTreptowKöpenick.put("tage", tage);
                                    itemsTreptowKöpenick.put("zeiten", zeiten);
                                    itemsTreptowKöpenick.put("betreiber", betreiber);
                                    itemsTreptowKöpenick.put("email", email);
                                    itemsTreptowKöpenick.put("www", web);
                                    itemsTreptowKöpenick.put("bemerkungen", bemerkungen);
                                    //replace "," with "." in "latitude" and "longitude" for correct parsing
                                    String lati = latitude.replaceAll(",", ".");
                                    Double lat = Double.parseDouble(lati);
                                    String lngi = longitude.replaceAll(",", ".");
                                    Double lng = Double.parseDouble(lngi);
                                    //set markers
                                    OverlayItem tTowKnickItem = new OverlayItem("Treptow-Köpenick", "Berlin",
                                            new GeoPoint(lat, lng));
                                    overlayItemArray.add(tTowKnickItem);
                                }
                            }

                        // adding each child node to HashMap key => value
                        //all bezirke
                        /*
                        items.put("id", id);
                        items.put("bezirk", bezirk);
                        items.put("location", location);
                        items.put("latitude", latitude);
                        items.put("longitude", longitude);
                        items.put("tage", tage);
                        items.put("zeiten", zeiten);
                        items.put("betreiber", betreiber);
                        items.put("email", email);
                        items.put("www", web);
                        items.put("bemerkungen", bemerkungen);
                        */
                        // adding item to itemLists
                        //for filterActivity
                        //itemList.add(items);
                        itemListBrandenburg.add(itemsBrandenburg);
                        itemListCharlottenburgWilmersdorf.add(itemsCharlottenburgWilmersdorf);
                        itemListFriedrichshainKreuzberg.add(itemsFriedrichshainKreuzberg);
                        itemListKlaistow.add(itemsKlaistow);
                        itemListLichtenberg.add(itemsLichtenberg);
                        itemListMarzahnHellersdorf.add(itemsBrandenburg);
                        itemListMitte.add(itemsMarzahnHellersdorf);
                        itemListNeukölln.add(itemsMitte);
                        itemListPankow.add(itemsPankow);
                        itemListReinickendorf.add(itemsReinickendorf);
                        itemListSpandau.add(itemsSpandau);
                        itemListSteglitzZehlendorf.add(itemsSteglitzZehlendorf);
                        itemListTempelhofSchöneberg.add(itemsTempelhofSchöneberg);
                        itemListTreptowKöpenick.add(itemsTreptowKöpenick);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //// Add the Array to the IconOverlay
            ItemizedIconOverlay<OverlayItem> itemItemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(OnlineActivity.this, overlayItemArray,null);

            // Add the overlay to the MapView
            mMapView.getOverlays().add(itemItemizedIconOverlay);

            /*
            //output for ListView
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(OnlineActivity.this, itemList,R.layout.list_item, new String[]{"bezirk", "location"},
            new int[]{R.id.output1, R.id.output2});
            lv.setAdapter(adapter);
            */
        }
        //---------------------------
    }

}//end of Class