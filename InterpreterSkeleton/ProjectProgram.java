import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;

// import ProjectCommand.Operator; 
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.animation.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.image.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;
import java.io.*;

/**
 * globalfloat $t
 * 
 * function main params endparams
 * float $x
 * float $q
 * float $c
 * float $ten
 * = $ten 10
 * = $x 5
 * = $q .1
 * time $c
 * $c $c $q
 * $c $c $q
 * % $c $c $ten
 * 
 * callfunction drawsquare args $c $q $q $x $x $c $c endargs
 * endfunction
 * 
 * 
 * function testop params $p1 $p2 endparams
 * jumpif l1 $b1
 * jump l2
 * label test
 * print $b1
 * print $i1
 * bool $b1
 * float $i1
 * time $f1
 * = $b1 $b2
 * = $b1 TRUE
 * = $i1 100
 * && $b1 $b2 $b3
 * || $b1 $b2 $b3
 * ! $b1 $b2
 * > $b1 $i1 $i2
 * >= $b1 $i1 $i2
 * == $b1 $i1 $i2
 * + $i1 $i2 $i3
 * - $i1 $i2 $i3
 * $i1 $i2 $i3
 * / $i1 $i2 $i3
 * callfunction drawsquare args $i1 $i2 $i3 $i4 $i5 $i6 $i7 endargs
 * endfunction
 */

public class ProjectProgram {
   ArrayList<ArrayList<ProjectCommand>> functions = new ArrayList<ArrayList<ProjectCommand>>();
   ArrayList<String> globals = new ArrayList<String>();
   // create a map/hashmap of the registers, can I do this for the functions as
   // well?

   // this method should read in the program file from the file. You may assume the
   // file is in a good format
   public ProjectProgram(String filename) {
      try {
         File myFile = new File(filename);
         Scanner fileReader = new Scanner(myFile);
         while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            if (line.contains("globalfloat")) { // will need to add functionality to this later
               // String[] splitLine = line.split(" ");
               // for(int i = 1; i < splitLine.length; i++) {
               // globals.add(splitLine[i]);
               // }
               globals.add(line);
            } else if (line.contains("function")) {
               ArrayList<ProjectCommand> function = new ArrayList<ProjectCommand>();
               ProjectCommand func = new ProjectCommand();
               func.theOp = ProjectCommand.Operator.FUNC;
               String[] funcLine = line.split(" ");
               func.funcName = funcLine[1];
               for (int c = 3; c < funcLine.length; c++) {
                  if (!funcLine[c].equals("endparams"))
                     func.params.add(funcLine[c]);
               }
               function.add(func);
               while (!line.contains("endfunction")) {
                  line = fileReader.nextLine();
                  String[] splitLine = line.split(" ");
                  ProjectCommand command = new ProjectCommand();
                  switch (splitLine[0]) {
                     case "callfunction":
                        command.theOp = ProjectCommand.Operator.CALLFUNC;
                        command.funcName = splitLine[1];
                        command.params = new ArrayList<String>();
                        for (int i = 3; i < splitLine.length; i++) {
                           if (!splitLine[i].equals("endargs"))
                              command.params.add(splitLine[i]);
                        }
                        break;
                     case "endfunction":
                        command.theOp = ProjectCommand.Operator.ENDFUNC;
                        break;
                     case "=":
                        command.theOp = ProjectCommand.Operator.EQUAL;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        break;
                     case "+":
                        command.theOp = ProjectCommand.Operator.ADD;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "-":
                        command.theOp = ProjectCommand.Operator.SUBTRACT;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "*":
                        command.theOp = ProjectCommand.Operator.MULTIPLY;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "/":
                        command.theOp = ProjectCommand.Operator.DIVIDE;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "%":
                        command.theOp = ProjectCommand.Operator.MOD;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "jump":
                        command.theOp = ProjectCommand.Operator.JUMP;
                        command.arg1 = splitLine[1];
                        break;
                     case "jumpif":
                        command.theOp = ProjectCommand.Operator.JUMPIF;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        break;
                     case "print":
                        command.theOp = ProjectCommand.Operator.PRINT;
                        command.arg1 = splitLine[1];
                        break;
                     case "label":
                        command.theOp = ProjectCommand.Operator.LABEL;
                        command.arg1 = splitLine[1];
                        break;
                     case "time":
                        command.theOp = ProjectCommand.Operator.TIME;
                        command.arg1 = splitLine[1];
                        break;
                     case "float":
                        command.theOp = ProjectCommand.Operator.FLOAT;
                        command.arg1 = splitLine[1];
                        break;
                     case "bool":
                        command.theOp = ProjectCommand.Operator.BOOL;
                        command.arg1 = splitLine[1];
                        break;
                     case "&&":
                        command.theOp = ProjectCommand.Operator.AND;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "||":
                        command.theOp = ProjectCommand.Operator.OR;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "!":
                        command.theOp = ProjectCommand.Operator.NOT;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        break;
                     case ">":
                        command.theOp = ProjectCommand.Operator.GREATERTHAN;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case ">=":
                        command.theOp = ProjectCommand.Operator.GREATEREQUAL;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                     case "==":
                        command.theOp = ProjectCommand.Operator.EQUALS;
                        command.arg1 = splitLine[1];
                        command.arg2 = splitLine[2];
                        command.arg3 = splitLine[3];
                        break;
                  }
                  function.add(command);
               }
               functions.add(function);
            }
         }
         fileReader.close();
      } catch (IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }

   // this method should print out the program in a readable format. The reason you
   // want to print out a program is to verify you read it in right
   public void print() {
      for (String var : globals) {
         System.out.println(var);
      }
      for (ArrayList<ProjectCommand> function : functions) {
         for (ProjectCommand command : function) {
            command.printOperator();
         }
      }
      // print out the program here.
      // for (Command c : commands) {
      // System.out.println(c);
      // }
   }

   // this method will only be called once, whenever the program is going to start,
   // right before the first frame.
   public void beginStart() {

      setStartTime(); // this line of code has to be here.
   }

   // this is part 2
   // this method should run the program that you have stored somewhere else. Each
   // time run is called, it starts with a blank canvas and starts running from the
   // top of the main
   public void run(ProjectCanvas theCanvas) {
      // runs the program here.
   }

   // these are for the time command in the bytecode.
   double startTime;

   public void setStartTime() {
      startTime = System.currentTimeMillis();
   }

   // this method returns the current time as a float.
   public double getTime() {
      return System.currentTimeMillis() - startTime;
   }
}

class ProjectCommand {
   public enum Operator {
      CALLFUNC, EQUALS, ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD, JUMP, JUMPIF, EQUAL, AND, OR, GREATERTHAN, GREATEREQUAL,
      FUNC, ENDFUNC, FLOAT, BOOL, LABEL, PRINT, TIME, NOT
   };

   Operator theOp = null;
   ArrayList<String> params = new ArrayList<String>();
   String funcName;

   String arg1, arg2, arg3, arg4, arg5, arg6;

   public void printOperator() {
      // add everything to a string and then print that using
      // System.out.println(string)
      String printString = "";
      switch (this.theOp) {
         case null:
            break;
         case CALLFUNC:
            printString += "callfunction " + funcName + " args ";
            for (String param : params) {
               printString += param + " ";
            }
            printString += "endargs";
            break;
         case EQUAL:
            printString += "= " + arg1 + " " + arg2;
            break;
         case ADD:
            printString += "+ " + arg1 + " " + arg2 + " " + arg3;
            break;
         case SUBTRACT:
            printString += "- " + arg1 + " " + arg2 + " " + arg3;
            break;
         case MULTIPLY:
            printString += "* " + arg1 + " " + arg2 + " " + arg3;
            break;
         case DIVIDE:
            printString += "/ " + arg1 + " " + arg2 + " " + arg3;
            break;
         case MOD:
            printString += "% " + arg1 + " " + arg2 + " " + arg3;
            break;
         case JUMP:
            printString += "jump " + arg1;
            break;
         case JUMPIF:
            printString += "jumpif " + arg1 + " " + arg2;
            break;
         case EQUALS:
            printString += "== " + arg1 + " " + arg2 + " " + arg3;
            break;
         case AND:
            printString += "&& " + arg1 + " " + arg2 + " " + arg3;
            break;
         case OR:
            printString += "|| " + arg1 + " " + arg2 + " " + arg3;
            break;
         case GREATERTHAN:
            printString += "> " + arg1 + " " + arg2 + " " + arg3;
            break;
         case GREATEREQUAL:
            printString += ">= " + arg1 + " " + arg2 + " " + arg3;
            break;
         case FUNC:
            printString += "function " + funcName + " params ";
            for (String param : params) {
               printString += param + " ";
            }
            printString += "endparams";
            break;
         case ENDFUNC:
            printString += "endfunction";
            break;
         case FLOAT:
            printString += "float " + arg1;
            break;
         case BOOL:
            printString += "bool " + arg1;
            break;
         case LABEL:
            printString += "label " + arg1;
            break;
         case PRINT:
            printString += "print " + arg1;
            break;
         case TIME:
            printString += "time " + arg1;
            break;
         case NOT:
            printString += "! " + arg1 + " " + arg2;
            break;
      }
      System.out.println(printString);
   }
}
