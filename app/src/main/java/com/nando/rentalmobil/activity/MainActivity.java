package com.nando.rentalmobil.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nando.rentalmobil.R;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //menghubungkan variabel button informasi ke komponen button pada layout
        Button informasi = findViewById(R.id.btn_info_mobil);
        //menghubungkan variabel button sewa ke komponen button pada layout
        Button sewa = findViewById(R.id.btn_sewa);

        //membuat fungsi onclick
        informasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek intent berpindah activity ke DaftarMobilActivity
                Intent i = new Intent(MainActivity.this, DaftarMobilActivity.class);
                startActivity(i);
            }
        });
        //membuat fungsi onclick
        sewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek intent berpindah activity ke PenyewaActivity
                Intent p = new Intent(MainActivity.this, PenyewaActivity.class);
                startActivity(p);
            }
        });



    }


}
