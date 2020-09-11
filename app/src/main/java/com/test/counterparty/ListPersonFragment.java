package com.test.counterparty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.counterparty.adapter.ListPersonAdapter;
import com.test.counterparty.database.AppDelegate;
import com.test.counterparty.database.Person;
import com.test.counterparty.database.PersonDao;

public class ListPersonFragment extends Fragment implements View.OnClickListener {
    private View root;
    private RecyclerView mRecyclerView;
    private ListPersonAdapter mAdapter;
    private ImageView mAddPersonBtn;
    private PersonDao personDao;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root =inflater.inflate(R.layout.fragment_list_person,container,false);
        // personDao = ((AppDelegate) root.getContext().getApplicationContext()).getPersonDatabase().getPersonDao();
        init();
        return root;
    }

    void init(){

        mAddPersonBtn = root.findViewById(R.id.add_person_btn);
        mAddPersonBtn.setOnClickListener(this);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        mAdapter = new ListPersonAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_person_btn:
                Intent intent = AddPartnerActivity.newIntent(getContext());
                startActivity(intent);
                //personDao.insertPersons(new Person("tfadhhhhhhhhhhhjjjjjjjjjjjhest","afasassa","Pasdas dafssd"));
                //mAdapter.updateListPersons();
                break;
        }
    }

    @Override
    public void onResume() {
        mAdapter.updateListPersons();
        super.onResume();
    }
}
