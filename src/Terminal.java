import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Terminal {
    Parser parser;
    String currentDir;

    public Terminal(){
        parser = new Parser();
        currentDir = pwd();
    }

    public String pwd(){
        return Path.of("").toAbsolutePath().toString();
    }

    public void cd(String[] args){
        String[] currentPath = currentDir.split(" ");
        if(currentDir.contains(args[0])){
            StringBuilder new_dir = new StringBuilder();
            for(int i=0; i<currentDir.length(); i++){
                if(!(currentDir.equals(args[0]))){

                }
            }
        }
    }

    public void ls(String path){
        File direct = new File(path);
        //list of files and folders in directory.
        File[] list = direct.listFiles();
        //loop through files and after it finishes its returns targeted file path.
        ArrayList fileNames = new ArrayList();

        for (File f : list) {
            fileNames.add(f.getName());
        }

        if(!(parser.getArgs()[0].contains("-r"))) {
            for(int j=0; j<fileNames.size(); j++){
                System.out.println(fileNames.get(j));
            }
        }else{
            Collections.reverse(fileNames);
            for(int j=0; j<fileNames.size(); j++){
                System.out.println(fileNames.get(j));
            }
        }
    }

    public void chooseCommandAction(){
        switch (parser.getCommandName()){
            case "pwd":
                System.out.println(pwd());
                break;
            case "cd":
                cd(parser.getArgs());
                break;
            case "ls":
                ls(currentDir);
            default:
                break;
        }
    }

    public String getCurrentDir(){
        return currentDir;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input="";
        Terminal terminal = new Terminal();

        while (!input.equals("exit")){
            System.out.print(terminal.getCurrentDir()+"$: ");
            input = scanner.nextLine();
            terminal.parser.parse(input);
            terminal.chooseCommandAction();
        }
    }
}
