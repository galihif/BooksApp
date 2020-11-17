package com.giftech.booksapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    private TextView textTitle;
    private TextView textAuthor;
    private TextView textDesc;
    private ImageView imgCover;
    private ItemData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();

        Intent intent = getIntent();
        if(intent.hasExtra("DATA")){
            data = intent.getParcelableExtra("DATA");
            textTitle.setText(data.itemTitle);
            textAuthor.setText(data.itemAuthor);
            textDesc.setText(data.itemDescription);
            new LoadImage(imgCover).execute(data.itemImage);
        }

    }

    private void initView() {

        textTitle = findViewById(R.id.tv_title);
        textAuthor = findViewById(R.id.tv_author);
        textDesc = findViewById(R.id.tv_desc);
        imgCover = findViewById(R.id.iv_cover);
    }

    private class LoadImage extends AsyncTask<String,Void, Bitmap>{
        private ImageView imageView;

        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            Bitmap bitmap = null;

            try {
                url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream(
                        url.openConnection().getInputStream()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}