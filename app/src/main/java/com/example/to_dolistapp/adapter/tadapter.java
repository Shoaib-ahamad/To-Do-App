package com.example.to_dolistapp.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.to_dolistapp.MainActivity;
import com.example.to_dolistapp.Mode.Todomode;
import com.example.to_dolistapp.R;
import com.example.to_dolistapp.addNewTask;
import com.example.to_dolistapp.utils.databasee;

import java.util.List;

public class tadapter extends RecyclerView.Adapter<tadapter.Myviewholder> {
    private List<Todomode>  mlist;
    private MainActivity activity;
    private databasee mydb;

    public tadapter(databasee mydb,MainActivity activity){
        this.activity=activity;
        this.mydb =mydb;
    }
    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View V= LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);
        return new Myviewholder(V);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        final Todomode item = mlist.get(position);
        holder.box.setText(item.getTask());
        holder.box.setChecked(tob(item.getStatus()));
        holder.box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    mydb.updateStatus(item.getId(),1);
                }else {
                    mydb.updateStatus(item.getId(),0);
                }
            }
        });

    }
    public boolean tob(int num){
        return num!=0;
    }
    public Context getContext(){
        return activity;

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setTasks(List<Todomode> mlist){
        this.mlist = mlist;
        notifyDataSetChanged();
    }
    public void deleteTask(int position){
        Todomode item = mlist.get(position);
        mydb.deleteTask(item.getId());

        mlist.remove(position);
        notifyItemRemoved(position);
    }



    public void edititem(int position){
        Todomode item = mlist.get(position);
        Bundle bundel = new Bundle();
        bundel.putInt("id",item.getId());
        bundel.putString("task", item.getTask());

        addNewTask task =new addNewTask();
        task.setArguments(bundel);
        task.show(activity.getSupportFragmentManager(), task.getTag());
    }
    public static class Myviewholder extends RecyclerView.ViewHolder {
        CheckBox box;
        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            box =itemView.findViewById(R.id.box);

        }
    }
}
