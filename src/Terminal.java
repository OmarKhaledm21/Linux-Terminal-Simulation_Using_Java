import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;


class Parser {
    String commandName;
    String[] args;

    public Parser(){
        commandName="";
        args=null;
    }

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

    public void cd(){
        String path = "";
        if (parser.getArgs()[0].equals("cd")){
            path="C:\\Users\\"+System.getProperty("user.name");
            currentDir=path;
        }else if(parser.getArgs()[0].equals("~")){

        }else if (parser.getArgs()[0].equals("..")){
            String[] temp_path = currentDir.split("\\\\");

            path += temp_path[0];
            if(temp_path.length==2 || temp_path.length==1){
                path+="\\";
            }

            for(int i=1; i<temp_path.length-1; i++){
                path += "\\";
                path += temp_path[i];
            }
            currentDir = path;

        }else {
            path=parser.getArgs()[0];
            File file = new File(path);
            if (file.isDirectory()){
                currentDir=path;
                //System.out.println(path);
            }else{
                System.out.println("path not found");
            }
        }
    }

    public boolean double_redirection_checker(){
        return (Arrays.toString(parser.getArgs()).contains(">>"));
    }

    public boolean redirection_checker(){
        return (Arrays.toString(parser.getArgs()).contains(">"));
    }

    public void redirect(){
        try{
            boolean drc = double_redirection_checker();
            FileWriter file=null;
            try {
                file = new FileWriter((parser.getArgs()[parser.getArgs().length-1]),drc);
                for(int i=0; i<parser.getArgs().length-2; i++){
                    String temp = parser.getArgs()[i];
                    file.write("\n"+temp);
                }
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch(Exception e){
            System.out.println("Error occurred");
        }
    }



    public void ls(String path){
        try{
        File direct = new File(path);
        File[] list = direct.listFiles();
        ArrayList fileNames = new ArrayList();

        for (File f : list) {
            fileNames.add(f.getName());
        }

        if(redirection_checker() || double_redirection_checker()){
            boolean drc = double_redirection_checker();
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(parser.getArgs()[parser.getArgs().length-1],drc);
                for(int i=0; i<fileNames.size(); i++){
                    String temp = fileNames.get(i).toString();
                    fileWriter.write("\n"+temp);
                }
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }else {
            if (!(parser.getArgs()[0].contains("-r"))) {
                for (int j = 0; j < fileNames.size(); j++) {
                    System.out.println(fileNames.get(j));
                }
            } else {
                Collections.reverse(fileNames);
                for (int j = 0; j < fileNames.size(); j++) {
                    System.out.println(fileNames.get(j));
                }
            }
        }


    }
        catch (Exception e ){
            System.out.println("Error occurred");
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
        StringBuilder path = new StringBuilder();
        for(int i=0; i<parser.getArgs().length; i++){
            path.append(parser.getArgs()[i]);
        }

        File file = new File(currentDir+"\\"+path.toString());
        try
        {
            if (!file.exists()) {
                new FileOutputStream(file).close();
            }else {
                System.out.println("File Exists");
            }
        }
        catch (IOException e) {
            System.out.println("Error occurred");
        }
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
        try {
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
        catch (Exception e ){
            System.out.println("Error occurred");
        }
    }

    public void cat() {
        try {


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
                System.out.println("Error occurred");
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
                System.out.println("Error occurred");
            }
        }
        }
        catch (Exception e){
            System.out.println("Error occurred");
        }

    }


    public void rm(){
        File file = new File(parser.getArgs()[0]);
        if(file.delete()){
            System.out.println("File: "+file.getName()+" is deleted");
        }else{
            System.out.println("Error File: "+file.getName()+" is not deleted");

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
                cd();
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
            case "rm":
                rm();
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
