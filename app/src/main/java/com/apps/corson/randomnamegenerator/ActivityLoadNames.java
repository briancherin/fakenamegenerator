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

    List<String> list = MainActivity.savedNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_load_names);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        clearButton = (Button)findViewById(R.id.clearButton);

        listView = (ListView)findViewById(R.id.listView);

        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id){
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable(){
                            @Override
                            public void run(){
                                //do something here???
                                //list.remove(item)
                                //adapter.notifyDataSetChanged();
                                //view.setAlpha(1);
                            }
                        });
            }
        });


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

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects){
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); i++){
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position){
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds(){
            return true;
        }
    }

    public void clearFile(){
        try{
            FileOutputStream fos = new FileOutputStream((getFilesDir() + MainActivity.filename));
            fos.write("".getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "Names cleared.", Toast.LENGTH_LONG).show();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
