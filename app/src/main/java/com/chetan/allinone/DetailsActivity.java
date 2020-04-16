package com.chetan.allinone;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {
    String imgUrl;
    ImageView imageView;
    Button wallButton;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        Intent intent = getIntent();
        imgUrl = intent.getStringExtra("image");
        imageView = findViewById(R.id.dImageView);
        wallButton = findViewById(R.id.wallpaperButton);
        Picasso.get().load(imgUrl).into(imageView);
        wallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setImgWallpaper();
            }
        });
    }

    public void setImgWallpaper() {
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        if (bitmap != null) {

            try {
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(DetailsActivity.this, "Wallpaper set..", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
