package com.example.kickmyb;

import android.content.Intent;
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

import org.kickmyb.transfer.HomeItemResponse;

import java.io.InterruptedIOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ListeAdapter extends RecyclerView.Adapter<ListeAdapter.MyViewHolder> {
    public List<HomeItemResponse> list;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeItemResponse tache = list.get(position);

        holder.textView.setText(tache.name);
        holder.progressBar.setProgress(tache.percentageDone);
        holder.textViewPourc.setText(tache.percentageDone + "%");

        //DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        DateFormat df = DateFormat.getDateInstance();

        //holder.textViewDateD.setText(tache.dateDebut.format(f));
        //holder.textViewDateD.setText("3/7jours passé");
        holder.textViewDateF.setText(df.format(tache.deadline) + "date limite");

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConsultActivity.class);
                //À mettre l'ID de l'obj apr pour simplifier
                //intent.putExtra("tQ", tache.sQueTuDoisFaire);
                //intent.putExtra("tDD", tache.dateDebut);
                //intent.putExtra("TDF", tache.dateFinal);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
