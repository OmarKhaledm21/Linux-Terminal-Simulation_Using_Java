import java.util.Arrays;

class Parser {
    String commandName;
    String[] args;

    public Parser(){
        commandName="";
        args=null;
    }
    //This method will divide the input into commandName and args
    //where "input" is the string command entered by the user
    public boolean parse(String input){
        commandName = input.split(" ")[0];
        input = input.substring(input.indexOf(" ")+1);
        args = input.split(" ");
        return true;
    }

    public String getCommandName(){
        return commandName.trim();
    }

    public String[] getArgs(){
        return args;
    }
}
