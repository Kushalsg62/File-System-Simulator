import java.util.Stack;

//this is like when the user input says ../home/.smthg smthg, these dots mean something.
// 2 dots mean u are out of the certain directory u were in before.
//just like ur cmd lol.

public class pathResolver {
    public static String Normalize(String path){
        String[] stuff  = path.split(" ");
        Stack<String> stack = new Stack<>();

        for(String s:stuff){
            if(s.equals(".") || s.equals(null))continue;

            else if(s.equals("..")){
                if(!stack.isEmpty()){
                    stack.pop();
                }
                else{
                    return null;
                }
            }
            else{
                stack.push(s);
            }
        }

        return "/" + String.join("/", stack);
    }
}
