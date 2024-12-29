package com.example.to_dolistapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapp.Mode.Todomode;
import com.example.to_dolistapp.adapter.tadapter;
import com.example.to_dolistapp.utils.databasee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDialogCloseL {
    RecyclerView cyclerView;
    FloatingActionButton add;
    databasee mydb;
    private List<Todomode> mlist;
    private tadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cyclerView =findViewById(R.id.cycleView);
        add = findViewById(R.id.add);

        mydb = new databasee(MainActivity.this);
        mlist=new ArrayList<>();
        adapter= new tadapter(mydb,MainActivity.this);

        cyclerView.setHasFixedSize(true);
        cyclerView.setLayoutManager(new LinearLayoutManager(this));
        cyclerView.setAdapter(adapter);

        mlist= mydb.getAllTask();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTask.newInstance().show(getSupportFragmentManager(),addNewTask.Tag);

            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new touchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(cyclerView);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mlist= mydb.getAllTask();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);


    }
}