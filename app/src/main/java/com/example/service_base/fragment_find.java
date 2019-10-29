package com.example.service_base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service_base.adapter.RepairAdapter;

public class fragment_find extends Fragment {

    private RecyclerView repairView;
    private RepairAdapter repairAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find, container, false);

        repairView = v.findViewById(R.id.recycleView_repair);


        repairView.setLayoutManager(new LinearLayoutManager(v.getContext()));


        repairAdapter = new RepairAdapter(50);
        repairView.setAdapter(repairAdapter);

        return v;
    }
}
