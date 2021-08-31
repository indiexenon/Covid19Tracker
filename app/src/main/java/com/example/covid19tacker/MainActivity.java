package com.example.covid19tacker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import static androidx.core.content.ContextCompat.startActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvcases,tvrecovered,tvactive,tvdeath,tvcritical,tvtodayscase,tvtodaysdeath,tvaffectedcountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    FloatingActionButton mIndiadetails;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvcases= findViewById(R.id.tvcases);
        tvrecovered=findViewById(R.id.tvrecovered);
        tvactive=findViewById(R.id.tvactive);
        tvdeath=findViewById(R.id.tvdeaths);
        tvcritical=findViewById(R.id.tvcritical);
        tvtodayscase=findViewById(R.id.tvtodaycase);
        tvtodaysdeath=findViewById(R.id.tvtodaydeaths);
        tvaffectedcountries=findViewById(R.id.tvaffectedcountries);
        mIndiadetails=findViewById(R.id.gotoIndiadetails);
        simpleArcLoader=findViewById(R.id.loader);
        scrollView=findViewById(R.id.scrollstats);
        pieChart=findViewById(R.id.piechart);

        fetchData();

        mIndiadetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.covid19india.org/");
            }
        });

    }

    private void gotoUrl(String s) {


        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }

    private void fetchData() {

        String url ="https://corona.lmao.ninja/v2/all/";

        simpleArcLoader.start();

        StringRequest request= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject(response.toString());
                            tvcases.setText(jsonObject.getString("cases"));
                            tvdeath.setText(jsonObject.getString("deaths"));
                            tvrecovered.setText(jsonObject.getString("recovered"));
                            tvcritical.setText(jsonObject.getString("critical"));
                            tvactive.setText(jsonObject.getString("active"));
                            tvtodayscase.setText(jsonObject.getString("todayCases"));
                            tvtodaysdeath.setText(jsonObject.getString("todayDeaths"));
                            tvaffectedcountries.setText(jsonObject.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(tvcases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(tvrecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvdeath.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvactive.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);




                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();


            }
        });

        RequestQueue requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void goTrackcountry(View view){

        startActivity(new Intent(MainActivity.this,AffectedCountries.class));

    }

}