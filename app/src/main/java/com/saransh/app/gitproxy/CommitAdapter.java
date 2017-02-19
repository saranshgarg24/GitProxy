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

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.ViewHolder> {

    Context r_context;
    int count;
    List<String> title = new ArrayList<>();
    List<String> committer = new ArrayList<>();
    List<String> htmlurl = new ArrayList<>();

    public CommitAdapter(Context context, JSONArray repos){
        r_context = context;
        count = repos.length();
        Log.d("count", String.valueOf(count));
        for (int i = 0; i<count;i++)
        {
            try {
                JSONObject obj = repos.getJSONObject(i);
               // Log.d("OBJ", String.valueOf(obj));
                title.add(obj.getJSONObject("commit").getString("message"));
                committer.add(obj.getJSONObject("commit").getJSONObject("committer").getString("name"));
                htmlurl.add(obj.getString("html_url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_item, parent, false);
        ViewHolder vItem = new ViewHolder(v, viewType, parent.getContext());

        return vItem;
    }//onCreateViewHolder()

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.title.setText(title.get(position));
        holder.committer.setText(committer.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(r_context,PCommit.class);
                i.putExtra("url",htmlurl.get(position));
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
        TextView committer;
        LinearLayout layout;

        public ViewHolder(View itemView, int item_type, final Context context) {
            super(itemView);


            title = (TextView) itemView.findViewById(R.id.title);
            committer = (TextView) itemView.findViewById(R.id.committer);
            layout = (LinearLayout) itemView.findViewById(R.id.layout);

                HolderId = 1;
            }//if


    }//Constructor






}//MyRecyclerAdaterClass

