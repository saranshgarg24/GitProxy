package com.saransh.app.gitproxy;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context r_context;


    public RecyclerAdapter(Context context){
        r_context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        //Set Images And Position Here

    }//onBindViewHolder()

    @Override
    public int getItemCount() {
        return 10;
    }



    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        int HolderId = 1;

        public ViewHolder(View itemView, int item_type, final Context context) {
            super(itemView);


                HolderId = 1;
            }//if
        }//Constructor






}//MyRecyclerAdaterClass

