package com.apps.corson.randomnamegenerator;

import android.app.LoaderManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityLoadNames extends AppCompatActivity{


    ListView listView;
    Button clearButton;

    ArrayList<String> list = MainActivity.savedNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_load_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        clearButton = (Button)findViewById(R.id.clearButton);

        listView = (ListView)findViewById(R.id.listView);

        MyCustomAdapter adapter = new MyCustomAdapter(list, this);

        listView.setAdapter(adapter);


        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clear everythng + delete stuff from text file
                clearFile();
                MainActivity.savedNames.clear();
                listView.setAdapter(null);      //clear ListView
            }
        });


    }


    public void clearFile(){
        try{
            FileOutputStream fos = new FileOutputStream((MainActivity.directory + "/" + MainActivity.filename));
            fos.write("".getBytes());
            fos.close();
           // Toast.makeText(getApplicationContext(), "Names cleared.", Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
