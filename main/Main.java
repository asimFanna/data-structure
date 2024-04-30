package main;

import impl.NumericTimeSeriesImpl;
import impl.TimeSeriesImp;
import interfaces.StockData;

import java.text.ParseException;


public class Main {

    public static void main(String[] args) throws ParseException {

        loadNumericTimeSeriesData();

    }

    public static void loadTimeSeriesData() throws ParseException {
        TimeSeriesImp<StockData> timeSeries = Helpers.readTimeSeriesData("C1.csv");
        Helpers.traverse(timeSeries.getDataPointsInRange(Helpers.getDateFromString("2024-01-01"),Helpers.getDateFromString("2024-01-03")));

    }


    public static void loadNumericTimeSeriesData() throws ParseException {
        NumericTimeSeriesImpl timeSeries = Helpers.readTimeNumericSeriesData("C1.csv");
        System.out.println(timeSeries.calculateMovingAverage(3).getMin());
        Helpers.traverse(timeSeries.movingAverageList);
    }


}
