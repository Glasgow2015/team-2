package uk.org.urbanroots.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import uk.org.urbanroots.models.Task;
import uk.org.urbanroots.urbanroots.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskDataHolder> {

    private List<Task> mTasks;

    public TaskAdapter(List<Task> t) {
       mTasks = t;
    }

    public class TaskDataHolder extends RecyclerView.ViewHolder {
        CardView mCardView;
        TextView mTitle;
        TextView mDescription;
        TextView mLocation;

        public TaskDataHolder(View itemView) {
            super(itemView);
            mCardView = (CardView) itemView.findViewById(R.id.cv_task);
            mTitle = (TextView) itemView.findViewById(R.id.tv_task_title);
            mDescription = (TextView) itemView.findViewById(R.id.tv_task_description);
            mLocation = (TextView) itemView.findViewById(R.id.tv_task_location);
        }
    }

    @Override
    public TaskDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_card_view, parent, false);
        TaskDataHolder t = new TaskDataHolder(v);
        return t;
    }

    @Override
    public void onBindViewHolder(TaskDataHolder holder, int position) {
        holder.mTitle.setText(mTasks.get(position).getTitle());
        holder.mDescription.setText(mTasks.get(position).getDescription());
        holder.mLocation.setText(mTasks.get(position).getLocation());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return mTasks.size();

    }
}
