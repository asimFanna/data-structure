package interfaces;

import java.util.Date;

// Inerface representing the stock history of a given company .
public interface StockHistory {
    // Returns the number of e l e m e n t s in the history .
    int size();

    // Returns true if the history is empty , false o t h e r w i s e .
    boolean empty();

    // Clears all data from the storage .
    void clear();

    // Returns company code .
    String getCompanyCode();

    // Sets company code
    void SetCompanyCode(String companyCode);

    // Returns stock history as a time series .
    TimeSeries<StockData> getTimeSeries();

    // R e t r i e v e s S t o c k D a t a for a s p e c i f i c date , or null if no data is found .
    StockData getStockData(Date date);

    // Adds a new S t o c k D a t a and returns true if the o p e r a t i o n is successful , false
// o t h e r w i s e .
    boolean addStockData(Date date, StockData stockData);

    // Remove the S t o c k D a t a of a given date , and returns true if the o p e r a t i o n is
// successful , false o t h e r w i s e .
    boolean removeStockData(Date date);
}
