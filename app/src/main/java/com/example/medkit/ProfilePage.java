package com.example.medkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfilePage extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    Cursor cursor;

    FloatingActionButton settingsButton;
    FloatingActionButton logoutButton;
    Button vaccinationButton;
    Button healthInsuranceButton;
    Button xRayButton;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        databaseHelper = new DatabaseHelper(this);
        cursor = databaseHelper.getUser();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Profile Details", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

            }
        }

        settingsButton = findViewById(R.id.editButton);
        logoutButton = findViewById(R.id.logoutButton);

        vaccinationButton = findViewById(R.id.Profile_Vaccination_Button);
        healthInsuranceButton = findViewById(R.id.Profile_HealthInsurance_Button);
        xRayButton = findViewById(R.id.Profile_XRay_Button);

        sharedPref = getSharedPreferences("login", MODE_PRIVATE);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditProfile.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                sharedPref.edit().putBoolean("logged", false).apply();
                startActivity(intent);
            }
        });

        vaccinationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DocumentActivity.class);
                startActivity(intent);
            }
        });

    }


}