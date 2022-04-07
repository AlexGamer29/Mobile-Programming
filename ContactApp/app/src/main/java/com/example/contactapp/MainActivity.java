package com.example.contactapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SortedList;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.contactapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private List<Contact> contactList;
    private ContactAdapter contactAdapter;

    private AppDatabase appDatabase;
    private ContactDao contactDao;

    private ContactViewModel contactViewModel;


    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        contactList = new ArrayList<>();

        appDatabase = AppDatabase.getInstance(this);
        contactDao = appDatabase.contactsDao();


//        this.deleteDatabase("Contact");

        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));
        contactAdapter = new ContactAdapter(contactList);
        binding.rvContacts.setAdapter(contactAdapter);


        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getListContacts().observe(this, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {
                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                contactList.addAll(contacts);
//                contactAdapter = new ContactAdapter(contacts);
                contactAdapter.setContacts(contacts);
                binding.rvContacts.setAdapter(contactAdapter);
            }
        });


//        contactList.add(new Contact("Nguyen Van A", "0935024962", "a@gmail.com"));
//        contactList.add(new Contact("Nguyen Thi B", "0905123456", "b@gmail.com"));
//        contactList.add(new Contact("Do Thi Hoa", "0956302593", "429@gmail.com"));
//        contactList.add(new Contact("Do Nguyen Anh Thi", "0702165742", "e@gmail.com"));
//        contactList.add(new Contact("Ho Van Giang", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Nguyen Thi Tuyet", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Tram Thi Anh", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Nguyen Anh Giang", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Vo Minh Nhut", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Maria Ozawa", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Tran Thi Hong Van", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Le Thi Anh", "0702105158", "e@gmail.com"));
//        contactList.add(new Contact("Nguyen Duong Hong Ngoc", "0935060925", "e@gmail.com"));
//        contactList.add(new Contact("Nguyen Huu Minh Duc", "0898547586", "e@gmail.com"));
//        contactList.add(new Contact("Le Thi Anh", "0702105158", "e@gmail.com"));


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact();
            }
        });
    }

    public void addContact() {
        Intent addContact = new Intent(MainActivity.this, NewContactActivity.class);
        newContactActivityLauncher.launch(addContact);
    }

    ActivityResultLauncher<Intent> newContactActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String name = data.getStringExtra("name");
                            String email = data.getStringExtra("email");
                            String mobile = data.getStringExtra("mobile");
                            contactViewModel.insert(new Contact(name, mobile, email));
//                            contactAdapter.notifyDataSetChanged();
                        }
                        Toast.makeText(MainActivity.this, "Added Contact", Toast.LENGTH_SHORT).show();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(MainActivity.this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // in this on create options menu we are calling
        // a menu inflater and inflating our menu file.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        // on below line we are getting our menu item as search view item
        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        // on below line we are creating a variable for our search view.
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        // on below line we are setting on query text listener for our search view.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // on query submit we are clearing the focus for our search view.
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // on changing the text in our search view we are calling
                // a filter method to filter our array list.
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private final SortedList.Callback<Contact> contactCallback = new SortedList.Callback<Contact>() {
        @Override
        public int compare(Contact o1, Contact o2) {
            return 0;
        }

        @Override
        public void onChanged(int position, int count) {

        }

        @Override
        public boolean areContentsTheSame(Contact oldItem, Contact newItem) {
            return false;
        }

        @Override
        public boolean areItemsTheSame(Contact item1, Contact item2) {
            return false;
        }

        @Override
        public void onInserted(int position, int count) {

        }

        @Override
        public void onRemoved(int position, int count) {

        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {

        }
    };
}