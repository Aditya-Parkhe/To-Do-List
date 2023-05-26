package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
        EditText item;
        Button add;
        ListView listview;
        ArrayList<String> itemList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        item=findViewById(R.id.editText);
        add=findViewById(R.id.button);
        listview=findViewById(R.id.list);

        itemList = FileList.readData(this);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,itemList);
        listview.setAdapter(arrayAdapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemName = item.getText().toString();
                itemList.add(itemName);
                item.setText("");
                FileList.writeData(itemList,getApplicationContext());
                arrayAdapter.notifyDataSetChanged();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder( MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do You Want To Delete This Item? ");
                alert.setCancelable(false);
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        itemList.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        FileList.writeData(itemList,getApplicationContext());
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });

    }
}