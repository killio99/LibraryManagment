import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
    //Will be called when the book is returned
    public void returnBook(LocalDate returnDate){
        this.returnDate = returnDate;
    }

    //Checks if the book is overdue more than 14 days late 
    public boolean overdue(){
        if(returnDate != null){
            long daysBetween = ChronoUnit.DAYS.between(borrowDate, returnDate);
            return daysBetween > 14;
        }
        return false;
    }
}
