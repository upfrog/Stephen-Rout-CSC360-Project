/**
 * Helps each object to track relevant external objects.
 * 
 *  At times this solution is clumsy. For example, for maximum flexibility 
 *  it contains Structures, meaning that subclass methods cannot be directly
 *  called on objects fetched from this container.
 *  
 *  I believe this is still less of a pain, and more extensible, than every
 *  class having an explicitly defined set of ArrayLists. This is more
 *  flexible, since it can be modified at runtime.
 *  
 *  Most of this implementation's issues will become irrelevant in the next
 *  sprint, where it will be refactored to consist of URLs for making API
 *  calls. This will avoid the class conflict issue mentioned above.
 */

import java.util.HashMap;
import java.util.ArrayList;

public class LinkContainer
{
	private String listNotFound = "List type not found.";
	private HashMap<String, ArrayList<Structure>> linkMap;
	
	public LinkContainer()
	{
		linkMap = new HashMap<String, ArrayList<Structure>>();
	}
	
	public boolean contains(String listType, Structure object)
	{
		try
		{
			return linkMap.get(listType).contains(object);
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void addLinkList(String key)
	{
		linkMap.put(key, new ArrayList<Structure>());
	}
	
	public void addLink(String listType, Structure object)
	{
		linkMap.get(listType).add(object);
	}
	
	public void removeLink(String listType, Structure object)
	{
		linkMap.get(listType).remove(object);
	}
	
	/**
	 * Returns a list of links.
	 * 
	 * This function should only ever be called on a lists that are
	 * in the calling class's linkTypes list. A validation function
	 * might be worth considering for the future.
	 * 
	 * @param	listType: 	The key associated with the desired list
	 * @return				The desired list 
	 */
	public ArrayList<Structure> getList(String listType)
	{

		ArrayList<Structure> result = linkMap.get(listType);
		
		if (result != null)
		{
			return result;
		}
		else
		{
			throw new RuntimeException(this.listNotFound);
		}
		

	}
	
	/*
	 * @param	listType: 	The key associated with the desired list
	 * @return 				the number of elements in the desired list, or 
	 * 						-1 if the list does not exist.
	 */
	public int getListLength(String listType)
	{
		try
		{
			return linkMap.get(listType).size();
		}
		catch(Exception e)
		{
			
			throw new RuntimeException(listNotFound);
		}
	}
}
