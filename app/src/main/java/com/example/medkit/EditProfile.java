package com.example.medkit;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.Calendar;

public class EditProfile extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SharedPreferences sharedPref;

    private DatePickerDialog datePickerDialog;
    private Button dateButton, imageButton;
    private FloatingActionButton backButton;
    private ExtendedFloatingActionButton saveButton;
    private Spinner spinnerSex, spinnerBloodType, spinnerRelation;
    private EditText fullNameInput, heightInput, weightInput, allergiesInput, historyInput, chronicIllnessesInput, emergencyFullNameInput, emergencyMobileInput, mobileInput, emailInput, addressInput;
    private LinearLayout emailLayout;
    private ShapeableImageView profileImageInput;

    private String email;

    private Uri uri;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        databaseHelper = new DatabaseHelper(this);
        sharedPref = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);

        backButton = findViewById(R.id.backButton);
        dateButton = findViewById(R.id.birthdayButton);
        saveButton = findViewById(R.id.saveButton);
        imageButton = findViewById(R.id.imageButton);
        spinnerSex = (Spinner) findViewById(R.id.sexButton);
        spinnerBloodType = (Spinner) findViewById(R.id.bloodTypeButton);
        spinnerRelation = (Spinner) findViewById(R.id.emergencyRelationButton);
        fullNameInput = findViewById(R.id.fullNameInput);
        heightInput = findViewById(R.id.heightInput);
        weightInput = findViewById(R.id.weightInput);
        allergiesInput = findViewById(R.id.allergiesInput);
        historyInput = findViewById(R.id.historyInput);
        chronicIllnessesInput = findViewById(R.id.chronicIllnessesInput);
        emergencyFullNameInput = findViewById(R.id.emergencyFullNameInput);
        emergencyMobileInput = findViewById(R.id.emergencyMobileInput);
        mobileInput = findViewById(R.id.mobileInput);
        emailInput = findViewById(R.id.emailInput);
        addressInput = findViewById(R.id.addressInput);
        emailLayout = findViewById(R.id.emailLayout);
        profileImageInput = findViewById(R.id.profileImageInput);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            String origin = bundle.getString("origin");
            if(origin!=null && origin.equals("SignUp")) {
                backButton.hide();
                emailLayout.setVisibility(View.GONE);
            }
        }

        initDatePicker();

        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(findViewById(android.R.id.content).getRootView());
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(
                this,
                R.array.biological_sex,
                R.layout.spinner_list
        );
        adapterSex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSex.setAdapter(adapterSex);

        ArrayAdapter<CharSequence> adapterBloodType = ArrayAdapter.createFromResource(
                this,
                R.array.blood_type,
                R.layout.spinner_list
        );
        adapterBloodType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBloodType.setAdapter(adapterBloodType);


        ArrayAdapter<CharSequence> adapterRelation = ArrayAdapter.createFromResource(
                this,
                R.array.relation,
                R.layout.spinner_list
        );
        adapterRelation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRelation.setAdapter(adapterRelation);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    assert data != null;
                    uri = data.getData();
                    try {
                        bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    profileImageInput.setImageBitmap(bitmapImage);
                } else {
                    Toast.makeText(EditProfile.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                } catch (Exception e) {
                    Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullNameInput.getText().toString().equals("")) {
                    Toast.makeText(EditProfile.this, "Name field is mandatory", Toast.LENGTH_SHORT).show();
                } else {
                    storeData();
                    Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intent);
                }
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }


    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    private void storeData() {

        if (weightInput.getText().toString().equals("")) { weightInput.setText("-1"); }
        if (heightInput.getText().toString().equals("")) { heightInput.setText("-1"); }

        User user = new User(fullNameInput.getText().toString(), mobileInput.getText().toString(), addressInput.getText().toString(), dateButton.getText().toString().toString(), spinnerSex.getSelectedItem().toString(),
                spinnerBloodType.getSelectedItem().toString(), allergiesInput.getText().toString(), historyInput.getText().toString(), chronicIllnessesInput.getText().toString(), emergencyFullNameInput.getText().toString(),
                spinnerRelation.getSelectedItem().toString(), emergencyMobileInput.getText().toString(), Integer.parseInt(heightInput.getText().toString().toString()), Integer.parseInt(weightInput.getText().toString()), bitmapImage);

        databaseHelper.storeData(user, sharedPref.getString("email", ""));
    }
}