/*
 *  Name: Rodrigo Efraim
 *  Login: cs11fapl
 *  Date: November 17, 2016
 *  File: Snake.java
 *  Sources of Help: Java an Eventful Approach, objectdraw.docs, java API, lab
 *  
 *  This program contains the object that gets created.
 */
import objectdraw.*;
import java.awt.Color;
import java.util.*;
import java.awt.event.*;

/**
 * Represents the Snake object on the canvas.
 */
public class Snake extends ActiveObject 
{
    // Constants
    public static final int SEG_SIZE = 50;

    private DrawingCanvas copyCanvas;
    private Location copyLocation;
    private double copyX;
    private double copyY;
    private int copyDelay;
    private SnakeController copyController;


    private Location tempLocation;

    private Location lastLoc;
    private Location newLocation;   

    private Direction currentDirection = Direction.LEFT;

    private SnakeSegment head;

    private ArrayList<SnakeSegment> snakeSegmentList = new 
        ArrayList<SnakeSegment>();

    /**
     *  This is the constructor of the Snake class.
     *
     *  @param canvas being passed to place the snake segments on top of it.
     *  @param start the initial location of the snake head.
     *  @param delay the delay passed in the string arguments.
     *  @param controller need the refererence of controller to activate some
     *  of its functionalities.
     */
    public Snake( DrawingCanvas canvas, Location start, int delay,
                  SnakeController controller ) 
    {
      //Set the instance variables equal to the parameters being passed in the
      //constructor.
      copyCanvas = canvas;

      copyLocation = new Location(start.getX(), start.getY());

      copyX = start.getX();
      copyY = start.getY();

      copyDelay = delay;

      copyController = controller;

      //Calls the snakeSegment constructor. 
      head = new SnakeSegment(start, SEG_SIZE, Color.LIGHT_GRAY, canvas);

      //Starts the run() method.
      start();
    }

    /**
     *  Run method needed for active objects.  
     */
    public void run() 
    {
      // TODO Implement your movement logic here
      while(true)
      {
        eatBodyCheck(); 
        outOfBounds();     
        if(copyController.getPause())
        {
          move( SEG_SIZE * getDirection().x(), SEG_SIZE * getDirection().y());
          grow();
        }
        pause(copyDelay);
      }  
    }

    /**
     *  Class that creates individual pieces of the snake. These individual
     *  pieces are the segments of the snake. The head is a segment that is
     *  seperate from the body segment, which is contained in an ArrayList.
     */
    private class SnakeSegment
    {
      //A SnakeSegment is going to need a FilledRect to represent its visual
      //appearance on the canvas.
      private FilledRect individualRect;

      //Each SnakeSegment should also have a Location of where it is on the
      //grid.
      private Location location;
      
      /**
       *  The constructor of the SnakeSegment class needed to create
       *  SnakeSegment objects.
       *
       *  @param loc location of where the segment will be initially created.
       *  @param size passing the size of what the segment needs to be.
       *  @param color passing the color of what the segment needs to be.
       *  @param canvas passing the canvas of where the segment should be
       *  placed.
       */
      public SnakeSegment(Location loc, int size, Color col,  DrawingCanvas
          canvas)
      {
        individualRect = new FilledRect(loc, size, size, canvas);   
        individualRect.setColor(col);
        location = loc;
      }
      
      /**
       *  Used to return the location of the individual segment to just the
       *  Snake class.
       *
       *  @return location The current location of the segment. 
       */
      public Location getLocation()
      {
        return location;
      }

      /**
       *  Used for setting the location of the individual segments
       *
       *  @param loc parameter used to update the location of the SnakeSegment
       *  location object.
       */
      public void setLocation(Location loc)
      {
        location = loc;
      }

      /**
       *  Used to move the body segment of the snake
       *
       *  @param loc used to update the location of the body segments.
       */
      public synchronized void moveTo(Location loc)
      {
        location = new Location(loc);
        individualRect.moveTo(loc);
      }

      /**
       *  Used to move the head segment of the snake.
       *
       *  @param dx used to update the x coordinate of the snake head.
       *  @param dy used to update the y coordinate of the snake head.
       */
      public synchronized  void move(double dx, double dy)
      {
        //SWAP THESE
        
        location = new Location(location.getX() + dx, location.getY() + dy);
        individualRect.move(dx, dy);
        //location = new Location(location.getX() + dx, location.getY() + dy);
      }
      //TODO: make a public moveTo() method.
      //TODO: make a public move() method.
      //TODO: make a equals() method???
    }

    /**
     *  This move method is used to move both head and body segments. This is
     *  used in the run method of this class.
     *
     *  @param dx used to update the x coordinate of the snake head.
     *  @param dy used to update the y coordinate of the snake head.
     */
    private synchronized void move(double dx, double dy)
    {
      lastLoc = new Location(head.getLocation());

      //Location of head.
      head.move(dx, dy);

      //Process to update the location of the body parts.
      for(int count = 0; count < snakeSegmentList.size(); count++)
      {
        Location temp = new
            Location(snakeSegmentList.get(count).getLocation());
        snakeSegmentList.get(count).moveTo(lastLoc); 

        //Location of tail.
        lastLoc = temp;
      }
    }

    /**
     *  Increments the amount of segments in the snake. It is called
     *  everytime the snake eats food.
     */
    private synchronized void grow()
    {
      if(head.getLocation().equals(copyController.getFoodLocation()))
      {
        if(snakeSegmentList.size() == 0)
        {
          newLocation = lastLoc;// head.getLocation();
          snakeSegmentList.add(new SnakeSegment(newLocation, SEG_SIZE,
                Color.WHITE, copyCanvas)); 
        }
        else
        {
          Location temp = new Location(lastLoc);
          SnakeSegment newSeg = new SnakeSegment(temp, SEG_SIZE, Color.WHITE,
              copyCanvas);
          snakeSegmentList.add(newSeg);
        }

        //This method being called placeNewFood so that foodDraw knows where to
        //draw the food.  
        copyController.foodDraw();
      }
    }


    /**
     *  Used to reset the whole entire snake. Places the snake head back to the
     *  center and removes all the body segments.
     *
     *  @param loc location of where the snake head needs to be placed.
     */
    public void resetSnake(Location loc)
    {
      ////lastLoc = loc;
      head.moveTo(loc);
      for(int count = 0; count < snakeSegmentList.size(); count++)
      {
        snakeSegmentList.get(count).individualRect.removeFromCanvas();
      }

      //Is this needed?
      snakeSegmentList = new ArrayList<SnakeSegment>();
    }
    

    /**
     *  Sets the direction of the SnakeSegment object.
     *
     *  @param dir direction that sets the direction of the SnakeSegment
     *  object.
     */
    public void setDirection(Direction dir)
    {
      currentDirection = dir;
    }

    /**
     *  Gets the direction of the SnakeSegment object.
     *
     *  @return currentDirection, the current direction of SnakeSegment object.
     */
    public Direction getDirection()
    {
      return currentDirection; 
    }


    /**
     *  Method that prevents the newly randomized food from being placed on top
     *  of the snakebody.
     *
     *  @param randomX the randomized x coordinate
     *  @param randomY the randomized y coordinate
     *  @return valid true if coordinates are not placed on snake.
     */
    public boolean checkBody(int randomX, int randomY)
    {
      boolean valid = true;
      Location randomLocation = new Location(randomX, randomY);
     
     if(head.getLocation().equals(randomLocation))
     {
        valid = false;
     } 

      for(int count = 0; count < snakeSegmentList.size(); count++)
      {
        System.out.println(snakeSegmentList.get(count).getLocation());
        System.out.println(randomLocation);
        if(snakeSegmentList.get(count).getLocation().equals(randomLocation))
          valid = false;
      }
      return valid;
    }
 
   /**
    * Method that checks whether or not the snake head is out of bounds.
    */ 
    public void outOfBounds()
    {
      ///ORIGINAL ATTEMPT
      /*if(head.getLocation().getX() > copyController.getWindowWidth() || 
          head.getLocation().getX() < copyController.getXOffset())
      {
        System.out.println("OUT OF BOUNDS!");
        copyController.looser();
      } 
      if(head.getLocation().getY() > (copyController.getWindowHeight() - SEG_SIZE) || 
          head.getLocation().getY() < 0)
      {
        System.out.println("OUT OF BOUNDS!");
        copyController.looser();
      }*/

      if(head.getLocation().getX() + (currentDirection.x() * SEG_SIZE) 
          < copyController.getXOffset()     ||
         head.getLocation().getX() + (currentDirection.x() * SEG_SIZE) + 1 
         > copyController.getWindowWidth()  ||
         head.getLocation().getY() + (currentDirection.y() * SEG_SIZE) 
         < 0 ||
         head.getLocation().getY() + (currentDirection.y() * SEG_SIZE) + 1
         > copyController.getWindowHeight())
      {
        copyController.looser();
      }
    }

    /**
     *  Method that checks whether or not the head of the snake is placed on
     *  the snake body segment.
     */
    public void eatBodyCheck()
    {
      if(snakeSegmentList.size() != 0)
      {
        //Iterating through the ArrayList.
        for(int count = 0; count < snakeSegmentList.size(); count++)
        {
          /*if(head.getLocation().getX() + currentDirection.x() * SEG_SIZE ==
              snakeSegmentList.get(count).getLocation().getX() &&
             head.getLocation().getY() + currentDirection.y() * SEG_SIZE ==
               snakeSegmentList.get(count).getLocation().getY())
          {
            System.out.println("IT ATE ITSELF!!!");
          }*/

          if((head.getLocation().getX() == snakeSegmentList.get(count).getLocation().getX()) 
              &&
             (head.getLocation().getY() == snakeSegmentList.get(count).getLocation().getY()))
          {
            copyController.looser();
          }
          
          //else if(head.getLocation().getY() + currentDirection.y() * SEG_SIZE)
        }
      }
    }
} // end of class Snake
