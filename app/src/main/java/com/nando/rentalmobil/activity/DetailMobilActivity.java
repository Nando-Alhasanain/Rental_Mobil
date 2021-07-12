package com.nando.rentalmobil.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nando.rentalmobil.R;
import com.nando.rentalmobil.helper.DataHelper;

public class DetailMobilActivity extends AppCompatActivity {

    //deklarasi variabel
    protected Cursor cursor;
    String sMerk, sHarga, sGambar;
    DataHelper dbHelper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mobil);

        //deklarasi variabel bundle utk mengambil pesan yg dikirimkan melalui method intent
        Bundle terima = getIntent().getExtras();

        //membuat objek dbcenter
        dbHelper = new DataHelper(this);

        //membuat variabel string untuk menangkap data "merk"
        String merk = terima.getString("merk");
        //membuat objek db yang akan di baca
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //membaca database melalui cursor dengan query
        cursor = db.rawQuery("select * from mobil where merk = '" + merk + "'", null);
        cursor.moveToFirst();
        //mendapatkan value berdasarkan merk yg dipilih user
        if (cursor.getCount() > 0) {
            sMerk = cursor.getString(0);
            sHarga = cursor.getString(1);
        }
        //menampilkan gambar berdasarkan merk yg dipilih user
        if (sMerk.equals("Avanza")) {
            sGambar = "avanza";
        } else if (sMerk.equals("Xenia")) {
            sGambar = "xenia";
        } else if (sMerk.equals("Ertiga")) {
            sGambar = "ertiga";
        } else if (sMerk.equals("APV")) {
            sGambar = "apv";
        } else if (sMerk.equals("Innova")) {
            sGambar = "innova";
        } else if (sMerk.equals("Xpander")) {
            sGambar = "xpander";
        } else if (sMerk.equals("Pregio")) {
            sGambar = "pregio";
        } else if (sMerk.equals("Elf")) {
            sGambar = "elf";
        } else if (sMerk.equals("Alphard")) {
            sGambar = "alphard";
        }

        //menghubungkan variabel ke komponen pada layout
        ImageView ivGambar = findViewById(R.id.ivMobil);
        TextView tvMerk = findViewById(R.id.JMobil);
        TextView tvHarga = findViewById(R.id.JHarga);

        //menampilkan nilai
        tvMerk.setText(sMerk);
        tvHarga.setText("Rp. " + sHarga);
        //menghubungkan gambar mobil ke drawable
        ivGambar.setImageResource(getResources().getIdentifier(sGambar, "drawable", getPackageName()));
        //memangil method setupToolbar
        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbDetailMbl);
        toolbar.setTitle("Detail Mobil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
