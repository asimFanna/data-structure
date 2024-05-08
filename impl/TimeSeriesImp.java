package impl;

import interfaces.*;

import java.util.Date;

public class TimeSeriesImp<T> implements TimeSeries<T> {

    public DLLCompImp<CompPair<DataPoint<T>, Date>> dates = new DLLCompImp<>();
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
        return dates.empty();
    }

    @Override
    public T getDataPoint(Date date) {
        CompPair<DataPoint<T>, Date> node = dates.find(new CompPair<>(null,date));
        if (node == null) return null;

        return node.first.value;
    }

    @Override
    public DLL<Date> getAllDates() {

        dates.sort(true);
        DLL<Date> d=new DLLImp<Date>();

        dates.findFirst();
        for(int i=0;i<dates.size();i++) {
            d.insert(dates.retrieve().second);
            dates.findNext();

        }
        return d;
    }

    @Override
    public Date getMinDate() {
        if (dates.empty()) return null;

        return dates.head.data.second;
    }

    @Override
    public Date getMaxDate() {
        if (dates.empty()) return null;


        return dates.cur.data.second;
    }

    @Override
    public boolean addDataPoint(DataPoint<T> dataPoint) {
        dates.insert(new CompPair<>(dataPoint,dataPoint.date));
        size++;
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<T> dataPoint) {
        CompPair<DataPoint<T>, Date> found = dates.find(new CompPair<>(dataPoint,dataPoint.date));
        if (found == null) return false;
        found.first.date = dataPoint.date;
        found.first.value = dataPoint.value;
        return true;
    }

    @Override
    public boolean removeDataPoint(Date date) {
        CompPair<DataPoint<T>, Date> found = dates.find(new CompPair<>(null,date));
        if (found == null) return false;

        dates.remove();
        size--;
        return true;
    }

    @Override
    public DLL<DataPoint<T>> getAllDataPoints() {
        dates.sort(true);

        DLL<DataPoint<T>> list = new DLLImp<>();
        Node<CompPair<DataPoint<T>, Date>> i = dates.head;

        while (i!=null){
            list.insert(i.data.first);
            i=i.next;
        }
        return list;
    }

    @Override
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {
        Node<CompPair<DataPoint<T>, Date>> index = dates.head;

        DLLImp<DataPoint<T>> newList = new DLLImp<>();
        while (index != null && index.data !=null) {
            if(endDate==null ){
               if(  index.data.first.date.compareTo(startDate) >= 0 ){
                   newList.insert(index.data.first);
               }
            }else {
                if (index.data.first.date.compareTo(startDate) >= 0 && index.data.first.date.compareTo(endDate) <= 0) {
                    newList.insert(index.data.first);
                } else if (index.data.first.date.compareTo(endDate) > 0) {
                    /*
                     * Assuming List is sorted increasingly then we break the loop early if current data is grater than endDate
                     * */
                    return newList;
                }
            }

            index = index.next;
        }
        return newList;
    }
}

