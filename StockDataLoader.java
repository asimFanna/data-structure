public interface StockDataLoader {

    StockHistory loadStockDataFile(String fileName);

    StockHistoryDataSet loadStockDataDir(String directoryPath);
}