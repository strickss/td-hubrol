package com.example.towerdefense;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private final Path path;
    private int StartingGold;
    private int StartingIncome;
    private String[][] mapMatrix;
    private ArrayList<Elements> mapList;

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

    private void CreateMapList(String[][] mapMatrix) {
    }

    public int getBlockSizeX() {
        return blockSizeX;
    }

    public int getBlockSizeY() {return blockSizeY; }

    public ArrayList<Integer> getPath() {
        return path.getPathList().get(0);
    }
}