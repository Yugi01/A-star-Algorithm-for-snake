
public class Node {

	int x;
	int y;
	boolean visited;
	int h=0;//heuristic
	int g=0;//cost from current Node
	int f=0;//total cost of present node
	Node parent;
	NodeType type=null;
	
	public Node(int x, int y, Node parent)
	{
		this.x=x;
		this.y=y;
		this.parent=parent;
		
	}
	
	public void seth(int h)
	{
		this.h=h;
	}

	public void setg(int g)
	{
		this.g=g;
	}
	
	public void setf()
	{
		f=g+h;
	}
	
	public int getf()
	{
		return f;
	}

	public int getg()
	{
		return g;
	}
	public int geth()
	{
		return h;
	}
	
	

}
