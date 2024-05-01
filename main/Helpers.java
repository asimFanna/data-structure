package main;

import impl.DLLCompImp;
import impl.NumericTimeSeriesImp;
import impl.StockHistoryImp;
import impl.TimeSeriesImp;
import interfaces.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Helpers {

    public static <T> void traverse(DLL<T> list) {
        list.findFirst();
        System.out.println("Size:" + list.size());
        while (!list.last()) {
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

    public static StockHistory readStockHistory(String path) {
        StockHistory stockHistory = new StockHistoryImp();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // skipping first line cuase it is columns header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                StockData stockData = new StockData(Double.parseDouble(values[1]), Double.parseDouble(values[2]), Double.parseDouble(values[3]), Double.parseDouble(values[4]), Long.parseLong(values[5]));
                stockHistory.addStockData(getDateFromString(values[0]), stockData);
                String [] pathArray = path.split("/");
                stockHistory.SetCompanyCode(pathArray[pathArray.length-1]);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return stockHistory;
    }


    public static NumericTimeSeriesImp readTimeNumericSeriesData(String fileName) {
        NumericTimeSeriesImp timeSeries = new NumericTimeSeriesImp();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // skipping first line cuase it is columns header
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                DataPoint<Double> dt = new DataPoint<>(getDateFromString(values[0]), Double.parseDouble(values[4]));
                timeSeries.addDataPoint(dt);
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return timeSeries;
    }

    public static Date getDateFromString(String date) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        return format.parse(date);

    }

    public static <T> void sortDataPoints(boolean increasing, DLLCompImp<DataPoint<T>> list) {
        //bubble sort
        Node<DataPoint<T>> index = list.head;
        while (index != null) {
            Node<DataPoint<T>> innerIndex = index.next;
            while (innerIndex != null) {
                boolean compareResult = increasing ? innerIndex.value.date.compareTo(index.value.date) < 0 : innerIndex.value.date.compareTo(index.value.date) > 0;
                if (compareResult) {
                    //swapping values
                    DataPoint<T> temp = innerIndex.value;
                    innerIndex.value = index.value;
                    index.value = temp;
                }
                innerIndex = innerIndex.next;
                if(innerIndex!=null)
                    list.current =innerIndex;
            }
            index = index.next;
        }
    }


}
