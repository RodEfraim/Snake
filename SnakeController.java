/*
 *  Name: Rodrigo Efraim
 *  Login: cs11fapl
 *  Date: November 17, 2016
 *  File: SnakeController.java
 *  Sources of Help: Java an Eventful Approach, objectdraw.docs, java API, lab
 *  
 *  This is the program that controls the snake. All the GUI's are placed here.
 *  This program allows user interaction with mouse and keyboard since it
 *  implements ActionListener and KeyListener.
 */
import Acme.*;
import objectdraw.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Controller for the Snake game.
 */
public class SnakeController extends WindowController implements
    ActionListener, KeyListener {

    private static final int FOOD_SIZE = 50;

    private static final int MIN_DIMENSION = 200;
    private static final int MAX_DIMENSION = 800;
    private static final int MIN_DELAY = 50;
    private static final int MAX_DELAY = 600;

    private static final int HEAD_SIZE = 50;

    private static final int HALFER = 2;

    private static final int Y_PADDING = 50;

    //private static int countFood = 0;
  
    private int xOffset;

    //GUI  
    private JLabel lblGameOver;
    private JLabel lblWin;      
    private JLabel lblPause;    

    private JLabel lblScore;
    private JLabel lblHighScore;
    private JButton btnNewGame;

    //Game Descriptions
    private int score = 0;
    private int highScore = 0;
    private int maxScorePossible = 0;

    //When paused equals false, the snake can't do any movement.
    private boolean paused = false;
    //Starting up the game, allows any key to activate the game.
    private boolean startUp = true;

    //Snake Components
    private double snakeX;
    private double snakeY;
    private Snake snakePlayer;
    private Location snakeBeginLocation;

    //Food Components
    private FilledOval food;
    private Location foodLocation;

    //NEW TESTING VARIABLES!!!
    private boolean resetFoodCount = false;//or false???
    //private int resetFoodCount = 0;

    //Boundaries Component
    private FramedRect boundary;
    private int windowWidth, windowHeight;

    //delay variable is applied for the run method of the Snake class.
    private int delay;

    /**
     * Constructor. Parses the command line arguments,
     * and initializes the controller state.
     *
     * @param args the string arguments to be parsed
     */
    public SnakeController( String[] args ) 
    {
        // TODO Implement this
    }

    /**
     * Window setup. Sets up variables and all GUI components.
     */
    public void begin() 
    {
      // TODO Setup your GUI here

      JPanel pnlNorth = new JPanel();
      JPanel pnlSouth = new JPanel();

      lblScore = new JLabel("Score: " + score);
      lblHighScore = new JLabel("High Score: " + highScore); 

      lblGameOver = new JLabel("GAME OVER");
      lblGameOver.setVisible(false);
      lblGameOver.setForeground(Color.RED);

      lblWin = new JLabel("YOU WIN!!!");
      lblWin.setVisible(false);   
      lblWin.setForeground(Color.BLUE);

      lblPause = new JLabel("PAUSED");
      lblPause.setVisible(false);
      lblPause.setForeground(Color.RED);

      btnNewGame = new JButton("New Game");
      btnNewGame.addActionListener(this);

      pnlNorth.add(lblScore);
      //TODO: What it the proper way to space out the labels???
      pnlNorth.add(Box.createRigidArea(new Dimension(300, 0)));
      pnlNorth.add(lblHighScore);

      pnlSouth.add(btnNewGame);
      pnlSouth.add(lblGameOver);
      pnlSouth.add(lblWin);
      pnlSouth.add(lblPause);

      Container contentPane = getContentPane();
      contentPane.add(pnlNorth, BorderLayout.NORTH);
      contentPane.add(pnlSouth, BorderLayout.SOUTH);
      contentPane.validate();

      System.out.println("canvas.getWidth(): " + canvas.getWidth());
      System.out.println("width input: " +  windowWidth);

      //Should the xOffset be put in the main???
      xOffset = (canvas.getWidth() - windowWidth) / 2;
      System.out.println("xOffset: " + xOffset);
      
      snakeX = xOffset + ((windowWidth / HALFER) / Snake.SEG_SIZE) *
          Snake.SEG_SIZE;
      snakeY = ((windowHeight / HALFER) / Snake.SEG_SIZE) * Snake.SEG_SIZE;

      System.out.println("snakeX: " + snakeX);
      System.out.println("snakeY: " + snakeY);
      snakeBeginLocation = new Location(snakeX, snakeY);

      maxScorePossible = (windowHeight / Snake.SEG_SIZE) *
          (windowWidth / Snake.SEG_SIZE);
      System.out.println("MAX SCORE POSSIBLE: " + maxScorePossible);

      //Constructing the Snake class.
      snakePlayer = new Snake(canvas, snakeBeginLocation, delay, this);


      //Creating the initial food.
      food = new FilledOval(placeNewFood(), FOOD_SIZE, FOOD_SIZE, canvas);
      food.setColor(Color.ORANGE);

      //Creating the boundary.
      boundary = new FramedRect(xOffset, 0, windowWidth, windowHeight,
           canvas);
      boundary.setColor(Color.WHITE);

      //OLD LOCATION
      /*//Constructing the Snake class.
      snakePlayer = new Snake(canvas, snakeBeginLocation, delay, this);*/

      //The canvas will now react to the keys pressed on the keyboard.
      canvas.addKeyListener(this);

      //Without the following line, you will need to click the canvas every
      //time you want the KeyListener to work.

      ((JDrawingCanvas)canvas).setBackground(Color.BLACK);

      canvas.requestFocusInWindow();
    }

    /**
     * Program runner.
     *
     * @param args command line string arguments
     */
    public static void main( String[] args ) 
    {
      boolean valid = true;

      SnakeController game = new SnakeController( args );
        
      if(args.length != 3)
      {
        //System.err.format(PA8Strings.USAGE);
        valid = false;
        //System.exit(1);      
      } 
        
      game.windowWidth = Integer.parseInt(args[0]);
      game.windowHeight = Integer.parseInt(args[1]);
      game.delay = Integer.parseInt(args[2]);

      //Determines if windowWidth is out of range.
      if(!game.checkRange(game.windowWidth, MIN_DIMENSION, MAX_DIMENSION)) 
      {
        System.err.format(PA8Strings.OUT_OF_RANGE, "Width",
            game.windowWidth, MIN_DIMENSION, MAX_DIMENSION);

        valid = false;
      }

      //Determines if windowWidth is divisible by 50.
      if(game.windowWidth % Y_PADDING != 0)
      {
        System.err.format(PA8Strings.NOT_EVENLY_DIVISIBLE, "Width",
            game.windowWidth);

        valid = false;
      }
   
      //Determines if windowHeight is out of range.
      if(!game.checkRange(game.windowHeight, MIN_DIMENSION, MAX_DIMENSION))
      {
        System.err.format(PA8Strings.OUT_OF_RANGE, "Height",
            game.windowHeight, MIN_DIMENSION, MAX_DIMENSION);      

        valid = false;
      }

      //Determines if windowHeight is divisible by 50. 
      if(game.windowHeight % Y_PADDING != 0)
      {
        System.err.format(PA8Strings.NOT_EVENLY_DIVISIBLE, "Height",
            game.windowHeight);

        valid = false;
      }
       
      //Determines if delay is out of range.
      if(!game.checkRange(game.delay, MIN_DELAY, MAX_DELAY))
      {
        System.err.format(PA8Strings.OUT_OF_RANGE, "Delay", game.delay, MIN_DELAY,
            MAX_DELAY);

        valid = false;
      }

      if(!valid)
      {  
        System.err.format(PA8Strings.USAGE);
        System.out.print("\n");
        System.exit(1);
      }

      new Acme.MainFrame( game, game.windowWidth - 6,
                            game.windowHeight + Y_PADDING + 10);//NEWWWWW
    }

    // TODO Add more functionality, and helper methods

    /**
     * Action listener method to check for button presses.
     * Handles newgame button presses
     * @param evt the event that triggers callback
     */
    public void actionPerformed( ActionEvent evt ) 
    {
      // TODO handle new game button presses

      //The score should be reset to 0.
     
      resetFoodCount = true;


      score = 0;  
      highScore = 0;
      startUp = true;
      paused = false;
      lblGameOver.setVisible(false);
      lblWin.setVisible(false);   
      lblPause.setVisible(false);

      foodDraw(); 
      snakePlayer.resetSnake(snakeBeginLocation);
     //snakePlayer.moveTo(snakeBeginLocation);
     //snakePlayer = new Snake(canvas, snakeBeginLocation, delay, this);

      canvas.requestFocusInWindow();
    }

    /**
     * Key Press handler. Manages snake movement
     * @param e the keyboard event that triggers.
     */
    public void keyPressed( KeyEvent e ) 
    {  
      if(paused == true)
      {
        keyBoardControls(e);
      }
      else if(paused == true || startUp == true)
      {
        keyBoardControls(e); 
        startUp = false;
        paused = true;
      }

      if(e.getKeyCode() == KeyEvent.VK_SPACE)
      {
        System.out.println("pressed SPACE");
        if(paused == true)
        {
          //TODO: Display pause text on the screen.
          paused = false;
          lblPause.setVisible(true);
        }
        else
        {
          //TODO: Hide pause text on the screen.
          paused = true;
          lblPause.setVisible(false);
        }
        //TODO:
        //The direction the snake is moving should be preserved when the game
        //paused. If the snake is moving left, then it should still move left
        //when the game is unpaused.
        System.out.println("PAUSE STATUS:" + paused);
      }
    }

    /**
     *  Controls of all the keys on the keyboard.
     *
     *  @param e the keyboard event that triggers.
     */
    public void keyBoardControls(KeyEvent e)
    {
      // TODO handle keyboard events here
      if(e.getKeyCode() == KeyEvent.VK_UP /*&& snake.getDirection() !=
          KeyEvent.VK_DOWN*/)
      {
        System.out.println("pressed UP ARROW");
        snakePlayer.setDirection(Direction.UP);
        //Direction(Direction.UP);   
        //snakePlayer.move(snakeX, snakeY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_DOWN /*&& snake.getDirection() !=
          KeyEvent.VK_UP*/)
      {
        System.out.println("pressed DOWN ARROW");
        snakePlayer.setDirection(Direction.DOWN);
        //snakePlayer.move(snakeX, snakeY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_LEFT /*&& snake.getDirection() !=
          KeyEvent.VK_RIGHT*/)
      {
        System.out.println("pressed LEFT ARROW");
        snakePlayer.setDirection(Direction.LEFT);
        //snakePlayer.move(snakeX, snakeY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_RIGHT /*&& snake.getDirection() !=
          KeyEvent.VK_LEFT*/)
      {
        System.out.println("pressed RIGHT ARROW");
        snakePlayer.setDirection(Direction.RIGHT);
        //snakePlayer.move(snakeX, snakeY);
      }
      else if(e.getKeyCode() == KeyEvent.VK_SPACE)
      {
        //Do nothing... 
      }
      else 
      {
        System.out.println("ANY OTHER KEY WAS PRESSED");
        snakePlayer.setDirection(Direction.LEFT);
        //By default, whatever key is pressed that isn't any of the ones above
        //it should make the snake move toward the left.
      }
    }

    /**
     *  Checks the range of the string inputs.
     *
     *  @param input one of the string inputs string(0, 1, or 2).
     *  @param min the lower limit.
     *  @param max the upper limit.
     */
    public boolean checkRange(int input, int min, int max)
    {
      if(input >= min && input <= max)
      {
        return true;
      }
      return false;
    }

    /**
     *  Generates 2 random integers that make up the x and y coordinates of the
     *  random location of the food.
     *
     */
    public Location placeNewFood()
    {
      int randomX;
      int randomY;

      RandomIntGenerator random = new RandomIntGenerator(0, MAX_DIMENSION
          - FOOD_SIZE);

      for(;;)
      { 
        //Creates a random x-coordinate that is divisible by 50.
        for(;;)
        {  
          randomX = random.nextValue();
          if(randomX % FOOD_SIZE == 0 && (randomX <= (windowWidth - 
                  FOOD_SIZE)))
          {
            randomX += xOffset;
            break;
          }
        }

        //Creates a random y-coordinate that is divisible by 50.
        for(;;)
        {
          randomY = random.nextValue();
          if(randomY % FOOD_SIZE == 0 && (randomY <= (windowHeight - 
                  FOOD_SIZE)))
          {
            break;
          }
        }

        //MAYBE???
        //Makes sure that the location of food is not the same as the location
        //of the initial snakehead.
        /*if(randomX != snakeX || randomY != snakeY)
        {
          break;
        }*/

        //Makes sure that the Location of food is not the same as the Location
        //of current snakehead and current body.
        if((snakePlayer.checkBody(randomX, randomY)))
        {
          break;  
        }
        System.out.println("CHECKING " + snakePlayer.checkBody(randomX, randomY)); 
      }

      foodLocation = new Location(randomX, randomY);
       
      return foodLocation;
    }

    /**
     *  Called when the snakehead location reaches the snake location. The
     *  method keeps track of the score. Also removes food and adds another
     *  food on the canvas.
     */
    public void foodDraw()
    {
      food.removeFromCanvas();

      score += 10;

      if(resetFoodCount == true)
      {
        score = 0;
        resetFoodCount = false;
      } 
     
      lblScore.setText("Score: " + score);
      if(score == ((maxScorePossible * 10) - 10 ))
      {
        winner();
      } 
      food = new FilledOval(placeNewFood(), FOOD_SIZE, FOOD_SIZE, canvas);
      food.setColor(Color.ORANGE);
      //food.sendForward();
    }

    /**
     *  Used to let the snake class when to pause the game.
     *
     *  @return paused, when paused is false, pause the game. Otherwise,
     *  continue the game.
     */
    public boolean getPause()
    {
      return paused;
    }

    /**
     *  Used to return the location of the food that is set on canvas.
     *
     *  @return foodLocation is the location of the filledOval placed on the
     *  canvas.
     */
    public Location getFoodLocation()
    {
      return foodLocation;  
    }

   /**
    * Used to return the windowWidth to the Snake class.
    *
    * @return windowWidth is a one of the 3 main string inputs that defines
    * the width of the game dimension.
    */
    public int getWindowWidth()
    {
      return windowWidth;  
    }
 
   /**
    * Used to return the windowHeight to the Snake class.
    *
    * @return windowHeight is a one of the 3 main string inputs that defines
    * the height of the game dimension.
    */
   public int getWindowHeight()
   {
     return windowHeight;
   } 

   /**
    * Used to return the xOffset to the Snake class.
    *
    * @return xOffset is the difference between canvas.getWidth() and user
    * width input.
    */
   public int getXOffset()
   {
      return xOffset;
   }

   /**
    * Called when the user looses. Loosing consists of whether the snake goes
    * out of bounds or if the snake eats itself. 
    */
   public void looser() 
   {
      //Another boolean ???????????????
      //startUp = true;
      paused = false;
      lblGameOver.setVisible(true);
   }

   /**
    * Called when the score reaches the maximum amount to win the game.
    */
   public void winner()
   {
      paused = false;
      lblWin.setVisible(true);
   }

    /* method stubs for the KeyListener interface */
    public void keyTyped(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}

} // end of class SnakeController
