package com.test.counterparty;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.test.counterparty.adapter.ListPersonAdapter;
import com.test.counterparty.database.AppDelegate;
import com.test.counterparty.database.Person;
import com.test.counterparty.database.PersonDao;

public class PartnerDescriptionFragment extends Fragment implements View.OnClickListener {

    private LinearLayout mLinearEdit;
    private LinearLayout mLinearAccept;
    private EditText mNamePerson;
    private EditText mPhonePerson;
    private EditText mEmailPerson;
    private EditText mDescriptionPerson;
    private ImageView mDeletePerson;
    private ImageView mEditPerson;
    private ImageView mAcceptEdit;
    private ImageView mCancelEdit;
    private ImageView mLogoPerson;
    private View root;
    private Person mPerson;

    private PersonDao mPersonDao;
    private InputMethodManager imm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_person_description,container,false);

        mPersonDao = ((AppDelegate) root.getContext().getApplicationContext()).getPersonDatabase().getPersonDao();
        Intent i = getActivity().getIntent();
        mPerson = (Person) i.getSerializableExtra(PartnerDescriptionActivity.EXTRA_PERSON);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE); //keyboard
        init();

        return root;
    }
    void init(){
        mNamePerson = root.findViewById(R.id.name_person_tv);
        mPhonePerson = root.findViewById(R.id.phone_person_tv);
        mEmailPerson = root.findViewById(R.id.email_person_tv);
        mDescriptionPerson = root.findViewById(R.id.description_person_tv);
        mNamePerson.setText(mPerson.getName());
        mPhonePerson.setText(mPerson.getPhone());
        mEmailPerson.setText(mPerson.getEmail());
        mDescriptionPerson.setText(mPerson.getDescription());
        mLogoPerson = root.findViewById(R.id.logo_person_iv);
        if(mPerson.getImagePath()!=null){
            Picasso.get().load(Uri.parse(mPerson.getImagePath()) ).noPlaceholder().centerCrop().fit().into((mLogoPerson));
        }

        mDeletePerson = root.findViewById(R.id.delete_person_im);
        mDeletePerson.setOnClickListener(this);

        mEditPerson = root.findViewById(R.id.edit_person);
        mEditPerson.setOnClickListener(this);

        mLinearEdit = root.findViewById(R.id.linear_edit);
        mLinearAccept = root.findViewById(R.id.linear_accept);
        mLinearAccept.setOnClickListener(this);
        mLinearEdit.setOnClickListener(this);

        mAcceptEdit = root.findViewById(R.id.accept_edit);
        mCancelEdit = root.findViewById(R.id.cancel_edit);
        mAcceptEdit.setOnClickListener(this);
        mCancelEdit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.delete_person_im: // удаление партнера
                mPersonDao.deletePerson(mPerson);
                getActivity().finish();
                Toast.makeText(getContext(), R.string.profile_delete, Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_person: // изменения партнера
                focusEnable();
                mLinearEdit.setVisibility(View.GONE);
                mLinearAccept.setVisibility(View.VISIBLE);
                break;

            case R.id.accept_edit: // принятие изменения
                mPerson.setName(mNamePerson.getText().toString());
                mPerson.setEmail(mEmailPerson.getText().toString());
                mPerson.setPhone(mPhonePerson.getText().toString());
                mPerson.setDescription(mDescriptionPerson.getText().toString());
                mPersonDao.insertPersons(mPerson);
                getActivity().finish();
                Toast.makeText(getContext(), R.string.profile_edit, Toast.LENGTH_SHORT).show();
                break;

            case R.id.cancel_edit: // back
                focusDisable();
                mLinearEdit.setVisibility(View.VISIBLE);
                mLinearAccept.setVisibility(View.GONE);
                break;


        }
    }

    void focusEnable(){
     mPhonePerson.setFocusableInTouchMode(true); mPhonePerson.setClickable(true);
     mNamePerson.setFocusableInTouchMode(true); mNamePerson.setClickable(true);
     mEmailPerson.setFocusableInTouchMode(true); mEmailPerson.setClickable(true);
     mDescriptionPerson.setFocusableInTouchMode(true); mDescriptionPerson.setClickable(true);
        mNamePerson.requestFocus();

        imm.showSoftInput(mNamePerson, InputMethodManager.SHOW_IMPLICIT);

    }

    void focusDisable(){
        mPhonePerson.setFocusableInTouchMode(false); mPhonePerson.setClickable(false);
        mNamePerson.setFocusableInTouchMode(false); mNamePerson.setClickable(false);
        mEmailPerson.setFocusableInTouchMode(false); mEmailPerson.setClickable(false);
        mDescriptionPerson.setFocusableInTouchMode(false); mDescriptionPerson.setClickable(false);
    }
}
