public class StockHistoryDataSetImp implements StockHistoryDataSet {

  Map<String, StockHistory> Companies;

  public StockHistoryDataSetImp() {
    Companies = new BST<String, StockHistory>();
  }

  public boolean empty() {
    return Companies.empty();
  }

  public int size() {
    return Companies.size();
  }

  public void clear() {
    Companies.clear();
  }

  public Map<String, StockHistory> getStockHistoryMap() {
    return Companies;
  }


  @Override
  public DLLComp<String> getAllCompanyCodes() {
    return Companies.getKeys();
  }

  public StockHistory getStockHistory(String companyCode) {
    if (Companies.find(companyCode))
      return Companies.retrieve();
    return null;
  }

  public boolean addStockHistory(StockHistory stockHistory) {
    if (!Companies.find(stockHistory.getCompanyCode())) {
      Companies.insert(stockHistory.getCompanyCode(), stockHistory);
      return true;
    }
    return false;
  }

  public boolean removeStockHistory(String companyCode) {
    if (Companies.find(companyCode)) {
      Companies.remove(companyCode);
      return true;
    }
    return false;
  }


}