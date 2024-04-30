package interfaces;

import java.util.Date;

public interface StockDataSetAnalyzer {
    StockHistoryDataSet getStockHistoryDataSet();

    void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet);

    DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate);

    DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate);

    DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate);
}