package com.example.kickmyb;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.MyViewHolder> {
    public List<Tache> list;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public LinearLayout ll;
        public TextView textView;
        public TextView textViewDateD;
        public TextView textViewDateF;
        public TextView textViewPourc;
        public ProgressBar progressBar;
        
        public MyViewHolder(LinearLayout v) {
            super(v);
            this.ll = v;
            textView = v.findViewById(R.id.textView_lstItem);
            textViewDateD = v.findViewById(R.id.textView_lstItemDate);
            textViewDateF = v.findViewById(R.id.textView_lstItemDateLimite);
            progressBar = v.findViewById(R.id.progress_lstItemProgres);
            textViewPourc = v.findViewById(R.id.textView_pourcentage);
        }
    }

    public ListeAdapter() {list = new ArrayList<>();}

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_lst_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Tache tache = list.get(position);

        int pourcentage = 100;

        holder.textView.setText(tache.sQueTuDoisFaire);
        holder.textViewDateD.setText(tache.dateDebut.toString());
        holder.textViewDateF.setText(tache.dateDebut.toString());
        holder.progressBar.setProgress(pourcentage);
        holder.textViewPourc.setText(pourcentage + "%");

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("GNA", "clic sur ");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
