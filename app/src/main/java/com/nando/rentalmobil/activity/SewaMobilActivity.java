package com.nando.rentalmobil.activity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nando.rentalmobil.R;
import com.nando.rentalmobil.helper.DataHelper;

import java.util.List;

public class SewaMobilActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //deklarasi variabel
    EditText nama, alamat, no_hp, lama;
    RadioGroup promo;
    RadioButton weekday, weekend;
    Button selesai;

    String sNama, sAlamat, sNo, sMerk, sLama;
    double dPromo, dTotal;
    int iLama, iPromo, iHarga;

    private Spinner spinner;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sewa);

        //mengambil data dari dbHelper
        dbHelper = new DataHelper(this);

        //menghubungkan variabel ke komponen pada layout
        spinner = findViewById(R.id.spinner);
        selesai = findViewById(R.id.selesaiHitung);
        nama = findViewById(R.id.eTNama);
        alamat = findViewById(R.id.eTAlamat);
        no_hp = findViewById(R.id.eTHP);
        promo = findViewById(R.id.promoGroup);
        weekday = findViewById(R.id.rbWeekDay);
        weekend = findViewById(R.id.rbWeekEnd);
        lama = findViewById(R.id.eTLamaSewa);

        //membuat fungsi setOnItemSelectedListener pada spinner
        spinner.setOnItemSelectedListener(this);

        //memengil methode loadSpinnerData
        loadSpinnerData();

        //membuat fungsi onclick ke variabel selesai
        selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menyimpan value inputan user
                sNama = nama.getText().toString();
                sAlamat = alamat.getText().toString();
                sNo = no_hp.getText().toString();
                sLama = lama.getText().toString();

                //mengecek apakah semua data sudah terisi
                if (sNama.isEmpty() || sAlamat.isEmpty() || sNo.isEmpty() || sLama.isEmpty()) {
                    //menampilkan toast
                    Toast.makeText(SewaMobilActivity.this, "(*) tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    return;
                }
                //value untuk promo weekday 10%
                if (weekday.isChecked()) {
                    dPromo = 0.1;
                }
                //value untuk promo weekend 25%
                else if (weekend.isChecked()) {
                    dPromo = 0.25;
                }

                //menentukan harga berdasarkan merk yang dipilih user
                if (sMerk.equals("Avanza")) {
                    iHarga = 400000;
                } else if (sMerk.equals("Xenia")) {
                    iHarga = 400000;
                } else if (sMerk.equals("Ertiga")) {
                    iHarga = 400000;
                } else if (sMerk.equals("APV")) {
                    iHarga = 450000;
                } else if (sMerk.equals("Innova")) {
                    iHarga = 500000;
                } else if (sMerk.equals("Xpander")) {
                    iHarga = 550000;
                } else if (sMerk.equals("Pregio")) {
                    iHarga = 550000;
                } else if (sMerk.equals("Elf")) {
                    iHarga = 700000;
                } else if (sMerk.equals("Alphard")) {
                    iHarga = 1500000;
                }

                //iLama merupakan sLama
                iLama = Integer.parseInt(sLama);
                //perintah perhitungan promo
                iPromo = (int) (dPromo * 100);
                //perintah perhitungan total harga
                dTotal = (iHarga * iLama) - (iHarga * iLama * dPromo);

                //mengisi database
                SQLiteDatabase dbH = dbHelper.getWritableDatabase();
                //memasukan value ke tabel penyewa
                dbH.execSQL("INSERT INTO penyewa (nama, alamat, no_hp) VALUES ('" +
                        sNama + "','" +
                        sAlamat + "','" +
                        sNo + "');");
                //memasukan value ke tabel sewa
                dbH.execSQL("INSERT INTO sewa (merk, nama, promo, lama, total) VALUES ('" +
                        sMerk + "','" +
                        sNama + "','" +
                        iPromo + "','" +
                        iLama + "','" +
                        dTotal + "');");
                PenyewaActivity.m.RefreshList();
                finish();

            }
        });
        //memangil methode setupToolbar
        setupToolbar();

    }

    private void setupToolbar() {
        //menghubungkan variabel toolbar ke komponen toolbar pada layout
        Toolbar toolbar = findViewById(R.id.tbSewaMobl);
        //set judul
        toolbar.setTitle("Sewa Mobil");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void loadSpinnerData() {

        //membuat objek dari DataHelper
        DataHelper db = new DataHelper(getApplicationContext());
        //mengambil data
        List<String> categories = db.getAllCategories();

        //menghubungkan array list mobil ke spinner item pada layout
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        sMerk = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}