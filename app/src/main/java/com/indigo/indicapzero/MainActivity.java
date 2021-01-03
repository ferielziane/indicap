package com.indigo.indicapzero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.database.sqlite.SQLiteDatabase.openDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fileName = "t3b.db";
        final File file = getDatabasePath(fileName );
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }

            InputStream inputStream = null;
            try {
                inputStream = getAssets().open("t3b.db");
            } catch (IOException e) {
                e.printStackTrace();
            }
            OutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[1024 * 8];
            int numOfBytesToRead = 0;
            while(true) {
                try {
                    if (!((numOfBytesToRead = inputStream.read(buffer)) > 0)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    outputStream.write(buffer, 0, numOfBytesToRead);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

       // db = SQLiteDatabase.openOrCreateDatabase(file, null);

        final Button button = findViewById(R.id.monbutton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SQLiteDatabase maBase;
                 maBase = SQLiteDatabase.openOrCreateDatabase(file,null);
                Cursor pos = maBase.rawQuery("SELECT * FROM stations", null);

                if (pos.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "Base de deonnée vide", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer contenu = new StringBuffer();
                while (pos.moveToNext()) {
                    contenu.append("id:" + pos.getString(0) + "/n");
                    contenu.append("nom:" + pos.getString(1) + "/n");
                    contenu.append("ligne:" + pos.getString(2) + "/n");
                    contenu.append("direction1:" + pos.getString(3) + "/n");
                    contenu.append("direction2:" + pos.getString(4) + "/n");
                    contenu.append("latitude:" + pos.getString(5) + "/n");
                    contenu.append("longitude:" + pos.getString(6) + "/n");
                }
                Toast.makeText(getApplicationContext(), "contenu de la base" + "/n" + contenu.toString(), Toast.LENGTH_LONG).show();

            }
        });

    }
}

