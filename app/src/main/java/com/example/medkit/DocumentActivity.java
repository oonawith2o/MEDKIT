package com.example.medkit;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

public class DocumentActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SharedPreferences sharedPref;

    FloatingActionButton backButton;
    FloatingActionButton editButton;
    ImageView imageView;

    private Uri uri;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        databaseHelper = new DatabaseHelper(this);
        sharedPref = getApplicationContext().getSharedPreferences("CurrentUser", MODE_PRIVATE);

        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        imageView = findViewById(R.id.document_image_view);

        this.setImageView();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);

                Intent originalIntent = getIntent();
                Bundle bundle = originalIntent.getExtras();
                String type = null;
                if(bundle!=null) {
                    type = bundle.getString("type");
                }
                if (bitmapImage == null && databaseHelper.getImage(sharedPref.getString("email", ""), type) == null) {
                    bitmapImage = null;
                } else if (bitmapImage == null && databaseHelper.getImage(sharedPref.getString("email", ""), type) != null) {
                    bitmapImage = databaseHelper.getImage(sharedPref.getString("email", ""), type);
                }
                databaseHelper.setImage(sharedPref.getString("email",""), bitmapImage, type);
                startActivity(intent);
            }
        });

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
                                Toast.makeText(DocumentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            imageView.setImageBitmap(bitmapImage);
                        } else {
                            Toast.makeText(DocumentActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    activityResultLauncher.launch(intent);
                } catch (Exception e) {
                    Toast.makeText(DocumentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setImageView() {

        Intent originalIntent = getIntent();
        Bundle bundle = originalIntent.getExtras();
        String type = null;
        if(bundle!=null) {
            type = bundle.getString("type");
        }

        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        if (databaseHelper.getImage(sharedPref.getString("email", ""), type) != null) {
            imageView.setImageBitmap(databaseHelper.getImage(sharedPref.getString("email", ""), type));
        }
    }

}