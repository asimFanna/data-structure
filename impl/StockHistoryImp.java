package impl;

import interfaces.DataPoint;
import interfaces.StockData;
import interfaces.StockHistory;
import interfaces.TimeSeries;

import java.util.Date;

public class StockHistoryImp  implements StockHistory {
    private TimeSeriesImp<StockData> stockDataTimeSeries = new TimeSeriesImp<>();
    private String companyCode;
    @Override
    public int size() {
        return stockDataTimeSeries.size();
    }

    @Override
    public boolean empty() {
        return stockDataTimeSeries.empty();
    }

    @Override
    public void clear() {
        stockDataTimeSeries = new TimeSeriesImp<>();
    }

    @Override
    public String getCompanyCode() {
        return companyCode;
    }

    @Override
    public void SetCompanyCode(String companyCode) {
            this.companyCode = companyCode;
    }

    @Override
    public TimeSeries<StockData> getTimeSeries() {
        return stockDataTimeSeries;
    }

    @Override
    public StockData getStockData(Date date) {
        return stockDataTimeSeries.getDataPoint(date);
    }

    @Override
    public boolean addStockData(Date date, StockData stockData) {
        return stockDataTimeSeries.addDataPoint(new DataPoint<>(date,stockData));
    }

    @Override
    public boolean removeStockData(Date date) {
        return stockDataTimeSeries.removeDataPoint(date);
    }
}
