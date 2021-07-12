package com.nando.rentalmobil.activity;

import android.R.layout;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nando.rentalmobil.R;
import com.nando.rentalmobil.helper.DataHelper;

public class DaftarMobilActivity extends AppCompatActivity {

    //deklarasi variabel
    String[] daftar;
    ListView ListView1;
    protected Cursor cursor;
    DataHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil);
        //membuat objek dbcenter
        dbcenter = new DataHelper(this);
        //memangil method
        RefreshList();
        setupToolbar();

    }

    public void RefreshList() {
        //membuat objek db yang akan di baca
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        //membaca database melalui cursor dengan query
        cursor = db.rawQuery("SELECT * FROM mobil", null);
        ////membuat objek untuk mengembalikan jumlah item yg akan ditampilkan ke list
        daftar = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }
        //menghubungkan variabel ke komponen pada layout
        ListView1 = findViewById(R.id.listView1);
        //membuat variabel adapter
        ListView1.setAdapter(new ArrayAdapter(this, layout.simple_list_item_1, daftar));
        //select listview
        ListView1.setSelected(true);
        //membuat fungsi onItemClick
        ListView1.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                //membuat objek intent berpindah activity ke DetailMobilActivity
                Intent i = new Intent(DaftarMobilActivity.this, DetailMobilActivity.class);
                i.putExtra("merk", selection);
                startActivity(i);
            }
        });

        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbInfoMbl);
        toolbar.setTitle("Informasi Daftar Mobil");
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