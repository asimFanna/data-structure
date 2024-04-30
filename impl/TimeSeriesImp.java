package impl;

import interfaces.DLL;
import interfaces.DataPoint;
import interfaces.Node;
import interfaces.TimeSeries;

import java.util.Date;

public class TimeSeriesImp<T> implements TimeSeries<T> {

    public DLLCompImp<DataPoint<T>> dataPointsList = new DLLCompImp<>();
    public DLLCompImp<Date> datesList = new DLLCompImp<>();
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean empty() {
        /*
         * we can use size variable or dataPointsList.empty();
         * */
        return datesList.empty();
    }

    @Override
    public T getDataPoint(Date date) {
        DataPoint<T> node = dataPointsList.find(new DataPoint<>(date, null));
        if (node == null) return null;

        return node.value;
    }

    @Override
    public DLL<Date> getAllDates() {
        datesList.sort(true);
        return datesList;
    }

    @Override
    public Date getMinDate() {
        if (datesList.empty()) return null;

        return datesList.head.value;
    }

    @Override
    public Date getMaxDate() {
        if (datesList.empty()) return null;


        return datesList.current.value;
    }

    @Override
    public boolean addDataPoint(DataPoint<T> dataPoint) {
        dataPointsList.insert(dataPoint);
        datesList.insert(dataPoint.date);
        size++;
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<T> dataPoint) {
        DataPoint<T> found = dataPointsList.find(dataPoint);
        if (found == null) return false;
        found.date = dataPoint.date;
        found.value = dataPoint.value;
        return true;
    }

    @Override
    public boolean removeDataPoint(Date date) {
        DataPoint<T> found = dataPointsList.find(new DataPoint<>(date, null));
        if (found == null) return false;

        dataPointsList.remove();
        size--;
        return true;
    }

    @Override
    public DLL<DataPoint<T>> getAllDataPoints() {
        dataPointsList.sort(true);
        return dataPointsList;
    }

    @Override
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {
        Node<DataPoint<T>> index = dataPointsList.head;
        DLLImp<DataPoint<T>> newList = new DLLImp<>();
        while (index != null) {
            if (index.value.date.compareTo(startDate) >= 0 && index.value.date.compareTo(endDate) <= 0) {
                newList.insert(index.value);
            } else if (index.value.date.compareTo(endDate) > 0) {
                /*
                 * Assuming List is sorted increasingly then we break the loop early if current data is grater than endDate
                 * */
                return newList;
            }
            index = index.next;
        }
        return newList;
    }
}
