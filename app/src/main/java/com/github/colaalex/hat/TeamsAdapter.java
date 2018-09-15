package com.github.colaalex.hat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;

public class TeamsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_FOOTER = 1;

    ArrayList<Team> teams;
    Context context;

    public TeamsAdapter(ArrayList<Team> teams, Context context) {
        this.teams = teams;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == TYPE_FOOTER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_footer_view, parent, false);
            return new ViewHolderFooter(v);
        }

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_item_view, parent, false);
        return new ViewHolder(itemView, new EditTextListener());
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        try {
            if (holder instanceof ViewHolder) {
                ViewHolder vh = (ViewHolder) holder;
                vh.editTextListener.setPosition(vh.getAdapterPosition());
                if (teams.get(position).getName() != null)
                    vh.editTeam.setText(teams.get(position).getName());
                else
                    vh.editTeam.setText(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        holder.editTextListener.setPosition(holder.getAdapterPosition());
//        if (teams.get(position).getName() != null) {
//            holder.editTeam.setText(teams.get(position).getName());
//        }
    }

    @Override
    public int getItemCount() {
        if (teams.size() == 0)
            return 1;
        return teams.size() + 1;
        //нужно на 1 больше, чтобы отобразить футер
        //return teams.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == teams.size())
            return TYPE_FOOTER;

        return super.getItemViewType(position);
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

    public class ViewHolderFooter extends RecyclerView.ViewHolder {

        Button addButton;
        Button goButton;

        public ViewHolderFooter(View itemView) {
            super(itemView);

            addButton = itemView.findViewById(R.id.btn_add);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    teams.add(new Team(null));
                    notifyDataSetChanged();
                    notifyItemRangeChanged(getAdapterPosition(), teams.size());
                }
            });

            goButton = itemView.findViewById(R.id.btn_start);
            goButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("teams", teams);
                    context.startActivity(intent);
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
