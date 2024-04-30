package impl;

import interfaces.StockDataLoader;
import interfaces.StockHistory;
import interfaces.StockHistoryDataSet;
import main.Helpers;

import java.io.File;

public class StockDataLoaderImpl implements StockDataLoader {
    private final StockHistoryDataSet dataSet = new StockHistoryDataSetImpl();
    @Override
    public StockHistory loadStockDataFile(String fileName) {
       return Helpers.readStockHistory(fileName);

    }

    @Override
    public StockHistoryDataSet loadStockDataDir(String directoryPath) {
        File folder = new File(directoryPath);
        File[] listOfFiles = folder.listFiles();
        if(listOfFiles==null) return dataSet;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println("Added "+file.getName()+" :"+dataSet.addStockHistory(loadStockDataFile(file.getPath()))+"");;
                System.out.println(file.getName() + " Loaded");
            }
        }
        return dataSet;
    }
}
