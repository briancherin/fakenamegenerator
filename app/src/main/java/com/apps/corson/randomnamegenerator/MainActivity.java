package com.apps.corson.randomnamegenerator;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
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

    List<String> savedNames = new ArrayList<>();

//test

    TextView finalName;

    String gender = "all";

    ImageButton maleButton;
    ImageButton anyButton;
    ImageButton femaleButton;


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

        finalName = (TextView)findViewById(R.id.finalName);
        maleButton = (ImageButton)findViewById(R.id.maleButton);
        anyButton = (ImageButton)findViewById(R.id.anyButton);
        femaleButton = (ImageButton)findViewById(R.id.femaleButton);


        try {
            initializeNames();
            System.out.println("in try");
        }catch(IOException ex){
            System.out.println("FILE NOT FOUND EXCEPTION");
        }

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

    public void getName(View view) throws IOException{

        finalName.setText(getName(gender) + " " + getName("lastnames"));
    }

    public void saveName(View view){
        String name = finalName.getText().toString() + "\n";
        String filename = "saved_names";
        try {
            FileOutputStream fos = new FileOutputStream((getFilesDir() + filename), true);
            fos.write(name.getBytes());
            fos.close();
            Toast.makeText(getApplicationContext(), "Name saved.", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadName(View view){
        String filename = "saved_names";
        String input;
  //      StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(getFilesDir() + filename));

  //          sb.append(bf.readLine());

            while ((input = bf.readLine()) != null){
  //              sb.append(input);
                savedNames.add(input);
            }

            for (int i = 0; i < savedNames.size(); i++){
                finalName.setText(finalName.getText() + "\n" + savedNames.get(i));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

  //      finalName.setText(sb.toString());
    }

    public void initializeNames() throws IOException{

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

}
