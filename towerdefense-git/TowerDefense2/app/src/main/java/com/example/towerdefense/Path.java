package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 24/11/2015.
 */
public class Path {
    private final ArrayList<Integer> path1;
    private final ArrayList<Integer> path2;
    private static final String TAG = Map.class.getSimpleName();
    private final Map map;
    private ArrayList<ArrayList<Integer>> PathList;
    private Bitmap bitmap;

    public Path(String [][]mapMatrix, Map map, Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.path);
        this.map = map;
        path1 = new ArrayList<>();
        path2 = new ArrayList<>();
        PathList = new ArrayList<ArrayList<Integer>>();

        for(int i=0; i<5; i++){
            path1.add(null);
            path1.add(null);
            path2.add(null);
            path2.add(null);
            Log.d(TAG, "PATH" + path1);
        }
        CreatePathList(mapMatrix);

    }

    private void CreatePathList(String[][] mapMatrix) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {

                if (mapMatrix[x][y].equals("0")) {
                    path1.set(0, map.getBlockSizeX() * x);
                    path1.set(1, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("1")) {
                    path1.set(2, map.getBlockSizeX() * x);
                    path1.set(3, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("2")) {
                    path1.set(4, map.getBlockSizeX() * x);
                    path1.set(5, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("3")) {
                    path1.set(6, map.getBlockSizeX() * x);
                    path1.set(7, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("4")) {
                    path1.set(8, map.getBlockSizeX() * x);
                    path1.set(9, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("5")) {
                    path2.set(0, map.getBlockSizeX() * x);
                    path2.set(1, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("6")) {
                    path2.set(2, map.getBlockSizeX() * x);
                    path2.set(3, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("7")) {
                    path2.set(4, map.getBlockSizeX() * x);
                    path2.set(5, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("8")) {
                    path2.set(6, map.getBlockSizeX() * x);
                    path2.set(7, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("9")) {
                    path2.set(8, map.getBlockSizeX() * x);
                    path2.set(9, map.getBlockSizeY() * y);
                    Log.d(TAG, "PATH" + path2);
                }
            }
        }
        PathList.add(path1);
        PathList.add(path2);
    }

    public ArrayList<ArrayList<Integer>> getPathList() {
        return PathList;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(bitmap, x- (bitmap.getWidth()/2), y -(bitmap.getHeight()/2), null);
    }
}
