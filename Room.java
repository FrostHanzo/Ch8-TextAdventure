import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;

/**
 
 * @author  Matthew Dornick
 * @version 1.10
 */

public class Room
{
    private String description;
                private ArrayList <Items> items; // stores items in the rooms
        private HashMap<String, Room> exits;        // stores exits of this room.

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description)
    {
        this.description = description;
                exits = new HashMap<String, Room>();
        items = new ArrayList<Items>();
              }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor)
    {
        exits.put(direction, neighbor);
    }

/**
* add a list of items
*/
public void addItem(Items i)
{
	items.add(i);
}

/**
* Return the list of all items
*/
public ArrayList<Items> getItems()
{
	return items;
}

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        String s = "" + description + ".\n" + getExitString();
    if(items.size() == 0)
    {
    s = s+ ". There are no items in this room.";
        }
else {
	s = s + "The room contains the following items:";
	for (Items i : items)
	{
		s = s + i.getDescription() + " ";
	}
}
return s;
}


    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction)
    {
        return exits.get(direction);
    }
}