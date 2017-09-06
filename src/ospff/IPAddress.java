package ospff;

/**
 * <p>Title: swarmNet</p>
 * <p>Description: The swarm network based on DEVS theory</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Arizona State University</p>
 * @author Ahmet Zengin
 * @version 1.0
 */

public class IPAddress {

  private int address_int; // The whole IP address as an integer
  /**
   * this constructs an IP address from the four byte parameters.
   * @param byte1
   * @param byte2
   * @param byte3
   * @param byte4
   */
  public IPAddress(int byte1, int byte2, int byte3, int byte4) {
    address_int = ( (byte1 & 0xff) << 24) | ( (byte2 & 0xff) << 16) |
        ( (byte3 & 0xff) << 8) | (byte4 & 0xff);
  }

  /**
   * constructs IPAddress from an integer
   * @param address
   */
  public IPAddress(int address) {
    address_int = address;
  }

  /**
   *  Make a copy of an existing IP address.
   */
  public IPAddress(IPAddress copy) {
    address_int = copy.address_int;
  }

  /**
   *
   * @param intAddress
   * @return
   */
  public int getByte1() {
    return (address_int & 0xff);
  }

  /**
   *
   * @param intAddress
   * @return
   */
  public int getByte2() {
    return (address_int >> 8) & 0xff;
  }

  /**
   *
   * @param intAddress
   * @return
   */
  public int getByte3() {
    return (address_int >> 16) & 0xff;
  }

  /**
   *
   * @param intAddress
   * @return
   */
  public int getByte4() {
    return (address_int >> 24) & 0xff;
  }
  public void setByte4(int a){
	  address_int= ( ((a) & 0xff) << 24) | ( (getByte3() & 0xff) << 16) |
      ( (getByte2() & 0xff) << 8) | (getByte1() & 0xff);
  }
  /**
   * compares for equality
   * @param copy
   * @return
   */
  public boolean equals(IPAddress copy) {
    return address_int == copy.address_int;
  }

  /**
   * Return the IP address.
   * @return
   */
  public int getIntegerAddress() {
    return address_int;
  }

  /**
   * printouts the raw representation of this address
   * @return
   */
  public String toString() {
    return ( (address_int >> 24) & 0xff) + "." +
        ( (address_int >> 16) & 0xff) + "." +
        ( (address_int >> 8) & 0xff) + "." +
        (address_int & 0xff);
  }
}