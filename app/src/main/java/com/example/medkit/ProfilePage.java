package com.example.medkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

    TextView emailOutput;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        databaseHelper = new DatabaseHelper(this);

        settingsButton = findViewById(R.id.editButton);
        logoutButton = findViewById(R.id.logoutButton);

        emailOutput = findViewById(R.id.emailOutput);

        vaccinationButton = findViewById(R.id.Profile_Vaccination_Button);
        healthInsuranceButton = findViewById(R.id.Profile_HealthInsurance_Button);
        xRayButton = findViewById(R.id.Profile_XRay_Button);

        sharedPref = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);

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
                sharedPref.edit().putString("email", null).apply();
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