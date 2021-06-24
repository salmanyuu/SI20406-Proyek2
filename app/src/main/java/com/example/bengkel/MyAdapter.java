package com.example.bengkel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ShowActivity activity;
    private List<Model> modelList;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MyAdapter(ShowActivity activity, List<Model> modelList){
        this.activity = activity;
        this.modelList = modelList;
    }

    public void updateData(int position){
        Model item = modelList.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("uId", item.getId());
        bundle.putString("uTanggal", item.getTanggal());
        bundle.putString("uPlatNomor", item.getPlat());
        bundle.putString("uMerkJenisKendaraan", item.getMj());
        bundle.putString("uNamaPemilik", item.getPemilik());
        bundle.putString("uKeluhan", item.getKeluhan());
        Intent intent = new Intent(activity, DataServis.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    public void deleteData(int position){
        Model item = modelList.get(position);
        db.collection("DataServis").document(item.getId()).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            notifyRemoved(position);
                            Toast.makeText(activity, "Data Telah Dihapus", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(activity, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void notifyRemoved(int position){
        modelList.remove(position);
        notifyItemRemoved(position);
        activity.showData();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tanggal.setText(modelList.get(position).getTanggal());
        holder.plat.setText(modelList.get(position).getPlat());
        holder.mj.setText(modelList.get(position).getMj());
        holder.pemilik.setText(modelList.get(position).getPemilik());
        holder.keluhan.setText(modelList.get(position).getKeluhan());

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView tanggal, plat, mj, pemilik, keluhan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggal);
            plat = itemView.findViewById(R.id.plat_text);
            mj = itemView.findViewById(R.id.jkendaraan_text);
            pemilik = itemView.findViewById(R.id.pemilik_text);
            keluhan = itemView.findViewById(R.id.keluhan_text);
        }
    }
}
