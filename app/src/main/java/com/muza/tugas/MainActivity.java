package com.muza.tugas;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.EventListener;

public class MainActivity extends AppCompatActivity {
    private ExtendedFloatingActionButton mFloatingActionButton;
    private EditText ket;
    private EditText jum;
    private EditText tipe;
    private RecyclerView mRecyclerView;
    private MainAdapter mAdapter;
    private ArrayList<ModelTransaksi> daftarTransaksi;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        mAdapter = new MainAdapter(daftarTransaksi, this);
        RecyclerView rv = (RecyclerView) findViewById(R.id.list_transaksi);
        rv.setAdapter(mAdapter);
        // Set layout manager to position the items
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Start ----- Baca daftar pesan
        DatabaseReference messagesRef = database.getReference("/transaksi");
        messagesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Menambahkan data pesan baru ke adapter pada saat pesan baru ditambahkan ke database
                ModelTransaksi newMessage = snapshot.getValue(ModelTransaksi.class);
                mAdapter.addItem(newMessage);
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
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(MainActivity.this,
                        databaseError.getDetails() + " " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        mFloatingActionButton = findViewById(R.id.tambah_transaksi);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogTambahBarang();
            }
        });

    }

        private void dialogTambahBarang() {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Tambah Data ModelBarang");
            View view = getLayoutInflater().inflate(R.layout.edit_transaksi, null);

            ket = view.findViewById(R.id.ket);
            jum = view.findViewById(R.id.jumlah);
            tipe = view.findViewById(R.id.tipe);
            builder.setView(view);

            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    mAuth = FirebaseAuth.getInstance();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    String namaBarang = ket.getText().toString();
                    String merkBarang = jum.getText().toString();
                    String hargaBarang = tipe.getText().toString();

                    if (namaBarang.isEmpty() && merkBarang.isEmpty() && hargaBarang.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                    } else {
                        DatabaseReference newMessageRef = database.getReference("/transaksi");
                        ModelTransaksi newTransaksi = new ModelTransaksi(ket.getText().toString(), mAuth.getUid(), System.currentTimeMillis(), jum.getText().toString(), tipe.getText().toString());
                        newMessageRef.push().setValue(newTransaksi);

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
        }}

