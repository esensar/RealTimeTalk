package com.smarthomies.realtimetalk.views.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.smarthomies.realtimetalk.R;
import com.smarthomies.realtimetalk.databinding.SearchUserListItemBinding;
import com.smarthomies.realtimetalk.databinding.UserListItemBinding;
import com.smarthomies.realtimetalk.models.db.User;
import com.smarthomies.realtimetalk.viewmodels.UserViewModel;

import java.util.List;

/**
 * Created by ensar on 26/11/16.
 */
public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    public static final String TAG = UsersAdapter.class.getSimpleName();

    public enum ViewType {
        LIST,
        SEARCH
    }

    private List<User> users;
    private ViewType viewType;

    public UsersAdapter(ViewType viewType) {
        this.viewType = viewType;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (this.viewType) {
            case LIST:
                UserListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.user_list_item, parent, false);
                return new UserViewHolder(binding);
            case SEARCH:
            default:
                SearchUserListItemBinding binding2 = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.search_user_list_item, parent, false);
                return new UserViewHolder(binding2);
        }
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        switch (this.viewType) {
            case LIST:
                UserListItemBinding binding = holder.getBinding();
                binding.setViewModel(new UserViewModel(users.get(position)));
                break;
            case SEARCH:
                SearchUserListItemBinding binding2 = holder.getSearchUserListItemBinding();
                binding2.setViewModel(new UserViewModel(users.get(position)));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private UserListItemBinding binding;
        private SearchUserListItemBinding searchUserListItemBinding;

        public UserViewHolder(UserListItemBinding userListItemBinding) {
            super(userListItemBinding.getRoot());
            this.binding = userListItemBinding;
        }

        public UserViewHolder(SearchUserListItemBinding userListItemBinding) {
            super(userListItemBinding.getRoot());
            this.searchUserListItemBinding = userListItemBinding;
        }

        public UserListItemBinding getBinding() {
            return binding;
        }

        public SearchUserListItemBinding getSearchUserListItemBinding() {
            return searchUserListItemBinding;
        }
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
