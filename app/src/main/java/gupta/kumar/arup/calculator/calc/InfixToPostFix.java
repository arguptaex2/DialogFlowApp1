package gupta.kumar.arup.calculator.calc;

import java.util.Stack;

/**
 * Created by arups on 29-03-2018.
 */

public class InfixToPostFix {


    public static String removeSpaces(String inp){
        return inp.replace(" ","");
    }


    private static int Prec(char ch)
    {
        switch (ch)
        {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

    // The main method that converts given infix expression
    // to postfix expression.
    private static String infixToPostfix(String exp)
    {
        exp = removeSpaces(exp);
        // initializing empty String for result
        String result = new String("");

        // initializing empty stack
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i<exp.length(); ++i)
        {
            char c = exp.charAt(i);

            // If the scanned character is an operand, add it to output.
            if (Character.isLetterOrDigit(c))
                result += c;

                // If the scanned character is an '(', push it to the stack.
            else if (c == '(')
                stack.push(c);

                //  If the scanned character is an ')', pop and output from the stack
                // until an '(' is encountered.
            else if (c == ')')
            {
                while (!stack.isEmpty() && stack.peek() != '(')
                    result += stack.pop();

                if (!stack.isEmpty() && stack.peek() != '(')
                    return "Invalid Expression"; // invalid expression
                else
                    stack.pop();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
                    result += stack.pop();
                stack.push(c);
            }

        }

        // pop all the operators from the stack
        while (!stack.isEmpty())
            result += stack.pop();

        return result;
    }


    public static String calculateResult(String exp)
    {
        String infix = exp;
        exp = infixToPostfix(exp);
        //create a stack
        Stack<Integer> stack=new Stack<>();

        // Scan all characters one by one
        try{


        for(int i=0;i<exp.length();i++)
        {
            char c=exp.charAt(i);

            // If the scanned character is an operand (number here),
            // push it to the stack.
            if(Character.isDigit(c))
                stack.push(c - '0');

                //  If the scanned character is an operator, pop two
                // elements from stack apply the operator
            else
            {
                int val1 = stack.pop();
                int val2 = stack.pop();

                switch(c)
                {
                    case '+':
                        stack.push(val2+val1);
                        break;

                    case '-':
                        stack.push(val2- val1);
                        break;

                    case '^':
                        stack.push((int)Math.pow(val2,val1));
                        break;

                    case '*':
                        stack.push(val2*val1);
                        break;
                }
            }
        }
        return infix + " = " + stack.pop().toString();
        }catch (Exception e){
            return "I am unable to understand... try again!";
        }
    }
}
