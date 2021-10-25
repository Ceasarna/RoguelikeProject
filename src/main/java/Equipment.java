//Förälder till alla sorters utrustning

public abstract class Equipment {

    //All utrustning har ett namn. Ändras aldrig
    private final String name;

    //Konstruktor
    public Equipment(String name){
        this.name = name;
    }

    //Get-metoden för namnet
    public String getName() {
        return name;
    }

}
