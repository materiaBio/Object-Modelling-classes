package objectmodelling;

/**
 *
 * @author Tamara
 */
public class Homework1 {
    static int count = 1;
    int countInstance = 1;
    
    public boolean f(){
        return true;
   }
    
    public static boolean g(){
        return false;
    }
    
    public int h(){
        return countInstance++;
    
    }
    public int i(){
        return count++;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Homework1 home = new Homework1();
        System.out.println("vrati vzdy true:  home.f() =            " +  home.f());
        System.out.println("vrati vzdy false: home.g() =            " +  home.g());
        System.out.println("pro kazdou instanci vzlast : home.h() = " +  home.h());
        
        System.out.println("pro vsech spolecne : home.i() =         " +  home.i());
        
        Homework1 homeSecond = new Homework1();
        
        System.out.println("2. vrati vzdy true:  home.f() =            " +  homeSecond.f());
        System.out.println("2. vrati vzdy false: home.g() =            " +  homeSecond.g());
        System.out.println("2. pro kazdou instanci vzlast : home.h() = " +  homeSecond.h());
        System.out.println("2. pro vsech spolecne : home.i() =         " +  homeSecond.i());
        System.out.println("2. pro kazdou instanci vzlast : home.h() = " +  homeSecond.h());
        System.out.println("2. pro vsech spolecne : home.i() =         " +  homeSecond.i());
        System.out.println("2. pro kazdou instanci vzlast : home.h() = " +  homeSecond.h());
        System.out.println("2. pro vsech spolecne : home.i() =         " +  homeSecond.i());
        
       
    }
    
}
