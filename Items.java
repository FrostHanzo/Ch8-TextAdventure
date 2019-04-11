
/**
 
 * @author  Matthew Dornick
 * @version 1.10
 */
public class Items
{
	// description and weight of the item
	private String description;
	private int    weight;

	/**
	 * Constructor for objects of class Item
	 */
	public Items(String d)
	{
		description = d;
		weight = 0;
	}

	public Items(String d, int w)
	{
	    description = d;
	    weight = w;
	}

/**
	 * Accessor for description
	 *
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Accessor for weight
	 *
	 */
	public int getWeight()
	{
		return weight;
	}

   		}