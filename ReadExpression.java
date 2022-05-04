public class ReadExpression {

    public enum states
    {
        q0, // start
        q1, // float (initial)
        q2, // operator
        q3, // (
        q4, // float (with parentheses)
        q5, // operator (with parentheses)
        q6, // float (with closed parentheses)
        q7, // )
        q8,
        q9,
        q10,
        fail // fail
    }

    public float getValue(String input)
    {
        LinkedStack<Character> postfixStack = new LinkedStack<Character>();
        String[] postfix = new String[100];
        int numElements = 0;
        int leftParentheses = 0;
        states pda = states.q0;
        ReadString reader = new ReadString();
        String currentFloat = "";
        for (int i = 0; i < input.length(); i++)
        {
            switch (pda)
            {
                case q0:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q1;
                    }
                    else if (input.charAt(i) == '(')
                    {
                        postfixStack.push(input.charAt(i));
                        leftParentheses++;
                        pda = states.q3;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q1:
                    if (input.charAt(i) == '.' || Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_')
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q1;
                    }
                    else if ((input.charAt(i) == '+' || input.charAt(i) == '-') && (currentFloat.charAt(currentFloat.length()-1) == 'e' || currentFloat.charAt(currentFloat.length()-1) == 'E'))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q1;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        postfix[numElements] = currentFloat;
                        currentFloat = "";
                        numElements++;
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q2;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals(""))
                        {
                            postfix[numElements] = currentFloat;
                            currentFloat = "";
                            numElements++;
                        }
                        pda = states.q8;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q2:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q1;
                    }
                    else if (input.charAt(i) == '(')
                    {
                        postfixStack.push(input.charAt(i));
                        leftParentheses++;
                        pda = states.q3;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        pda = states.q2;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q3:
                    if (input.charAt(i) == '(')
                    {
                        postfixStack.push(input.charAt(i));
                        leftParentheses++;
                        pda = states.q3;
                    }
                    else if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q4;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        pda = states.q3;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q4:
                    if (input.charAt(i) == '.' || Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_')
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q4;
                    }
                    else if ((input.charAt(i) == '+' || input.charAt(i) == '-') && (currentFloat.charAt(currentFloat.length()-1) == 'e' || currentFloat.charAt(currentFloat.length()-1) == 'E'))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q4;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        postfix[numElements] = currentFloat;
                        currentFloat = "";
                        numElements++;
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q5;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals(""))
                        {
                            postfix[numElements] = currentFloat;
                            currentFloat = "";
                            numElements++;
                        }
                        pda = states.q9;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q5:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q6;
                    }
                    else if (input.charAt(i) == '(')
                    {
                        postfixStack.push(input.charAt(i));
                        leftParentheses++;
                        pda = states.q3;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        pda = states.q5;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q6:
                    if (input.charAt(i) == '.' || Character.isLetterOrDigit(input.charAt(i)) || input.charAt(i) == '_')
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q6;
                    }
                    else if ((input.charAt(i) == '+' || input.charAt(i) == '-') && (currentFloat.charAt(currentFloat.length()-1) == 'e' || currentFloat.charAt(currentFloat.length()-1) == 'E'))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q6;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        postfix[numElements] = currentFloat;
                        currentFloat = "";
                        numElements++;
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q5;
                    }
                    else if (input.charAt(i) == ')')
                    {
                        if (currentFloat.length() > 0)
                        {
                            postfix[numElements] = currentFloat;
                            currentFloat = "";
                            numElements++;
                        }
                        char topOperator = postfixStack.pop();
                        while (topOperator != '(')
                        {
                            String add = "";
                            add += topOperator;
                            postfix[numElements] = add;
                            numElements++;
                            topOperator = postfixStack.pop();
                        }
                        leftParentheses--;
                        pda = states.q7;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals(""))
                        {
                            postfix[numElements] = currentFloat;
                            currentFloat = "";
                            numElements++;
                        }
                        pda = states.q10;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q7:
                    if (input.charAt(i) == ')')
                    {
                        if (currentFloat.length() > 0)
                        {
                            postfix[numElements] = currentFloat;
                            currentFloat = "";
                            numElements++;
                        }
                        char topOperator = postfixStack.pop();
                        while (topOperator != '(')
                        {
                            String add = "";
                            add += topOperator;
                            postfix[numElements] = add;
                            numElements++;
                            topOperator = postfixStack.pop();
                        }
                        leftParentheses--;
                        pda = states.q7;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q5;
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        pda = states.q7;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q8:
                    if (input.charAt(i) == ' ')
                    {
                        pda = states.q8;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q2;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q9:
                    if (input.charAt(i) == ' ')
                    {
                        pda = states.q9;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q5;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q10:
                    if (input.charAt(i) == ' ')
                    {
                        pda = states.q10;
                    }
                    else if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/')
                    {
                        while (!postfixStack.isEmpty() && precedence(input.charAt(i)) <= precedence(postfixStack.peek())) {
                            String add = "";
                            add += postfixStack.pop();
                            postfix[numElements] = add;
                            numElements++;
                        }
                        postfixStack.push(input.charAt(i));
                        pda = states.q5;
                    }
                    else if (input.charAt(i) == ')')
                    {
                        char topOperator = postfixStack.pop();
                        while (topOperator != '(')
                        {
                            String add = "";
                            add += topOperator;
                            postfix[numElements] = add;
                            numElements++;
                            topOperator = postfixStack.pop();
                        }
                        leftParentheses--;
                        pda = states.q7;
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                default:
                    break;
            }
            if (pda == states.fail)
            {
                System.out.println("Invalid expression");
                break;
            }
        }
        
        //Check if final state is accept state
        if (leftParentheses != 0 && input.charAt(input.length()-1) != ')' && Character.isLetterOrDigit(input.charAt(input.length()-1)))
        {
            System.out.println("Invalid expression");
            pda = states.fail;
        }
        if (pda == states.fail)
        {
            return -1;
        }
        if (currentFloat.length() > 0)
        {
            postfix[numElements] = currentFloat;
            currentFloat = "";
            numElements++;
        }
        while (!postfixStack.isEmpty()) {
            String add = "";
            add += postfixStack.pop();
            postfix[numElements] = add;
            numElements++;
        }
        printPostfix(postfix, numElements);
        System.out.println(currentFloat);
        return -1;

    }

    private static void printPostfix(String[] input, int length)
    {
        System.out.print("Postfix: ");
        for (int i = 0; i < length; i++)
        {
            System.out.print(input[i] + " ");
        }
        System.out.println();
    }

    private static int precedence(char character) {
		switch (character) {
			case '+' : case '-':
				return 0;
			case '*' : case '/':
				return 1;
		}
		return -1;
	}
}