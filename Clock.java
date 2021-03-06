import java.util.Calendar; 
import java.util.GregorianCalendar;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.event.*;
import javafx.application.Application;

public class Clock extends Application {
	@Override	// Override the start method in the application class
	public void start(Stage primaryStage) {
		ClockPane clock = new ClockPane();	// Create a clock
		
		//add function stop & start
		Button stop = new Button("Stop");
		stop.setPrefSize(120,50);
		Button start = new Button("Start");
		start.setPrefSize(120,50);
		
		HBox h1 = new HBox();
		BorderPane pane = new BorderPane();
		h1.getChildren().addAll(start, stop);
		pane.setCenter(clock);
		pane.setBottom(h1);
		
		// Create a handler for animation
		EventHandler<ActionEvent> eventHandler = e -> {
			clock.setCurrentTime();	// Set a new clock time
		};
		
		// Create an animation for a running clock
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();	// Start animation
		
		stop.setOnAction(e -> {
			animation.stop();
		});	
		start.setOnAction(e -> {
			animation.play();
		});	
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 220, 250);
		primaryStage.setResizable(false);
		primaryStage.setTitle("ClockAnimation");	// Set the stage title
		primaryStage.setScene(scene);	// Place the scene in the stage
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}

class ClockPane extends Pane {
  //Data fields
  private int hour;
  private int minute;
  private int second;
  
  //No-arguement constructor
  public ClockPane() {
    //Set current time
    setCurrentTime();
  }

  //Constructor
  public ClockPane(int hour, int minute, int second) {
    this.hour = hour;
    this.minute = minute;
    this.second = second;
  }

  //Accessor method to return hour
  public int getHour() {
    return hour;
  }

  //Mutator method to set hour
  public void setHour(int hour) {
    this.hour = hour;
    paintClock();
  }

  //Accessor method to return minute
  public int getMinute() {
    return minute;
  }

  //Mutator method to set minute and repaint clock
  public void setMinute(int minute) {
    this.minute = minute;
    paintClock();
  }

  //Accessor method to return second
  public int getSecond() {
    return second;
  }

  //Mutator method to set second and repaint clock
  public void setSecond(int second) {
    this.second = second;
    paintClock();
  }
  
  //Mutator method to set current time and repaint clock
  public void setCurrentTime() {
    // Construct a calendar for the current date and time
    Calendar calendar = new GregorianCalendar();

    // Set current hour, minute and second
    this.hour = calendar.get(Calendar.HOUR_OF_DAY);
    this.minute = calendar.get(Calendar.MINUTE);
    this.second = calendar.get(Calendar.SECOND);
    
    paintClock(); 
  }
  
  //Method paintClocke to paint clock
  private void paintClock() {    
    double clockRadius = 
      Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
    double centerX = getWidth() / 2;
    double centerY = getHeight() / 2;

    //Draw circle
    Circle circle = new Circle(centerX, centerY, clockRadius);
    circle.setFill(Color.WHITE);
    circle.setStroke(Color.BLACK);
    Text t1 = new Text(centerX - 5, centerY - clockRadius + 12, "12");
    Text t2 = new Text(centerX - clockRadius + 3, centerY + 5, "9");
    Text t3 = new Text(centerX + clockRadius - 10, centerY + 3, "3");
    Text t4 = new Text(centerX - 3, centerY + clockRadius - 3, "6");
    
    //Draw second
    double sLength = clockRadius * 0.8;
    double secondX = centerX + sLength * 
      Math.sin(second * (2 * Math.PI / 60));
    double secondY = centerY - sLength * 
      Math.cos(second * (2 * Math.PI / 60));
    Line sLine = new Line(centerX, centerY, secondX, secondY);
    sLine.setStroke(Color.RED);

    //Draw minute
    double mLength = clockRadius * 0.65;
    double xMinute = centerX + mLength * 
      Math.sin(minute * (2 * Math.PI / 60));
    double minuteY = centerY - mLength * 
      Math.cos(minute * (2 * Math.PI / 60));
    Line mLine = new Line(centerX, centerY, xMinute, minuteY);
    mLine.setStroke(Color.BLUE);
    
    //Draw hour
    double hLength = clockRadius * 0.5;
    double hourX = centerX + hLength * 
      Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
    double hourY = centerY - hLength *
      Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
    Line hLine = new Line(centerX, centerY, hourX, hourY);
    hLine.setStroke(Color.GREEN);
    
    getChildren().clear();  
    getChildren().addAll(circle, t1, t2, t3, t4, sLine, mLine, hLine);
  }
}
