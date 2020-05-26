package com.example.repticare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

public class GraphTestActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_test);

        GraphView graph = findViewById(R.id.graph);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        DataPoint[] dpa= new DataPoint[24];

         /*int counterHour = 23;

        for(int i = hour; i >= 0; i--){
            dpa[counterHour--] = new DataPoint(i, 20);
        }

        int otherHourCounter = 23;
        for(int j = 23 - hour; j > 0; j--){
            dpa[counterHour--] = new DataPoint(otherHourCounter--, 20);

        }*/

         for(int i = 0; i < 24; i++){
             dpa[i] = new DataPoint(i, i+10);
         }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dpa);
        graph.addSeries(series);
    }
}
