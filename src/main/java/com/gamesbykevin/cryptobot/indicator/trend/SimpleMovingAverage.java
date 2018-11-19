package com.gamesbykevin.cryptobot.indicator.trend;

import com.gamesbykevin.cryptobot.candle.Candle;
import com.gamesbykevin.cryptobot.candle.Candle.Fields;
import com.gamesbykevin.cryptobot.indicator.Indicator;
import lombok.Data;

import java.util.List;

public class SimpleMovingAverage extends Indicator {

    public SimpleMovingAverage(int periods, Fields field) {
        super(Key.SimpleMovingAverage, periods, field);
    }

    @Override
    public void calculate(List<Candle> candles) throws Exception {

        //clear our list
        getValues().clear();

        //look at the history in order to calculate our values
        for (int index = 0; index <= candles.size(); index++) {

            //make sure we have enough data first
            if (index < getPeriods())
                continue;

            double sum = 0;

            //check the periods to perform our calculation
            for (int j = index - getPeriods(); j < index; j++) {

                //add the total sum
                sum += getValue(candles.get(j));
            }

            //add the value to our list
            getValues().add((sum / (double)getPeriods()));
        }
    }

    /**
     * Calculate the simple moving average
     * @param values List of values to calculate
     * @param start Start index
     * @param end End index
     * @return The average of the provided parameters
     */
    public static double calculate(List<Double> values, int start, int end) {

        double sum = 0;

        for (int index = start; index <= end; index++) {
            sum += values.get(index);
        }

        //return our result
        return (sum / (end - start + 1));
    }
}