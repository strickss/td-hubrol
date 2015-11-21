package com.example.towerdefense;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private int StartingGold;
    private int StartingIncome;
    private int[][] mapMatrix;
    private int blockSizeX;
    private int blockSizeY;
    private int mapSizeX;
    private int mapSizeY;
    private ArrayList<Elements> mapList;
    private ArrayList<Integer> path;
    private int node;
    private static final String TAG = Map.class.getSimpleName();

    public Map(int lvl){
        node = 5;
        path = new ArrayList();
        for(int i=0; i<node; i++){
            path.add(null);
            path.add(null);
        }
        Log.d(TAG, "PATH" + path);
        mapMatrix = ReadFile.getmap(lvl);
        Log.d(TAG, "LENGTH: " + mapMatrix.length + " LENGTH2: " + mapMatrix[0].length);
        Log.d(TAG, "MAP: " + mapMatrix[0]);
        mapSizeX=2000;
        mapSizeY=500;
        blockSizeX = mapSizeX/mapMatrix.length;
        blockSizeY = mapSizeY/mapMatrix[0].length;
        CreateMapList(mapMatrix);

        //createPath();
    }

    private void CreateMapList(int[][] mapMatrix) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {
                if (mapMatrix[x][y] == 1) {
                    path.set(0, blockSizeX * x);
                    path.set(1, blockSizeY * y);
                    Log.d(TAG, "PATH" + path);
                }
                if (mapMatrix[x][y] == 2) {
                    path.set(2, blockSizeX * x);
                    path.set(3, blockSizeY * y);
                    Log.d(TAG, "PATH" + path);
                }
                if (mapMatrix[x][y] == 3) {
                    path.set(4, blockSizeX * x);
                    path.set(5, blockSizeY * y);
                    Log.d(TAG, "PATH" + path);
                }
                if (mapMatrix[x][y] == 4) {
                    path.set(6, blockSizeX * x);
                    path.set(7, blockSizeY * y);
                    Log.d(TAG, "PATH" + path);
                }
                if (mapMatrix[x][y] == 5) {
                    path.set(8, blockSizeX * x);
                    path.set(9, blockSizeY * y);
                    Log.d(TAG, "PATH" + path);
                }
            }
        }
    }


    private void createPath(){
        ArrayList<Integer> node1 =  new ArrayList<Integer>();
        node1.add(180);
        node1.add(1000);
        ArrayList<Integer> node2 =  new ArrayList<Integer>();
        node2.add(1000);
        node2.add(1000);

    }

    public ArrayList<Integer> getPath() {
        return path;
    }
}