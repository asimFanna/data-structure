import java.util.Date;


@SuppressWarnings("removal")
public class StockDataSetAnalyzerImp implements StockDataSetAnalyzer {

    StockHistoryDataSet Shda = new StockHistoryDataSetImp();

    // Returns .
    public StockHistoryDataSet getStockHistoryDataSet() {
        return Shda;
    }

    // Sets .

    public void setStockHistoryDataSet(StockHistoryDataSet stockHistoryDataSet) {
        DLLComp<String> companies_name = stockHistoryDataSet.getAllCompanyCodes();
        if (!companies_name.empty()) {
            companies_name.findFirst();
            for (int i = 0; i < companies_name.size(); i++) {

                StockHistory SH = new StockHistoryImp();
                SH.SetCompanyCode(companies_name.retrieve());
                StockHistory company = stockHistoryDataSet.getStockHistory(companies_name.retrieve());
                DLL<DataPoint<StockData>> data = company.getTimeSeries().getAllDataPoints();
                if (!data.empty()) {
                    data.findFirst();
                    for (int j = 0; j < data.size(); j++) {
                        DataPoint sp_new = data.retrieve();
                        StockData sd_new = (StockData) sp_new.value;

                        SH.addStockData(sp_new.date,
                                new StockData(sd_new.open, sd_new.close, sd_new.high, sd_new.low, sd_new.volume));

                        data.findNext();
                    }

                } else
                    System.out.println("No Company Available");

                Shda.addStockHistory(SH);
                companies_name.findNext();
            }
        } else
            System.out.println("No Company Available");
    }

    public DLLComp<CompPair<String, Double>> getSortedByPerformance(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Double>> dllcomp = new DLLCompImp<CompPair<String, Double>>();
        DLLComp<String> companies_name = Shda.getAllCompanyCodes();

        if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
            companies_name.findFirst();
            for (int i = 0; i < companies_name.size(); i++) {

                String CompanyName = companies_name.retrieve();
                DLL<DataPoint<StockData>> data = Shda.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);

                Double performance = new Double(0);

                if (!data.empty()) {
                    data.findFirst();
                    double day1 = data.retrieve().value.close;
                    while (!data.last())
                        data.findNext();
                    double day2 = data.retrieve().value.close;

                    performance = (day2 - day1) / day1;
                }

                CompPair<String, Double> val = new CompPair<String, Double>(CompanyName, performance);
                dllcomp.insert(val);

                companies_name.findNext();
            }
            dllcomp.sort(true);
        }
        return dllcomp;
    }

    public DLLComp<CompPair<String, Long>> getSortedByVolume(Date startDate, Date endDate) {
        DLLComp<CompPair<String, Long>> dllcomp = new DLLCompImp<CompPair<String, Long>>();
        DLLComp<String> companies_name = Shda.getAllCompanyCodes();

        if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
            companies_name.findFirst();
            for (int i = 0; i < companies_name.size(); i++) {

                String CompanyName = companies_name.retrieve();
                DLL<DataPoint<StockData>> data = Shda.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);

                Long vol = new Long(0);

                if (!data.empty()) {
                    data.findFirst();
                    for (int j = 0; j < data.size(); j++) {
                        vol += data.retrieve().value.volume;
                        data.findNext();
                    }
                }

                CompPair<String, Long> val = new CompPair<String, Long>(CompanyName, vol);
                dllcomp.insert(val);

                companies_name.findNext();
            }
            dllcomp.sort(true);
        }
        return dllcomp;
    }


    // date.
    public DLLComp<CompPair<Pair<String, Date>, Double>> getSortedByMSDPI(Date startDate, Date endDate) {
        DLLComp<CompPair<Pair<String, Date>, Double>> dllcomp = new DLLCompImp<CompPair<Pair<String, Date>, Double>>();
        DLLComp<String> companies_name = Shda.getAllCompanyCodes();

        if (!(startDate == null && endDate == null) && (!companies_name.empty())) {
            companies_name.findFirst();
            for (int i = 0; i < companies_name.size(); i++) {

                String CompanyName = companies_name.retrieve();
                Pair<String, Date> company_data = null;
                Double maxSPDI = new Double(0);
                Date maxDay = endDate;

                DLL<DataPoint<StockData>> data = Shda.getStockHistory(CompanyName).getTimeSeries().getDataPointsInRange(startDate, endDate);

                if (!data.empty()) {
                    DLLComp<CompPair<Date, Double>> allDays = new DLLCompImp<CompPair<Date, Double>>();
                    data.findFirst();
                    while (!data.last()) {
                        double SDPI = data.retrieve().value.close - data.retrieve().value.open;
                        CompPair<Date, Double> val = new CompPair<Date, Double>(data.retrieve().date, SDPI);
                        allDays.insert(val);
                        data.findNext();
                    }
                    allDays.sort(true);
                    maxDay = allDays.getMax().first;
                    maxSPDI = new Double(allDays.getMax().second);
                }

                company_data = new Pair<String, Date>(CompanyName, maxDay);
                CompPair<Pair<String, Date>, Double> val = new CompPair<Pair<String, Date>, Double>(company_data, maxSPDI);
                dllcomp.insert(val);

                companies_name.findNext();
            }
            dllcomp.sort(true);
        }
        return dllcomp;
    }


}
