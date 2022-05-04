import java.util.EmptyStackException;
public class LinkedStack<T> implements StackInterface<T>{
	
	private Node topNode;
	public LinkedStack()
	{
		topNode = null;
	}
	
	@Override
	public void push(T newEntry) {
		topNode = new Node(newEntry, topNode);
	}

	@Override
	public T pop() {
		T top  = peek();
		topNode = topNode.getNextNode();
		return top;
	}

	@Override
	public T peek() {
		if (isEmpty()) {
			throw new EmptyStackException();
		}
		else return topNode.getData();
	}

	@Override
	public boolean isEmpty() {
		return topNode == null;
	}

	@Override
	public void clear() {
		topNode = null;	
	}
	
	//Can be copy and pasted into test class and used there. If using method from LinkedStack class, use LinkedStack.convertToPostFix(infix)
	public static String convertToPostfix(String infix) {
		LinkedStack<Character> operatorStack = new LinkedStack<Character>();
		String postfix = "";
		//Traverse through each char of infix String
		for (int i = 0; i < infix.length(); i++) {
			char nextCharacter = infix.charAt(i);
			//Add any alphabetical chars to postfix
			if (Character.isLetterOrDigit(nextCharacter)) {
				postfix += nextCharacter;
			}
			//Add ^ operator to operatorStack
			if (nextCharacter == '^') {
				operatorStack.push(nextCharacter);
			}	
			//Adds operator to operatorStack in order of precedence or adds operatorStack items to postfix
			if (nextCharacter == '-' || nextCharacter == '+' || nextCharacter == '*' || nextCharacter == '/' || nextCharacter == '%') {
				while (!operatorStack.isEmpty() && precedence(nextCharacter) <= precedence(operatorStack.peek())) {
					postfix += operatorStack.pop();
				}
				operatorStack.push(nextCharacter);
			}
			//Adds ( operator to operatorStack
			if (nextCharacter == '(') {
				operatorStack.push(nextCharacter);
			}
			//Adds contents of operatorStack to postfix 
			if (nextCharacter == ')') {
				char topOperator = operatorStack.pop();
				while (topOperator != '(') {
					postfix += topOperator;
					topOperator = operatorStack.pop();
				}
			}
		}
		//Adds remaining operatorStack items to postfix
		while (!operatorStack.isEmpty()) {
			postfix += operatorStack.pop();
		}
		//Returns postfix value converted from infix value
		return postfix;
	}
	
	//Assigns ints to operators to determine order of precedence
	private static int precedence(char character) {
		switch (character) {
			case '+' : case '-':
				return 0;
			case '*' : case '/':
				return 1;
		}
		return -1;
	}

	private class Node {
		private T data;
		private Node next;
		
		public Node(T dataPortion, Node nextNode)
		{
			data = dataPortion;
			next = nextNode;
		}
		
		public T getData()
		{
			return data;
		}
		
		public Node getNextNode() 
		{
			return next;
		}
	}
}
