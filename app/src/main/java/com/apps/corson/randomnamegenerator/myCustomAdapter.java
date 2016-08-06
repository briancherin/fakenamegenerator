package com.apps.corson.randomnamegenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Brian on 6/29/2016.
 */
public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<>();
    private Context context;

    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem (int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
    //    return list.get(pos).getId();
        //just return 0 if your list items do not have an Id variable.
        return 0;
    }

    ClipboardManager clipboard;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)  {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  inflater.inflate(R.layout.delete_layout, null);
            clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        }
        TextView listItemText = (TextView) view.findViewById(R.id.listItemString);
        listItemText.setText(list.get(position));

        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        Button copyButton = (Button)view.findViewById(R.id.copyButton);



        final MainActivity activity = new MainActivity();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                //delete name from file
                try {
                    activity.rewriteNameFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clip = ClipData.newPlainText("name", list.get(position));
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Name copied.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
