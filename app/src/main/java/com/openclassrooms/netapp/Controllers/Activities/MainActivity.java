package com.openclassrooms.netapp.Controllers.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.netapp.Controllers.Fragments.MainFragment;
import com.openclassrooms.netapp.Models.GithubUser;
import com.openclassrooms.netapp.R;

public class MainActivity extends AppCompatActivity implements MainFragment.OnItemClickListener {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureAndShowMainFragment();

        startActivity(new Intent(this, DetailActivity.class));
    }



    // -------------------
    // CONFIGURATION
    // -------------------

    private void configureAndShowMainFragment(){

        mainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);

        if (mainFragment == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_frame_layout, mainFragment)
                    .commit();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

        Toast.makeText(getContext(), "You are trying to delete user : "+user.getLogin(), Toast.LENGTH_SHORT).show();
    }
}
