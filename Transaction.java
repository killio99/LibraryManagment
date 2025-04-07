import java.time.LocalDate;

public class Transaction {
    private int userId;
    private int bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    
    public Transaction(int userId, int bookId, LocalDate borrowDate){
        this.userId = userId;
        this.bookId = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = null;
    }
}
