package com.example.to_dolistapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.to_dolistapp.Mode.Todomode;
import com.example.to_dolistapp.utils.databasee;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class addNewTask extends BottomSheetDialogFragment {
    public static  final String Tag ="AddNewTask";

    private EditText mEdittext;
    private Button mSavebutton;

    private databasee mydb;

    public static addNewTask newInstance(){
        return new addNewTask();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View V =inflater.inflate(R.layout.add_task,container,false);
        return V;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEdittext=view.findViewById(R.id.edittxt);
        mSavebutton=view.findViewById(R.id.add);

        mydb=new databasee(getActivity());
        boolean isUpdate =false;

        Bundle bundle =getArguments();
        if (bundle!=null){
            isUpdate= true;
            String task = bundle.toString();
            mEdittext.setText(task);

            if (task.length() >0){
                mSavebutton.setEnabled(false);
            }
        }
        mEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")){
                    mSavebutton.setEnabled(false);
                    mSavebutton.setBackgroundColor(Color.GRAY);
                }else {
                    mSavebutton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        boolean finalIsUpdate = isUpdate;
        mSavebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text =mEdittext.getText().toString();
                if (finalIsUpdate){
                    mydb.updateTask(bundle.getInt("id"),text);
                }else {
                    Todomode item = new Todomode();
                    item.setTask(text);
                    item.setStatus(0);
                    mydb.insertTask(item);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OnDialogCloseL){
            ((OnDialogCloseL)activity).onDialogClose(dialog);

        }
    }
}
