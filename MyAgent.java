
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.*;
import java.util.Random;

import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {

	static int otherHeadx=0;
	static int otherHeady=0;
	static int otherDirx=0;
	static int otherDiry=0;
	static int snakeHeadx=0;
	static int snakeHeady=0;
	static int snakeDirx=0;
	static int snakeDiry=0;
	static int appleDist=0;
	static int applex=0;
	static int appley=0;
	static int newApplex;
	static int newAppley;
	static int mySnakeDist=0;
	static int otherSnakeDist=0;
	static int minDist=100;
	static int snakeTailx=0;
	static int snakeTaily=0;
	static int idlex=0;
	static int idley=0;
	double appleHP;
    static int[] row= {1,0,-1,0};
    static int[] col= {0,-1,0,1};
    Node[][] gameMap = new Node[50][50];
    //String[][] boardView= new String[50][50];
    
    
  	public void setBoard()
	{
		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				gameMap[i][j].type=NodeType.empty;
				//boardView[i][j]=".";
			}
		}
	}
//  	public void setWall()
//  	{
//  		for (int i = 0; i < gameMap.length; i++) {
//			for (int j = 0; j < gameMap[0].length; j++) {
//				
//			}
//		}
//  	}
  	
  	public void setIdle()
  	{
  		if(snakeHeadx<25 && snakeHeady<25)
  		{
  			idlex=48;
  			idley=48;
  		}
  		else if(snakeHeadx<25 && snakeHeady>25)
  		{
  			idlex=48;
  			idley=1;
  		}
  		else if(snakeHeadx>25 && snakeHeady<=25)
  	  	{
  	 		idlex=1;
  	  		idley=48;
  	  	}
  		else
  		{
  			idlex=1;
  			idley=1;
  		}
  	}
  	
  	public boolean dangerApple()
  	{
  		if(isValid(snakeHeadx,snakeHeady,gameMap))
  		{
	  		if(gameMap[applex-1][appley].type!=NodeType.empty&&gameMap[applex+1][appley].type!=NodeType.empty)
	  		{
	  			return true;
	  		}
	  		else if(gameMap[applex][appley-1].type!=NodeType.empty&&gameMap[applex][appley-1].type!=NodeType.empty)
	  		{
	  			return true;
	  		}
	  		else
	  		{
	  			return false;
	  		}
  		}
  		else
  		{
  			return true;
  		}
  	}
  	
//  	public boolean worthIt()
//  	{	
//  		if(appleHP-(distToApple()*0.1)>0)
//  		{
//  			return true;
//  		}
//  		else
//  		{  		
//  	  		return false;
//  		}
//
//  	}
  	public void setAppleHP()
  	{
  		if (newAppley != appley && newApplex != applex) {
            //reset apple
            appleHP = 5;
            setIdle();
        } else {
            appleHP -= 0.1;
            
        }
  		
  	}
	
	public void setApple()
	{

		gameMap[applex][appley].type=NodeType.apple;
		//gameMap[appley][applex].type=NodeType.obstacle;
		if(appleHP<0)
		{
			gameMap[applex][appley].type=NodeType.obstacle;
			setIdle();
		}
		//boardView[applex][appley]="A";
		//boardView[appley][applex]="B";
	}
	public int distToBait()
	{
		return Math.abs(snakeHeadx-idlex)+Math.abs(snakeHeady-idley);
	}
	public int distToApple()
	{
		return Math.abs(snakeHeadx-applex)+Math.abs(snakeHeady-appley);
	}
	
	public void setObs(int obsx, int obsy)
	{
		gameMap[obsx][obsy].type=NodeType.obstacle;
		//boardView[obsx][obsy]="O";
	}
		
//	public void setSnake(int x, int y)
//	{
//		gameMap[x][y].type=NodeType.snake;
//	}
    
    public static boolean isValid(int x, int y,Node gameMap[][])
	{
    	
		if(x>=0 && x<=49 && y>=0 && y<=49)
		{
			if(gameMap[x][y].type==NodeType.empty || gameMap[x][y].type==NodeType.apple)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		
		return false; 
	}
    
    public boolean ifContain(PriorityQueue<Node> cue, Node node)
    {

    	Iterator<Node> it = cue.iterator();
    	
    	while(it.hasNext())
    	{
    		Node curr = it.next();
    		if(curr.x==node.x && curr.y==node.y)
			{
				return true;
			}
    	}

    	return false;
    	
    }
	
    
	public int heuristic(Node start, Node end)
	{
		return Math.abs(start.x-end.x) + Math.abs(start.y-end.y);
		
	}
	
	public int snakeDist(int x, int y)
	{
		return Math.abs(x-applex)+ Math.abs(y-appley);
	}
//	
//	public int NeighbourDist(Node start, Node end)
//	{
//		return heuristic(start,end);
//	}
	
	public ArrayList<Node> Neighbours(Node id)
	{
		ArrayList<Node> neighbour = new ArrayList<Node>();
		 for (int i = 0; i < 4; i++) 
		 {
			int x= id.x + row[i];
			int y= id.y + col[i];
			if(id.type==null||id.type==NodeType.empty)
			{
				
					neighbour.add(new Node(x,y,id));
				
			}
			
		 }
		
		 return neighbour;
	}

	
//	public boolean appleHealth()
//	{
//		boolean output=true;
//		
//		if(appleHP<0)
//		{
//			return false;
//		}
//		
//		return output;
//	}
	
    public Node findPath(Node start, Node end)
    {
    	NodeCompare n = new NodeCompare();
    	PriorityQueue<Node> openQueue = new PriorityQueue<>(n);
    	PriorityQueue<Node> closeQueue = new PriorityQueue<>(n);
    	openQueue.add(start);
    	//Stack<Node> path = new Stack<>();
    	
    	while(openQueue.size()>0)
    	{
    		
    		Node curr = openQueue.poll();

    		if(curr.x==end.x && curr.y==end.y)
    		{
    			return curr;
    		}
    		//curr.visited=true;
    		closeQueue.add(curr);
    		
    		ArrayList<Node> neighbours = Neighbours(curr);
    		for(Node neighbour : neighbours)
    		{
    			
    			if(neighbour.type==NodeType.snake || neighbour.type==NodeType.obstacle || ifContain(closeQueue,neighbour))
    			{
    				
    				continue;
    			}
    			
    			if(neighbour.x==end.x && neighbour.y==end.y)
    			{
    					return neighbour;
    			}
    			
    			
    			int gcost= curr.getg()+heuristic(curr,neighbour);
    			if(gcost>neighbour.getg() && !ifContain(openQueue,neighbour))
    			{
    				if(isValid(neighbour.x,neighbour.y,gameMap))
    				{
    					
    					//neighbour.visited=true;
    					neighbour.setg(gcost);
    					neighbour.seth(heuristic(neighbour,end));
    					neighbour.setf();
    					neighbour.parent=curr;
    					System.err.println(neighbour.x+" "+ neighbour.y+" "+openQueue.size()+" "+closeQueue.size());
   						openQueue.add(neighbour);  

    	    			  

    				}
    			}

    		}

    		
    		closeQueue.add(curr);
	
    	}
    	System.err.println("No path found");
    	return null;
    }
    
    public void printPath(Node node)
    {
    	
    	Node current=node;
    	Node parent=null;

    	if(current.parent!=null)
    	{
    		parent=current.parent;
    	}
    	
    	while(current.parent.parent!=null)
    	{
    		current=parent;
    		parent=current.parent;
    	}

    	moveTo(parent,current);

    }
    
    
    void moveTo(Node current, Node next) {
        if (current.x == next.x) {
            if (current.y < next.y) {
                System.out.println(1);
               // System.err.println(1);
            } else {
                System.out.println(0);
               // System.err.println(0);
            }
        } else {
            if (current.x < next.x) {
                System.out.println(3);
             //   System.err.println(3);
            } else {
            	System.out.println(2);
            	//System.err.println(2);
            }
        }
    }
    

    
    public void plotSnakes(int headx, int heady, int directionx, int directiony)
    {
    	if(directionx>headx && directiony==heady)
		{
			for(int l=headx; l<=directionx; l++)
			{
				gameMap[l][heady].type=NodeType.snake;
				//boardView[l][heady]="S";
			}
		}
		else if(directionx<headx && directiony==heady)
		{
			for(int l=directionx; l<=headx; l++)
			{
				gameMap[l][heady].type=NodeType.snake;
				//boardView[l][heady]="S";
			}
		}
		else if(headx==directionx && directiony>heady)
		{
			for(int l=heady; l<=directiony; l++)
			{
				gameMap[headx][l].type=NodeType.snake;
				//boardView[headx][l]="S";
			}
		}
		else
		{
			for(int l=directiony; l<=heady; l++)
			{
				gameMap[headx][l].type=NodeType.snake;
				//boardView[headx][l]="S";
			}
		}
    }
    
    public void snakeHelmet(int headx, int heady, int directionx, int directiony)
    {
    	if(headx>0&&heady>0&&directionx>0&&directiony>0&&headx<49&&heady<49&&directionx<49&&directiony<49)
    	{
	    	if(directionx>headx && directiony==heady)
			{
	    		gameMap[headx-1][heady].type=NodeType.danger;
				gameMap[headx][heady-1].type=NodeType.danger;
				gameMap[headx][heady+1].type=NodeType.danger;
				//boardView[headx][heady]="Z";
				//boardView[headx-1][heady]="H";
				//boardView[headx][heady-1]="H";
				//boardView[headx][heady+1]="H";
			}
			else if(directionx<headx && directiony==heady)
			{
				gameMap[headx+1][heady].type=NodeType.danger;
				gameMap[headx][heady-1].type=NodeType.danger;
				gameMap[headx][heady+1].type=NodeType.danger;
				//boardView[headx][heady]="Z";
			//	boardView[headx+1][heady]="H";
				//boardView[headx][heady-1]="H";
				//boardView[headx][heady+1]="H";
			}
			else if(headx==directionx && directiony>heady)
			{
				gameMap[headx][heady-1].type=NodeType.danger;
				gameMap[headx-1][heady].type=NodeType.danger;
				gameMap[headx+1][heady].type=NodeType.danger;
				//boardView[headx][heady]="Z";
				//boardView[headx][heady-1]="H";
				//boardView[headx-1][heady]="H";
				//boardView[headx+1][heady]="H";
			}
			else
			{
				gameMap[headx][heady+1].type=NodeType.danger;
				gameMap[headx-1][heady].type=NodeType.danger;
				gameMap[headx+1][heady].type=NodeType.danger;
				//boardView[headx][heady]="Z";
				//boardView[headx][heady+1]="H";
				//boardView[headx-1][heady]="H";
				//boardView[headx+1][heady]="H";
			}
    	}
    }
    
    public boolean checkSafe()
    {
    	if(isValid(snakeHeadx,snakeHeady,gameMap))
    	{
	    	if(gameMap[snakeHeadx+1][snakeHeady].type==NodeType.danger)
	    	{
	    		return false;
	    	}
	    	else if(gameMap[snakeHeadx-1][snakeHeady].type==NodeType.danger)
	    	{
	    		return false;
	    	}
	    	else if(gameMap[snakeHeadx][snakeHeady+1].type==NodeType.danger)
	    	{
	    		return false;
	    	}
	    	else if(gameMap[snakeHeadx][snakeHeady-1].type==NodeType.danger)
	    	{
	    		return false;
	    	}
	    	else
	    	{
	    		return true;
	    	}
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            while (true) {
                String line = br.readLine();
               
                for (int i = 0; i < gameMap.length; i++) {
                	for (int j = 0; j < gameMap.length; j++) {
						gameMap[i][j]=new Node(i,j,null);
					}
				}
                setBoard();
                if (line.contains("Game Over")) {
                    break;        
                }
                String apple1 = line;
                String[] applePos=apple1.split(" ");
                newApplex=Integer.parseInt(applePos[0]);
                newAppley=Integer.parseInt(applePos[1]);
                
                setAppleHP();
                applex=newApplex;
                appley=newAppley;
                setApple();
                
                //do stuff with apple
                int nObstacles = 3;
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    /// do something with obs
                    
                    	String[] fullObs = obs.split(" ");
                    
                    for (int i = 0; i < fullObs.length; i++) {
	
                    	String[] part1= fullObs[i].split(",");
                    	int obsx=Integer.parseInt(part1[0]);
                    	int obsy=Integer.parseInt(part1[1]);
	
                    	setObs(obsx,obsy);

                    }
                    	
                }
                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) {
                        //hey! That's me :)
                    	

                    	String[] mySnake=snakeLine.split(" ");
                    	String[] headPos=mySnake[3].split(",");
                    	String[] direction=mySnake[4].split(",");
                    	//String[] TailPos=mySnake[mySnake.length-1].split(",");
                    	
                    	//snakeTailx=Integer.parseInt(TailPos[0]);
                    	//snakeTaily=Integer.parseInt(TailPos[1]);
                    	
                    	snakeDirx=Integer.parseInt(direction[0]);
                    	snakeDiry=Integer.parseInt(direction[1]);
                    	
                    	snakeHeadx=Integer.parseInt(headPos[0]);
                    	snakeHeady=Integer.parseInt(headPos[1]);
                    	//setSnake(snakeHeadx,snakeHeady);
                    	//plotSnakes(gameMap,snakeHeadx,snakeHeady,snakeDirx,snakeDiry);
                    	mySnakeDist = snakeDist(snakeHeadx,snakeHeady);
                    }
                    
                    String[] otherSnakes=snakeLine.split(" ");
                    for (int j = 3; j < otherSnakes.length-1; j++) {

                    	{
	                    	String[] others=otherSnakes[j].split(",");
	                    	String[] direction=otherSnakes[j+1].split(",");

	                    	int directionx=Integer.parseInt(direction[0]);
	                    	int directiony=Integer.parseInt(direction[1]);
	                    	
							int otherx=Integer.parseInt(others[0]);
							int othery=Integer.parseInt(others[1]);
							if(j==3)
							{
								otherHeadx=otherx;
								otherHeady=othery;
								otherDirx=directionx;
								otherDiry=directiony;
							}
							
							
							
							plotSnakes(otherx,othery,directionx,directiony);
							
                    	}
                    	if(i!=mySnakeNum)
                    	{
                    		snakeHelmet(otherHeadx,otherHeady,otherDirx,otherDiry);
                    		otherSnakeDist=snakeDist(otherHeadx,otherHeady);
                    		if(minDist>otherSnakeDist)
							{
								minDist=otherSnakeDist;
							}
                    	}
					}
                    //do stuff with snakes
                } 
                // finished reading, calculate move:
                
                if(!checkSafe()&&dangerApple())
                {
                	
                  
                	if(distToBait()>=1&&mySnakeDist>=minDist)
                	{
                	  setApple();
                	  Node start= new Node(snakeHeadx,snakeHeady,null);
                      Node end= new Node(idlex,idley,null);
                      Node node=findPath(start,end);
                      if(node==null)
                      {
                      	 setIdle();
                      	 start= new Node(snakeHeadx,snakeHeady,null);
                         end= new Node(idlex,idley,null);
                         node=findPath(start,end);
                         if(node==null||node.parent==null)
                         {
                        	 System.out.println(0);
                         }
                         else
                         {
                        	 printPath(node);
                         }
                      }
                      else
                      {
                      	printPath(node);
                      }
                      
                	}
                	else
                	{
                		 Node start= new Node(snakeHeadx,snakeHeady,null);
                         Node end= new Node(applex,appley,null);
                         Node node=findPath(start,end);
                        if(node==null)
                        {
                        	 start= new Node(snakeHeadx,snakeHeady,null);
                             end= new Node(idlex,idley,null);
                             node=findPath(start,end);
                            if(node==null||node.parent==null)
                            {
                            	System.out.print(0);
                            }
                            else
                            {
                            	printPath(node);
                            }
                        }
                         else
                         {
                         	printPath(node);
                         }
                	}
//                  	
                }
                else
                {
                	  Node start= new Node(snakeHeadx,snakeHeady,null);
                      Node end= new Node(applex,appley,null);
                      Node node=findPath(start,end);
                      if(node==null)
                      {
                      	System.out.print(0);
                      }
                      else
                      {
                      	printPath(node);
                      }
//                  	
                }
                
//                Node start= new Node(snakeHeadx,snakeHeady,null);
//                Node end= new Node(applex,appley,null);
//                Node node=findPath(start,end);
//                if(node==null)
//                {
//                	System.out.print(0);
//                }
//                else
//                {
//                	printPath(node);
//                }
                
              
//            	for (int i = 0; i < boardView.length; i++) {
//					for (int j = 0; j < boardView.length; j++) {
//						System.err.print(boardView[i][j]);
//					}
//					System.err.println();
//				}
//            	System.err.println();
            	//System.err.println(appleHP);
            	//System.err.println(worthIt());
            	gameMap = new Node[50][50];
            	minDist=100;
//            	boardView= new String[50][50];
            
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}