package com.cheekupeeku.testapicall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cheekupeeku.testapicall.databinding.PostItemListBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    Context context;
    ArrayList<Post>al;
    public PostAdapter(Context context,ArrayList<Post>al){
        this.context = context;
        this.al = al;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PostItemListBinding binding = PostItemListBinding.inflate(LayoutInflater.from(context),parent,false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
       Post p = al.get(position);
       holder.binding.tvPostId.setText(""+p.getId());
       holder.binding.tvPostTitle.setText(p.getTitle());
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
      PostItemListBinding binding;
      public PostViewHolder(PostItemListBinding binding){
          super(binding.getRoot());
          this.binding = binding;
      }
  }
}
