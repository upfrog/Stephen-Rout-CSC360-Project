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

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

/*
 * This class is mostly a wrapper for a single HashMap, which maps String category names ("Followers", etc, 
 * henceforth referred to as listTypes) onto ArrayLists of UIDs for the associated object type. It also
 * contains some additional methods for convenience, but most of these closely mirror the function of a 
 * default HashMap. The class mostly lets me define my own interface and behavior, with slightly cleaner
 * behavior for my specific use case.
 * 
 * With the exception of getList() and getListLength(), the methods of this class should smoothly handle
 * impossible requests, without throwing. Data duplication is also avoided.
 */
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
		return linkMap.get(listType).contains(UID);
	}
	
	/*
	 * Adds a new <key, arrayList> pairing to the hashmap.
	 * 
	 * Should only be called at object instantiation.
	 */
	public void addLinkList(String listType)
	{
		if (!linkMap.containsKey(listType))
		{
			linkMap.put(listType, new ArrayList<String>());
		}
	}
	
	public void addLink(String listType, String UID)
	{
		if (linkMap.containsKey(listType) && !(this.contains(listType, UID)))
		{
			linkMap.get(listType).add(UID);
		}
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
	
	public void setLinkMap(HashMap<String, List<String>> linkMap)
	{
		this.linkMap = linkMap;
	}
	
	public HashMap<String, List<String>> getLinkMap()
	{
		return this.linkMap;
	}

	@Override
	public int hashCode()
	{
		return linkMap.hashCode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof LinkContainer))
			return false;
		LinkContainer other = (LinkContainer) obj;
		return linkMap.equals(other.getLinkMap());
	}
}