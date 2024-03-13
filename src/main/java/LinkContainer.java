import java.util.HashMap;
import java.util.ArrayList;

public class LinkContainer
{
	HashMap<String, ArrayList<Structure>> linkMap;
	
	public LinkContainer()
	{
		linkMap = new HashMap<String, ArrayList<Structure>>();
	}
	
	public boolean contains(String listType, Structure object)
	{
		return linkMap.get(listType).contains(object);
	}
	
	public void addLink(String listType, Structure object)
	{
		linkMap.get(listType).add(object);
	}
	
	public void removeLink(String listType, Structure object)
	{
		linkMap.get(listType).remove(object);
	}
	
	public ArrayList<Structure> getList(String listType)
	{
		return linkMap.get(listType);
	}
	
	public int getListLength(String listType)
	{
		return linkMap.get(listType).size();
	}
}
