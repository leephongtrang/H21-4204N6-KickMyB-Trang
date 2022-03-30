package com.example.kickmyb;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.kickmyb.transfer.HomeItemResponse;

import java.text.DateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

        int pourIn = 100 - tache.percentageTimeSpent;

        int jourQuiReste = (int)( (tache.deadline.getTime() - Date.from(Instant.now()).getTime())
                / (1000 * 60 * 60 * 24) );

        int jourTotal = jourQuiReste * 100 / pourIn;
        int jourPasse = jourTotal - jourQuiReste;

        holder.textViewDateD.setText(jourPasse + "/ " + jourTotal + " jour passé");
        holder.textViewDateF.setText(df.format(tache.deadline) + "date limite");

        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ConsultActivity.class);
                intent.putExtra("id", tache.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
