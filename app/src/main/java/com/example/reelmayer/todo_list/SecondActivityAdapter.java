package com.example.reelmayer.todo_list;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

public class SecondActivityAdapter extends BaseAdapter{

    private ArrayList<String> projectsList = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;
    private int selectedPosition = 0;

    public SecondActivityAdapter(Context context, ArrayList<String> projectsList) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.projectsList = projectsList;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int i) {
        selectedPosition = i;
    }

    @Override
    public int getCount() {
        return projectsList.size();
    }

    @Override
    public Object getItem(int i) {
        return projectsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.projectlist_cell, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textName);
            holder.imageView = (ImageView) convertView.findViewById(R.id.done) ;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPosition = position;
                notifyDataSetChanged();
            }

        });
       holder.imageView.setVisibility(View.INVISIBLE);
        holder.textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf"));
        holder.textView.setText(projectsList.get(position));
        if (position == selectedPosition) holder.imageView.setVisibility(View.VISIBLE);
        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public ImageView imageView;
    }


}
