import java.util.HashMap;
import java.util.ArrayList;

public class LinkContainer
{
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
	 * in the calling class's linkTypes list.
	 * 
	 * @param	listType: 	The key associated with the desired list
	 * @return				The desired list 
	 */
	public ArrayList<Structure> getList(String listType)
	{
		try
		{
			return linkMap.get(listType);
		}
		catch(Exception e)
		{
			throw e;
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
			return -1;
		}
	}
}
