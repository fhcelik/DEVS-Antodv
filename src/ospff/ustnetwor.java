package ospff;



import java.awt.Dimension;
import java.awt.Point;

import model.modeling.IODevs;
import model.modeling.componentIterator;



import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

public class ustnetwor extends ViewableDigraph {
	 private  networ n[];
	 protected IODevs d;
	 protected componentIterator ci;
  public ustnetwor(){
    this("ustnetwor", 1,1);
  }

  public ustnetwor(String name, int i�leme_zaman�,int ip){
    super(name);
    make(i�leme_zaman�,ip);

 }

  protected void make(int i�leme_zaman�,int ip){
		int ag_sayisi=10;
	 	int a=0;
	    addInport("inEvent");
	   
	    addOutport("outEvent");
	n=new networ[ag_sayisi];
	for (int i=1;i<ag_sayisi;i++){
	n[i] = new networ("net"+i, 1,ip++);
	add(n[i]);
	}
  ci = getComponents().cIterator();

  for (int i = 0; i < getComponents().size(); i++) {
    d = ci.nextComponent();
    if (d.getName().substring(0,4).equals("link")) continue;
    addCoupling(d, "outEvent",this, "outEvent");
    addCoupling(this, "inEvent", d, "inEvent");

  }

	addCoupling(n[1], "eternet_��k���0", n[2], "eternet_giri�i0");
	addCoupling(n[2], "eternet_��k���0",n[1], "eternet_giri�i0" );
	for (int i=2;i<ag_sayisi-1;i++){
	addCoupling(n[i], "eternet_��k���1", n[i+1], "eternet_giri�i0");
	addCoupling(n[i+1], "eternet_��k���0", n[i], "eternet_giri�i1");
	a=i;
	}
	addCoupling(n[a+1], "eternet_��k���1", n[1], "eternet_giri�i1");
	addCoupling(n[1], "eternet_��k���1", n[a+1], "eternet_giri�i1");

  initialize();

}
    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(944, 537);
        ((ViewableComponent)withName("net3")).setPreferredLocation(new Point(488, 322));
        ((ViewableComponent)withName("net1")).setPreferredLocation(new Point(27, 324));
        ((ViewableComponent)withName("net2")).setPreferredLocation(new Point(26, 4));
        ((ViewableComponent)withName("net4")).setPreferredLocation(new Point(495, 9));
    }
}