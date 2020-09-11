package com.test.counterparty;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;


import androidx.fragment.app.Fragment;

import com.test.counterparty.database.Person;

import java.io.Serializable;

public class PartnerDescriptionActivity extends SingleFragmentActivity implements Serializable {
    public static final String EXTRA_PERSON ="PartnerDescriptionActivity.Person";

    @Override
    protected Fragment createFragment() {
        return new PartnerDescriptionFragment();
    }

    public  static Intent newIntent (Context context, Person person){
        Intent intent = new Intent(context,PartnerDescriptionActivity.class);
        intent.putExtra(EXTRA_PERSON, person);
        return intent;
    }
}
