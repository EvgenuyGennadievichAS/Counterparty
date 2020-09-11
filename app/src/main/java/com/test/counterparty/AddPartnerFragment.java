package com.test.counterparty;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.test.counterparty.database.AppDelegate;
import com.test.counterparty.database.Person;
import com.test.counterparty.database.PersonDao;

import static android.app.Activity.RESULT_OK;

public class AddPartnerFragment extends Fragment implements View.OnClickListener {

    private PersonDao personDao;
    private View root;

    private String mImagePath;

    private EditText mFIOPerson;
    private EditText mEmailPerson;
    private EditText mPhonePerson;
    private EditText mDescription;
    private Button mSavePerson;
    private ImageView mLogoPerson;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_add_partner,container,false);
        personDao = ((AppDelegate) root.getContext().getApplicationContext()).getPersonDatabase().getPersonDao();
        init();
        return root;
    }

    void init(){
        mFIOPerson = root.findViewById(R.id.FIO_person_et);
        mEmailPerson = root.findViewById(R.id.email_person_et);
        mPhonePerson = root.findViewById(R.id.phone_person_et);
        mSavePerson = root.findViewById(R.id.save_person_btn);
        mLogoPerson = root.findViewById(R.id.image_person);
        mDescription = root.findViewById(R.id.description_person_et);
        mSavePerson = root.findViewById(R.id.save_person_btn);
        mSavePerson.setOnClickListener(this);
        mLogoPerson = root.findViewById(R.id.image_person);
        mLogoPerson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_person_btn:
                if(!(mFIOPerson.getText().toString().isEmpty()) & !(mPhonePerson.getText().toString().isEmpty())) {


                    personDao.insertPersons(new Person(
                            mFIOPerson.getText().toString(),
                            mPhonePerson.getText().toString(),
                            mEmailPerson.getText().toString(),
                            mDescription.getText().toString(),
                            mImagePath));
                    getActivity().finish();
                    Toast.makeText(getContext(), R.string.profile_is_add, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), R.string.dont_fill_required_fields, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.image_person:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode ==RESULT_OK){
            if(requestCode == 0){
                Uri selectedImage = data.getData();
                mImagePath=selectedImage.toString();
                Picasso.get().load(selectedImage).noPlaceholder().centerCrop().fit().into((ImageView) root.findViewById(R.id.image_person));
            }
        }
    }

}
