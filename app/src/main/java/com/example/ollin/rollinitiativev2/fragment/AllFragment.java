package com.example.ollin.rollinitiativev2.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ollin.rollinitiativev2.R;
import com.example.ollin.rollinitiativev2.model.Spacecraft;
import com.example.ollin.rollinitiativev2.sql.DBAdapter;

public class AllFragment extends Fragment {
    ListView listView;
    Button refreshBtn;
    ArrayAdapter<Spacecraft> adapter;

    public static AllFragment newInstance(){
        return new AllFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.allfrgmt, null);

        initializeViews(rootView);
        loadData();

        return  rootView;
    }

    private void initializeViews(View rootView){
        listView = (ListView)rootView.findViewById(R.id.all_LV);
        refreshBtn = (Button)rootView.findViewById((R.id.allRefresh));

        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    private void loadData(){
        DBAdapter dbAdapter = new DBAdapter(getActivity());
        adapter = new ArrayAdapter<Spacecraft>(getActivity(),android.R.layout.simple_list_item_1, dbAdapter.retrieveAll(this.toString()));
        listView.setAdapter(adapter);
    }

    @Override
    public String toString() {
        return "All";
    }
}
