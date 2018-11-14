package com.gamesbykevin.cryptobot.indicator;

import com.gamesbykevin.cryptobot.candle.Candle;
import com.gamesbykevin.cryptobot.candle.Candle.Fields;
import com.gamesbykevin.cryptobot.util.Util;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class Indicator {

    /**
     * Each indicator will have it's own key
     */
    public enum Key {

        //trend
        SimpleMovingAverage,
    }

    /**
     * How many indicator values do we print?
     */
    public static final int DISPLAY_LIMIT = 5;

    //the indicator we are using
    private final Key key;

    //each indicator will be calculating for a certain number of periods
    private final int periods;

    //what field are we calculating?
    private final Fields field;

    //list of values accessible to our indicator
    private List<Double> values;

    //implement logic to calculate
    public abstract void calculate(List<Candle> candles);

    public List<Double> getValues() {

        if (this.values == null)
            this.values = new ArrayList<>();

        return this.values;
    }

    public void display() {

        String tmp = "";

        //display the most recent calculations
        for (int index = getValues().size() - DISPLAY_LIMIT; index < getValues().size(); index++) {

            //each value is separated by a comma
            if (tmp != null && tmp.trim().length() > 0)
                tmp += ", ";

            tmp += getValues().get(index);
        }

        //display the key of this indicator and how many periods it was for
        Util.display(getKey() + " (" + getPeriods() + "): " + tmp);
    }
}