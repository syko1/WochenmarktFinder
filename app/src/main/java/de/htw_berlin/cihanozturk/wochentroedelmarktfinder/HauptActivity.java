package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class HauptActivity extends AppCompatActivity implements View.OnClickListener {

    public static Button btn_onlineMode;
    Button btn_filter;
    Button btn_credits;
    Button btn_offlineMOde;
    Button btn_list;
    private static final int REQUEST_ACCESS_FINE_LOCATION = 111, REQUEST_WRITE_STORAGE = 112;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_haupt);

        btn_onlineMode = (Button) findViewById(R.id.btn_onlineMode);
        btn_onlineMode.setOnClickListener(this);
        btn_onlineMode.setEnabled(false);

        btn_offlineMOde = (Button) findViewById(R.id.btn_offlineMOde);
        btn_offlineMOde.setOnClickListener(this);

        btn_filter = (Button) findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(this);

        btn_list = (Button) findViewById(R.id.btn_list);
        btn_list.setOnClickListener(this);

        btn_credits = (Button) findViewById(R.id.btn_credits);
        btn_credits.setOnClickListener(this);

        //Test, ob ACCESS_FINE_LOCATION
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        Log.d("WRITE_EXTERNAL_STORAGE", String.valueOf(permissionCheck));

        boolean hasPermissionLocation = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionLocation) {
            ActivityCompat.requestPermissions(HauptActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
        }

        boolean hasPermissionWrite = (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermissionWrite) {
            ActivityCompat.requestPermissions(HauptActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HauptActivity.this, "Berechtigung gegönnt !!!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(HauptActivity.this, "The app was not allowed to get your location. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();
                }
            }
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(HauptActivity.this, "Berechtigung gegönnt !!!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                } else {
                    Toast.makeText(HauptActivity.this, "The app was not allowed to write to your storage. Hence, it cannot function properly. Please consider granting it this permission", Toast.LENGTH_LONG).show();

                }
            }
        }
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_onlineMode:
                Intent intent = new Intent(this, OnlineActivity.class);
                startActivity(intent);
                break;

            case  R.id.btn_offlineMOde:
                Intent intentOffline = new Intent(this, OfflineActivity.class);
                startActivity(intentOffline);
                break;

            case R.id.btn_filter:
                Intent intentN = new Intent(this, FilterActivity.class);
                startActivity(intentN);
                break;

            case  R.id.btn_list:
                Intent in = new Intent(this, JsonParser.class);
                startActivity(in);
                break;

            case R.id.btn_credits:
                Intent intento = new Intent(this, CreditsActivity.class);
                startActivity(intento);
                break;
        }
    }
}//end of Activity