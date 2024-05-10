import java.util.Date;

public class TimeSeriesImp<T> implements TimeSeries<T> {
    DLLComp<CompPair<DataPoint<T>, Date>> dates;

    public TimeSeriesImp() {
        dates = new DLLCompImp<CompPair<DataPoint<T>, Date>>();
    }

    @Override
    public int size() {
        return dates.size();
    }

    @Override
    public boolean empty() {
        return dates.empty();
    }

    @Override
    public T getDataPoint(Date date) {
        if (dates.empty())
            return null;

        dates.findFirst();
        for (int i = 0; i < dates.size(); i++) {
            if (dates.retrieve().second.compareTo(date) == 0)
                return dates.retrieve().first.value;
            dates.findNext();
        }
        return null;
    }

    @Override
    public DLL<Date> getAllDates() {

        dates.sort(true);
        DLL<Date> d = new DLLImp<Date>();

        dates.findFirst();
        for (int i = 0; i < dates.size(); i++) {
            d.insert(dates.retrieve().second);
            dates.findNext();

        }
        return d;
    }

    @Override
    public Date getMinDate() {
        dates.sort(true);
        return dates.getMin().second;
    }

    @Override
    public Date getMaxDate() {
        dates.sort(true);
        return dates.getMax().second;
    }

    @Override
    public boolean addDataPoint(DataPoint<T> dataPoint) {
        CompPair<DataPoint<T>, Date> v = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date);

        /**
         * this is not needed let DLLImp handle insertion
         *   if(dates.empty()) {
         *             dates.insert(v);
         *             return true;
         *         }
         *
         *         dates.findFirst();
         *         for(int i=0;i<dates.size();i++) {
         *             if ( dates.retrieve().compareTo(v) == 0)
         *                 return false;
         *             return true;
         *         }
         *
         * */
        dates.insert(v);
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<T> dataPoint) {
        if (dates.empty())
            return false;
        dates.findFirst();

        for (int i = 0; i < dates.size(); i++) {
            if (dates.retrieve().second.compareTo(dataPoint.date) == 0) {
                CompPair<DataPoint<T>, Date> v = new CompPair<DataPoint<T>, Date>(dataPoint, dataPoint.date);
                v.first.date = dates.retrieve().second;
                v.second = dates.retrieve().second;
                dates.update(v);
                return true;

            }
            dates.findNext();

        }
        return false;

    }

    @Override
    public boolean removeDataPoint(Date date) {
        if (dates.empty())
            return false;

        dates.findFirst();
        for (int i = 0; i < dates.size(); i++) {
            if (dates.retrieve().second.compareTo(date) == 0) {
                dates.remove();
                return true;
            }
            dates.findNext();
        }
        return false;
    }

    @Override
    public DLL<DataPoint<T>> getAllDataPoints() {
        DLL<DataPoint<T>> alldp_s = new DLLImp<DataPoint<T>>();
        if (!dates.empty()) {
            dates.sort(true);
            dates.findFirst();
            for (int i = 0; i < dates.size(); i++) {
                alldp_s.insert(dates.retrieve().first);
                dates.findNext();
            }
        }
        return alldp_s;
    }

    @Override
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {
        DLL<DataPoint<T>> pir = getAllDataPoints();

        if (!pir.empty()) {
            pir.findFirst();
            if (startDate != null) {
                while ((!pir.empty()) && (pir.retrieve().date.compareTo(startDate) > 0)) ;
                pir.remove();
            }
            if (endDate != null) {
                while ((!pir.empty()) && !pir.last() && (pir.retrieve().date.compareTo(endDate) <= 0))
                    pir.findNext();

                while ((!pir.empty()) && (pir.retrieve().date.compareTo(endDate) > 0))
                    pir.remove();
            }
        }
        return pir;
    }

}

class TimeSeriesImpOld<T> implements TimeSeries<T> {

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
        CompPair<DataPoint<T>, Date> node = dates.find(new CompPair<>(null, date));
        if (node == null) return null;

        return node.first.value;
    }

    @Override
    public DLL<Date> getAllDates() {

        dates.sort(true);
        DLL<Date> d = new DLLImp<Date>();

        dates.findFirst();
        for (int i = 0; i < dates.size(); i++) {
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
        dates.insert(new CompPair<>(dataPoint, dataPoint.date));
        size++;
        return true;
    }

    @Override
    public boolean updateDataPoint(DataPoint<T> dataPoint) {
        CompPair<DataPoint<T>, Date> found = dates.find(new CompPair<>(dataPoint, dataPoint.date));
        if (found == null) return false;
        found.first.date = dataPoint.date;
        found.first.value = dataPoint.value;
        return true;
    }

    @Override
    public boolean removeDataPoint(Date date) {
        CompPair<DataPoint<T>, Date> found = dates.find(new CompPair<>(null, date));
        if (found == null) return false;

        dates.remove();
        size--;
        return true;
    }

    @Override
    public DLL<DataPoint<T>> getAllDataPoints() {
        dates.sort(true);

        DLL<DataPoint<T>> list = new DLLImp<>();
        node<CompPair<DataPoint<T>, Date>> i = dates.head;

        while (i != null) {
            list.insert(i.data.first);
            i = i.next;
        }
        return list;
    }

    @Override
    public DLL<DataPoint<T>> getDataPointsInRange(Date startDate, Date endDate) {
        node<CompPair<DataPoint<T>, Date>> index = dates.head;

        DLLImp<DataPoint<T>> newList = new DLLImp<>();
        while (index != null && index.data != null) {
            if (endDate == null) {
                if (index.data.first.date.compareTo(startDate) >= 0) {
                    newList.insert(index.data.first);
                }
            } else {
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

