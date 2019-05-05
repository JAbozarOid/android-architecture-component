package com.zamanak.plainolnotes3.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.zamanak.plainolnotes3.database.AppRepository;
import com.zamanak.plainolnotes3.database.NoteEntity;
import java.util.List;

//***** viewModel class store persistence data in memory and manage business logic
// one of the greatest advantages of viewModel class is it survive changes and configuration such as device changes from portrait to landscape orientation and back again
// all the logic goes to viewModel class
// for each activity and fragment there is one viewModel class *****

/**
 * View Model Advantages :
 * Survive configuration changes
 * Efficient in terms of memory
 * Sharable between components
 *
 * LiveData Advantages
 * It's a lifecycle ware component
 * Decouples data access logic from the one who consumes it
 * Bound to a life cycle owner
 */

public class MainViewModel extends AndroidViewModel {

    /**
     * A live data object can publish changes and then an activity or a fragment can subscribe to observe those changes and react whenever the data
     * needs to be updated visually.
     */
    public LiveData<List<NoteEntity>> mNotes; // this instantiated in the repository class so we must have a reference to Repository class
    private AppRepository mRepository;

    /**
     * both of two above variable must initialize in the MainViewModel method
     * @param application
     */
    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext()); // here we pass context to a repository and repository use the context to initialize the database
        mNotes = mRepository.mNotes;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllNotes() {
        mRepository.deleteAllNotes();
    }
}
