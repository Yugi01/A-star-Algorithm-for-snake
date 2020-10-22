import java.util.Comparator;

public class NodeCompare implements Comparator<Node>{


	public int compare(Node node1, Node node2) {
		
		if(node1.getf()>node2.getf())
		{
			return 1;
		}
		else
		{
			return -1;
		}

	}

}
