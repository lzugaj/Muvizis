package com.luv2code.android.muvizis.ui.screens;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.luv2code.android.muvizis.R;
import com.luv2code.android.muvizis.ui.adapters.SectionsPagerAdapter;
import com.luv2code.android.muvizis.ui.dialogs.ExitDialog;

import static com.luv2code.android.muvizis.utils.AppConstants.EXIT_DIALOG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Muvizis");
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        Toast.makeText(this, helloMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            ExitDialog exitDialog = new ExitDialog();
            exitDialog.setCancelable(false);
            exitDialog.show(getSupportFragmentManager(), EXIT_DIALOG);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String helloMessage() {
        return "Muvizis App";
    }
}
