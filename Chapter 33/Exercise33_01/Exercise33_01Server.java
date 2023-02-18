// Exercise31_01Server.java: The server can communicate with
// multiple clients concurrently using the multiple threads
import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();
  private ObjectOutputStream outputToFile;
  private ObjectInputStream inputFromClient;

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread(() -> {
    	try {
    		ServerSocket serverSocket = new ServerSocket(8000);
    		Platform.runLater(() ->
    		ta.appendText("Server started at " + new Date() + '\n'));
    		while (true) {
    		Socket socket = serverSocket.accept();
    		ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
    		Object object = inputFromClient.readObject();
    		outputToFile.writeObject(object);
    		}
    	} catch (IOException | ClassNotFoundException ex) {
    		ex.printStackTrace();
    	}
    	finally {
    		try {
    			inputFromClient.close();
    			outputToFile.close();
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    	}
    });
    
  }
    
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
	  new Exercise33_01Server();
    launch(args);
  }
}
