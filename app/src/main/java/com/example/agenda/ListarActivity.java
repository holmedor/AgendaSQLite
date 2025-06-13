package com.example.agenda;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {
    private ListView lista;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_listar);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

         */
        lista = (ListView) findViewById(R.id.lista);
        try {
            DataBaseApp adminSQL = new DataBaseApp(this, "agendaDB", null, 1);
            SQLiteDatabase bd = adminSQL.getReadableDatabase();

            items = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text2, items);
            lista.setAdapter(adapter);
            String sql="SELECT codigo, fecha FROM agenda order by fecha";
            Cursor cursor = bd.rawQuery(sql, null);
            Log.i("BDSQL",sql);
            if (cursor.moveToFirst()) {
                items.add(cursor.getString(1));
                cursor.moveToNext();
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}