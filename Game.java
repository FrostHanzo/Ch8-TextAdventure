import java.util.ArrayList;


/**
 
 * @author  Matthew Dornick
 * @version 1.10
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private ArrayList<Items> carriedItems;    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        carriedItems = new ArrayList<Items>();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room Start, ChooseWeapon, Trap, three, DeadEnd, Deadend, five, Boss, Doors, MiniBoss, Win, Loss, eleven, LorR, reset, GearingUp, BOSS, WINNER, HardReset;
      
        // create the rooms
        Start = new Room("HAHA traveler you have entered the World of Death good luck");
        ChooseWeapon = new Room("Well it looks like you aren't gonna leave if that's the case take your weapon.");
        Trap = new Room("HAHAHA you thought this was going to be easy!");
        three = new Room("This your first test you better take out these grunts.");
        Deadend = new Room("Welp you found a deadend");
        DeadEnd = new Room("HAHA you fell in to the Deadend AGAIN you are dumb.");
        five = new Room("So you managed to deafeat them impressive.");
        Boss = new Room("So you tried to kill the Lava Boss right now haha.");
        Doors = new Room("Two doors are in front of you choose who you face.");
        MiniBoss = new Room("Here is a true test here's a little boss HAHA!");
        Win = new Room("OK OK I see you. You have some skills.");
        Loss = new Room("Welp maybe you should restart.");
        eleven = new Room("Here are some more grunts HAhaaaa.");
        LorR = new Room("Choose where you wanna go my g.");
        reset = new Room("HA gottem restart.");
        GearingUp = new Room("Make sure you eat and look around.");
        BOSS = new Room("Choose which room you wanna fight the Lava Boss in.");
        WINNER = new Room("You chose the water room and defeated the boss great job yo can quit the game now.");
        HardReset = new Room("You chose to fight the lava Boss in the a lava DUMMY!");
        // initialise room exits
        
        
        Start.setExit("south", Trap);
        Start.setExit("east", ChooseWeapon);
        
        ChooseWeapon.setExit("east", Deadend);
        ChooseWeapon.setExit("south", three);
        Items sword = new Items("sword", 1);
        ChooseWeapon.addItem(sword);
        
        Trap.setExit("north", Start);

        three.setExit("west", Trap);
        three.setExit("north", ChooseWeapon );
        three.setExit("east", five);
        
        Deadend.setExit("west", ChooseWeapon);
        DeadEnd.setExit("west", ChooseWeapon);
        
        five.setExit("west", three);
        five.setExit("south", Doors);
        five.setExit("north", DeadEnd);
        
        Boss.setExit("east", five);
        
        Doors.setExit("north", five);
        Doors.setExit("west", Boss);
        Doors.setExit("south", MiniBoss);
        
        MiniBoss.setExit("north", Doors);
        MiniBoss.setExit("west", Win);
        MiniBoss.setExit("south", Loss);
        
        Win.setExit("south", eleven);
        
        Loss.setExit("west", Start);
        
        eleven.setExit("south", LorR);
        
        LorR.setExit("west", reset);
        LorR.setExit("east", GearingUp);
        
        reset.setExit("north", Start);
        
        GearingUp.setExit("east", BOSS);
        Items Armour = new Items("Armour", 2);
        GearingUp.addItem(Armour);
        
        BOSS.setExit("east", WINNER);
        BOSS.setExit("south", HardReset);
        
        HardReset.setExit("east", Start);

        currentRoom = Start;  // start game outside
    }
    
    private void printCarriedItems() {
        if (carriedItems.size() == 0) {
            System.out.println("you are not carrying anything");
        } else {
            System.out.print("You have the following:");
            for (int n = 0; n < carriedItems.size(); n++) {
                Items item = (Items) carriedItems.get(n);
                System.out.print(" " + item.getDescription());
            }
            System.out.println();
       System.out.println("Total weight of all carried Items: " + totalWeight(carriedItems));
                    }
    }
    
    private int totalWeight(ArrayList L) {
        int n=0;
        int sum = 0;
        while (n < L.size()) {
            Items i = (Items) L.get(n);
            sum += i.getWeight();
            n++;
        }
        return sum;    // not found above
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
        System.out.println("Welcome to the World of Death!");
        System.out.println("World of Death is a new, incredibly boring adventure game.");
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

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
                        goRoom(command);
        }
        else if (commandWord.equals("look")) {
            goRoom(command);
         }
         else if (commandWord.equals("take")) {
            take(command);
        }
        else if (commandWord.equals("drop")) {
            drop(command);
        }
    else if (commandWord.equals("items")) {
        printCarriedItems();
    }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }
    
    private void eat()
    {
        System.out.println("You ate now you ain't hungry");
    }    
    
    private void look()
    {
        System.out.println(currentRoom.getLongDescription());
    }
    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You have entered the World of Death and alone.");
        System.out.println("figure out where to go.");
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
    
    private Items findByName(String s, ArrayList L) {
        int n=0;
        while (n < L.size()) {
            Items i = (Items) L.get(n);
            if (s.equals(i.getDescription()))
                return i;
            n++;
        }
        return null;
    }
    
    public void drop(Command command) {
        if (! command.hasSecondWord()) {  // "DROP",but no object named
            System.out.println("Drop what?");
            return;
        }
        String s = command.getSecondWord();
        Items i = findByName(s, carriedItems);
        if (i == null) {
            System.out.println("You don't have a " + s);
            return;
        }
        carriedItems.remove(i);
        currentRoom.addItem(i);
        System.out.println("You have dropped the " + s);
      }

      /**
         * Implement a Take command
           */
      public void take(Command command) {
        if (! command.hasSecondWord()) {  // "TAKE",but no object named
            System.out.println("Take what?");
            return;
        }
        String s = command.getSecondWord();
        Items i = findByName(s, currentRoom.getItems());
        if (i == null) {
            System.out.println("There is no " + s + " in this room ");
            return;
        }
        currentRoom.getItems().remove(i);
        carriedItems.add(i);
               System.out.println("You have taken the " + s);
    }

}
