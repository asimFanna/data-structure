package main;

import impl.*;
import interfaces.StockData;
import interfaces.StockDataLoader;
import interfaces.StockHistory;

import java.text.ParseException;


public class Main {

    public static void main(String[] args) throws ParseException {


        StockDataLoader loader = new StockDataLoaderImpl();
        loader.loadStockDataDir("/home/aqar/Downloads/resources (1)/Resources/data/real");



    }

    public static void loadTimeSeriesData() throws ParseException {
        StockHistory stockHistory = new StockHistoryImp();
        TimeSeriesImp<StockData> timeSeries = Helpers.readTimeSeriesData("C1.csv");

    }


    public static void loadNumericTimeSeriesData() throws ParseException {
        NumericTimeSeriesImpl timeSeries = Helpers.readTimeNumericSeriesData("C1.csv");
        System.out.println(timeSeries.calculateMovingAverage(3).getMin());
        Helpers.traverse(timeSeries.movingAverageList);
    }


}
