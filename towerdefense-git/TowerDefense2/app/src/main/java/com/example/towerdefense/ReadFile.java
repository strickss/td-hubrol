package com.example.towerdefense;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Hugo on 21/11/2015.
 */
public class ReadFile {
    static ArrayList<String> ListLevel;
    private static final String TAG = ReadFile.class.getSimpleName();
    public ReadFile(){

    }

    public static int [][] getmap(int a){

        ListLevel= new ArrayList<String>();
        ListLevel.add("Map1.txt");
        ListLevel.add("Map2.txt");
        ListLevel.add("Map3.txt");

        int [][] map = new int[40][20];
        Log.d(TAG, "n: " + ListLevel.get(a));
        try{

            AssetManager assetManager = ApplicationContextProvider.getContext().getAssets();
            InputStream iS = assetManager.open(ListLevel.get(a));
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));

            String ligne;
            int line = 0;


            while ((ligne=br.readLine())!= null){

                for (int i=0; i < ligne.length(); i++){
                    String l = ligne.substring(i,i+1);
                    int n = Integer.parseInt(l);
                    map [i][line] = n;
                }
                line += 1;
            }
            br.close();
        }
        catch (Exception e){
            System.out.println("Can't find the file");
        }

        return map;
    }



}
