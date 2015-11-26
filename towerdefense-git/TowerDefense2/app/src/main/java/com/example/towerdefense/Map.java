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
    private double endzoneX;
    private double endzoneY;

    public Map(Context context, int lvl) {
        mapSizeX = 2000;
        mapSizeY = 500;
        mapMatrix = ReadFile.getmap(lvl);
        blockSizeX = mapSizeX / mapMatrix.length;
        blockSizeY = mapSizeY / mapMatrix[0].length;
        path = new PathList(mapMatrix, this, context);
        rand = new Random();
        mapList = new ArrayList<>();
        CreateMapList(context);
    }

    public void draw(Canvas canvas) {
        for(int i=0; i < path.getPathList().get(0).size(); i=i+1){
            path.getPathList().get(0).get(i).draw(canvas);
            path.getPathList().get(1).get(i).draw(canvas);
        }
        for(int j=0; j<mapList.size();j++){
            mapList.get(j).draw(canvas);
        }
        //for (int x = 0; x < mapMatrix.length; x++) {
        //for (int y = 0; y < mapMatrix[0].length; y++) {
        //        if (mapMatrix[x][y].equals("P")) {
        //            path.getPathList().get(0).get(1).draw(canvas); // A changer: les P sont des chemins affiché mais pas "logic"
        //        }
        //    }
        //}
    }

    private void CreateMapList(Context context) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {
                if (mapMatrix[x][y].equals("Z")) {
                    mapList.add(new EndZone(getBlockSizeX() * x, getBlockSizeY() * y, context));
                    endzoneX = getBlockSizeX() * x;
                    endzoneY = getBlockSizeY() * y;
                }
            }
        }
    }

    public int getBlockSizeX() {
        return blockSizeX;
    }

    public int getBlockSizeY() {return blockSizeY; }
    public double getEndZoneX() {return endzoneX; }
    public double getEndZoneY() {return endzoneY; }

    public ArrayList<Elements> getLogicPath() {
       if(rand.nextDouble()<0.5) {
           return path.getPathList().get(0);
       }else{
           return path.getPathList().get(1);
       }
    }
}