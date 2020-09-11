package com.test.counterparty;

import androidx.fragment.app.Fragment;

public class ListPersonActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ListPersonFragment();
    }
}
