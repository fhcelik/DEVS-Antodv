// Export from BRITE topology
// Generator Model Used: Model (3 - ASWaxman):  5 100 100 1  2  0.15000000596046448 0.20000000298023224 1 1 10.0 1024.0 

package swarmNet;

import java.awt.Dimension;
import java.awt.Point;
import swarmNet.Components.IPAddress;
import swarmNet.Framework.AtomicNode;
import view.modeling.ViewableAtomic;
import view.modeling.ViewableComponent;
import view.modeling.ViewableDigraph;

public class Net5 extends ViewableDigraph {
	public Net5() {
		this("Generated Network", 1, 1);
	}

	public Net5(String name, double processing_time, double propagation_delay) {
		super(name);
		make(processing_time, propagation_delay);
	}

	protected void make(double processing_time, double propagation_delay) {
		addInport("in");
		addInport("inEvent");
		addInport("out");
		addInport("outEvent");

		ViewableAtomic src;
		ViewableAtomic dst;
		String src_port_no;
		String dst_port_no;
		ViewableAtomic[] d_nodes = new ViewableAtomic[5];
		for (int i = 0; i < d_nodes.length; i++) {
			d_nodes[i] = new AtomicNode("Router" + i, new IPAddress(i),
					(processing_time));
			add(d_nodes[i]);
		}

		// link or couplings definitions
		src = d_nodes[0];
		dst = d_nodes[1];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[0];
		dst = d_nodes[2];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		//addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		//addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[2];
		dst = d_nodes[1];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[3];
		dst = d_nodes[4];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[3];
		dst = d_nodes[1];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[4];
		dst = d_nodes[1];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		src = d_nodes[4];
		dst = d_nodes[2];
		src_port_no = "NIC" + (src.getNumInports() + 1);
		src.addInport(src_port_no + "-in");
		src.addOutport(src_port_no + "-out");
		dst_port_no = "NIC" + (dst.getNumInports() + 1);
		dst.addInport(dst_port_no + "-in");
		dst.addOutport(dst_port_no + "-out");
		addCoupling(src, src_port_no + "-out", dst, dst_port_no + "-in");
		addCoupling(dst, dst_port_no + "-out", src, src_port_no + "-in");
		
		initialize();
		int x = 0, y = 0;
		preferredSize = new Dimension(1000, 1000);
		for (int i = 0; i < d_nodes.length; i++) {
			if (i != 0 && i % 10 == 0) {
				y += 100;
				x = 0;
			}
			((ViewableComponent) withName("Router" + i))
					.setPreferredLocation(new Point(x, y));
			x += 125;
		}
		preferredSize = new Dimension(508, 32 + 80 * 3);
	}
}
