package edu.sfsu.cs.orange.ocr;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.id.list;

public class directory extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        list= (ListView)findViewById(R.id.list);
        list.setAdapter(new VivzAdapter(this));
    }
    public class SingleRow{
        String title;
        String location;
        String contact;
        SingleRow(String title, String location, String contact){
            this.title= title;
            this.location= location;
            this.contact= contact;

        }
    }
    class VivzAdapter extends BaseAdapter {
        ArrayList<SingleRow> list;
        Context context;
        VivzAdapter(Context c){
            context = c;
            list = new ArrayList<SingleRow>();
            Resources res = c.getResources();
            String[] title = res.getStringArray(R.array.title);
            String[] location = res.getStringArray(R.array.Location);
            String[] contact= res.getStringArray(R.array.Contact);
            for(int i=0;i<26;i++){
                list.add(new SingleRow(title[i], location[i], contact[i]));
            }
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.list_row, parent, false);
            // SingleRow singleRow = new SingleRow(title[i],);
            TextView title = (TextView) row.findViewById(R.id.title);
            TextView location = (TextView) row.findViewById(R.id.location);
            Button contact = (Button) row.findViewById(R.id.contact);
            final SingleRow temp = list.get(i);
            title.setText(temp.title);
            location.setText(temp.location);
            contact.setText(temp.contact);
            contact.setClickable(true);
            contact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i;
                    i = new Intent(Intent.ACTION_DIAL);
                    i.setData(Uri.parse("tel:"+temp.contact));
                    startActivity(i);
                }
            });


            return  row;
        }

    }
}
