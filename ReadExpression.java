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
    
    //Traverses through String and separates operators and floats into postfix array using PDA enum
    //Pushes to stack when open parentheses or operator (with empty) is read
    //Pops stack when closed parentheses or operator (with nonempty stack) is read
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
                    if (input.charAt(i) == '.' || Character.isLetterOrDigit(input.charAt(i)))
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
                        if (reader.GetFloat(currentFloat) == -1)
                        {
                            pda = states.fail;
                        }
                        else
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

                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals("") && reader.GetFloat(currentFloat) != -1)
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
                        if (reader.GetFloat(currentFloat) == -1)
                        {
                            pda = states.fail;
                        }
                        else
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

                    }
                    else if (input.charAt(i) == ')')
                    {
                        if (leftParentheses <= 0)
                        {
                            pda = states.fail;
                        }
                        else
                        {

                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[numElements] = currentFloat;
                                currentFloat = "";
                                numElements++;
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
                        }
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals("") && reader.GetFloat(currentFloat) != -1)
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
                        if (reader.GetFloat(currentFloat) == -1)
                        {
                            pda = states.fail;
                        }
                        else
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
                    }
                    else if (input.charAt(i) == ')')
                    {
                        if (leftParentheses <= 0)
                        {
                            pda = states.fail;
                        }
                        else
                        {

                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[numElements] = currentFloat;
                                currentFloat = "";
                                numElements++;
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
                        }
                    }
                    else if (input.charAt(i) == ' ')
                    {
                        if (!currentFloat.equals("") && reader.GetFloat(currentFloat) != -1)
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
                        if (leftParentheses > 0)
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
                    else if (input.charAt(i) == ')')
                    {
                        if (leftParentheses > 0)
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
                        if (leftParentheses > 0)
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
        if (currentFloat.length() > 0 && pda != states.fail)
        {
            if (reader.GetFloat(currentFloat) == -1)
            {
                System.out.println("Invalid expression");
                pda = states.fail;
            }
            else
            {
                postfix[numElements] = currentFloat;
                currentFloat = "";
                numElements++;
            }
        }

        // Check if final state is accept state
        if ((pda == states.q1 || pda == states.q4 || pda == states.q6 || pda == states.q7 || pda == states.q8) && leftParentheses == 0 && pda != states.fail)
        {
            System.out.println("Valid expression");
        }
        else if (pda == states.fail)
        {
            return -1;
        }
        else
        {
            System.out.println("Invalid expression");
            pda = states.fail;
        }

        if (pda == states.fail)
        {
            return -1;
        }

        //Push remaining operators to stack
        while (!postfixStack.isEmpty()) {
            String add = "";
            add += postfixStack.pop();
            postfix[numElements] = add;
            numElements++;
        }
        //Print out postfix (for testing)
        printPostfix(postfix, numElements);

        //Evaluate postfix
        LinkedStack<Float> floatStack = new LinkedStack<Float>();
        for (int i = 0; i < numElements; i++)
        {
            if (postfix[i].equals("+"))
            {
                float one = floatStack.pop();
                float two = floatStack.pop();
                float result = one + two;
                floatStack.push(result);
            }
            else if (postfix[i].equals("-"))
            {
                float two = floatStack.pop();
                float one = floatStack.pop();
                float result = one - two;
                floatStack.push(result);
            }
            else if (postfix[i].equals("/"))
            {
                float two = floatStack.pop();
                float one = floatStack.pop();
                float result = one/two;
                floatStack.push(result);
            }
            else if (postfix[i].equals("*"))
            {
                float one = floatStack.pop();
                float two = floatStack.pop();
                float result = one * two;
                floatStack.push(result);
            }
            else
            {
                floatStack.push(reader.GetFloat(postfix[i]));
            }
        }
        return floatStack.peek();
    }

    //Traverses through postfix array and prints out each element
    private static void printPostfix(String[] input, int length)
    {
        System.out.print("Postfix: ");
        for (int i = 0; i < length; i++)
        {
            System.out.print(input[i] + ", ");
        }
        System.out.println();
    }

    //Method for checking precedence of operators of postfix
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