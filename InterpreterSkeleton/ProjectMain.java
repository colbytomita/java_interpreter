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

public class ProjectMain extends Application
{
   static String filename;
   static ProjectCanvas theCanvas;

   public void start(Stage stage)
   {
      VBox root = new VBox();
      root.setAlignment(Pos.TOP_CENTER);
      
      theCanvas = new ProjectCanvas(filename);
      theCanvas.print();
      
      root.getChildren().add(theCanvas);
      
      theCanvas.begin(); //call begin
      theCanvas.run(); //call initial run. 
      
      //start the animation handler
      AnimationHandler ah = new AnimationHandler();
      ah.start();

      Scene scene = new Scene(root, 500, 500);
      stage.setScene(scene);
      stage.setTitle("CSC3094 Interpreter");
      stage.show();
   }
   public static void main(String[] args)
   {
      filename = args[0]; //if it errors here then you forgot a program arg :)
      launch(args);
   }
   
   //this is for the 
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         theCanvas.run();
      }
   }
}
