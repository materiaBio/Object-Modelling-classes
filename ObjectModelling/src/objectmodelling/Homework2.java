
package objectmodelling;

import java.util.HashSet;


interface OMOSetView {

    boolean contains(int element); // testuje na přítomnost prvku v množině

    int[] toArray(); //vrátí kopii prvků množiny v poli (na pořadí prvků nezáleží)

    OMOSetView copy(); //vrátí kopii množiny
    
}

// třída reprezentující obecnou množinu, definuje metody add/remove pro přidávání/odebírání prvků


class OMOSet implements OMOSetView {

    public HashSet<Integer> hashStorage = new HashSet();;
    
    OMOSet(){
       this.hashStorage = new HashSet();
   }
    
//    OMOSet(HashSet<Integer> hashStorage){
//       this.hashStorage = new HashSet();
//   }
//    

    public void add(int element) {
       // System.out.println("OMOSet add(" + element + ")");
        this.hashStorage.add(element);
    }

    public void remove(int element) {
       // System.out.println("OMOSet remove(" + element + ")");
        this.hashStorage.remove(element);
    }
    

    @Override
    public boolean contains(int element) {
       // System.out.println("OMOSet contains(" + element + ")");
       return this.hashStorage.contains(element);
    }

//    @Override
//    public OMOSetView copy() {
//        return new OMOSet((HashSet)this.hashStorage.clone());
//    }
    
    @Override
    public int[] toArray() {
        System.out.println("OMOSet toArray()");
        int[] arrayInteger = new int[this.hashStorage.size()];
        int i = 0;
        for (Integer value : this.hashStorage) {
            arrayInteger[i++] = value;
        }
        return arrayInteger;
    }

  
    @Override
    public OMOSetView copy() {
System.out.println("class OMOSet - OMOSetView copy()");
        OMOSet omoCopy = new OMOSet();
        for (Integer elementHash : this.hashStorage) {
            omoCopy.add(elementHash);
        }
        return omoCopy;  
    }
    
}


// třída reprezentující sjednocení dvou množin: A sjednoceno B
class OMOSetUnion implements OMOSetView {
  
    OMOSetView setA;
    OMOSetView setB;

    OMOSetUnion(OMOSetView setA, OMOSetView setB) {
        this.setA = setA;
        this.setB = setB;
    }
    

    // testuje na přítomnost prvku v množině
    @Override
    public boolean contains(int element) {
        System.out.println(" OMOSetUnion contains(int element) ");
        return union().contains(element);
    }

//vrátí kopii prvků množiny v poli (na pořadí prvků nezáleží)
    @Override
    public int[] toArray() {
        System.out.println(" OMOSetUnion toArray() ");
        return union().toArray();
    }


    public OMOSetView union() {
        System.out.println(" union() ");

        OMOSet setC = new OMOSet();

        int[] A = this.setA.toArray();
        int[] B = this.setB.toArray();
        
        for (Integer a: A) {
            setC.add(a);
       }
        for (Integer b: B) {
            setC.add(b);
       }
        
        return setC;
    }

    @Override
    public OMOSetView copy() {
        System.out.println(" OMOSetUnion copy() ");
        return new OMOSetUnion(this.setA.copy(),this.setB.copy());
    }
}

// třída reprezentující průnik dvou množin: A průnik B
class OMOSetIntersection implements OMOSetView {

    OMOSetView setA;
    OMOSetView setB;

    OMOSetIntersection(OMOSetView setA, OMOSetView setB) {
        this.setA = setA;
        this.setB = setB;
    }

// metody rozhraní OMOSetView
    @Override
    public boolean contains(int element) {
        System.out.println("OMOSetIntersection contains(int element)");

        return intersection().contains(element);
    }

//vrátí kopii prvků množiny v poli (na pořadí prvků nezáleží)
    @Override
    public int[] toArray() {
        System.out.println("OMOSetIntersection toArray() ");
        return intersection().toArray();
    }

    public OMOSetView intersection() {
        System.out.println("intersection() ");
       
        OMOSet setC = new OMOSet();
        OMOSet setD = new OMOSet();
      
      for(Integer a: this.setA.toArray()){
         setC.add(a);
    }
      for(Integer b: this.setB.toArray()){
         setD.add(b);
    }
      setC.hashStorage.retainAll(setD.hashStorage);
      
      
    return setC;
  }

    @Override
    public OMOSetView copy() {
        System.out.println("OMOSetIntersection copy() ");
        return new OMOSetIntersection(this.setA.copy(),this.setB.copy());
    }
}

// třída reprezentující A\B: doplněk množiny B vzhledem k množině A:  A\B = { x | x∈A ∧ x∉B }
class OMOSetComplement implements OMOSetView {

    OMOSetView setA;
    OMOSetView setB;

    OMOSetComplement(OMOSetView setA, OMOSetView setB) {
        this.setA = setA;
        this.setB = setB;
    }

    
    public OMOSetView complement() {
        System.out.println("complement()");
         OMOSet setC = new OMOSet();
         OMOSet setD = new OMOSet();

        for (Integer a : this.setA.toArray()) {
            setC.add(a);
        }
        for (Integer b : this.setB.toArray()) {
            setD.add(b);
        }
        setC.hashStorage.removeAll(setD.hashStorage);

        return setC;
    }

    @Override
    public boolean contains(int element) {
        System.out.println("OMOSetComplement contains(int element)");
        return complement().contains(element);
    }

    @Override
    public int[] toArray() {
        System.out.println("OMOSetComplement toArray()");
        return complement().toArray();
    }

    @Override
    public OMOSetView copy() {
        System.out.println("OMOSetComplement copy()");
         return new OMOSetComplement(this.setA.copy(),this.setB.copy());
    }

}

// třída reprezentující množinu sudých čísel 
class OMOSetEven implements OMOSetView {

    OMOSetView setA;

    OMOSetEven(OMOSetView setA) {
        this.setA = setA;
    }
    

    public OMOSetView even() {
        
        System.out.println("even()");
        
        OMOSet setC = new OMOSet();
        
      for (Integer i : this.setA.toArray()) {
            if (i % 2 == 0) {
                setC.add(i);
            }
        }
        return setC;
    }

    @Override
    public boolean contains(int element) {
        System.out.println("OMOSetEven contains(int element)");
         return even().contains(element);
    }

    @Override
    public int[] toArray() {
        System.out.println("OMOSetEven toArray() ");
        return even().toArray();
    }

    @Override
    public OMOSetView copy() {
        System.out.println("OMOSetEven copy() ");
        return new OMOSetEven(this.setA.copy());
    }
}
