package impl;

import interfaces.DLLComp;
import interfaces.Map;
import interfaces.StockHistory;
import interfaces.StockHistoryDataSet;

public class StockHistoryDataSetImpl implements StockHistoryDataSet {
    Map<String, StockHistory> map = new BST<>();

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean empty() {
        return map.empty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<String, StockHistory> getStockHistoryMap() {
        return map;
    }

    @Override
    public DLLComp<String> getAllCompanyCodes() {
        return map.getKeys();
    }

    @Override
    public StockHistory getStockHistory(String companyCode) {
        map.find(companyCode);
        return map.retrieve();
    }

    @Override
    public boolean addStockHistory(StockHistory stockHistory) {
        return map.insert(stockHistory.getCompanyCode(), stockHistory);
    }

    @Override
    public boolean removeStockHistory(String companyCode) {
        return map.remove(companyCode);
    }
}
