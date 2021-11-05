import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        currentDir = Path.of("").toAbsolutePath().toString();
    }

    public String pwd(){
        return currentDir;
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

    public void echo(){
        StringBuilder output = new StringBuilder();
        for(int i=0; i<parser.getArgs().length; i++){
            output.append(parser.getArgs()[i]);
            output.append(" ");
        }
        System.out.println(output);
    }

    public void touch() {
        String fileName = parser.getArgs()[0];
        File file = new File(currentDir+"\\"+fileName);
        try
        {
            if (!file.exists()) {
                new FileOutputStream(file).close();
            }
        }
        catch (IOException e) {e.printStackTrace(); }
    }


    public void mkdir(){
        if(parser.getArgs().length <=1) {
            File dir = new File(currentDir +"\\"+ parser.getArgs()[0]);
            boolean isCreated = dir.mkdir();
            if (isCreated) {
                System.out.println("Directory created successfully!");
            } else {
                System.out.println("Error occurred while creating dictionary!");
            }
        }else if(parser.getArgs().length>1){

            String new_path = "\\";
            for (int i = 0; i < parser.getArgs().length; i++) {
                new_path += (parser.getArgs()[i] + "\\");
                File dir = new File(currentDir + new_path);
                boolean isCreated = dir.mkdir();
                if (isCreated) {
                    System.out.println("Directory created successfully!");
                } else if(!(isCreated) && !(dir.exists())){
                    System.out.println("Error: occurred while creating directory!");
                }else{
                    System.out.println("Error: directory already exists!");
                }
            }
        }
    }

    public void rmdir(){

    }

    public void chooseCommandAction(){
        switch (parser.getCommandName()){
            case "echo":
                echo();
                break;
            case "pwd":
                System.out.println(pwd());
                break;
            case "cd":
                //cd(parser.getArgs());
                break;
            case "ls":
                ls(currentDir);
                break;
            case "touch":
                touch();
                break;
            case "mkdir":
                mkdir();
                break;
            default:
                System.out.println("Command not available!");
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
