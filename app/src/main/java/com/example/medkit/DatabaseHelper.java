package com.example.medkit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DB_NAME = "medkit.db";
    private static int DB_VERSION = 1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    private static String createTableQuery = "Create table User(email TEXT primary key, password TEXT, " +
            "name TEXT, mobile TEXT, address TEXT, birthday TEXT, biologicalSex TEXT, bloodType TEXT," +
            "allergies TEXT, history TEXT, chronicIllnesses TEXT, emergencyName TEXT, emergencyRelation TEXT," +
            "emergencyMobile TEXT, height INTEGER, weight INTEGER,  profileImage BLOB," +
            "vaccinationImage BLOB, healthCareImage BLOB, xRayImage BLOB)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists User");
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = myDB.insert("User", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkEmail(String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from User where email = ?", new String[] {email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from User where email = ? and password = ?", new String[] {email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public void storeData(User user, String email) {
        SQLiteDatabase database = this.getWritableDatabase();
        Bitmap bitmapImage = user.getProfileImage();

        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("mobile", user.getMobile());
        contentValues.put("address", user.getAddress());
        contentValues.put("birthday", user.getBirthday());
        contentValues.put("biologicalSex", user.getBiologicalSex());
        contentValues.put("bloodType", user.getBloodType());
        contentValues.put("allergies", user.getAllergies());
        contentValues.put("history", user.getHistory());
        contentValues.put("chronicIllnesses", user.getChronicIllnesses());
        contentValues.put("emergencyName", user.getEmergencyName());
        contentValues.put("emergencyRelation", user.getEmergencyRelation());
        contentValues.put("emergencyMobile", user.getEmergencyMobile());
        contentValues.put("height", user.getHeight());
        contentValues.put("weight", user.getWeight());
        contentValues.put("profileImage", byteImage);

        String[] whereArgs = new String[] {email};

        long checkQuery = database.update("User", contentValues, "email = ?", whereArgs);
        if(checkQuery != -1) {
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
            // database.close()
        } else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor getUser(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from User", null);
        return cursor;
    }


    /*public void storeImage(User user, String type) {
        SQLiteDatabase database = this.getWritableDatabase();
        Bitmap bitmapImage;
        switch (type) {
            case "VACCINATION": bitmapImage = user.getVaccinationImage(); break;
            case "HEALTHCARE": bitmapImage = user.getHealthCareImage(); break;
            case "XRAY": bitmapImage = user.getXRayImage();
            default: bitmapImage = user.getProfileImage();
        }
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();
    }*/
}
