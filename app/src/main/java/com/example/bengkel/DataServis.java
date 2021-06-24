package com.example.bengkel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class DataServis extends AppCompatActivity {

    private EditText tgl, plat, kendaraan, pemilik, keluhan;
    private Button tambahBtn, lihatBtn;
    private FirebaseFirestore db;
    private String uId, uTanggal, uPlatNomor, uMerkJenisKendaraan, uNamaPemilik, uKeluhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_servis);

        tgl = findViewById(R.id.et_tgl);
        plat = findViewById(R.id.et_plat);
        kendaraan = findViewById(R.id.et_jkendaraan);
        pemilik = findViewById(R.id.et_pemilik);
        keluhan = findViewById(R.id.et_keluhan);
        tambahBtn = findViewById(R.id.btn_simpan);
        lihatBtn = findViewById(R.id.btn_show);

        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            tambahBtn.setText("Update");
            uTanggal = bundle.getString("uTanggal");
            uId = bundle.getString("uId");
            uPlatNomor = bundle.getString("uPlatNomor");
            uMerkJenisKendaraan = bundle.getString("uMerkJenisKendaraan");
            uNamaPemilik = bundle.getString("uNamaPemilik");
            uKeluhan = bundle.getString("uKeluhan");
            tgl.setText(uTanggal);
            plat.setText(uPlatNomor);
            kendaraan.setText(uMerkJenisKendaraan);
            pemilik.setText(uNamaPemilik);
            keluhan.setText(uKeluhan);
        }else{
            tambahBtn.setText("Save");
        }

        lihatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DataServis.this, ShowActivity.class));
            }
        });

        tambahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tanggal = tgl.getText().toString();
                String no_plat = plat.getText().toString();
                String mj = kendaraan.getText().toString();
                String nama_pemilik = pemilik.getText().toString();
                String komplain = keluhan.getText().toString();

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String id = uId;
                    updateToFireStore(id, tanggal, no_plat, mj, nama_pemilik, komplain);
                }else{
                    String id = UUID.randomUUID().toString();
                    saveToFireStore(id, tanggal, no_plat, mj, nama_pemilik, komplain);
                }
            }
        });
    }

    private void updateToFireStore(String id, String tanggal, String no_plat, String mj, String nama_pemilik, String komplain){

        db.collection("DataServis").document(id).update("Tanggal", tanggal,
                "PlatNomor", no_plat, "MerkJenisKendaraan", mj, "NamaPemilik", nama_pemilik,
                "Keluhan", komplain).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DataServis.this, "Data Diubah", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DataServis.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(DataServis.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveToFireStore(String id, String tanggal, String no_plat, String mj, String nama_pemilik, String komplain){
        if (!tanggal.isEmpty() && !no_plat.isEmpty() && !mj.isEmpty() && !nama_pemilik.isEmpty() && !komplain.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("Tanggal", tanggal);
            map.put("PlatNomor", no_plat);
            map.put("MerkJenisKendaraan", mj);
            map.put("NamaPemilik", nama_pemilik);
            map.put("Keluhan", komplain);

            db.collection("DataServis").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(DataServis.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(DataServis.this, "Data Tidak Tersimpan", Toast.LENGTH_SHORT).show();
                }
            });
        }else
            Toast.makeText(this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
    }
}