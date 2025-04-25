public class Library{
    private static final int MAX_CHECKOUTS = 3;
    private static final double finesPerDay = 0.50;

    //mass entry of books and users in construcotr
    public Library(){
        
    };

    public static int getMaxCheckouts(){ 
        return MAX_CHECKOUTS;
    }

    public static double getFinesPerDay(){
        return finesPerDay;
    }

}