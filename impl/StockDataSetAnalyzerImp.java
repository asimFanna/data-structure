package impl;

import interfaces.*;

import java.util.Date;

public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer {
    StockHistoryDataSet dataSet = new StockHistoryDataSetImpl();
    @Override
    public StockHistoryDataSet getStockHistoryDataSet() {
        return dataSet;
    }

    @Override
    public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet) {
            dataSet = stockHistoryDataSet;
    }

    @Override
    public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Double>> comp = new DLLCompImp<>();

        DLLComp<String> codes  = dataSet.getAllCompanyCodes();
        codes.findFirst();

        while (!codes.last()){
            StockHistory stockHistory = dataSet.getStockHistory(codes.retrieve());
            DLL<DataPoint<StockData>> points =  stockHistory .getTimeSeries().getDataPointsInRange(startDate,endDate);
            points.findFirst();
            DataPoint<StockData> first = points.retrieve();
            while (!points.last()){
                points.findNext();
            }
            DataPoint<StockData> last = points.retrieve();
            Double performance = (last.value.close -  first.value.close)/first.value.close;
            comp.insert(new CompPair<>(stockHistory.getCompanyCode(), performance));
            codes.findNext();
        }

        return  comp;
    }

    @Override
    public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Long>> comp = new DLLCompImp<>();

        DLLComp<String> codes  = dataSet.getAllCompanyCodes();
        codes.findFirst();

        while (!codes.last()){
            StockHistory stockHistory = dataSet.getStockHistory(codes.retrieve());
            Long totalVolume = 0L;
            DLL<DataPoint<StockData>> points =  stockHistory .getTimeSeries().getDataPointsInRange(startDate,endDate);
            points.findFirst();
            while (!points.last()){
              totalVolume+=points.retrieve().value.volume;
                points.findNext();
            }
            comp.insert(new CompPair<>(stockHistory.getCompanyCode(), totalVolume));
            codes.findNext();
        }

        return  comp;
    }

    @Override
    public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate) {
        DLLComp<CompPair<Pair<String, Date>, Double>> comp = new DLLCompImp<>();

        DLLComp<String> codes  = dataSet.getAllCompanyCodes();
        codes.findFirst();

        while (!codes.last()){
            StockHistory stockHistory = dataSet.getStockHistory(codes.retrieve());
            DLL<DataPoint<StockData>> points =  stockHistory .getTimeSeries().getDataPointsInRange(startDate,endDate);
            double hiegest = 0;
            Date date = new Date();
            points.findFirst();
            while (!points.last()){
               if(points.retrieve().value.high>hiegest){
                   hiegest = points.retrieve().value.high;
                   date =  points.retrieve().date;
               }

                points.findNext();
            }
            comp.insert(new CompPair<>(new Pair<>(stockHistory.getCompanyCode(), date),hiegest) );
            codes.findNext();
        }

        return  comp;
    }
}
