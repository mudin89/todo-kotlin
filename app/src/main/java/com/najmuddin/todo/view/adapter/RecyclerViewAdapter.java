package com.najmuddin.todo.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ads.uts.utils.NavigationManager;
import com.ads.uts.utils.PaperDbManager;
import com.najmuddin.todo.R;
import com.najmuddin.todo.model.Todo;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity context;
    ArrayList<Todo> userArrayList;

    public RecyclerViewAdapter(Activity context, ArrayList<Todo> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.todo_item,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Todo todo = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.tvTitle.setText(todo.getTitle());
        viewHolder.tvStartDate.setText(todo.getStartDateAsString());
        viewHolder.tvEndDate.setText(todo.getEndDateAsString());
        viewHolder.tvCountDown.setText(todo.getCountDown());

        if(todo.isComplete()){
            viewHolder.tvStatus.setText("Complete");
            viewHolder.cbComplete.setChecked(true);
        } else {
            viewHolder.tvStatus.setText("Incomplete");
            viewHolder.cbComplete.setChecked(false);
        }

        viewHolder.cbComplete.setText(todo.getTitle());

        viewHolder.cbComplete.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    viewHolder.tvStatus.setText("Complete");
                    viewHolder.cbComplete.setChecked(true);
                } else {
                    viewHolder.tvStatus.setText("Incomplete");
                    viewHolder.cbComplete.setChecked(false);
                }

                todo.setComplete(b);
                userArrayList.set(position,todo);
                notifyDataSetChanged();
                // save the changes into database
                PaperDbManager.TODO.INSTANCE.saveTodoList(userArrayList);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvStartDate;
        TextView tvEndDate;
        TextView tvCountDown;
        TextView tvStatus;
        CheckBox cbComplete;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStartDate = itemView.findViewById(R.id.tvStartDate);
            tvEndDate = itemView.findViewById(R.id.tvEndDate);
            tvCountDown = itemView.findViewById(R.id.tvCountDown);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            cbComplete = itemView.findViewById(R.id.cbComplete);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            new NavigationManager().toFormActivity(view.getContext(),String.valueOf(getAdapterPosition()));
        }
    }
}
