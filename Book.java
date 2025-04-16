public class Book{
    private int id;
    private String author;
    private String title;
    private int copies;

    //Constructor
    public Book(int id, String author, String title, int copies){
        this.id = id;
        this.author = author;
        this.title = title;
        this.copies = copies;
    }

    //Setters
    public void setId(int id){
        this.id = id;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAvailable(int copies){
        this.copies = copies;
    }

    //getters    
    public int getID(){
        return id;
    }

    public String getAuthor(){
        return author;
    }

    public String getTitle(){
        return title;
    }

    public int getCopiesAvailable(){
        return copies;
    }

    public boolean borrowBook(){
        if(copies > 0){
            copies --;
            return true;
        }
        return false;
    }

    public void returnBook(){
        copies++;
    }

    public boolean isAvailable(){
        return copies > 0;
    }

}