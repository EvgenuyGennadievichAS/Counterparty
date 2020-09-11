package com.test.counterparty;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class AddPartnerActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AddPartnerFragment();
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,AddPartnerActivity.class);
        return intent;
    }
}
