package com.test.counterparty.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.test.counterparty.PartnerDescriptionActivity;
import com.test.counterparty.R;
import com.test.counterparty.database.AppDelegate;
import com.test.counterparty.database.Person;
import com.test.counterparty.database.PersonDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListPersonAdapter extends RecyclerView.Adapter<ListPersonAdapter.ListPersonHolder> implements Comparator<Person> {
    private final PersonDao personDao;
    private List<Person> mPersons;


    public ListPersonAdapter(Context context){
        personDao = ((AppDelegate) context.getApplicationContext()).getPersonDatabase().getPersonDao();
        mPersons = new ArrayList<>();
    }

    public void updateListPersons(){
        mPersons.clear();
        mPersons.addAll(personDao.getPersons());
        Collections.sort(mPersons,this);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ListPersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ListPersonHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPersonHolder holder, int position) {
        holder.bind(mPersons.get(position));
    }

    @Override
    public int getItemCount() {
        return mPersons.size();
    }

    @Override
    public int compare(Person o1, Person o2) {
        return o1.getName().compareTo(o2.getName());
    }

    class ListPersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNamePersonTv;
        private TextView mPhonePersonTv;
        private TextView mEmailPersonTv;
        private CardView mCardView;
        private Person mPerson;
        private ImageView mDeletePerson;
        private ImageView mLogoPerson;
        public ListPersonHolder(@NonNull View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_item);
            mNamePersonTv = itemView.findViewById(R.id.name_person_tv);
            mPhonePersonTv = itemView.findViewById(R.id.phone_person_tv);
            mEmailPersonTv = itemView.findViewById(R.id.email_person_tv);
            mDeletePerson = itemView.findViewById(R.id.delete_person_im);
            mLogoPerson = itemView.findViewById(R.id.logo_person_iv);
            mCardView.setOnClickListener(this);
            mDeletePerson.setOnClickListener(this);
        }
        void bind(Person person){
            mPerson = person;
            mNamePersonTv.setText(person.getName());
            mPhonePersonTv.setText(person.getPhone());
            mEmailPersonTv.setText(person.getEmail());
            if(person.getImagePath()!=null){
                Picasso.get().load(Uri.parse(person.getImagePath()) ).noPlaceholder().centerCrop().fit().into((mLogoPerson));
            }

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.card_item:
                    Intent intent = PartnerDescriptionActivity.newIntent(v.getContext(),mPerson);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.delete_person_im:
                    personDao.deletePerson(mPerson);
                    updateListPersons();
                    Toast.makeText(v.getContext(), "Данный профиль удален", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
