package com.apps.corson.randomnamegenerator;

import android.content.Context;
import android.content.res.AssetFileDescriptor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Brian on 11/26/15.
 */
public class namegen {
    /*public static String getName(String gender) throws IOException{
        Context mContext;
        AssetFileDescriptor descriptor = getAssets().openFD(gender + "names.txt");

        BufferedReader reader = new BufferedReader(new FileReader(descriptor.getFileDescriptor()));
        List<String> names = new ArrayList<String>();

        String line = reader.readLine();


        while (line !=null) {
            names.add(line.substring(0, line.indexOf(" ")));
            line = reader.readLine();
        }

        Random rand = new Random();
        String name = names.get(rand.nextInt(names.size()));

        return name;
    }*/
}
