package com.example.laba4;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import java.util.ArrayList;

public class mainActivity extends Activity {
    public int[] a = {0,0};
    public String username;
    public String editik;
    int selectedItem;
    ArrayList itemList;
    String someArray[] = {"bob", "bibka", "shmupsik", "pupsik", "bobik"};
    ListView listView;
    public SharedPreferences sharedPrefs;
    public SharedPreferences.Editor editor;

    ArrayAdapter<String> arrayAdapter;
    public TextView editikText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainactivity);

        Bundle arguments = getIntent().getExtras();
        username = arguments.get("username").toString();
        TextView usernameText = findViewById(R.id.username);
        usernameText.setText(username);

        listView = (ListView) findViewById(R.id.List1);
        itemList = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_list_view, R.id.textItem, itemList);
        listView.setAdapter(arrayAdapter);

        loadData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("LIST_VIEW", "item id:  " + i);
                selectedItem = i;
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.add("item " + itemList.size());
                arrayAdapter.notifyDataSetChanged();
                selectedItem = itemList.size() - 1;
                Log.i("LIST_VIEW", "selected:  " + selectedItem);
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    itemList.remove(selectedItem);
                    selectedItem = itemList.size() - 1;
                    arrayAdapter.notifyDataSetChanged();
                } catch ( IndexOutOfBoundsException e ) {

                }
            }
        });

        Button deleteAllButton = findViewById(R.id.deleteAllButton);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemList.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        editor = sharedPrefs.edit();

    }

    public void loadData() {
        sharedPrefs = this.getPreferences(Context.MODE_PRIVATE);
//      editikText.setText(sharedPrefs.getString("test1","bib"));

        int size = sharedPrefs.getInt("size", 0);
        int i;

        for (i = 0; i < size; i++) {
            itemList.add(sharedPrefs.getString("" + i, ""));
            arrayAdapter.notifyDataSetChanged();
        }

        Log.i("OTLADKA_PRINTFOM", "loadData");
        Log.i("OTLADKA_PRINTFOM", (sharedPrefs.getString("test1","bib")));
    }

    public void saveData() {
        editor.putInt("size", itemList.size());

        int i;
        for (i = 0; i < itemList.size(); i++) {
            editor.putString("" + i, itemList.get(i).toString());
        }

        editor.apply();

        Log.i("OTLADKA_PRINTFOM", "saveData");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("OTLADKA_PRINTFOM", "onPause");
        saveData();

    }
}



