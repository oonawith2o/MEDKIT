package com.example.medkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.transition.MaterialContainerTransform;

import java.util.Calendar;

public class ProfilePage extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPref;

    FloatingActionButton settingsButton;
    FloatingActionButton logoutButton;
    Button vaccinationButton;
    Button healthInsuranceButton;
    Button xRayButton;

    ShapeableImageView profileImageOutput;
    TextView fullNameOutput, birthdayOutput, biologicalSexOutput, heightOutput, weightOutput, bloodTypeOutput,
                allergiesOutput, historyOutput, chronicIllnessesOutput, emergencyNameOutput, emergencyRelationOutput,
                emergencyMobileOutput, mobileOutput, emailOutput, addressOutput;
    LinearLayout birthdayLayout, biologicalSexLayout, heightLayout, weightLayout, bloodTypeLayout, emergencyNameLayout,
                    emergencyRelationLayout, emergencyMobileLayout, mobileLayout, addressLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        databaseHelper = new DatabaseHelper(this);

        settingsButton = findViewById(R.id.editButton);
        logoutButton = findViewById(R.id.logoutButton);

        profileImageOutput = findViewById(R.id.profileImageOutput);

        fullNameOutput = findViewById(R.id.fullNameOutput);
        birthdayOutput = findViewById(R.id.birthdayOutput);
        biologicalSexOutput = findViewById(R.id.biologicalSexOutput);
        heightOutput = findViewById(R.id.heightOutput);
        weightOutput = findViewById(R.id.weightOutput);
        bloodTypeOutput = findViewById(R.id.bloodTypeOutput);
        allergiesOutput = findViewById(R.id.allergiesOutput);
        historyOutput = findViewById(R.id.historyOutput);
        chronicIllnessesOutput = findViewById(R.id.chronicIllnessesOutput);
        emergencyNameOutput = findViewById(R.id.emergencyNameOutput);
        emergencyRelationOutput = findViewById(R.id.emergencyRelationOutput);
        emergencyMobileOutput = findViewById(R.id.emergencyMobileOutput);
        mobileOutput = findViewById(R.id.mobileOutput);
        emailOutput = findViewById(R.id.emailOutput);
        addressOutput = findViewById(R.id.addressOutput);

        birthdayLayout = findViewById(R.id.birthdayLayoutProfile);
        biologicalSexLayout = findViewById(R.id.biologicalSexLayoutProfile);
        heightLayout = findViewById(R.id.heightLayoutProfile);
        weightLayout = findViewById(R.id.weightLayoutProfile);
        bloodTypeLayout = findViewById(R.id.bloodTypeLayoutProfile);
        emergencyNameLayout = findViewById(R.id.emergencyNameLayoutProfile);
        emergencyRelationLayout = findViewById(R.id.emergencyRelationLayoutProfile);
        emergencyMobileLayout = findViewById(R.id.emergencyMobileLayoutProfile);
        mobileLayout = findViewById(R.id.mobileLayoutProfile);
        addressLayout = findViewById(R.id.addressLayoutProfile);

        vaccinationButton = findViewById(R.id.Profile_Vaccination_Button);
        healthInsuranceButton = findViewById(R.id.Profile_HealthInsurance_Button);
        xRayButton = findViewById(R.id.Profile_XRay_Button);

        sharedPref = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);

        this.setFields();

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

    private void setFields() {
        String email = sharedPref.getString("email", "");
        User user = databaseHelper.retrieveData(email);

        fullNameOutput.setText(user.getName());
        emailOutput.setText(email);
        profileImageOutput.setImageBitmap(user.getProfileImage());

        if (user.getBirthday().equals(EditProfile.getTodaysDate())) { birthdayLayout.setVisibility(View.GONE); } else { birthdayOutput.setText(user.getBirthday()); }
        if (user.getBiologicalSex().equals("SELECT")) { biologicalSexLayout.setVisibility(View.GONE); } else {  biologicalSexOutput.setText(user.getBiologicalSex()); }
        if (String.valueOf(user.getHeight()).equals("-1")) { heightLayout.setVisibility(View.GONE); } else { heightOutput.setText(String.valueOf(user.getHeight())); }
        if (String.valueOf(user.getWeight()).equals("-1")) { weightLayout.setVisibility(View.GONE); } else { weightOutput.setText(String.valueOf(user.getWeight())); }
        if (user.getBloodType().equals("SELECT")) { bloodTypeLayout.setVisibility(View.GONE); } else { bloodTypeOutput.setText(user.getBloodType()); }
        if (user.getAllergies().equals("")) { allergiesOutput.setText("n/a.");} else { allergiesOutput.setText(user.getAllergies()); }
        if (user.getHistory().equals("")) { historyOutput.setText("n/a");} else { historyOutput.setText(user.getHistory()); }
        if (user.getChronicIllnesses().equals("")) { chronicIllnessesOutput.setText("n/a"); } else { chronicIllnessesOutput.setText(user.getChronicIllnesses());}
        if (user.getEmergencyName().equals("")) { emergencyNameLayout.setVisibility(View.GONE); } else { emergencyNameOutput.setText(user.getEmergencyName()); }
        if (user.getEmergencyRelation().equals("SELECT")) { emergencyRelationLayout.setVisibility(View.GONE); } else {  emergencyRelationOutput.setText(user.getBiologicalSex()); }
        if (user.getEmergencyMobile().equals("")) { emergencyMobileLayout.setVisibility(View.GONE); } else { emergencyMobileOutput.setText(user.getEmergencyMobile()); }
        if (user.getMobile().equals("")) { mobileLayout.setVisibility(View.GONE); } else { mobileOutput.setText(user.getMobile()); }
        if (user.getAddress().equals("")) { addressLayout.setVisibility(View.GONE); } else { addressOutput.setText(user.getAddress()); }

    }

}