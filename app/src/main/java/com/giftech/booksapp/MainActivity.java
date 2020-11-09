package com.giftech.booksapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editSearch;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private ArrayList<ItemData> values;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSearch = findViewById(R.id.edit_query);
        buttonSearch = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.recycler_view);

        values= new ArrayList<>();
        itemAdapter = new ItemAdapter(this, values);
        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setAdapter(itemAdapter);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });



    }

    private void searchBooks() {
        String queryString = editSearch.getText().toString();

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected() && queryString != null){
            new FetchBook(this, values, itemAdapter, recyclerView)
                    .execute(queryString);
        }else {
            Toast.makeText(this, "Check your network connection", Toast.LENGTH_LONG).show();
        }

    }
}