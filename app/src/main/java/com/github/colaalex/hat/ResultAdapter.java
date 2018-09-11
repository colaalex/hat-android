package com.github.colaalex.hat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ViewHolder> {

    ArrayList<Team> teams;

    public ResultAdapter(ArrayList<Team> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textTeam.setText(teams.get(position).getName());
        holder.textScore.setText(String.valueOf(teams.get(position).getScore()));
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTeam;
        TextView textScore;

        public ViewHolder(View itemView) {
            super(itemView);
            textTeam = itemView.findViewById(R.id.txt_team_name);
            textScore = itemView.findViewById(R.id.txt_team_score);
        }
    }
}
