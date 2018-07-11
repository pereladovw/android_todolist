package com.example.reelmayer.todo_list;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;

import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.TreeSet;


public class CustomAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;
    public int myCliquedPosition = 0;

    private Context context;

    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
    private final ArrayList<Todo> todos = new ArrayList<>();



    private LayoutInflater mInflater;

    public CustomAdapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }



    public void addItem(final Todo item) {
        todos.add(item);
        notifyDataSetChanged();
    }

    public void reset() {

        sectionHeader.clear();
        todos.clear();
    }



    public void addSectionHeaderItem(final Todo projectTitle) {
        todos.add(projectTitle);
        sectionHeader.add(todos.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Todo getItem(int position) {

        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        int rowType = getItemViewType(position);
        View rowView = mInflater.inflate(R.layout.cell, parent, false);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.cell, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.txtName);
                    holder.checkBox = (AppCompatCheckBox) convertView.findViewById(R.id.checkBox);

                    break;
                case TYPE_HEADER:
                    convertView = mInflater.inflate(R.layout.header_item, null);
                    holder.textView = (TextView) convertView.findViewById(R.id.textSeparator);

                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();


        }

        if(rowType == TYPE_ITEM){
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    Todo totoInFocus = todos.get(position);
                    if (totoInFocus.isCompleted == isChecked) return;
                    todos.get(position).setCompleted(isChecked);
                    notifyDataSetChanged();
                    JsonObject param = new JsonObject();

                    param.addProperty("id", todos.get(position).id );
                    param.addProperty("isCompleted", todos.get(position).isCompleted);
                    Ion.with(context).load("https://murmuring-gorge-28716.herokuapp.com/projects/androidupdate")
                            .setJsonObjectBody(param)
                            .asJsonObject();
                }

            });

            holder.textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Regular.ttf"));
            if (todos.get(position).isCompleted()) {
                holder.textView.setText(todos.get(position).getText());
                holder.textView.setPaintFlags(holder.textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.checkBox.setChecked(true);

            } else {
                holder.textView.setText(todos.get(position).getText());
                holder.textView.setPaintFlags( holder.textView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                holder.checkBox.setChecked(false);
            }


        }else if(rowType == TYPE_HEADER){
            holder.textView.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/OpenSans-Semibold.ttf"));
            holder.textView.setText(todos.get(position).getText());
        }


        return convertView;
    }


    public int getPosition(int position) {
        return position - sectionHeader.headSet(position).size();
    }

    public static class ViewHolder {
        public TextView textView;
        public AppCompatCheckBox checkBox;

    }
}