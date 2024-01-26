import javafx.application.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.util.*;
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

// ensure that labels can only be jumped to inside of a function, you cant jump to a a label outside of the function

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
               String[] funcLine = line.split(" ");

               ArrayList<ProjectCommand> function = new ArrayList<ProjectCommand>();
               ProjectCommand func = new ProjectCommand(ProjectCommand.Operator.FUNC);
               for (int c = 1; c < funcLine.length; c++) {
                  func.addArg(funcLine[c]);
               }
               function.add(func);
               while (!line.contains("endfunction")) {
                  line = fileReader.nextLine();
                  String[] splitLine = line.split(" ");
                  ProjectCommand command = new ProjectCommand();
                  switch (splitLine[0]) {
                     case "callfunction":
                        command = new ProjectCommand(ProjectCommand.Operator.CALLFUNC);
                        for (int i = 1; i < splitLine.length; i++) {
                           command.addArg(splitLine[i]);
                        }
                        break;
                     case "endfunction":
                        command = new ProjectCommand(ProjectCommand.Operator.ENDFUNC);
                        break;
                     case "=":
                        command = new ProjectCommand(ProjectCommand.Operator.EQUAL,
                              Arrays.copyOfRange(splitLine, 1, 3));
                        break;
                     case "+":
                        command = new ProjectCommand(ProjectCommand.Operator.ADD, Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "-":
                        command = new ProjectCommand(ProjectCommand.Operator.SUBTRACT,
                              Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "*":
                        command = new ProjectCommand(ProjectCommand.Operator.MULTIPLY,
                              Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "/":
                        command = new ProjectCommand(ProjectCommand.Operator.DIVIDE,
                              Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "%":
                        command = new ProjectCommand(ProjectCommand.Operator.MOD, Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "jump":
                        command = new ProjectCommand(ProjectCommand.Operator.JUMP, Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "jumpif":
                        command = new ProjectCommand(ProjectCommand.Operator.JUMPIF,
                              Arrays.copyOfRange(splitLine, 1, 3));
                        break;
                     case "print":
                        command = new ProjectCommand(ProjectCommand.Operator.PRINT,
                              Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "label":
                        command = new ProjectCommand(ProjectCommand.Operator.LABEL,
                              Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "time":
                        command = new ProjectCommand(ProjectCommand.Operator.TIME, Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "float":
                        command = new ProjectCommand(ProjectCommand.Operator.FLOAT,
                              Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "bool":
                        command = new ProjectCommand(ProjectCommand.Operator.BOOL, Arrays.copyOfRange(splitLine, 1, 2));
                        break;
                     case "&&":
                        command = new ProjectCommand(ProjectCommand.Operator.AND, Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "||":
                        command = new ProjectCommand(ProjectCommand.Operator.OR, Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "!":
                        command = new ProjectCommand(ProjectCommand.Operator.NOT, Arrays.copyOfRange(splitLine, 1, 3));
                        break;
                     case ">":
                        command = new ProjectCommand(ProjectCommand.Operator.GREATERTHAN,
                              Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case ">=":
                        command = new ProjectCommand(ProjectCommand.Operator.GREATEREQUAL,
                              Arrays.copyOfRange(splitLine, 1, 4));
                        break;
                     case "==":
                        command = new ProjectCommand(ProjectCommand.Operator.EQUALS,
                              Arrays.copyOfRange(splitLine, 1, 4));
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
   }

   // this method will only be called once, whenever the program is going to start,
   // right before the first frame.
   HashMap<String, ProjectRegister> globalMap = new HashMap<String, ProjectRegister>();

   public void beginStart() {
      for (int i = 0; i < globals.size(); i++) {
         // this creates the globals with the names of globals.get(i) as floats. In my
         // register class, the first arg is the name, the second arg is the value, and
         // the third arg is isBool
         globalMap.put(globals.get(i), new ProjectRegister(globals.get(i), 0, false));
      }
      setStartTime(); // this line of code has to be here.
   }

   // this is part 2
   // this method should run the program that you have stored somewhere else. Each
   // time run is called, it starts with a blank canvas and starts running from the
   // top of the main
   public void run(ProjectCanvas theCanvas) {
      // runs the program here.
      for (int i = 0; i < functions.size(); i++) {
         // functions.get(i) is the ith function
         // functions.get(i).get(0) is the first command in the function
         // functions.get(i).get(0).getArg(0) is the first argument, i.e., the function
         // name
         if (functions.get(i).get(0).getArg(0).equals("main")) {
            runPriv(functions.get(i), theCanvas, null);
         }
      }
   }

   public void runPriv(ArrayList<ProjectCommand> theFunction, ProjectCanvas theCanvas,
         ArrayList<ProjectRegister> params) {
      // this map is used to store this function's registers (this is for static
      // scoping, dynamic scoping would have passed in the regMap into the function).
      HashMap<String, ProjectRegister> regMap = null;

      for (int i = 0; i < theFunction.size(); i++) {
         if (theFunction.get(i).getOperator() != null)
            switch (theFunction.get(i).getOperator()) {
               case FUNC:
                  // note that we create a new regmap for each function.
                  regMap = new HashMap<String, ProjectRegister>();

                  // we then put the global regs into it.
                  for (int j = 0; j < globals.size(); j++) {
                     regMap.put(globals.get(j), globalMap.get(globals.get(j)));
                  }

                  // if the function has params, then you get all the registers from the params
                  // and stick them into the map too
                  if (params != null) {
                     for (int j = 0; j < params.size(); j++) {
                        // theFunction.get(i) is the ith command in the function, .getParamArg(j) gets
                        // the jth parameter for the function.,
                        regMap.put(theFunction.get(i).getArg(j), params.get(j));
                     }
                  }

                  break;
               case CALLFUNC:
                  // this draws the square
                  // theFunction.get(i) is the ith command in the function, .getArg(0) is the
                  // function name
                  if (theFunction.get(i).getArg(0).equals("drawsquare")) {
                     // pulls each of the 7 arguments from the function call to pass them into
                     // canvas's draw
                     float rc = regMap.get(theFunction.get(i).getArg(2)).getValue();
                     float gc = regMap.get(theFunction.get(i).getArg(3)).getValue();
                     float bc = regMap.get(theFunction.get(i).getArg(4)).getValue();
                     float xc = regMap.get(theFunction.get(i).getArg(5)).getValue();
                     float yc = regMap.get(theFunction.get(i).getArg(6)).getValue();
                     float xsc = regMap.get(theFunction.get(i).getArg(7)).getValue();
                     float ysc = regMap.get(theFunction.get(i).getArg(8)).getValue();

                     theCanvas.drawRect(rc, gc, bc, xc, yc, xsc, ysc);
                     break;
                  } else {

                     // gets the amount of paramters for the function you are about to call
                     // recall thefunction.get(i) is the functioncall's command
                     int amount = theFunction.get(i).getNumOfArgs();

                     // create an arraylist to be used to hold the param registers
                     ArrayList<ProjectRegister> registers = new ArrayList<ProjectRegister>();

                     // find all the registers you want to use as params and add them
                     for (int j = 0; j < amount; j++) {
                        registers.add(regMap.get(theFunction.get(i).getArg(j)));
                     }

                     // this block of code finds the function you want to call within the list of
                     // functions
                     for (int k = 0; k < functions.size(); k++) {
                        // theFunction.get(i).getArg(0) is the name of the function you want to call
                        // functions.get(k) is the kth function
                        // functions.get(k).get(0) is the command "function" for the kth function
                        // functions.get(k).get(0).getArg(0) is the name of the kth function
                        if (functions.get(k).get(0).getArg(0).equals(theFunction.get(i).getArg(0))) {
                           // calls run on itself. Oh, I bet this works with recursive function calls :).
                           runPriv(functions.get(k), theCanvas, registers);
                        }
                     }
                  }
                  break;
               case EQUAL:
                  if ((theFunction.get(i).getArg(1)).equals("TRUE")) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                     regMap.get(theFunction.get(i).getArg(0)).setIsBool(true);
                  } else if ((theFunction.get(i).getArg(1))
                        .equals("FALSE")) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                     regMap.get(theFunction.get(i).getArg(0)).setIsBool(true);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0))
                           .setValue((Float.parseFloat(theFunction.get(i).getArg(1))));
                  }
                  break;
               case ADD:
                  regMap.get(theFunction.get(i).getArg(0)).setValue(
                        regMap.get(theFunction.get(i).getArg(1)).getValue()
                              + regMap.get(theFunction.get(i).getArg(2)).getValue());
                  break;
               case SUBTRACT:
                  regMap.get(theFunction.get(i).getArg(0)).setValue(
                        regMap.get(theFunction.get(i).getArg(1)).getValue()
                              - regMap.get(theFunction.get(i).getArg(2)).getValue());
                  break;
               case MULTIPLY:
                  regMap.get(theFunction.get(i).getArg(0)).setValue(
                        regMap.get(theFunction.get(i).getArg(1)).getValue()
                              * regMap.get(theFunction.get(i).getArg(2)).getValue());
                  break;
               case DIVIDE:
                  regMap.get(theFunction.get(i).getArg(0)).setValue(
                        regMap.get(theFunction.get(i).getArg(1)).getValue()
                              / regMap.get(theFunction.get(i).getArg(2)).getValue());
                  break;
               case MOD:
                  regMap.get(theFunction.get(i).getArg(0)).setValue(
                        regMap.get(theFunction.get(i).getArg(1)).getValue()
                              % regMap.get(theFunction.get(i).getArg(2)).getValue());
                  break;
               case JUMP:
                  i = ((int) regMap.get(theFunction.get(i).getArg(0)).getValue() - 1);
                  break;
               case JUMPIF:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() == 1) {
                     i = ((int) regMap.get(theFunction.get(i).getArg(0)).getValue() - 1);
                  }
                  break;
               case EQUALS:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() == regMap.get(theFunction.get(i).getArg(2))
                        .getValue()) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  }
                  break;
               case AND:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() == 1
                        && regMap.get(theFunction.get(i).getArg(2)).getValue() == 1) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  }
                  break;
               case OR:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() == 1
                        || regMap.get(theFunction.get(i).getArg(2)).getValue() == 1) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  }
                  break;
               case GREATERTHAN:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() > regMap.get(theFunction.get(i).getArg(2))
                        .getValue()) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  }
                  break;
               case GREATEREQUAL:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() >= regMap.get(theFunction.get(i).getArg(2))
                        .getValue()) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  }
                  break;
               case BOOL:
                  regMap.put(theFunction.get(i).getArg(0), new ProjectRegister(theFunction.get(i).getArg(0), 0, true));
                  break;
               case FLOAT:
                  regMap.put(theFunction.get(i).getArg(0), new ProjectRegister(theFunction.get(i).getArg(0), 0, false));
                  break;
               case LABEL:
                  regMap.put(theFunction.get(i).getArg(0), new ProjectRegister(theFunction.get(i).getArg(0), i, false));
               case NOT:
                  if (regMap.get(theFunction.get(i).getArg(1)).getValue() == 1) {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(0);
                  } else {
                     regMap.get(theFunction.get(i).getArg(0)).setValue(1);
                  }
                  break;
               case PRINT:
                  if (regMap.get(theFunction.get(i).getArg(0)).getIsBool()) {
                     if (regMap.get(theFunction.get(i).getArg(0)).getValue() == 1) {
                        System.out.println("TRUE");
                     } else {
                        System.out.println("FALSE");
                     }
                  } else {
                     System.out.println(regMap.get(theFunction.get(i).getArg(0)).getValue());
                  }
                  break;
               case TIME:
                  regMap.get(theFunction.get(i).getArg(0))
                        .setValue((float) getTime());
                  break;
               case ENDFUNC:
                  break;
            }
      }

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

   private Operator theOp;
   private ArrayList<String> args;

   public ProjectCommand() {
      this.theOp = null;
      this.args = new ArrayList<String>();
   }

   public ProjectCommand(Operator op, String[] args) { // this is for operators
      this.theOp = op;
      this.args = new ArrayList<>(Arrays.asList(args));
   }

   public ProjectCommand(Operator op) { // this is for function stuff
      this.theOp = op;
      this.args = new ArrayList<String>();
   }

   public void printOperator() {
      String printString = "";
      switch (this.theOp) {
         case null:
            break;
         case CALLFUNC:
            printString = "callfunction";
            break;
         case EQUAL:
            printString = "=";
            break;
         case ADD:
            printString = "+";
            break;
         case SUBTRACT:
            printString = "-";
            break;
         case MULTIPLY:
            printString = "*";
            break;
         case DIVIDE:
            printString = "/";
            break;
         case MOD:
            printString = "%";
            break;
         case JUMP:
            printString = "jump";
            break;
         case JUMPIF:
            printString = "jumpif";
            break;
         case EQUALS:
            printString = "==";
            break;
         case AND:
            printString = "&&";
            break;
         case OR:
            printString = "||";
            break;
         case GREATERTHAN:
            printString = ">";
            break;
         case GREATEREQUAL:
            printString = ">=";
            break;
         case FUNC:
            printString = "function";
            break;
         case ENDFUNC:
            printString = "endfunction";
            break;
         case FLOAT:
            printString = "float";
            break;
         case BOOL:
            printString = "bool";
            break;
         case LABEL:
            printString = "label";
            break;
         case PRINT:
            printString = "print";
            break;
         case TIME:
            printString = "time";
            break;
         case NOT:
            printString = "!";
            break;
      }
      for (String arg : this.args) {
         printString += " " + arg;
      }
      System.out.println(printString);
   }

   public void setOperator(Operator opIn) {
      this.theOp = opIn;
   }

   public Operator getOperator() {
      return this.theOp;
   }

   public void addArg(String argIn) {
      this.args.add(argIn);
   }

   public String getArg(int input) {
      return this.args.get(input);
   }

   public int getNumOfArgs() {
      return this.args.size();
   }
}

class ProjectRegister {
   private String name;
   private float value;
   private boolean isBool;

   public ProjectRegister(String name, float value, boolean isBool) {
      this.name = name;
      this.value = value;
      this.isBool = isBool;
   }

   public void setName(String nameIn) {
      this.name = nameIn;
   }

   public String getName() {
      return this.name;
   }

   public void setValue(float valueIn) {
      this.value = valueIn;
   }

   public float getValue() {
      return this.value;
   }

   public void setIsBool(boolean isBoolIn) {
      this.isBool = isBoolIn;
   }

   public boolean getIsBool() {
      return this.isBool;
   }
}