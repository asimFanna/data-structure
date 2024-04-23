package main;

import impl.NumericTimeSeriesImpl;
import impl.TimeSeriesImp;
import interfaces.DLL;
import interfaces.DataPoint;
import interfaces.StockData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helpers {

    public static <T> void traverse (DLL<T> list) {
       list.findFirst();
       System.out.println("Size:" + list.size());
        while (!list.last()){
            System.out.println(list.retrieve());
            list.findNext();
        }
        System.out.println(list.retrieve());
    }
    public static TimeSeriesImp<StockData> readTimeSeriesData(String fileName) {
        TimeSeriesImp<StockData> timeSeries = new TimeSeriesImp<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // skipping first line cuase it is columns header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DataPoint<StockData> dt = new DataPoint<>(getDateFromString(values[0]), new StockData(Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Long.parseLong(values[5])));
                timeSeries.addDataPoint(dt);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return timeSeries;
    }
    public static NumericTimeSeriesImpl readTimeNumericSeriesData(String fileName) {
        NumericTimeSeriesImpl timeSeries = new NumericTimeSeriesImpl();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // skipping first line cuase it is columns header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DataPoint<Double> dt = new DataPoint<> (getDateFromString(values[0]),Double.parseDouble(values[4]));
                timeSeries.addDataPoint(dt);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return timeSeries;
    }

    public static Date getDateFromString(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return  format.parse(date);

    }



}
