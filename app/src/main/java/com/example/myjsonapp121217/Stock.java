package com.example.myjsonapp121217;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

/**
 * Created by kevin on 12/12/2017.
 */

public class Stock {
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjusted_close;
    private String volume;
    private String dividend_amount;

    public Stock(){}
    public Stock(String open, String high, String low, String close, String adjusted_close,
                 String volume, String dividend_amount){
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.adjusted_close = adjusted_close;
        this.volume = volume;
        this.dividend_amount = dividend_amount;
    }

    public String getOpen(){
        return open;
    }
    public String getHigh(){
        return high;
    }
    public String getLow(){
        return low;
    }
    public String getClose(){
        return close;
    }
    public String getAdjusted_close(){
        return adjusted_close;
    }
    public String getVolume(){
        return volume;
    }
    public String getDividend_amount(){
        return dividend_amount;
    }
    @Override
    public String toString(){
        return "Open: " + open + "High: " + high + "Low " + low + "Close " + close + "Adj_close: " +
                adjusted_close + "Vol: " + volume + "Div_Amt" + dividend_amount;
    }
}
