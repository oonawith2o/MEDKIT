package com.example.medkit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DB_NAME = "medkit.db";
    private static int DB_VERSION = 1;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] byteImage;

    private static String createTableQuery_User = "Create table User(email TEXT primary key, password TEXT, " +
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
        db.execSQL(createTableQuery_User);
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
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("Select * from User where email = ? and password = ?", new String[] {email, password});

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
        } else {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    public User retrieveData(String email) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from User where email = ?", new String[] {email});
        User user = null;

        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Profile Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()){
                byte[] imageByte = cursor.getBlob(16);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);

                user = new User(cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                                cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getString(10),cursor.getString(11), cursor.getString(12),
                                cursor.getString(13),cursor.getInt(14), cursor.getInt(15), bitmap);
            }
        }
        return user;
    }

    public Bitmap getImage(String email, String type) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("Select * from User where email = ?", new String[] {email});
        if (cursor.getCount() == 0) {
            Toast.makeText(context, "No Profile Found", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                byte[] imageByte = cursor.getBlob(16);
                switch (type) {
                    case "PROFILE": imageByte = cursor.getBlob(16); break;
                    case "VACCINATION": imageByte = cursor.getBlob(17); break;
                    case "HEALTHCARE": imageByte = cursor.getBlob(18); break;
                    case "XRAY": imageByte = cursor.getBlob(19); break;
                }
                try {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
                    return bitmap;
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    public void setImage(String email, Bitmap image, String type) {
        SQLiteDatabase database = this.getReadableDatabase();

        byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byteImage = byteArrayOutputStream.toByteArray();

        ContentValues contentValues = new ContentValues();
        switch (type) {
            case "PROFILE": contentValues.put("profileImage", byteImage); break;
            case "VACCINATION": contentValues.put("vaccinationImage", byteImage); break;
            case "HEALTHCARE": contentValues.put("healthCareImage", byteImage); break;
            case "XRAY": contentValues.put("xRayImage", byteImage); break;
        }

        String[] whereArgs = new String[] {email};
        database.update("User", contentValues, "email = ?", whereArgs);
    }
}
