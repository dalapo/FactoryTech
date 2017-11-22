package dalapo.factech.helper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import dalapo.factech.tileentity.specialized.TileEntityDecoCoil;

/**
 * A Graph with vertices being TileEntityDecoCoil (TEDC) objects. Edges are defined by an adjacency list,
 * i.e. each vertex is mapped to a set of other vertices that it is adjacent to.
 * Additionally, when an edge is created, the pair of vertices used to create the edge are mapped
 * to a DecoCoilLink, which is a client-side rendering of a link between two TEDC objects.
 * 
 * Neighbours of a TEDC can be iterated over using getNeighbours(TEDC).
 * DecoCoilLink objects originating at a TEDC can be iterated over using getOutgoingLinks(TEDC).
 * The entire connected component (i.e. all neighbours of neighbours, recursive) of a TEDC can be
 * iterated over using getConnectedComponent(TEDC).
 */
public class DecoCoilLinkGraph
{
	// Some nifty code simulating "friend" code to allow exclusive access to function in TileEntityDecoCoil - found here:
	// https://stackoverflow.com/questions/182278/is-there-a-way-to-simulate-the-c-friend-concept-in-java/18634125#18634125
	public static final class DCLG { private DCLG() {} }
	private static final DCLG TEDCLINK = new DCLG();
	
	private Map<TileEntityDecoCoil, Set<TileEntityDecoCoil> > graph;
	private Map<Pair<TileEntityDecoCoil, TileEntityDecoCoil>, DecoCoilLink> edgeMap;

	/**
	 * Constructs an empty graph.
	 */
	public DecoCoilLinkGraph()
	{
		this.graph = new HashMap<TileEntityDecoCoil, Set<TileEntityDecoCoil> >();
		this.edgeMap = new HashMap<Pair<TileEntityDecoCoil, TileEntityDecoCoil>, DecoCoilLink>();
	}
	
	/**
	 * Merges this graph with the other graph provided. Ensures that all TileEntityDecoCoil objects in other
	 * have their links set to this graph - this ensures that all added objects correctly point to this graph.
	 * Note that the other graph should no longer be considered to be valid after this point, because
	 * subsequent calls to this function from that function (e.g. other.merge(evenMoreOther)) would not result
	 * in other being in a valid state.
	 * 
	 * @param other The graph to merge this graph with.
	 */
	public void merge(DecoCoilLinkGraph other)
	{
		for (TileEntityDecoCoil vertex : other.graph.keySet())
		{
			this.addVertex(vertex);
			for (TileEntityDecoCoil connection : other.graph.get(vertex))
			{
				this.addEdge(vertex, connection);
			}
			vertex.setLinks(this, TEDCLINK);
		}
	}
	
	/**
	 * Adds the given vertex if this doesn't contain the vertex already.
	 */
	public void addVertex(TileEntityDecoCoil vertex)
	{
		if (vertex != null && !graph.containsKey(vertex))
		{
			graph.put(vertex, new HashSet<TileEntityDecoCoil>());
		}
	}
	
	/**
	 * Removes all edges containing the given vertex, then removes the vertex.
	 */
	public void removeVertex(TileEntityDecoCoil vertex)
	{
		Iterable<TileEntityDecoCoil> neighbours = graph.get(vertex);
		graph.remove(vertex);
		for (TileEntityDecoCoil connected : neighbours)
		{
			removeEdge(vertex, connected);
		}
	}
	
	/**
	 * Adds edges/links from first to second and from second to first.
	 */
	public void addEdge(TileEntityDecoCoil first, TileEntityDecoCoil second)
	{
		if (first == null || second == null || first.equals(second)) return;
		
		if (!graph.containsKey(first))
		{
			addVertex(first);
		}
		if (!graph.containsKey(second))
		{
			addVertex(second);
		}
		
		graph.get(first).add(second);
		graph.get(second).add(first);
		edgeMap.put(new Pair<TileEntityDecoCoil, TileEntityDecoCoil>(first, second), new DecoCoilLink(first.getPos(), second.getPos()));
		edgeMap.put(new Pair<TileEntityDecoCoil, TileEntityDecoCoil>(second, first), new DecoCoilLink(second.getPos(), first.getPos()));
	}
	
	/**
	 * Removes edges/links between first and second, if they exist.
	 */
	public void removeEdge(TileEntityDecoCoil first, TileEntityDecoCoil second)
	{
    	if (graph.containsKey(first))
    	{
    		this.graph.get(first).remove(second);
    	}
    	if (graph.containsKey(second))
    	{
            this.graph.get(second).remove(first);
    	}
		edgeMap.remove(new Pair<TileEntityDecoCoil, TileEntityDecoCoil>(first, second));
		edgeMap.remove(new Pair<TileEntityDecoCoil, TileEntityDecoCoil>(second, first));
	}
	
	/**
	 * Returns true if any connected TEDecoCoil is powered (i.e. TileEntityBase.isPowered() is true).
	 */
	public boolean isPowered(TileEntityDecoCoil source)
	{
		for (TileEntityDecoCoil vertex : getConnectedComponents(source))
		{
			if (vertex.isPowered()) return true;
		}
		return false;
	}
	
	/**
	 * Finds and returns all immediate neighbours of the given TileEntityDecoCoil. This takes constant time.
	 * Returns an empty Iterable if the source is not in the graph or has no neighbours.
	 * 
	 * @param source The vertex to look for neighbours of.
	 * @return An Iterable<TileEntityDecoCoil> of all neighbours of the source. 
	 */
	public Iterable<TileEntityDecoCoil> getNeighbours(TileEntityDecoCoil source)
	{
		if (graph.containsKey(source)) {
			return graph.get(source);
		} else {
			return new HashSet<TileEntityDecoCoil>();
		}
	}
	
	/**
	 * Gets all DecoCoilLinks that originate at the given TileEntityDecoCoil.
	 * This takes O(E) time, where E is the number of edges (i.e. DecoCoilLink objects).
	 * Returns an empty Iterable if the source is not in the graph or has no edges originating from it.
	 * 
	 * @param source The TileEntityDecoCoil to look for links from.
	 * @return An iterable list of DecoCoilLinks originating from source.
	 */
	public Iterable<DecoCoilLink> getOutgoingLinks(TileEntityDecoCoil source)
	{
		Set<DecoCoilLink> links = new HashSet<DecoCoilLink>();
		for (Pair<TileEntityDecoCoil, TileEntityDecoCoil> edge : edgeMap.keySet())
		{
			if (source.equals(edge.a))
			{
				links.add(edgeMap.get(edge));
			}
		}
		return links;
	}
	
	/**
	 * Gets all TileEntityDecoCoil objects in the connected component of the given TileEntityDecoCoil.
	 * 
	 * @param source The TileEntityDecoCoil to get the connected components of.
	 * @return An Iterable containing each vertex in the connected component originating at the source, including the source.
	 */
    public Iterable<TileEntityDecoCoil> getConnectedComponents(TileEntityDecoCoil source)
    {
    	Set<TileEntityDecoCoil> checked = new HashSet<TileEntityDecoCoil>();
    	LinkedList<TileEntityDecoCoil> toCheck = new LinkedList<TileEntityDecoCoil>();
    	toCheck.add(source);
    	while (!toCheck.isEmpty())
    	{
    		// take first item in list of items to check
    		TileEntityDecoCoil vertex = toCheck.remove(0);
        	if (graph.containsKey(vertex)) {
        		for (TileEntityDecoCoil neighbour : graph.get(vertex))
        		{
        			// equals check shouldn't be necessary but w/e
        			if (!checked.contains(neighbour) && !toCheck.contains(neighbour) && !vertex.equals(neighbour)) {
        				toCheck.add(neighbour);
        			}
        		}
        	}
    		checked.add(vertex);
    	}
    	return checked;
    }
}
