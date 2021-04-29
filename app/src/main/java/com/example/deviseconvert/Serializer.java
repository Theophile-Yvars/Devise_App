package com.example.deviseconvert;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

public abstract class Serializer {

    public static void serialize(String filename, Object object, Context context){
        try {
            FileOutputStream fo = new FileOutputStream("res\\data.dat");
            ObjectOutputStream oos;
            try{
                oos = new ObjectOutputStream(fo);
                oos.writeObject(object);
                oos.flush();
                oos.close();
                fo.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object deserialize(String filename, Context context){
        try {
            FileInputStream fi = new FileInputStream("res\\data.dat");
            ObjectInputStream ois;
            try {
                ois = new ObjectInputStream(fi);
                try{
                    Object object = ois.readObject();
                    ois.close();
                    fi.close();
                    return object;
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }catch (StreamCorruptedException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
