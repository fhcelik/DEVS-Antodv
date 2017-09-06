package honeycomb.BRITE.Visualizer;
import java.awt.Color;
import java.awt.Graphics;
import java.util.StringTokenizer;

/**
 * Representation of a node from a BRITE topology.
 */
public class Node
{
	/**
	 * The id of the node.
	 */
	private String name;
	private String x1;
	private String y1;
	private int id = -1;
	private Color m_tBlue = new Color(120, 255, 120, 20);
	private Color m_tyellow = new Color(0, 0,20, 20);
	/**
	 * The x coordinate.
	 */
	private double x = -1.0;

	/**
	 * The y coordinate.
	 */
	private double y = -1.0;

	/**
	 * Which Autonimous System (AS) does this belong to?
	 */
	private int AS = -1;

	/**
	 * Draw different colours for different AS groups.
	 */
	public final Color colours[] = {Color.black, Color.blue, Color.cyan, Color.gray, Color.green, Color.magenta, Color.orange, Color.pink, Color.red, Color.yellow};
	
	/**
	 * Default constructor
	 */
	public Node(final String str)
	{
		
		StringTokenizer t = new StringTokenizer(str);
		name=t.nextToken(); 
		//id = Integer.parseInt(t.nextToken());
		x = Double.parseDouble(t.nextToken());
		y = Double.parseDouble(t.nextToken());
		
		//discard the in and out degree.
	//	name=String.valueOf(name);
		x1=String.valueOf(x);
		y1=String.valueOf(y);
		t.nextToken();
		t.nextToken();
		AS = Math.abs(Integer.parseInt(t.nextToken()));
		
	}

	/**
	 * Paint this node's position
	 *
	 * @param g Graphics object.
	 */
	public void paint(Graphics g)
	{

		g.setColor(colours[1]);
		g.fillOval((int)x-2, (int)y-2, 10, 10);
	//	g.setColor(m_tyellow);	
	//	g.fillOval((int)x-127, (int)y-127,250,250);
		
		g.setColor(colours[0]);
		g.drawString(name,(int)x, (int)y);
	
	}
	public void boya(Graphics g)
	{

		g.setColor(colours[4]);
		g.fillOval((int)x, (int)y, 20, 20);
//		g.setColor(m_tBlue);	
	//	g.fillOval((int)x-127, (int)y-127,250,250);
		g.drawOval((int)x-302, (int)y-302,600,600);
		g.setColor(colours[0]);
		g.drawString(name,(int)x, (int)y);
		g.drawString(x1,(int)x+25, (int)y);
		g.drawString(y1,(int)x+65, (int)y);
	}
	/**
	 * Return the x value.
	 *
	 * @return x;
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Return the y value.
	 *
	 * @return y;
	 */
	public double getY()
	{
		return y;
	}

}
