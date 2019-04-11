/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room Start, ChooseWeapon, Trap, three, DeadEnd, Deadend, five, Boss, Doors, MiniBoss, nine, ten, eleven, LorR, reset, GearingUp, BOSS, WINNER, HardReset;
      
        // create the rooms
        Start = new Room("HAHA traveler you have entered the World of Death good luck");
        ChooseWeapon = new Room("Well it looks like you aren't gonna leave if that's the case choose your weapon.");
        Trap = new Room("HAHAHA you thought this was going to be easy!");
        three = new Room("This your first test you better take out these grunts.");
        Deadend = new Room("Welp you found a deadend");
        DeadEnd = new Room("HAHA you fell in to the Deadend AGAIN you are dumb.");
        five = new Room("So you managed to deafeat them impressive.");
        Boss = new Room("So you tried to kill the Lava Boss right now haha.");
        Doors = new Room("Two doors are in front of you choose who you face.");
        MiniBoss = new Room("");
        nine = new Room("");
        ten = new Room("");
        eleven = new Room("");
        LorR = new Room("");
        reset = new Room("");
        GearingUp = new Room("");
        BOSS = new Room("");
        WINNER = new Room("");
        HardReset = new Room("You choose to fight the lava Boss in the a lava DUMMY!");
        // initialise room exits
        
        Start.setExit("south", Trap);
        Start.setExit("east", ChooseWeapon);
        
        ChooseWeapon.setExit("east", Deadend);
        ChooseWeapon.setExit("south", three);

        Trap.setExit("north", Start);

        three.setExit("west", Trap);
        three.setExit("north", ChooseWeapon );
        three.setExit("east", five);
        
        Deadend.setExit("west", ChooseWeapon);
        
        five.setExit("west", three);
        five.setExit("south", Doors);
        five.setExit("north", Deadend);
        
        Boss.setExit("east", five);
        
        Doors.setExit("north", five);
        Doors.setExit("west", Boss);
        Doors.setExit("south", MiniBoss);
        
        MiniBoss.setExit("north", Doors);
        MiniBoss.setExit("west", nine);
        MiniBoss.setExit("south", ten);
        
        nine.setExit("south", eleven);
        
        ten.setExit("west", Start);
        
        eleven.setExit("south", LorR);
        
        LorR.setExit("west", reset);
        LorR.setExit("east", GearingUp);
        
        reset.setExit("north", Start);
        
        GearingUp.setExit("east", BOSS);
        
        BOSS.setExit("east", WINNER);
        BOSS.setExit("south", HardReset);
        
        HardReset.setExit("east", Start);

        currentRoom = Start;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
