import java.util.Scanner; 
public class Test
{
    public static void main (String[] args)
    {
        float value;
        ReadString reader = new ReadString();
        ReadExpression expression = new ReadExpression();
        String input = "";
        Scanner scanner = new Scanner(System.in);
       
        while (true)
        {
            
            System.out.println("Enter a java floating point literal: (q to quit)");
            input = scanner.nextLine();
            if (input.equals("q"))
            {
                break;
            }
            value = expression.getValue(input);
            // System.out.println(value);

        }
        scanner.close();

    }
}
