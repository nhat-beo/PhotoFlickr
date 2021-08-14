package com.example.photoflickr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.photoflickr.photo.model.Photo;
import com.example.photoflickr.photo.savephoto.SavePhotoHelper;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class PhotoActivity extends AppCompatActivity {
    private ImageView photoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);

        initUi();

        loadPhoto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Lay du lieu tu Photo
        Photo photo = (Photo) getIntent().getExtras().get("photoData");

        int id = item.getItemId();

        if (id == R.id.download) {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            Picasso.get()
                    .load(photo.getUrl_n())
                    .into(new SavePhotoHelper(PhotoActivity.this, getApplicationContext().getContentResolver(),
                            fileName, "Photo description"));
            Toast.makeText(getApplicationContext(), "Download successfully", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadPhoto() {
        Photo photo = (Photo) getIntent().getExtras().get("photoData");
        Picasso.get().load(photo.getUrl_o()).into(photoDetail);
    }

    private void initUi() {
        photoDetail = findViewById(R.id.photo_detail);
    }
}