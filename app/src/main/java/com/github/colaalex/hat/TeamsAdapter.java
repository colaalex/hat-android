package com.github.colaalex.hat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolder> {

    ArrayList<Team> teams;

    public TeamsAdapter(ArrayList<Team> teams) {
        this.teams = teams;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item_view, parent, false);
        return new ViewHolder(itemView, new EditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.editTextListener.setPosition(holder.getAdapterPosition());
        if (teams.get(position).getName() != null) {
            holder.editTeam.setText(teams.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return teams.size();
    }

    private void deleteItem(int position) {
        teams.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, teams.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EditText editTeam;
        ImageButton delButton;

        EditTextListener editTextListener;

        public ViewHolder(View itemView, EditTextListener editTextListener) {
            super(itemView);

            editTeam = itemView.findViewById(R.id.et_team_name);
            this.editTextListener = editTextListener;
            editTeam.addTextChangedListener(editTextListener);

            delButton = itemView.findViewById(R.id.btn_del);
            delButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(getAdapterPosition());
                }
            });
        }
    }

    private class EditTextListener implements TextWatcher {

        private int position;

        public void setPosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            teams.get(position).setName(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }
}
