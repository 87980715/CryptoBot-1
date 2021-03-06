package com.gamesbykevin.cryptobot.indicator.trend;

import com.gamesbykevin.cryptobot.candle.Candle;
import com.gamesbykevin.cryptobot.candle.Candle.Fields;
import com.gamesbykevin.cryptobot.indicator.Indicator;

import java.util.List;

public class ExponentialMovingAverage extends Indicator {

    public ExponentialMovingAverage(int periods, Fields field) {
        super(Key.ExponentialMovingAverage, periods, field);
    }

    @Override
    public void calculate(List<Candle> candles) throws Exception {

        //clear our list
        getValues().clear();

        //look at the history in order to calculate our values
        for (int index = 0; index < candles.size(); index++) {

            //make sure we have enough data first
            if (index < getPeriods() - 1)
                continue;

            //if there are no values yet the first one will be the sma
            if (getValues().isEmpty()) {

                double sum = 0;

                //check the periods to perform our calculation
                for (int j = index - getPeriods() + 1; j <= index; j++) {

                    sum += getValue(candles.get(j));
                }

                //add the value to our list
                getValues().add(sum / (double)getPeriods());

            } else {

                //get the previous ema
                final double previousEma = getValues().get(getValues().size() - 1);

                //calculate and add the new ema to our list
                getValues().add(calculate(getValue(candles.get(index)), getPeriods(), previousEma));
            }
        }
    }

    public static double calculate(double value, int periods, double previousEma) {

        //calculate the multiplier
        double multiplier = (2d / (periods + 1d));

        //return the result
        return ((value - previousEma) * multiplier) + previousEma;
    }

    @Override
    public void display() {
        displayDefault();
    }
}