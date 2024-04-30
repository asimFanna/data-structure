package interfaces;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataPoint<T>  implements Comparable<DataPoint<T>> {
    // The date of the data point .
    public Date date;
    // The value of the data point .
    public T value;

    public DataPoint(Date date, T value) {
        this.date = date;
        this.value = value;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy -MM - dd ");
        return dateFormat.format(date) + "␣:␣ " + value.toString();
    }


    @Override
    public int compareTo(DataPoint<T> dataPoint) {
        return this.date.compareTo(dataPoint.date);
    }
}