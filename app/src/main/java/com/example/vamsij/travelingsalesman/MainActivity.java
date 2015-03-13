package com.example.vamsij.travelingsalesman;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.widget.AdapterView.*;


public class MainActivity extends ActionBarActivity
        implements OnItemSelectedListener {
    private static Button button_sbm;
    private int numPoints;
    private static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.my_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.numbers_array,
                android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        addButtonClickListener();

    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View arg1, int pos, long id) {
        String s = adapterView.getItemAtPosition(pos).toString();
        Intent intent = new Intent("com.example.vamsij.travelingsalesman.GameActivity");
        intent.putExtra("Spinner_num", Integer.parseInt(s));
        setIntent(intent);
    }
    public void addButtonClickListener() {
        button_sbm = (Button) findViewById(R.id.button);
        button_sbm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(getIntent());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
