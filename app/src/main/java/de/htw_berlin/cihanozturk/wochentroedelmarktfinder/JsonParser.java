package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;

/**
 * Created by Pujan on 06.01.17.
 */

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class JsonParser extends AppCompatActivity {

    private String TAG = JsonParser.class.getSimpleName();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonparser);

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

        new GetItems().execute();

        //TextView textView =(TextView)findViewById(R.id.web);
        //textView.setClickable(true);
        //textView.setMovementMethod(LinkMovementMethod.getInstance());
        //String text = "<a href='http://www.google.com'> Google </a>";
        //textView.setText(Html.fromHtml(text));
    }

    private class GetItems extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(JsonParser.this, "Json Data is downloading", Toast.LENGTH_LONG).show();

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

                        if (bezirk.equals("Brandenburg")) {
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
                        }
                        if (bezirk.equals("Charlottenburg-Wilmersdorf")) {
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
                        }
                        if (bezirk.equals("Friedrichshain-Kreuzberg")) {
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
                        }
                        if (bezirk.equals("Klaistow")) {
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
                        }
                        if (bezirk.equals("Lichtenberg")) {
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
                        }
                        if (bezirk.equals("Marzahn-Hellersdorf")) {
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
                        }
                        if (bezirk.equals("Mitte")) {
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
                        }
                        if (bezirk.equals("Neukölln")) {
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
                        }
                        if (bezirk.equals("Reinickendorf")) {
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
                        }
                        if (bezirk.equals("Pankow")) {
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
                        }
                        if (bezirk.equals("Spandau")) {
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
                        }
                        if (bezirk.equals("Steglitz-Zehlendorf")) {
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
                        }
                        if (bezirk.equals("Tempelhof-Schöneberg")) {
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
                        }
                        if (bezirk.equals("Treptow-Köpenick")) {
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
                        }

                        // adding each child node to HashMap key => value
                        //all bezirke
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

                        // adding item to itemLists
                        //for filterActivity
                        itemList.add(items);
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
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(JsonParser.this, itemList,
                    R.layout.list_item, new String[]{"bezirk", "location", "tage", "zeiten", "www"},
                    new int[]{R.id.bezirk, R.id.adresse, R.id.tage, R.id.zeiten, R.id.web});
            lv.setAdapter(adapter);

        }
    }
}