package com.zamanak.plainolnotes3;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zamanak.plainolnotes3.database.NoteEntity;
import com.zamanak.plainolnotes3.ui.NotesAdapter;
import com.zamanak.plainolnotes3.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        startActivity(new Intent(this, EditorActivity.class));
    }

    private List<NoteEntity> notesData = new ArrayList<>();
    private NotesAdapter mAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView(); // when using Livedata and observer we must init first recycler and then viewmodel
        initViewModel(); // this method is for creating viewModel object

        //notesData.addAll(SampleData.getNotes()); // instead of getting data directly from SampleData we can use ViewModel
        /*notesData.addAll(mViewModel.mNotes);
        for (NoteEntity note : notesData) {
            Log.i("PlainOlNotes", note.toString());
        }*/ //*** we do not need this code when using observer
    }

    private void initViewModel() {

        // for getting data from database and show in the recycler view the activity must observe(Lifecycle) that data and react
        // *** this method call automatically when data is updated
        final Observer<List<NoteEntity>> notesObserver = new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                // in this method we receive the note data
                notesData.clear();
                notesData.addAll(noteEntities);
                if (mAdapter == null) {
                    // this lines of code is for the first time data displayed
                    mAdapter = new NotesAdapter(notesData, MainActivity.this);
                    recyclerView.setAdapter(mAdapter);
                } else {
                    // this line of code is for when data is updated
                    mAdapter.notifyDataSetChanged();
                }
            }
        };
/**
 * the most important we must do is to manage persistence data
 * instead of managing data directly in the activity all of the logic can go to the "View Model"
 */
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mViewModel.mNotes.observe(this,notesObserver); // now i subscribe to the data

    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true); // this line of code means each item of the recyclerview which would appear in the list would be exact same height
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // dividerItemDecoraqtion is part of support library -> add divider programmatically to recycler
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(),DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_sample_data) {
            addSampleData();
            return true;
        } else if (id == R.id.action_delete_all){
            deleteAllNotes();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        mViewModel.deleteAllNotes();
    }

    private void addSampleData() {
        mViewModel.addSampleData();
    }


}
