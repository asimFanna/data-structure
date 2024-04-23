package impl;

import interfaces.DataPoint;
import interfaces.NumericTimeSeries;

public class NumericTimeSeriesImpl extends TimeSeriesImp<Double> implements NumericTimeSeries {

    public DLLCompImp<DataPoint<Double>> movingAverageList = new DLLCompImp<>();

    @Override
    public NumericTimeSeries calculateMovingAverage(int period) {
        dataPointsList.findFirst();
        for (int i = 0; i < period; i++) {
            DataPoint<Double> dataPoint = new DataPoint<>(null,0.0);
            for (int j = 0; j < period; j++) {
                dataPoint.value += dataPointsList.retrieve().value;
                if (j != period - 1) dataPointsList.findNext();
            }
            dataPoint.date = dataPointsList.retrieve().date;
            dataPoint.value /=period;
            movingAverageList.insert(dataPoint);
            dataPointsList.findPrevious();
        }

        movingAverageList.sort(true);
        return this;
    }

    @Override
    public DataPoint<Double> getMax() {
        return movingAverageList.current.value;
    }

    @Override
    public DataPoint<Double> getMin() {
        return movingAverageList.head.value;
    }
}
