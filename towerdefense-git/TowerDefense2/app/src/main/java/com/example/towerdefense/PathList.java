package com.example.towerdefense;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Hugo on 26/11/2015.package com.example.towerdefense;

 import android.content.Context;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.Canvas;
 import android.util.Log;

 import java.util.ArrayList;

 /**
 * Created by Hugo on 24/11/2015.
 */
public class PathList{
    private final ArrayList<Elements> path1;
    private final ArrayList<Elements> path2;
    private static final String TAG = Map.class.getSimpleName();
    private final Map map;
    private final Context context;
    private ArrayList<ArrayList<Elements>> PathList;
    private Bitmap bitmap;

    public PathList(String [][]mapMatrix, Map map, Context context){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.path);
        this.map = map;
        path1 = new ArrayList<>();
        path2 = new ArrayList<>();
        PathList = new ArrayList<ArrayList<Elements>>();
        this.context = context;
        for(int i=0; i<6; i++){
            path1.add(null);
            path2.add(null);
            //Log.d(TAG, "PATH" + path1);
        }
        CreatePathList(mapMatrix);
    }

    private void CreatePathList(String[][] mapMatrix) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[0].length; y++) {
                if (mapMatrix[x][y].equals("S")) {
                    path1.set(0, new Path( map.getBlockSizeX() * x, map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("0")) {
                    path1.set(1, new Path( map.getBlockSizeX() * x, map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("1")) {
                    path1.set(2, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("2")) {
                    path1.set(3, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("3")) {
                    path1.set(4, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("4")) {
                    path1.set(5, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("T")) {
                    path2.set(0, new Path( map.getBlockSizeX() * x, map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path1);
                }
                if (mapMatrix[x][y].equals("5")) {
                    path2.set(1, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("6")) {
                    path2.set(2, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("7")) {
                    path2.set(3, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("8")) {
                    path2.set(4, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path2);
                }
                if (mapMatrix[x][y].equals("9")) {
                    path2.set(5, new Path((double) map.getBlockSizeX() * x,(double) map.getBlockSizeY() * y, context ));
                    //Log.d(TAG, "PATH" + path2);
                }
            }
        }
        PathList.add(path1);
        PathList.add(path2);
    }

    public ArrayList<ArrayList<Elements>> getPathList() {
        return PathList;
    }

    public void draw(Canvas canvas, int x, int y){
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth()/ 2), y - (bitmap.getHeight()/2), null);
    }
}

