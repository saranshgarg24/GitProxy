package com.saransh.app.gitproxy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context r_context;
    int count;
    List<String> title = new ArrayList<>();
    List<String> lang = new ArrayList<>();
    List<String> owners = new ArrayList<>();

    public RecyclerAdapter(Context context, JSONArray repos){
        r_context = context;
        count = repos.length();
        Log.d("count", String.valueOf(count));
        for (int i = 0; i<count;i++)
        {
            try {
                JSONObject obj = repos.getJSONObject(i);
               // Log.d("OBJ", String.valueOf(obj));
                title.add(obj.getString("name"));
                lang.add(obj.getString("language"));
                owners.add(obj.getJSONObject("owner").getString("login"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.title.setText(title.get(position));
        holder.languages.setText(lang.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(r_context,Commits.class);
                i.putExtra("repo",title.get(position));
                i.putExtra("owner",owners.get(position));
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                r_context.startActivity(i);
            }
        });
        //Set Images And Position Here

    }//onBindViewHolder()

    @Override
    public int getItemCount() {
        return count;
    }



    //ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder{

        int HolderId = 1;
        TextView title;
        TextView languages;
        LinearLayout layout;

        public ViewHolder(View itemView, int item_type, final Context context) {
            super(itemView);

            layout = (LinearLayout) itemView.findViewById(R.id.layout);
            title = (TextView) itemView.findViewById(R.id.title);
            languages = (TextView) itemView.findViewById(R.id.lang);


                HolderId = 1;
            }//if


    }//Constructor






}//MyRecyclerAdaterClass

