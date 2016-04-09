package test.testapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gaurav on 9/4/16.
 */
public class Custom_Adapter extends BaseAdapter {
    String[] keys = {"name", "commit", "message"};
    HashMap<String, String> hashMap;
    ArrayList<HashMap> arrayList;
    LayoutInflater layoutInflater;
    Context context;

    public Custom_Adapter(Context context, ArrayList<HashMap> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // instantiating views
        View rootView = layoutInflater.inflate(R.layout.list_item, null);
        TextView textView_name = (TextView) rootView.findViewById(R.id.name);
        TextView textView_commit_text = (TextView) rootView.findViewById(R.id.commit_text);
        TextView textView_commit = (TextView) rootView.findViewById(R.id.commit);
        TextView textView_message_text = (TextView) rootView.findViewById(R.id.message_text);
        TextView textView_message = (TextView) rootView.findViewById(R.id.message);

        hashMap = arrayList.get(position);

        // displaying data
        textView_name.setText(hashMap.get(keys[0]));
        textView_commit.setText(hashMap.get(keys[1]));
        textView_message.setText(hashMap.get(keys[2]));

        return rootView;
    }
}
