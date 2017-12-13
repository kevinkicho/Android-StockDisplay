package com.example.myjsonapp121217;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    List<Stock> stocks = new ArrayList<Stock>();

    private LineChart mLineChart;
    Button btn_open, btn_high, btn_close, btn_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineChart = findViewById(R.id.line_chart1);

        String sb = fetchJson();
        JsonParser jsonParser = new JsonParser();

        JsonObject o = jsonParser.parse(sb.toString()).getAsJsonObject();
        JsonObject o_1 = o.getAsJsonObject("Weekly Adjusted Time Series");

        ArrayList<String> keys = new ArrayList<String>();
        ArrayList<Float> opens = new ArrayList<Float>();
        ArrayList<Float> highs = new ArrayList<Float>();
        ArrayList<Float> lows = new ArrayList<Float>();
        ArrayList<Float> closes = new ArrayList<Float>();

        for (Map.Entry<String, JsonElement> entry : o_1.entrySet()){
            String key = entry.getKey();
            JsonElement v= entry.getValue();
            if (v.isJsonObject()){
                String open = v.getAsJsonObject().get("1. open").toString();
                String s_open = open.substring(1,open.length()-1);
                Float d_open = Float.parseFloat(s_open);

                String high = v.getAsJsonObject().get("2. high").toString();
                String s_high = open.substring(1,open.length()-1);
                Float d_high = Float.parseFloat(s_high);

                String low = v.getAsJsonObject().get("3. low").toString();
                String s_low = open.substring(1,open.length()-1);
                Float d_low = Float.parseFloat(s_low);

                String close = v.getAsJsonObject().get("4. close").toString();
                String s_close = open.substring(1,open.length()-1);
                Float d_close = Float.parseFloat(s_close);

                String adj_close = v.getAsJsonObject().get("5. adjusted close").toString();
                String vol = v.getAsJsonObject().get("6. volume").toString();
                String div_amt = v.getAsJsonObject().get("7. dividend amount").toString();
                Stock stock = new Stock(open,high,low,close,adj_close,vol,div_amt);
                stocks.add(stock);
                //yVals.add(Double.parseDouble(open));
                // Keys = Dates
                //keys.add(key);
                opens.add(d_open);
                highs.add(d_high);
                lows.add(d_low);
                closes.add(d_close);
                //LineDataSet set = new LineDataSet(opens, "Data Set 1");
            }
        }

        ArrayList<Entry> yVals_open = new ArrayList<>();
        for (int i = 0; i < opens.size() ; i++){
            yVals_open.add(new Entry(i, opens.get(i)));
        }
        LineDataSet set_open = new LineDataSet(yVals_open, "Openning");
        final LineData data_open = new LineData(set_open);

        ArrayList<Entry> yVals_high = new ArrayList<>();
        for (int i =0; i <highs.size(); i++){
            yVals_high.add(new Entry(i, highs.get(i)));
        }
        LineDataSet set_high = new LineDataSet(yVals_high, "Highest");
        final LineData data_high = new LineData(set_high);

        ArrayList<Entry> yVals_low = new ArrayList<>();
        for (int i =0; i <lows.size(); i++){
            yVals_low.add(new Entry(i, lows.get(i)));
        }
        LineDataSet set_low = new LineDataSet(yVals_low, "Lowest");
        final LineData data_low = new LineData(set_low);

        ArrayList<Entry> yVals_close = new ArrayList<>();
        for (int i =0; i <closes.size(); i++){
            yVals_close.add(new Entry(i, lows.get(i)));
        }
        LineDataSet set_close = new LineDataSet(yVals_close, "Closing");
        final LineData data_close = new LineData(set_close);

        btn_open = (Button)findViewById(R.id.btn_open);
        btn_high = (Button)findViewById(R.id.btn_high);
        btn_low = (Button)findViewById(R.id.btn_low);
        btn_close = (Button)findViewById(R.id.btn_close);

        //mLineChart.setData(data_open);
        //mLineChart.animateX(1000);

        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineChart.setData(data_open);
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
                //mLineChart.animateX(1000);
            }
        });
        btn_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineChart.setData(data_high);
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
                //mLineChart.animateX(1000);
            }
        });
        btn_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineChart.setData(data_low);
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
                //mLineChart.animateX(1000);
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineChart.setData(data_close);
                mLineChart.notifyDataSetChanged();
                mLineChart.invalidate();
                //mLineChart.animateX(1000);
            }
        });
    }
    public String fetchJson(){
        InputStream is = getResources().openRawResource(R.raw.msft_mo_data_till_12_12_2017_1);

        Scanner sc = new Scanner(is);
        StringBuilder sb = new StringBuilder();

        while(sc.hasNextLine()){
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }
    public List<Stock> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readStocksObject(reader);
        } finally{
            reader.close();
        }
    }

    public List<Stock> readStocksObject(JsonReader reader) throws IOException {
        List<Stock> stocks = new ArrayList<Stock>();

        reader.beginObject();
        while (reader.hasNext()){
            stocks.add(readStock(reader));
        }
        reader.endObject();
        return stocks;
    }

    public Stock readStock(JsonReader reader) throws IOException {

        String open ="";
        String high ="";
        String low ="";
        String close ="";
        String adjusted_close ="";
        String volume ="";
        String dividend_amount ="";

        reader.beginObject();
        while (reader.hasNext()){
            String name = reader.nextName();
            if(name.equals("1. open")){
                open = reader.nextString();
            } else if (name.equals("2. high")){
                high = reader.nextString();
            } else if (name.equals("3. low")){
                low = reader.nextString();
            } else if (name.equals("4. close")){
                close = reader.nextString();
            } else if (name.equals("5. adjusted close")){
                adjusted_close = reader.nextString();
            } else if (name.equals("6. volume")){
                volume = reader.nextString();
            } else if (name.equals("7. dividend amount")){
                dividend_amount = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Stock(open, high, low, close, adjusted_close, volume, dividend_amount);
    }
}
