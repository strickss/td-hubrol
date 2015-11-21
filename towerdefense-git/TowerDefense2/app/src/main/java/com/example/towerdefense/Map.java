package com.example.towerdefense;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * Created by Hugo on 17/11/2015.
 */
public class Map {
    private ArrayList<ArrayList<Integer>> path;
    private int StartingGold;
    private int StartingIncome;
    private int[][] mapMatrix;
    private ArrayList<Elements> mapList;
    public Map(int lvl){
        // mapMatrix = ReadFile.getmap(int lvl)
        //CreateMapList(mapMatrix);
        path = new ArrayList<>();
        createPath();
    }
    private void CreateMapList(int[][] mapMatrix) {
        for (int x = 0; x < mapMatrix.length; x++) {
            for (int y = 0; y < mapMatrix[x].length; y++) {
            }
        }
    }
    private void createPath() {
        ArrayList node1 =  new ArrayList();
        node1.add(180);
        node1.add(1000);
        ArrayList node2 =  new ArrayList();
        node2.add(1000);
        node2.add(1000);
        path.add(node1);
        path.add(node2);
    }
    public ArrayList<ArrayList<Integer>> getPath() {return path;}
}