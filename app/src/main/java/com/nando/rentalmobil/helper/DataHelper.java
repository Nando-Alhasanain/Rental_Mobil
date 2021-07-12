package com.nando.rentalmobil.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataHelper extends SQLiteOpenHelper {

    //Deklarasi variabel untuk menyimpan nama database
    private static final String DATABASE_NAME = "rentalmobil.db";
    //Deklarasi variabel untuk menyimpan versi database
    private static final int DATABASE_VERSION = 1;

    //membuat konstruktor
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON");

        //membuat tabel penyewa
        db.execSQL("create table penyewa (" +
                "nama text," +
                "alamat text," +
                "no_hp text," +
                "primary key(nama)" +
                ");" +
                "");

        //membuat tabel mobil
        db.execSQL("create table mobil(" +
                "merk text," +
                "harga int," +
                "primary key(merk)" +
                ");" +
                "");

        //membuat tabel sewa
        db.execSQL("create table sewa(" +
                "merk text," +
                "nama text," +
                "promo int," +
                "lama int," +
                "total double," +
                "foreign key(merk) references mobil (merk), " +
                "foreign key(nama) references penyewa (nama) " +
                ");" +
                "");

        //Memasukan value ke tabel mobil
        db.execSQL("insert into mobil values (" +
                "'Avanza'," +
                "400000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Xenia'," +
                "400000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Ertiga'," +
                "400000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'APV'," +
                "400000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Innova'," +
                "500000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Xpander'," +
                "550000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Pregio'," +
                "550000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Elf'," +
                "700000" +
                ");" +
                "");
        db.execSQL("insert into mobil values (" +
                "'Alphard'," +
                "1500000" +
                ");" +
                "");
    }

    //method baca data
    public ArrayList<String> getAllCategories() {
        //membuat variabel lokal untuk menampung nilai sementara
        ArrayList<String> categories = new ArrayList<String>();
        //mambuat query untuk menuju tabel mobil
        String selectQuery = "select * from mobil";
        //membuat objek db yang akan di baca
        SQLiteDatabase db = this.getReadableDatabase();
        //proses membaca database melalui kursor
        Cursor cursor = db.rawQuery(selectQuery, null);
        //memindah kursor ke data yang pertama
        if (cursor.moveToFirst()) {
            //memindah cursor ke variabel categories
            do {
                categories.add(cursor.getString(0)); //baca dari index ke o
            }
            //peintah agar kursor berjalan dari depan ke belakang
            while (cursor.moveToNext());
        }

        //menutup cursor
        cursor.close();
        //menutup database
        db.close();

        //mengembalikan nilai
        return categories;
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

    }

}