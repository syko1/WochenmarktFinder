package de.htw_berlin.cihanozturk.wochentroedelmarktfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Pujan on 06.01.17.
 */
//for filtering the districts with checkboxes
public class FilterActivity extends AppCompatActivity implements OnClickListener{

    private Button update;

    public static CheckBox bBurg;
    public static CheckBox cBurgWilmers;
    public static CheckBox fHainKberg;
    public static CheckBox klaistow;
    public static CheckBox lBerg;
    public static CheckBox mZahnHdorf;
    public static CheckBox mitte;
    public static CheckBox nKölln;
    public static CheckBox pankow;
    public static CheckBox rDorf;
    public static CheckBox spandau;
    public static CheckBox sLitzZdorf;
    public static CheckBox tHofSberg;
    public static CheckBox tTowKnick;

    //check for other Activities
    public static Boolean comesFromFilter;

    public static Boolean bBurgChecked;
    public static Boolean cBurgWilmersChecked;


    public static ArrayList<String> selection = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        comesFromFilter = false;
        //set CheckboxesSelected to false
        bBurgChecked = false;
        cBurgWilmersChecked = false;

        //Button
        update = (Button) findViewById(R.id.btn_aktualisieren);
        update.setOnClickListener(this);

        //Checkboxes
        bBurg = (CheckBox) findViewById(R.id.bBurg);
        bBurg.setOnClickListener(this);

        cBurgWilmers = (CheckBox) findViewById(R.id.cBurgWilmers);
        cBurgWilmers.setOnClickListener(this);

        fHainKberg = (CheckBox) findViewById(R.id.fHainKberg);
        fHainKberg.setOnClickListener(this);

        klaistow = (CheckBox) findViewById(R.id.klaistow);
        klaistow.setOnClickListener(this);

        lBerg = (CheckBox) findViewById(R.id.lBerg);
        lBerg.setOnClickListener(this);

        mZahnHdorf = (CheckBox) findViewById(R.id.mZahnHdorf);
        mZahnHdorf.setOnClickListener(this);

        mitte = (CheckBox) findViewById(R.id.mitte);
        mitte.setOnClickListener(this);

        nKölln = (CheckBox) findViewById(R.id.nKölln);
        nKölln.setOnClickListener(this);

        pankow = (CheckBox) findViewById(R.id.pankow);
        pankow.setOnClickListener(this);

        rDorf = (CheckBox) findViewById(R.id.rDorf);
        rDorf.setOnClickListener(this);

        spandau = (CheckBox) findViewById(R.id.spandau);
        spandau.setOnClickListener(this);

        sLitzZdorf = (CheckBox) findViewById(R.id.sLitzZdorf);
        sLitzZdorf.setOnClickListener(this);

        tHofSberg = (CheckBox) findViewById(R.id.tHofSberg);
        tHofSberg.setOnClickListener(this);

        tTowKnick = (CheckBox) findViewById(R.id.tTowKnick);
        tTowKnick.setOnClickListener(this);

        //default districts
        bBurg.setChecked(true);
        cBurgWilmers.setChecked(true);
        rDorf.setChecked(true);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bBurg:
                Toast.makeText(FilterActivity.this,"Brandenburg checkbox checked", Toast.LENGTH_SHORT).show();
                if (bBurg.isChecked()){
                    bBurg.setChecked(true);
                }
                break;

            case R.id.cBurgWilmers:
                Toast.makeText(FilterActivity.this,"Charlottenburg-Wilmersdorf checkbox checked", Toast.LENGTH_SHORT).show();
                if (cBurgWilmers.isChecked()){
                    cBurgWilmers.setChecked(true);
                }
                break;
            case R.id.fHainKberg:
                Toast.makeText(FilterActivity.this,"Friedrichshain-Kreuzberg checkbox checked", Toast.LENGTH_SHORT).show();
                if (fHainKberg.isChecked()){
                    fHainKberg.setChecked(true);
                }
                break;

            case R.id.klaistow:
                Toast.makeText(FilterActivity.this,"Klaistow checkbox checked", Toast.LENGTH_SHORT).show();
                if (klaistow.isChecked()){
                    klaistow.setChecked(true);
                }
                break;

            case R.id.lBerg:
                Toast.makeText(FilterActivity.this,"Lichtenberg checkbox checked", Toast.LENGTH_SHORT).show();
                if (lBerg.isChecked()){
                    lBerg.setChecked(true);
                }
                break;

            case R.id.mZahnHdorf:
                Toast.makeText(FilterActivity.this,"Marzahn-Hellersdorf checkbox checked", Toast.LENGTH_SHORT).show();
                if (mZahnHdorf.isChecked()){
                    mZahnHdorf.setChecked(true);
                }
                break;
            case R.id.mitte:
                Toast.makeText(FilterActivity.this,"Mitte checkbox checked", Toast.LENGTH_SHORT).show();
                if (mitte.isChecked()){
                    mitte.setChecked(true);
                }
                break;

            case R.id.nKölln:
                Toast.makeText(FilterActivity.this,"Neukölln checkbox checked", Toast.LENGTH_SHORT).show();
                if (nKölln.isChecked()){
                    nKölln.setChecked(true);
                }
                break;
            case R.id.pankow:
                Toast.makeText(FilterActivity.this,"Pankow checkbox checked", Toast.LENGTH_SHORT).show();
                if (pankow.isChecked()){
                    pankow.setChecked(true);
                }
                break;

            case R.id.rDorf:
                Toast.makeText(FilterActivity.this,"Reinickendorf checkbox checked", Toast.LENGTH_SHORT).show();
                if (rDorf.isChecked()){
                    rDorf.setChecked(true);
                }
                break;
            case R.id.spandau:
                Toast.makeText(FilterActivity.this,"Spandau checkbox checked", Toast.LENGTH_SHORT).show();
                if (spandau.isChecked()){
                    spandau.setChecked(true);
                }
                break;

            case R.id.sLitzZdorf:
                Toast.makeText(FilterActivity.this,"Steglitz-Zehlendorf checkbox checked", Toast.LENGTH_SHORT).show();
                if (sLitzZdorf.isChecked()){
                    sLitzZdorf.setChecked(true);
                }
                break;
            case R.id.tHofSberg:
                Toast.makeText(FilterActivity.this,"Tempelhof-Schöneberg checkbox checked", Toast.LENGTH_SHORT).show();
                if (tHofSberg.isChecked()){
                    tHofSberg.setChecked(true);
                }
                break;

            case R.id.tTowKnick:
                Toast.makeText(FilterActivity.this,"Treptow-Köpenick checkbox checked", Toast.LENGTH_SHORT).show();
                if (tTowKnick.isChecked()){
                    tTowKnick.setChecked(true);
                }
                break;

            case R.id.btn_aktualisieren:
                comesFromFilter = true;
                Intent intentn = new Intent(this, OnlineActivity.class);
                HauptActivity hA = new HauptActivity();
                hA.btn_onlineMode.setEnabled(true);

                startActivity(intentn);
                break;

            default:
                break;
        }
    }

}
