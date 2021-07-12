package com.nando.rentalmobil.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nando.rentalmobil.R;
import com.nando.rentalmobil.helper.DataHelper;

import static android.R.layout.*;

public class PenyewaActivity extends AppCompatActivity {

    //deklarasi variabel
    String[] daftar;
    ListView ListView1;
    protected Cursor cursor;
    DataHelper dbcenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyewa);
        //menghubungkan variabel button tambah ke komponen button pada layout
        Button tambah = findViewById(R.id.tambahPenyewa);

        //membuat fungsi onclick
        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //membuat objek intent berpindah activity ke SewaMobilActivity
                Intent p = new Intent(PenyewaActivity.this, SewaMobilActivity.class);
                startActivity(p);
            }
        });

        //membuat objek dbcenter
        dbcenter = new DataHelper(this);

        //memengil method RefreshList
        RefreshList();
        //memengil method setupToolbar
        setupToolbar();

    }
    //method untuk list penyewa
    public void RefreshList() {
        //membuat objek db yang akan di baca
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        //membaca database melalui cursor dengan query
        cursor = db.rawQuery("SELECT * FROM penyewa", null);
        //membuat objek untuk mengembalikan jumlah item yg akan ditampilkan ke list
        daftar = new String[cursor.getCount()];
        //memindah kursor ke data yang pertama
        cursor.moveToFirst();
        //perulangan dengan beberapa kondisi
        for (int i = 0; i < cursor.getCount(); i++) {
            //memindah kursor ke data yang pertama
            cursor.moveToPosition(i);
            daftar[i] = cursor.getString(0);
        }
        //menghubungkan variabel listview informasi ke komponen pada layout
        ListView1 = findViewById(R.id.listView1);
        //membuat variabel adapter
        ListView1.setAdapter(new ArrayAdapter(this, simple_list_item_1, daftar));
        //select listview
        ListView1.setSelected(true);
        //membuat fungsi OnItemClick
        ListView1.setOnItemClickListener(new OnItemClickListener() {
            //menggunakan api android version marshmallow
            @RequiresApi(api = Build.VERSION_CODES.M)
            //method onItemClick
            public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2];
                //membuat dua pilihan menu lihat data dan hapus data
                final CharSequence[] dialogitem = {"Lihat Data", "Hapus Data"};
                //membuat objek
                AlertDialog.Builder builder = new AlertDialog.Builder(PenyewaActivity.this);
                //set judul
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        //switch case untuk lihat data dan hapus data
                        switch (item) {
                            //case lihat data
                            case 0: {
                                //membuat objek intent berpindah activity ke DetailPenyewaActivity
                                Intent i = new Intent(PenyewaActivity.this, DetailPenyewaActivity.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            }
                            case 1: {
                                //membuat objek db yang akan diubah datanya
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                //query untuk menghapus data
                                db.execSQL("DELETE FROM penyewa where nama = '" + selection + "'");
                                db.execSQL("DELETE FROM sewa where nama = '" + selection + "'");
                                RefreshList();
                                break;
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });
        ((ArrayAdapter) ListView1.getAdapter()).notifyDataSetInvalidated();

    }

    private void setupToolbar() {
        //menghubungkan variabel toolbar ke komponen toolbar pada layout
        Toolbar toolbar = findViewById(R.id.tbPenyewa);
        //set judul
        toolbar.setTitle("Daftar Penyewa");
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
