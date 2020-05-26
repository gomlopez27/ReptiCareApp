package com.example.repticare;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphsTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphs_test);

        GraphView graph = (GraphView) findViewById(R.id.temp_graph_test);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 100),
                new DataPoint(1, 500),
                new DataPoint(2, 300),
                new DataPoint(3, 200),
                new DataPoint(4, 600)
        });
        graph.addSeries(series);
    }


}
