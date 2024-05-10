import java.text.ParseException;
import java.util.Date;

@SuppressWarnings("deprecation")
public class Main {

    public static void main(String[] args) throws ParseException {

        loadNumericTimeSeriesData();
        testStockHistoryDataSetImp();
         testAnalyzer();
    }

    public static void testStockHistoryDataSetImp() throws ParseException {
        System.out.println("===================TESTING STOCK HISTORY DATA SET ===========================");

        StockDataLoaderImp loader = new StockDataLoaderImp();
        System.out.println("Loading data...");
        StockHistoryDataSet dataSet = loader.loadStockDataDir("./data/real");
        System.out.println("Loaded DataSet with size: " + dataSet.size());

        System.out.println("===================TEST STOCK HISTORY===========================");
        System.out.println("GET GOOGL stock history");
        StockHistory stockHistory = dataSet.getStockHistory("GOOGL");
        System.out.println("Find StockData For Date 2023-01-06");
        StockData stockData = stockHistory.getStockData(Helpers.getDateFromString("2023-01-06"));
        System.out.println(stockData);


        System.out.println("==================== TEST BST ==========================");

        BST<String, StockHistory> map = (BST<String, StockHistory>) dataSet.getStockHistoryMap();
        System.out.println("Test nbKeyComp for GOOGL moves:"+map.nbKeyComp("GOOGL"));
        System.out.println("Test nbKeyComp for TSLA moves:"+map.nbKeyComp("TSLA"));
        System.out.println("Test nbKeyComp for AMZN moves:"+map.nbKeyComp("AMZN"));

        System.out.print("INORDER: ");
        inOrder(map.root);
        System.out.println();
        System.out.print("PREORDER: ");
        postOrderTraverse(map.root);
        System.out.println();


    }


    public static void loadNumericTimeSeriesData() throws ParseException {
        System.out.println("==================== TEST NUMERIC TIME SERIES ==========================");
        System.out.println("Loading C1.csv");

        NumericTimeSeriesImp timeSeries = Helpers.readTimeNumericSeriesData("./C1.csv");

        System.out.println("Calculate Moving Average");
        timeSeries.calculateMovingAverage(3);
        System.out.println("Traversing Moving Average List");
        Helpers.traverse(timeSeries.movingAverageList);

        System.out.println("Test get [Max & Min]");
        System.out.println("[" + timeSeries.getMax().value + " & " + timeSeries.getMin().value + "]");


    }

    private static <T, K> void postOrderTraverse(BST.BSTNode<T, K> node) {
        if (node == null)
            return;


        // Print Key
        System.out.print(node.key + " ");

        // Visit Left SubTree
        postOrderTraverse(node.left);
        // Visit Left SubTree
        postOrderTraverse(node.right);

    }

    private static <T, K> void inOrder(BST.BSTNode<T, K> node) {
        if (node == null)
            return;


        // Visit Left SubTree
        inOrder(node.left);

        // Print Key
        System.out.print(node.key + " ");

        // Visit Left SubTree
        inOrder(node.right);

    }

    public static void testAnalyzer() {
        StockDataLoader SDL = new StockDataLoaderImp();

        String directoryPath = "./data/real";
        StockHistoryDataSet AllCompanies = SDL.loadStockDataDir(directoryPath);

        System.out.println("********* getAllCompanyCodes");
        DLLComp<String> companies_name = AllCompanies.getAllCompanyCodes();
        if (!companies_name.empty()) {

            companies_name.findFirst();
            for (int i = 0; i < companies_name.size(); i++) {
                System.out.println(companies_name.retrieve());

                System.out.println("******** getStockHistory");
                StockHistory company = AllCompanies.getStockHistory(companies_name.retrieve());
                System.out.println("Company name " + company.getCompanyCode());
                DLL<DataPoint<StockData>> data = company.getTimeSeries().getAllDataPoints();
                if (!data.empty()) {
                    data.findFirst();
                    for (int j = 0; j < data.size(); j++) {
                        System.out.println(data.retrieve());
                        data.findNext();
                    }
                } else
                    System.out.println("No Company Available");

                companies_name.findNext();
            }
        } else
            System.out.println("No Company Available");


        StockDataSetAnalyzer Analyzer = new StockDataSetAnalyzerImp();
        Analyzer.setStockHistoryDataSet(AllCompanies);

        DLLComp<CompPair<String, Double>> per = Analyzer.getSortedByPerformance(new Date("1/2/2024"), new Date("1/4/2024"));
        if (!per.empty()) {
            per.findFirst();
            for (int i = 0; i < per.size(); i++) {
                System.out.println(per.retrieve());
                per.findNext();
            }
        } else
            System.out.println("No Data Avialable");

        DLLComp<CompPair<String, Long>> vol = Analyzer.getSortedByVolume(new Date("1/2/2024"), new Date("1/4/2024"));
        if (!vol.empty()) {
            vol.findFirst();
            for (int i = 0; i < vol.size(); i++) {
                System.out.println(vol.retrieve());
                vol.findNext();
            }
        } else
            System.out.println("No Data Avialable");


        DLLComp<CompPair<Pair<String, Date>, Double>> SDPI = Analyzer.getSortedByMSDPI(new Date("1/2/2024"), new Date("1/4/2024"));
        if (!SDPI.empty()) {
            SDPI.findFirst();
            for (int i = 0; i < SDPI.size(); i++) {
                System.out.println(SDPI.retrieve());
                SDPI.findNext();
            }
        } else
            System.out.println("No Data Avialable");
    }

}
