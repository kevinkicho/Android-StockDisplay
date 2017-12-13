package com.example.myjsonapp121217;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by kevin on 12/13/2017.
 */

public class CandleChartActivity extends AppCompatActivity{
    private String TAG = "MainActivity";
    List<Stock> stocks = new ArrayList<Stock>();
    private CandleStickChart mCandleChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candle);

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
        List<CandleEntry> yVals_candle = new ArrayList<>();
        for (int i =0; i<opens.size(); i++){
            float[] tempArray = new float[4];
            tempArray[0] = opens.get(i);
            tempArray[1] = highs.get(i);
            tempArray[2] = lows.get(i);
            tempArray[3] = closes.get(i);
            Arrays.sort(tempArray);
            yVals_candle.add(new CandleEntry(i,tempArray[3], tempArray[0], tempArray[1], tempArray[2]));
        }
        mCandleChart = (CandleStickChart) findViewById(R.id.candle_chart1);

        CandleDataSet cds = new CandleDataSet(yVals_candle, "Stocks Daily");
        CandleData cd = new CandleData(cds);

        mCandleChart.setData(cd);
        mCandleChart.animateX(1000);
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
}
