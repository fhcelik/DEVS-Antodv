/*      Copyright 2002 Arizona Board of regents on behalf of 
 *                  The University of Arizona 
 *                     All Rights Reserved 
 *         (USE & RESTRICTION - Please read COPYRIGHT file) 
 * 
 *  Version    : DEVSJAVA 2.7 
 *  Date       : 08-15-02 
 */ 

package GenCol;




public class entity extends Object implements EntityInterface{
protected String name;


public entity(){
name = "anEntity";
}
public entity(String nm){
name = nm;
}
public boolean eq(String nm){
return getName().equals(nm);
}

public Object equalName(String nm){
if (eq(nm)) return this;
else return null;
}

public boolean equals(Object o){    //overrides pointer equality of Object
if  (!(o instanceof entity))return false;
else return eq(((entity)o).getName());
}

public ExternalRepresentation getExtRep(){
return new ExternalRepresentation.ByteArray();
}

public String getName(){
return name;
}


public synchronized void addSelf(ensembleCollection c){
c.add(this);
}
public synchronized void removeSelf(ensembleCollection c){
c.remove(this);
}

/**/

public String toString(){
return getName();
}

public void print(){
System.out.println(name);
}

}

