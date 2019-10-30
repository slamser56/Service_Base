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

import com.example.service_base.Repair_item.Repair_item;
import com.example.service_base.adapter.RepairAdapter;

import java.util.ArrayList;
import java.util.List;

public class fragment_find extends Fragment {

    private RecyclerView repairView;
    private RepairAdapter repairAdapter;
    private List<Repair_item> repair_items = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_find, container, false);


        setInitialData();

        repairView = v.findViewById(R.id.recycleView_repair);

        LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext());
        repairView.setLayoutManager(layoutManager);

        repairView.setHasFixedSize(true);

        repairAdapter = new RepairAdapter(repair_items);

        repairView.setAdapter(repairAdapter);

        return v;
    }

    private void setInitialData(){

        repair_items.add(new Repair_item (1, "22.02", "Open"));
        repair_items.add(new Repair_item (2, "21.02", "Open"));
        repair_items.add(new Repair_item (3, "20.02", "Open"));
        repair_items.add(new Repair_item (4, "10.02", "Close"));
    }
}
