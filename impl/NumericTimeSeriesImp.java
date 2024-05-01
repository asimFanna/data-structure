package impl;

import interfaces.DataPoint;
import interfaces.NumericTimeSeries;
import main.Helpers;

public class NumericTimeSeriesImp extends TimeSeriesImp<Double> implements NumericTimeSeries {

    public DLLCompImp<Double> movingAverageList = new DLLCompImp<>();

    @Override
    public NumericTimeSeries calculateMovingAverage(int period) {
        dataPointsList.findFirst();
        for (int i = 0; i < period; i++) {
            Double value = 0.0;
            for (int j = 0; j < period; j++) {
                value += dataPointsList.retrieve().value;
                if (j != period - 1) dataPointsList.findNext();
            }
            value /=period;
            movingAverageList.insert(value);
            dataPointsList.findPrevious();
        }

        Helpers.sortDataPoints(true, dataPointsList);
        return this;
    }

    @Override
    public DataPoint<Double> getMax() {
        return dataPointsList.current.value;
    }

    @Override
    public DataPoint<Double> getMin() {
        return dataPointsList.head.value;
    }
}
