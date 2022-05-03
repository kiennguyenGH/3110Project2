import java.lang.Math;

// Set of states
public class ReadString {

    private enum states {
        q0, // Start state
        q1,
        q2,
        q3,
        q4,
        q5,
        q6,
        q7,
        q8,
        q9,
        q10,
        q11,
        q12 // Fail state
    }

    /*Traverses through string inputed
    Uses enum to traverse through states
    If statements function as transitions
    If character read is valid, jump to next state
    If character is invalid, jump to fail state
    If enum is in fail state, break out of the for loop early
    When for loop is finished, uses if statement to check if state is in an accept state
    If final state is an accept state, calculate the final float and return the float
    If String is not accepted, return -1
    Function calculates accepted float by saving whole numbers and exponents to separate strings, and adds decimal to float variable
    Then whole number is added to float variable, and then multiplied by the value of the exponent
    */
    public float GetFloat(String input)
    {
        int decimalCount = -1;
        float finalNumber = 0;
        float exponentValue = 0;
        String integer = "";
        String exponent = "";
        states dfa = states.q0;
        boolean negativeExponent = false;
        for (int i = 0; i < input.length(); i++)
        {
            switch (dfa)
            {
                case q0:
                    if (input.charAt(i) == '.')
                    {
                        dfa = states.q7;
                    }
                    else if (input.charAt(i) == '1' ||
                            input.charAt(i) == '2' ||
                            input.charAt(i) == '3' ||
                            input.charAt(i) == '4' ||
                            input.charAt(i) == '5' ||
                            input.charAt(i) == '6' ||
                            input.charAt(i) == '7' ||
                            input.charAt(i) == '8' ||
                            input.charAt(i) == '9' ||
                            input.charAt(i) == '0')
                    {
                        dfa = states.q1;
                        integer += input.charAt(i);
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q1:
                    if (input.charAt(i) == '.')
                    {
                        dfa = states.q2;
                    }
                    else if (input.charAt(i) == '1' ||
                            input.charAt(i) == '2' ||
                            input.charAt(i) == '3' ||
                            input.charAt(i) == '4' ||
                            input.charAt(i) == '5' ||
                            input.charAt(i) == '6' ||
                            input.charAt(i) == '7' ||
                            input.charAt(i) == '8' ||
                            input.charAt(i) == '9' ||
                            input.charAt(i) == '0')
                    {
                        dfa = states.q1;
                        integer += input.charAt(i);
                    }
                    else if (input.charAt(i) == 'e' ||
                             input.charAt(i) == 'E')
                    {
                        dfa = states.q4;
                    }
                    else if (input.charAt(i) == 'd' ||
                            input.charAt(i) == 'D' ||
                            input.charAt(i) == 'f' ||
                            input.charAt(i) == 'F')
                    {
                        dfa = states.q6;
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q8;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q2:
                    if (input.charAt(i) == '1' ||
                    input.charAt(i) == '2' ||
                    input.charAt(i) == '3' ||
                    input.charAt(i) == '4' ||
                    input.charAt(i) == '5' ||
                    input.charAt(i) == '6' ||
                    input.charAt(i) == '7' ||
                    input.charAt(i) == '8' ||
                    input.charAt(i) == '9' ||
                    input.charAt(i) == '0')
                    {
                        dfa = states.q3;
                        finalNumber += Character.getNumericValue(input.charAt(i)) * Math.pow(10, decimalCount);
                        decimalCount--;
                    }
                    else if (input.charAt(i) == 'e' ||
                             input.charAt(i) == 'E')
                    {
                        dfa = states.q4;
                    }
                    else if (input.charAt(i) == 'd' ||
                            input.charAt(i) == 'D' ||
                            input.charAt(i) == 'f' ||
                            input.charAt(i) == 'F')
                    {
                        dfa = states.q6;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q3:
                    if (input.charAt(i) == '1' ||
                    input.charAt(i) == '2' ||
                    input.charAt(i) == '3' ||
                    input.charAt(i) == '4' ||
                    input.charAt(i) == '5' ||
                    input.charAt(i) == '6' ||
                    input.charAt(i) == '7' ||
                    input.charAt(i) == '8' ||
                    input.charAt(i) == '9' ||
                    input.charAt(i) == '0')
                    {
                        dfa = states.q3;
                        finalNumber += Character.getNumericValue(input.charAt(i)) * Math.pow(10, decimalCount);
                        decimalCount--;
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q9;
                    }
                    else if (input.charAt(i) == 'e' ||
                             input.charAt(i) == 'E')
                    {
                        dfa = states.q4;
                    }
                    else if (input.charAt(i) == 'd' ||
                            input.charAt(i) == 'D' ||
                            input.charAt(i) == 'f' ||
                            input.charAt(i) == 'F')
                    {
                        dfa = states.q6;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q4:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q5;
                        exponent += input.charAt(i);
                        
                    }
                    else if (input.charAt(i) == '-')
                    {
                        dfa = states.q11;
                        negativeExponent = true;
                    }
                    else if (input.charAt(i) == '+')
                    {
                        dfa = states.q11;
                    }
                    else 
                    {
                        dfa = states.q12;
                    }
                    break;
                case q5:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q5;
                        exponent += input.charAt(i);
                        
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q10;
                    }
                    else if (input.charAt(i) == 'd' ||
                            input.charAt(i) == 'D' ||
                            input.charAt(i) == 'f' ||
                            input.charAt(i) == 'F')
                    {
                        dfa = states.q6;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q6:
                    dfa = states.q12;
                    break;
                case q7:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q3;
                        finalNumber += Character.getNumericValue(input.charAt(i)) * Math.pow(10, decimalCount);
                        decimalCount--;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q8:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q1;
                        integer += input.charAt(i);
                        
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q8;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q9:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q3;
                        finalNumber += Character.getNumericValue(input.charAt(i)) * Math.pow(10, decimalCount);
                        decimalCount--;
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q9;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                    break;
                case q10:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q5;
                        // exponent += input.charAt(i);
                        
                    }
                    else if (input.charAt(i) == '_')
                    {
                        dfa = states.q10;
                    }
                    else
                    {
                        dfa = states.q12;
                    }
                case q11:
                    if (input.charAt(i) == '1' ||
                        input.charAt(i) == '2' ||
                        input.charAt(i) == '3' ||
                        input.charAt(i) == '4' ||
                        input.charAt(i) == '5' ||
                        input.charAt(i) == '6' ||
                        input.charAt(i) == '7' ||
                        input.charAt(i) == '8' ||
                        input.charAt(i) == '9' ||
                        input.charAt(i) == '0')
                    {
                        dfa = states.q5;
                        exponent += input.charAt(i);
                    }
                    break;
                default:
                    dfa = states.q12;
                    break;
            }
            // If in fail state, break out of loop early
            if (dfa == states.q12)
            {
                break;
            }
        }
        // Check if dfa is in an accept state
        if (dfa == states.q2 ||
            dfa == states.q3 ||
            dfa == states.q5 ||
            dfa == states.q6)
        {
            int count = 0;
            // Add whole number to finalNumber
            for (int i = integer.length()-1; i >= 0; i--)
            {
                float integerValue = (float)Math.pow(10, count) * Character.getNumericValue(integer.charAt(i));
                count++;
                finalNumber += integerValue;
            }
            count = 0;
            System.out.println(exponent);
            // Add exponent to finalNumber
            for (int i = exponent.length() - 1; i >= 0; i--)
            {
                float exponentVal = (float)Math.pow(10,count) * Character.getNumericValue(exponent.charAt(i));
                count++;
                exponentValue += exponentVal;
            }
            if (negativeExponent == true)
            {
                exponentValue *= -1;
            }

            finalNumber *= Math.pow(10, exponentValue);
        }
        // String not accepted
        else
        {
            return -1;
        }
        return finalNumber;
    }
}
    