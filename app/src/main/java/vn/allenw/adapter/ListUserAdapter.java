package vn.allenw.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.allenw.R;
import vn.allenw.model.User;

/**
 * Created by Allen on 01-Apr-16.
 */
public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.UserViewHolder> {
    private List<User> userList;


    public ListUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_view, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        if (userList == null) return;
        holder.display(userList.get(position));
    }

    @Override
    public int getItemCount() {
        if (userList == null) return 0;
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView name, pj, role;
        ImageView iv;

        public UserViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            pj = (TextView) itemView.findViewById(R.id.pj);
            role = (TextView) itemView.findViewById(R.id.role);
            iv = (ImageView) itemView.findViewById(R.id.avatar);
        }

        public void display(User user) {
            if (user == null) return;
            name.setText(user.getName());
            iv.setImageResource(user.getAvatarResource());
            User.ProjectsEntity project = user.getProjects();
            if (project != null) {
                pj.setText(project.getName());
                role.setText(project.getRole());
            } else {
                pj.setText("");
                role.setText("");
            }
        }

    }
}
