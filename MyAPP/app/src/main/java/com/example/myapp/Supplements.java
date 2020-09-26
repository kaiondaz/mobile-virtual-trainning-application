package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.TwitterAuthCredential;

public class Supplements extends AppCompatActivity {

    ListView listView;
    String mtitle []={"BCA","Creatine performance","Creatine ","Aswagandha","Omega set fish oil","Protein whey set","Creatine monohydrate"};
    String mDescription [] = {"BCA Description","Creatine performance description"," Creatine description",
                            "Aswagandha description","Omega set fish oil description","Protein whey set description","Creatine monohydrate  description"
                              };
    int images []={R.drawable.store_bca_supp,R.drawable.store_creatine_performance_supp,R.drawable.store_creatine_supp,
                   R.drawable.store_aswagandha,R.drawable.store_omegaset_supp,R.drawable.store_whey_set,R.drawable.store_creatinemono_supp};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplements);

        //Action bar and its title
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Store");

        //enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.listview);
        //now create an adapter class
        MyAdapter adapter = new MyAdapter(this, mtitle, mDescription, images);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Toast.makeText(Supplements.this,"Facebook Description",Toast.LENGTH_SHORT).show();
                }
                if (position==0){
                    Toast.makeText(Supplements.this,"Whatsapp Description",Toast.LENGTH_SHORT).show();
                }
                if (position==0){
                    Toast.makeText(Supplements.this,"Twitter Description",Toast.LENGTH_SHORT).show();
                }
                if (position==0){
                    Toast.makeText(Supplements.this,"Intagram Description",Toast.LENGTH_SHORT).show();
                }
                if (position==0){
                    Toast.makeText(Supplements.this,"Youtube Description",Toast.LENGTH_SHORT).show();
                }
                if (position==0){
                    Toast.makeText(Supplements.this,"lol1",Toast.LENGTH_SHORT).show();

                }
                if (position==0){
                    Toast.makeText(Supplements.this,"lol2",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        class MyAdapter extends ArrayAdapter<String> {
            Context context;
            String rTitle[];
            String rDescription[];
            int rImgs[];

            MyAdapter (Context c, String title[], String description[], int imgs[]) {
                super(c,R.layout.supplements_row,R.id.textView1,title);
                this.context=c;
                this.rTitle=title;
                this.rDescription=description;
                this.rImgs=imgs;
            }

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater layoutInflater =(LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View row=layoutInflater.inflate(R.layout.supplements_row,parent,false);
                ImageView images = row.findViewById(R.id.image);
                TextView myTitle = row.findViewById(R.id.textView1);
                TextView myDescription = row.findViewById(R.id.textView2);

                //now set image resources on views
                images.setImageResource(rImgs[position]);
                myTitle.setText(rTitle[position]);
                myDescription.setText(rDescription[position]);

                return row;

            }
        }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();// go previous activity
    }

}




