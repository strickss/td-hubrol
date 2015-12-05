package com.example.towerdefense;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private final PathList path;
    private int StartingGold;
    private int StartingIncome;
    private String[][] mapMatrix;
    private ArrayList<Elements> mapList;
    private static final String TAG = Map.class.getSimpleName();
    private final int mapSizeX;
    private final int mapSizeY;
    private final int blockSizeX;
    private final int blockSizeY;
    private Random rand;
    private Context context;
    private double endzoneX;
    private double endzoneY;

    public Map(Context context, int lvl) {
        mapSizeX = 2500;
        mapSizeY = 2000;
        mapMatrix = ReadFile.getmap(lvl);
        blockSizeX = mapSizeX / mapMatrix.length;
        blockSizeY = mapSizeY / mapMatrix[0].length;
        path = new PathList(mapMatrix, this, context);
        rand = new Random();
        mapList = new ArrayList<>();
        CreateMapList(context);
        CreateMapList(context);
    }

    public void draw(Canvas canvas) {
        for(int j=0; j<mapList.size();j++){
            mapList.get(j).draw(canvas);
        }
        for(int i=0; i < path.getPathList().get(0).size(); i=i+1){
            path.getPathList().get(0).get(i).draw(canvas);
            //Log.d(TAG, "i :" + i);
            path.getPathList().get(1).get(i).draw(canvas);
        }
    }

    private void CreateMapList(Context context) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {
                if (mapMatrix[x][y].equals("Z")) {
                    mapList.add(new EndZone(getBlockSizeX() * x, getBlockSizeY() * y, context));
                    endzoneX = getBlockSizeX() * x;
                    endzoneY = getBlockSizeY() * y;
                    Log.d(TAG, "EndZone X : "+ endzoneX + "EndZone y : " + endzoneY);
                }
                if (mapMatrix[x][y].equals("P")) {
                     mapList.add(new Path(getBlockSizeX() * x, getBlockSizeY() * y, context, getBlockSizeX(), getBlockSizeY()));
                }
                if (mapMatrix[x][y].equals("A")) {
                    //mapList.add(new Grass(getBlockSizeX() * x, getBlockSizeY() * y, context));
                }

            }
        }
    }

    public int getBlockSizeX() {return blockSizeX; }
    public int getBlockSizeY() {return blockSizeY; }
    public double getEndZoneX() {return endzoneX; }
    public double getEndZoneY() {return endzoneY; }
    public double getMapsizeX() {return mapSizeX; }
    public double getMapsizeY() {return mapSizeY; }

    public ArrayList<Elements> getLogicPath() {
       if(rand.nextDouble()<0.5) {
           return path.getPathList().get(0);
       }else{
           return path.getPathList().get(1);
       }
    }
}