package com.apps.corson.randomnamegenerator;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Context context = this;

    List<String> malenames = new ArrayList<>();
    List<String> femalenames = new ArrayList<>();
    List<String> allnames = new ArrayList<>();
    List<String> lastnames = new ArrayList<>();

    static ArrayList<String> savedNames = new ArrayList<>();


    public static String directory;


    TextView finalName;
    TextView loadText;

    String gender = "all";

    ImageButton maleButton;
    ImageButton anyButton;
    ImageButton femaleButton;

    Button saveButton;
    Button loadButton;

    final static String filename = "saved_names";

    static Context obj;

    static File myFile;

    Boolean bookmarkPressed = false;


    public void maleSelected(View view) {
        gender = "male";
        maleButton.setBackgroundResource(R.drawable.maledark);
        femaleButton.setBackgroundResource(R.drawable.female);
        anyButton.setBackgroundResource(R.drawable.any);
    }
    public void anySelected(View view){
        gender = "all";
        anyButton.setBackgroundResource(R.drawable.anydark);
        maleButton.setBackgroundResource(R.drawable.male);
        femaleButton.setBackgroundResource(R.drawable.female);
    }
    public void femaleSelected(View view){
        gender = "female";
        femaleButton.setBackgroundResource(R.drawable.femaledark);
        maleButton.setBackgroundResource(R.drawable.male);
        anyButton.setBackgroundResource(R.drawable.any);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        directory = getFilesDir().toString();
   //     System.out.println("Directory= " + directory);

        finalName = (TextView)findViewById(R.id.finalName);
        loadText = (TextView)findViewById(R.id.loadText);

        maleButton = (ImageButton)findViewById(R.id.maleButton);
        anyButton = (ImageButton)findViewById(R.id.anyButton);
        femaleButton = (ImageButton)findViewById(R.id.femaleButton);
        saveButton = (Button)findViewById(R.id.saveButton);
        loadButton = (Button)findViewById(R.id.loadButton);

        obj = this;

        try {
            initializeNames();
            System.out.println("in try");
        }catch(IOException e){
            e.printStackTrace();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadName();
            }
        });

    }
    /*
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
    */

    public String getName(String gender) throws IOException {

        Random rand = new Random();
        String name;

        switch(gender){
            case "male":
                name = malenames.get(rand.nextInt(malenames.size()));
                break;
            case "female":
                name = femalenames.get(rand.nextInt(femalenames.size()));
                break;
            case "all":
                name = allnames.get(rand.nextInt(allnames.size()));
                break;
            case "last":
                name = lastnames.get(rand.nextInt(lastnames.size()));
                break;
            default:
                name = allnames.get(rand.nextInt(allnames.size()));
        }

        return name;

    }

    public String makeUpperCamelCase(String name) {
        String modifiedName = (name.substring(0,1) + name.substring(1, name.indexOf(" ")).toLowerCase() + name.substring(name.indexOf(" "), (name.indexOf(" ") + 2)) + name.substring((name.indexOf(" ") + 2), name.length()).toLowerCase());
        return modifiedName;
    }

    public void getName(View view) throws IOException{

        saveButton.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
        bookmarkPressed = false;

        finalName.setText(makeUpperCamelCase(getName(gender) + " " + getName("lastnames")));
    }


    public void saveName(){
        if (!bookmarkPressed) {       //if they pres the bookmark buttono
            saveButton.setBackgroundResource(R.drawable.ic_bookmark_black_24dp);
            bookmarkPressed = true;
            String name = finalName.getText().toString() + "\n";
            savedNames.add(name);
        } else {                                     //if they unpress the bookmark button
            saveButton.setBackgroundResource(R.drawable.ic_bookmark_border_black_24dp);
            savedNames.remove(savedNames.size()-1);   //remove the name they just saved
            try {
                rewriteNameFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bookmarkPressed = false;
        }

        try {
            rewriteNameFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadName(){
        Intent intent = new Intent(MainActivity.this, ActivityLoadNames.class);
        startActivity(intent);
    }

    public void initializeNames() throws IOException{       //Add names from textfiles to ArrayLists
        AssetManager am = this.context.getAssets();
        InputStream is = am.open("malenames.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String name = " ";

        while((name = reader.readLine()) != null){
            malenames.add(name.substring(0, name.indexOf(' ')));
        }

        is = am.open("femalenames.txt");
        reader = new BufferedReader(new InputStreamReader(is));
        while((name = reader.readLine()) != null){
            femalenames.add(name.substring(0, name.indexOf(' ')));
        }

        is = am.open("allnames.txt");
        reader = new BufferedReader(new InputStreamReader(is));
        while((name = reader.readLine()) != null){
            allnames.add(name.substring(0, name.indexOf(' ')));
        }

        is = am.open("lastnames.txt");
        reader = new BufferedReader(new InputStreamReader(is));
        while((name = reader.readLine()) != null){
            lastnames.add(name.substring(0, name.indexOf(' ')));
        }

    }


    public void rewriteNameFile() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream((directory + "/" + filename));
            for (int i = 0; i < savedNames.size();  i++) {
                fos.write((savedNames.get(i) + "\n").getBytes());
            }
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
