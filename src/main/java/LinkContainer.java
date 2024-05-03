/**
* This class consists of a hashmap, which relates strings to lists of strings. Each key
* refers to one of the types of object links that a given object stores, and the list
* consists of the UIDs of those objects.
* 
* It is a hashmap for maximum flexibility. One implementation can be used for all objects,
* with the only difference being the types of links contained in that object's instances of
* LinkContainer.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

public class LinkContainer
{
	@JsonIgnore
	private static final String listNotFound = "List type not found.";
	private HashMap<String, List<String>> linkMap;
	
	public LinkContainer()
	{
		linkMap = new HashMap<String, List<String>>();
	}
	
	public boolean contains(String listType, String UID)
	{
		try
		{
			return linkMap.get(listType).contains(UID);
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public void addLinkList(String key)
	{
		linkMap.put(key, new ArrayList<String>());
	}
	
	public void addLink(String listType, String UID)
	{
		linkMap.get(listType).add(UID);
	}
	
	public void removeLink(String listType, String UID)
	{
		linkMap.get(listType).remove(UID);
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
	public List<String> getList(String listType)
	{
		List<String> result = linkMap.get(listType);
		
		if (result != null)
		{
			return result;
		}
		else
		{
			throw new RuntimeException(listNotFound);
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

	@Override
	public int hashCode()
	{
		return Objects.hash(linkMap);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof LinkContainer))
			return false;
		LinkContainer other = (LinkContainer) obj;
		return linkMap.values().equals(other.linkMap.values())
				&& linkMap.keySet().equals(other.linkMap.keySet());
	}
	
	
}