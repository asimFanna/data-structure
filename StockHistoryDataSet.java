public interface StockHistoryDataSet {
    int size();

    boolean empty();

    void clear();

    Map<String, StockHistory> getStockHistoryMap();

    DLLComp<String> getAllCompanyCodes();

    StockHistory getStockHistory(String companyCode);

    boolean addStockHistory(StockHistory StockHistory);

    boolean removeStockHistory(String companyCode);
}
