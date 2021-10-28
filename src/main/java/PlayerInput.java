import java.util.Scanner;

public class PlayerInput {

    int input;
    Scanner sc;

    public void playerInput(){
        this.input = getInput();
        this.sc = new Scanner(System.in);
    }

    public int getInput(){
        input = sc.nextInt();
        return input;
    }
}
