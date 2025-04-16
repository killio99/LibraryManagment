import java.util.List;

public class SetupRunner {
    public static void main(String[] args) {
        // Step 1: Make sure tables exist
        SchemaSetup.setup(); // This runs CREATE TABLE IF NOT EXISTS

        // Step 2: Create and save a book
        Book hobbit = new Book(1, "J.R.R. Tolkien", "The Hobbit", 3);
        hobbit.saveToDB();

        // Step 3: Load all books
        List<Book> books = Book.getAllBooks();
        System.out.println("📚 All Books in Database:");
        for (Book b : books) {
            System.out.println(b.getID() + ": " + b.getTitle() + " by " + b.getAuthor() +
                               " (" + b.getCopiesAvailable() + " copies)");
        }

        // Step 4: Borrow and update DB
        if (hobbit.borrowBook()) {
            hobbit.updateCopiesInDB();
            System.out.println("✅ Borrowed a copy of The Hobbit.");
        } else {
            System.out.println("❌ No copies left to borrow.");
        }

        // Step 5: Return and update DB
        hobbit.returnBook();
        hobbit.updateCopiesInDB();
        System.out.println("🔁 Returned a copy of The Hobbit.");
    }
}
