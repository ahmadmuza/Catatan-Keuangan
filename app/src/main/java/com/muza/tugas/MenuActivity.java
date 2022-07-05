package com.muza.tugas;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muza.tugas.databinding.ActivityMenuBinding;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private EditText mEditNama;
    private EditText mEditHarga;
    private Spinner typeElement;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private TransaksiAdapter mAdapter;
    private ArrayList<Transaksi> mTransaksi;
    private FirebaseDatabase mFirebaseInstance;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahTransaksi();
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mAdapter = new TransaksiAdapter(mTransaksi, this);

        // Menghubungkan recycler view dengan adapter
        RecyclerView rv = (RecyclerView) findViewById(R.id.list_transaksi);
        rv.setAdapter(mAdapter);
        // Set layout manager to position the items
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Start ----- Baca daftar pesan
        DatabaseReference transaksiRef = database.getReference("/transaksi");
        transaksiRef.addChildEventListener(new ChildEventListener() {


            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Transaksi newTransaksi = snapshot.getValue(Transaksi.class);
                mAdapter.addItem(newTransaksi);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_logout, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void dialogTambahTransaksi() {
        final Spinner List = findViewById(R.id.type_transaksi);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Transaksi");
        View view = getLayoutInflater().inflate(R.layout.fragment_slideshow, null);

        mEditNama = view.findViewById(R.id.ket);
        typeElement = view.findViewById(R.id.type_transaksi);
        mEditHarga = view.findViewById(R.id.jumlah);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String keterangan = mEditNama.getText().toString();
                long createdAt = System.currentTimeMillis();
                String tipe_transaksi= typeElement.getSelectedItem().toString();
                String jumlah= mEditHarga.getText().toString();


                if (!keterangan.isEmpty() && !tipe_transaksi.isEmpty()&& !jumlah.isEmpty()  ) {
                    submitDataTransaksi(new Transaksi(keterangan,tipe_transaksi,jumlah.indexOf(jumlah)));
                } else {
                    Toast.makeText(MenuActivity.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
    private void submitDataTransaksi(Transaksi transaksi) {
        mDatabaseReference.child("data_transaksi").push()
                .setValue(transaksi).addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void mVoid) {
                        Toast.makeText(MenuActivity.this, "Data transaksi berhasil di simpan !", Toast.LENGTH_LONG).show();
                    }
                });
    }
}