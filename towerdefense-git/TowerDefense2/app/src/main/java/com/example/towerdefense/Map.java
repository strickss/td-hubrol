package com.example.towerdefense;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private ArrayList<ArrayList<Integer>> path;
    private int StartingGold;
    private int StartingIncome;
    private int[][] mapMatrix;
    private int blockSizeX;
    private int blockSizeY;
    private int mapSizeX;
    private int mapSizeY;
    private ArrayList<Elements> mapList;
    private ArrayList<Integer> node;
    private static final String TAG = Map.class.getSimpleName();

    public Map(int lvl){
        this.path = new ArrayList<>();
        this.mapMatrix = ReadFile.getmap(lvl);
        Log.d(TAG, "LENGTH: " + mapMatrix.length + " LENGTH2: " + mapMatrix[0].length);
        Log.d(TAG, "MAP: " + mapMatrix[0]);
        this.mapSizeX=1000;
        this.mapSizeY=4000;
        int blockSizeX = mapSizeX/mapMatrix.length;
        int blockSizeY = mapSizeY/mapMatrix[0].length;
        CreateMapList(blockSizeX, blockSizeY);

        //createPath();
    }

    private void CreateMapList(int blockSizeX, int blockSizeY) {
        for (int x = 0; x < this.mapMatrix.length; x++) {
            for (int y = 0; y < this.mapMatrix[x].length; y++) {
                if(mapMatrix[x][y] == 1) {
                    ArrayList<Integer> node = new ArrayList<>();
                    node.add(blockSizeX * x);
                    node.add(blockSizeY * y);
                    Log.d(TAG, "xi" + node.get(0) + "yi" + node.get(1));
                    path.add(node);
                    //node.clear();
                }
            }
        }
    }
    private void createPath() {
        ArrayList<Integer> node1 =  new ArrayList<Integer>();
        node1.add(180);
        node1.add(1000);
        ArrayList<Integer> node2 =  new ArrayList<Integer>();
        node2.add(1000);
        node2.add(1000);
        path.add(node1);
        path.add(node2);
    }
    public ArrayList<ArrayList<Integer>> getPath() {return path;}

    /**
     public Map(int lvl){
     node =  new ArrayList<Integer>();
     path = new ArrayList<>();
     mapMatrix = ReadFile.getmap(lvl);
     Log.d(TAG, "LENGTH: " + mapMatrix.length + " LENGTH2: " + mapMatrix[0].length);
     Log.d(TAG, "MAP: " + mapMatrix[0]);
     mapSizeX=1000;
     mapSizeY=4000;
     blockSizeX = mapSizeX/mapMatrix.length;
     blockSizeY = mapSizeY/mapMatrix[0].length;
     CreateMapList(mapMatrix);

     //createPath();
     }

     private void CreateMapList(int[][] mapMatrix) {
     for (int x = 0; x < mapMatrix.length; x++) {
     for (int y = 0; y < mapMatrix[x].length; y++) {
     if(mapMatrix[x][y] == 1) {
     node.add(blockSizeX * x);
     node.add(blockSizeY * y);
     Log.d(TAG, "xi" + node.get(0) + "yi" + node.get(1));
     path.add(node);
     node.clear();
     }
     }
     }
     }

     */
}