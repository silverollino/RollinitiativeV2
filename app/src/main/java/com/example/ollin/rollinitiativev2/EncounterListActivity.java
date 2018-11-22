package com.example.ollin.rollinitiativev2;

import android.app.Dialog;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ollin.rollinitiativev2.adapter.PagerAdapterFragments;
import com.example.ollin.rollinitiativev2.fragment.AllFragment;
import com.example.ollin.rollinitiativev2.fragment.InterGalactic;
import com.example.ollin.rollinitiativev2.fragment.InterPlanetary;
import com.example.ollin.rollinitiativev2.fragment.InterStellar;
import com.example.ollin.rollinitiativev2.model.Spacecraft;
import com.example.ollin.rollinitiativev2.sql.DBAdapter;

public class EncounterListActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{

    private TabLayout tab;
    private ViewPager vp;
    int currentPos=0;

    EditText nameEditText;
    Button saveBtn;
    Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encounter_list);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //Crear las TABS de los fragments y el uso del ViewPager
        vp = (ViewPager)findViewById(R.id.viewpager);
        addPages();

        //TABS SETUP
        tab = findViewById(R.id.tabs);
        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.addOnTabSelectedListener(this);

    }

    private void displayDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setTitle("SQLITE DATA");
        dialog.setContentView(R.layout.dialog_layout);

        nameEditText = (EditText)dialog.findViewById(R.id.nameEditTxt);
        saveBtn = (Button)dialog.findViewById(R.id.saveBtn);
        sp = (Spinner)dialog.findViewById(R.id.category_SP);


        //Spinner
        String[] categories = {InterPlanetary.newInstance().toString(),
                InterStellar.newInstance().toString(),
                InterGalactic.newInstance().toString()};
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, categories));

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spacecraft spacecraft = new Spacecraft();
                spacecraft.setName(nameEditText.getText().toString());
                spacecraft.setCategory(sp.getSelectedItem().toString());

                if(new DBAdapter(EncounterListActivity.this).saveSpacecraft(spacecraft)){
                    nameEditText.setText("");
                    sp.setSelection(0);
                }else {
                    Toast.makeText(EncounterListActivity.this, getString(R.string.bad_save), Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Mostrar Dialog personalizado
        dialog.show();
    }

    private void addPages(){
        PagerAdapterFragments pagerAdapterFragments = new PagerAdapterFragments(getSupportFragmentManager());
        pagerAdapterFragments.addPage(InterPlanetary.newInstance());
        pagerAdapterFragments.addPage(InterStellar.newInstance());
        pagerAdapterFragments.addPage(InterGalactic.newInstance());
        pagerAdapterFragments.addPage(AllFragment.newInstance());

        vp.setAdapter(pagerAdapterFragments);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        vp.setCurrentItem(currentPos=tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.addMenu){
            displayDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
