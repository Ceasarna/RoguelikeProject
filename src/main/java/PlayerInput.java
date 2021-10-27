import java.util.Scanner;

public class PlayerInput {

    int input;

    public void playerInput(){
    }

    public void setInput(){
        Scanner sc = new Scanner(System.in);
        input = sc.nextInt();
    }

    public int getInput(){
        return input;
    }
}
