package impl;

import interfaces.CompPair;
import interfaces.DataPoint;
import interfaces.NumericTimeSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumericTimeSeriesImp extends TimeSeriesImp<Double> implements NumericTimeSeries {

    public DLLCompImp<CompPair<DataPoint<Double>, Double>> movingAverageList = new DLLCompImp<>();

    @Override
    public NumericTimeSeries calculateMovingAverage(int period) {
        dates.findFirst();
        for (int i = 0; i < period; i++) {
            Double value = 0.0;
            DataPoint<Double> point = new DataPoint<>(null, 0.0);
            String sumVerificationString = "";
            for (int j = 0; j < period; j++) {
                value += dates.retrieve().first.value;
                if(j==0)
                    sumVerificationString += dates.retrieve().first.value;
                else
                    sumVerificationString += " + "+ dates.retrieve().first.value;
                if (j != period - 1) {

                    dates.findNext();
                }
            }
            point = dates.retrieve().first;

            if (period - 1 != i)
                dates.findPrevious();

            value =new BigDecimal(value/period).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println(sumVerificationString+" /"+period+" = "+value);


            movingAverageList.insert(new CompPair<>(new DataPoint<>(point.date, value), value));


        }

        movingAverageList.sort(true);

        return this;
    }

    @Override
    public DataPoint<Double> getMax() {
        if (movingAverageList.isIncreasing)
            return movingAverageList.cur.data.first;
        return movingAverageList.head.data.first;
    }

    @Override
    public DataPoint<Double> getMin() {
        if (movingAverageList.isIncreasing)
            return movingAverageList.head.data.first;
        return movingAverageList.cur.data.first;
    }
}
