package com.example.galaxytrader;

import android.content.Context;

import com.example.galaxytrader.models.Player;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SaveLoadService {

    public static final String FILE_NAME = "playerJson.json";

    public static boolean saveJsonPlayer(Player player, Context context){

        Gson gson = new Gson();
        FileOutputStream fos = null;

        try{
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            String jsonString = gson.toJson(player);
            fos.write(jsonString.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static Player loadJsonPlayer(Context context){

        Gson gson = new Gson();
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(fis);
            BufferedReader bis = new BufferedReader(reader);
            return gson.fromJson(bis.readLine(), Player.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean deleteFile(Context context){
        File file = new File(context.getFilesDir(), FILE_NAME);
        return file.delete();
    }
}
