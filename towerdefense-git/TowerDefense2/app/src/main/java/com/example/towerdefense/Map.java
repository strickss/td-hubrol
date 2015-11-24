package com.example.towerdefense;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private final Path path;
    private int StartingGold;
    private int StartingIncome;
    private String[][] mapMatrix;
    private ArrayList<Elements> mapList;
<<<<<<< HEAD
    private ArrayList<Integer> path;
    private int node;
    private static final String TAG = Map.class.getSimpleName();
=======

    private static final String TAG = Map.class.getSimpleName();
    int node;
    private final int mapSizeX;
    private final int mapSizeY;
    private final int blockSizeX;
    private final int blockSizeY;
    private ArrayList<ArrayList<Integer>> pathList;

    public Map(Context context, int lvl) {
        mapSizeX = 2000;
        mapSizeY = 500;
>>>>>>> refs/remotes/origin/Samedi-matin

        mapMatrix = ReadFile.getmap(lvl);
        blockSizeX = mapSizeX / mapMatrix.length;
        blockSizeY = mapSizeY / mapMatrix[0].length;
        path = new Path(mapMatrix, this, context);
        CreateMapList(mapMatrix);
    }

    public void draw(Canvas canvas) {
        for(int i=0; i < path.getPathList().get(0).size(); i=i+2){
            path.draw(canvas, path.getPathList().get(0).get(i), path.getPathList().get(0).get(i+1));
        }
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {
                if (mapMatrix[x][y].equals("P")) {
                    path.draw(canvas, blockSizeX*x, blockSizeY*y);
                }
            }
        }
    }
<<<<<<< HEAD


    private void createPath(){
        ArrayList<Integer> node1 =  new ArrayList<Integer>();
        node1.add(180);
        node1.add(1000);
        ArrayList<Integer> node2 =  new ArrayList<Integer>();
        node2.add(1000);
        node2.add(1000);
=======
>>>>>>> refs/remotes/origin/Samedi-matin

    private void CreateMapList(String[][] mapMatrix) {
    }
<<<<<<< HEAD

    public ArrayList<Integer> getPath() {
        return path;
=======

    public int getBlockSizeX() {
        return blockSizeX;
    }

    public int getBlockSizeY() {return blockSizeY; }

    public ArrayList<Integer> getPath() {
        return path.getPathList().get(0);
>>>>>>> refs/remotes/origin/Samedi-matin
    }
}