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
        fail // fail
    }

    public float getValue(String input)
    {
        states pda = states.q0;
        ReadString reader = new ReadString();
        String currentFloat = "";
        String[] postfix = new String[100];
        int postfixLength = 0;
        int leftParentheses = 0;
        int rightParentheses = 0;
        for (int i = 0; i < input.length(); i++)
        {
            switch (pda)
            {
                //Start state
                case q0:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        pda = states.q1;
                        currentFloat += input.charAt(i);
                    }
                    else if (input.charAt(i) == '(')
                    {
                        pda = states.q3;
                        leftParentheses++;
                        //push
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                // float (without parentheses)
                case q1:
                    if (input.charAt(i) == 'e'
                        || input.charAt(i) == 'E'
                        || input.charAt(i) == 'f'
                        || input.charAt(i) == 'd'
                        || input.charAt(i) == 'D'
                        || input.charAt(i) == '_'
                        || input.charAt(i) == '.'
                        || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q1;
                    }
                    else if (input.charAt(i) == '+')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q1;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q2;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '-')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q1;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q2;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '*')
                    {
                        pda = states.q2;
                        //push
                    }
                    else if (input.charAt(i) == '/')
                    {
                        pda = states.q2;
                        //push
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q2:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        pda = states.q1;
                        currentFloat += input.charAt(i);
                    }
                    else if (input.charAt(i) == '(')
                    {
                        pda = states.q3;
                        leftParentheses++;
                        //push
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q3:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        pda = states.q4;
                        currentFloat += input.charAt(i);
                    }
                    else if (input.charAt(i) == '(')
                    {
                        pda = states.q3;
                        leftParentheses++;
                        //push
                    }
                    break;
                case q4:
                    if (input.charAt(i) == 'e'
                    || input.charAt(i) == 'E'
                    || input.charAt(i) == 'f'
                    || input.charAt(i) == 'd'
                    || input.charAt(i) == 'D'
                    || input.charAt(i) == '_'
                    || input.charAt(i) == '.'
                    || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q4;
                    }
                    else if (input.charAt(i) == '+')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q4;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q5;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '-')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q4;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q5;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '*')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == '/')
                    {
                        pda = states.q5;
                        //push
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q5:
                    if (input.charAt(i) == '.' || Character.isDigit(input.charAt(i)))
                    {
                        pda = states.q6;
                        currentFloat += input.charAt(i);
                    }
                    else if (input.charAt(i) == '(')
                    {
                        pda = states.q3;
                        leftParentheses++;
                        //push
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q6:
                    if (input.charAt(i) == 'e'
                    || input.charAt(i) == 'E'
                    || input.charAt(i) == 'f'
                    || input.charAt(i) == 'd'
                    || input.charAt(i) == 'D'
                    || input.charAt(i) == '_'
                    || input.charAt(i) == '.'
                    || Character.isDigit(input.charAt(i)))
                    {
                        currentFloat += input.charAt(i);
                        pda = states.q6;
                    }
                    else if (input.charAt(i) == '+')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q6;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q5;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '-')
                    {
                        if (currentFloat.charAt(currentFloat.length()) == 'e' 
                        || currentFloat.charAt(currentFloat.length()) == 'E')
                        {
                            currentFloat += input.charAt(i);
                            pda = states.q6;
                        }
                        else
                        {
                            if (reader.GetFloat(currentFloat) == -1)
                            {
                                pda = states.fail;
                            }
                            else
                            {
                                postfix[postfixLength] = currentFloat;
                                currentFloat = "";
                                postfixLength++;
                                pda = states.q5;
                                //push
                            }
                        }
                    }
                    else if (input.charAt(i) == '*')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == '/')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == ')')
                    {
                        if (leftParentheses <= 0)
                        {
                            pda = states.fail;
                        }
                        rightParentheses++;
                        pda = states.q7;
                        //pop
                    }
                    else
                    {
                        pda = states.fail;
                    }
                    break;
                case q7:
                    if (input.charAt(i) == '+')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == '-')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == '*')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == '/')
                    {
                        pda = states.q5;
                        //push
                    }
                    else if (input.charAt(i) == ')')
                    {
                        if (leftParentheses <= 0)
                        {
                            pda = states.fail;
                        }
                        rightParentheses++;
                        pda = states.q7;
                        //pop
                    }
                    break;
                default:
                    pda = states.fail;
            }
            if (pda == states.fail)
            {
                break;
            }
        }

        for (int i = 0; i < postfixLength; i++)
        {
            //evaulate postfix (with array)
        }
        return -1;
    }
}