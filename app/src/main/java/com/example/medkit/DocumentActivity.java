package com.example.medkit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DocumentActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 1000;

    FloatingActionButton backButton;
    FloatingActionButton editButton;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        backButton = findViewById(R.id.backButton);
        editButton = findViewById(R.id.editButton);
        imageView = findViewById(R.id.document_image_view);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intent);
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickImage = new Intent(Intent.ACTION_PICK);
                pickImage.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickImage,GALLERY_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) {
            if(requestCode==GALLERY_REQ_CODE) {
                imageView.setImageURI(data.getData());
            }
        }
    }
}