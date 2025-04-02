public class Book{
    private int id;
    private String author;
    private String title;
    private int num;

    //Constructor
    public Book(int id, String author, String title, int num){
        this.id = id;
        this.author = author;
        this.title = title;
        this.num = num;
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

    public void setAvailable(int num){
        this.num = num;
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

    public boolean borrowBook(){
        if(num > 0){
            num --;
            return true;
        }
        return false;
    }

    public void returnBook(){
        num++;
    }

}