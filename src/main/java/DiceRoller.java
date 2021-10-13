import java.util.Random;

public class DiceRoller {

    Random random = new Random();

    public int roll1d100(){
        return random.nextInt(100) + 1;
    }

    public int rollWithinRange(int low, int high){
        return random.nextInt((high - low) + 1) + low;
    }

}
