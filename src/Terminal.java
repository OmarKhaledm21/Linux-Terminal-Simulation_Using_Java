import java.io.*;
import java.nio.file.*;
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

    public boolean redirection_checker(){
        return (Arrays.toString(parser.getArgs()).contains(">"));
    }

    public void redirect(){
        FileWriter file=null;
        try {
            file = new FileWriter((parser.getArgs()[parser.getArgs().length-1]),true);
            for(int i=0; i<parser.getArgs().length-2; i++){
                String temp = parser.getArgs()[i];
                file.write("\n"+temp);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void echo(){
        if(redirection_checker()){
            redirect();
        }else{
            StringBuilder output = new StringBuilder();
            for(int i=0; i<parser.getArgs().length; i++){
                output.append(parser.getArgs()[i]);
                output.append(" ");
            }
            System.out.println(output);
        }
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
                System.out.println("Error occurred while creating directory");
            }
        }else if(parser.getArgs().length>1){

            String new_path = "\\";
            for (int i = 0; i < parser.getArgs().length; i++) {
                new_path += (parser.getArgs()[i] + "\\");
                File dir = new File(currentDir + new_path);
                boolean isCreated = dir.mkdir();
                if (isCreated) {
                    System.out.println("Directory created successfully!");
                } else if(!(dir.exists())){
                    System.out.println("Error: occurred while creating directory!");
                }else{
                    System.out.println("Error: directory already exists!");
                }
            }
        }
    }

    public void rmdir(){
        if(parser.getArgs().length <=1) {
            File dir = new File(currentDir +"\\"+ parser.getArgs()[0]);
            boolean isDeleted = dir.delete();
            if (isDeleted) {
                System.out.println("Directory deleted successfully!");
            } else {
                System.out.println("Error occurred while deleting directory!");
            }
        }else if(parser.getArgs().length>1){

            String new_path = "\\";
            for (int i = 0; i < parser.getArgs().length; i++) {
                new_path += (parser.getArgs()[i] + "\\");
            }
            File dir = new File(currentDir+new_path);
            boolean isDeleted = dir.delete();
            if (isDeleted) {
                System.out.println("Directory deleted successfully!");
            } else {
                System.out.println("Error occurred while deleting directory!");
            }
        }

    }

    public  void cp() {
        String fname1 = parser.getArgs()[0];
        String fname2 = parser.getArgs()[1];
        File fin= new File(fname1);
        FileWriter fout = null;
        try {
            fout=new FileWriter(fname2,true);
        }catch (Exception e ){
            e.printStackTrace();
        }
        try {
            Scanner reader = new Scanner(fin);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                fout.write(line);
            }
            fout.close();
            reader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void cat() {
        if (parser.getArgs().length>1) {
            String fname1 = parser.getArgs()[0];
            String fname2 = parser.getArgs()[1];
            File file1 = new File(fname1);
            File file2 = new File(fname2);
            try {
                Scanner reader = new Scanner(file1);
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    System.out.println(line);
                }
                reader.close();
                reader = new Scanner(file2);
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    System.out.println(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String fname1 = parser.getArgs()[0];
            File file1 = new File(fname1);
            try {
                Scanner reader = new Scanner(file1);
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();
                    System.out.println(line);
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
            case"rmdir":
                rmdir();
                break;
            case"cp":
                cp();
                break;
            case"cat":
                cat();
                break;
            case "exit":
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
