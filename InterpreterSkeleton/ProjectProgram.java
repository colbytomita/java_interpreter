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

/** 
globalfloat $t

function main params endparams
float $x
float $q
float $c
float $ten
= $ten 10
= $x 5
= $q .1
time $c
* $c $c $q
* $c $c $q
% $c $c $ten

callfunction drawsquare args $c $q $q $x $x $c $c endargs
endfunction


function testop params $p1 $p2 endparams
jumpif l1 $b1
jump l2
label test
print $b1
print $i1
bool $b1
float $i1
time $f1
= $b1 $b2
= $b1 TRUE 
= $i1 100
&& $b1 $b2 $b3
|| $b1 $b2 $b3
! $b1 $b2
> $b1 $i1 $i2
>= $b1 $i1 $i2
== $b1 $i1 $i2
+ $i1 $i2 $i3
- $i1 $i2 $i3
* $i1 $i2 $i3
/ $i1 $i2 $i3
callfunction drawsquare args $i1 $i2 $i3 $i4 $i5 $i6 $i7 endargs
endfunction
 */


public class ProjectProgram {
   Arraylist<ArrayList<ProjectCommand>> functions = new ArrayList<ArrayList<ProjectCommand>>();
   ArrayList<String> globals = new ArrayList<String>();

   //this method should read in the program file from the file. You may assume the file is in a good format
   public ProjectProgram(String filename) {
      try {
         File myFile = new File(filename);
         Scanner fileReader = new Scanner(myFile);
         while(fileRead.hasNextLine()) {
            String line = fileReader.nextLine();
            if(line.contains("globalfloat")) {
               String[] splitLine = line.split(" ");
               for(int i = 1; i < splitLine.length; i++) {
                  globals.add(splitLine[i]);
               }
            } else if(line.contains("function")) {
               ArrayList<ProjectCommand> function = new ArrayList<ProjectCommand>();
               while(!line.contains("endfunction")) {
                  line = fileReader.nextLine();
                  String[] splitLine = line.split(" ");
                  ProjectCommand command = new ProjectCommand();
                  if(splitLine[0].equals("callfunction")) {
                     command.theOp = Operator.CALLFUNC;
                     command.params = new ArrayList<String>();
                     for(int i = 1; i < splitLine.length; i++) {
                        command.params.add(splitLine[i]);
                     }
                  } else if(splitLine[0].equals("=")) {
                     command.theOp = Operator.EQUALS;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                  } else if(splitLine[0].equals("+")) {
                     command.theOp = Operator.ADD;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                     command.arg3 = splitLine[3];
                  } else if(splitLine[0].equals("-")) {
                     command.theOp = Operator.SUBTRACT;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                     command.arg3 = splitLine[3];
                  } else if(splitLine[0].equals("*")) {
                     command.theOp = Operator.MULTIPLY;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                     command.arg3 = splitLine[3];
                  } else if(splitLine[0].equals("/")) {
                     command.theOp = Operator.DIVIDE;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                     command.arg3 = splitLine[3];
                  } else if(splitLine[0].equals("%")) {
                     command.theOp = Operator.MOD;
                     command.arg1 = splitLine[1];
                     command.arg2 = splitLine[2];
                     command.arg3 = splitLine[3];
                  } else if(splitLine[0].equals("jump")) {
                     command.theOp = Operator.JUMP;
                     command.arg1
                  }
               }
            }
         }
         fileReader.close();
      } catch(IOException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
   
   //this method should print out the program in a readable format. The reason you want to print out a program is to verify you read it in right
   public void print() {
      //print out the program here.
      for (Command c : commands) {
         System.out.println(c);
      }
   }
   
   
   //this method will only be called once, whenever the program is going to start, right before the first frame.
   public void beginStart()
   {

      setStartTime(); //this line of code has to be here.
   }

   
   //this is part 2
   //this method should run the program that you have stored somewhere else. Each time run is called, it starts with a blank canvas and starts running from the top of the main
   public void run(ProjectCanvas theCanvas)
   {
      //runs the program here.   
   }
   
   

   
   //these are for the time command in the bytecode.
   double startTime;
   public void setStartTime()
   {
      startTime = System.currentTimeMillis();
   }
   
   //this method returns the current time as a float.
   public double getTime()
   {
      return System.currentTimeMillis() - startTime;
   }
}

public class ProjectCommand(){
   public enum Operator{CALLFUNC, EQUALS, ADD, SUBTRACT, MULTIPLY, DIVIDE, MOD, JUMP, JUMPIF, EQUAL, AND, OR, GREATERTHAN, LESSTHAN, GREATEREQUAL, LESSEQUAL, FUNC, ENDFUNC, FLOAT};
   Operator theOp;
   ArrayList<String> params;

   String arg1, arg2, arg3, arg4, arg5, arg6;
}