//package objectmodelling;
//
//import com.google.common.collect.ImmutableList;
//import com.google.common.base.Predicate;
//import java.util.Iterator;
////import java.util.List;
//
//
//
////instance třídy Up při jednom volání metody actuate pohne zadaným objektem o jeden pixel nahoru, 
//class Up implements Actuator {
// 
//    @Override
//    public void actuate(Movable movable) {
//        movable.move(0,-1);
//   }
//}
//
////instance třídy Down pohne zadaným objektem o jeden pixel dolů, 
//class Down implements Actuator {
// 
//    @Override
//    public void actuate(Movable movable) {
//        movable.move(0, 1);
//   }
//}
// 
//// instance třídy Left pohne zadaným objektem o jeden pixel doleva, 
//class Left implements Actuator {
// 
//    @Override
//    public void actuate(Movable movable) {
//        movable.move(-1, 0);
//   }
//}
// 
////instance třídy Right pohne zadaným objektem o jeden pixel doprava,
//class Right implements Actuator {
// 
//    @Override
//    public void actuate(Movable movable) {
//        movable.move(1, 0);
// }
//}
// 
////instance třídy ParallellyComposingActuator sečte pohyby všech aktuátorů zadaných v konstruktoru do jednoho a 
//class ParallellyComposingActuator implements Actuator {
//    
//    ImmutableList<Actuator> motors;
// 
//    //konstruktor
//    public ParallellyComposingActuator(ImmutableList<Actuator> motors) {
//      this.motors = motors;
// }
// 
//    @Override
//    public void actuate(Movable movable) {
//        for(Actuator act: motors){
//            act.actuate(movable);
//   }
//  }
//}
// 
////instance třídy AmplifyingActuator zopakuje pohyb aktuátorem zadaným v konstruktoru factor-krát. 
//class AmplifyingActuator implements Actuator {
//    Actuator motor;
//    int factor;
// 
//    //konstruktor
//    public AmplifyingActuator(Actuator motor, int factor) {
//        this.motor = motor;
//        this.factor = factor;
// 
//    }
// 
//    @Override
//    public void actuate(Movable movable) {
//        int i = 0;
//        while(i<factor){
//            motor.actuate(movable);
//            i++;
//        }
//  }
//}
//
///*Tato třída v konstruktoru dostane (NEKONEČNÝ) seznam dvojic (predikát, aktuátor). 
//Aktuátor z první dvojice vrácené iterátorem používá pro pohyb zadaného objektu tak dlouho,
//dokud první predikát na zadaném objektu vrací true. Poté přejde na druhou dvojici, 
//pak na třetí atd. */
//class SequentiallyComposingActuator implements Actuator {
//
//    Iterable<Pair<Predicate<Movable>, Actuator>> iterable;
//    Pair<Predicate<Movable>, Actuator> pair; 
//    Iterator<Pair<Predicate<Movable>, Actuator>> iter;
//
//    public SequentiallyComposingActuator(Iterable<Pair<Predicate<Movable>, Actuator>> iterable) {
//        System.out.println("SequentiallyComposingActuator");
//        this.iterable = iterable;
//        this.iter = iterable.iterator();
//        this.pair = this.iter.next();
// }
//
//      
//    @Override
//    public void actuate(Movable movable) {
//       
//        if (pair.first.apply(movable)) {
//            pair.second.actuate(movable);
//        } else {
//            pair = iter.next();
//            actuate(movable);
//        }
//    }
//}
//
//
// 
//public class Homework5 {
//  
//
//
//    /* Homework5.getFirstActuator vrátí aktuátor, který zadaným objektem pohne 
//o jeden pixel doprava a o jeden pixel nahoru,
//*/
//    static Actuator getFirstActuator() {
//        System.out.println("1");
//        Actuator right = new Right();//potrebujeme jit doprava
//        Actuator up = new Up();//a nahoru
//        ImmutableList<Actuator> motors = ImmutableList.of(right,up);//s toho udelame immutableList
//        Actuator actuator = new ParallellyComposingActuator(motors);//psali jsme tridu, ktera sečte pohyby všech aktuátorů zadaných v konstruktoru
//        return actuator;
// }
//    
//    /*Homework5.getSecondActuator vrátí aktuátor, který zadaným objektem pohne o jeden pixel doleva, 
//pokud uživatel stiskl klávesu doleva (indikováno metodou Keyboard.leftArrowPressedDown), 
//o jeden pixel doprava, pokud uživatel stiskl klávesu doprava (indikováno metodou Keyboard.rightArrowPressedDown)
//a nepohne nikam, pokud uživatel nestiskl ani jednu z kláves nebo stiskl obě naráz,*/
// 
//    static Actuator getSecondActuator() {
//        System.out.println("2");
//        
//        return (Movable movable) -> {
//            Left left = new Left();
//            Right right = new Right();
//            if(Keyboard.leftArrowPressedDown()&&!Keyboard.rightArrowPressedDown()){
//                left.actuate(movable);
//            }
//            if(Keyboard.rightArrowPressedDown()&&!Keyboard.leftArrowPressedDown()){
//                right.actuate(movable);
//            }
//            else{}
//        };
// }
//       
//        static int count = 0;
//             
// /* 
//
//    Homework5.getThirdActuator(Actuator firstActuator, Actuator secondActuator) 
//    vrátí aktuátor, který pro pohyb střídá dva zadané aktuátory - nejprve desetkrát 
//    použije firstActuator, potom dvacetkrát secondActuator, pak opět desetkrát firstActuator atd.
//*/
//    static Actuator getThirdActuator(final Actuator firstActuator, Actuator secondActuator) {
//        System.out.println("3");
//        
//      //  Actuator first = new AmplifyingActuator(firstActuator, 10);
//       // Actuator second = new AmplifyingActuator(secondActuator, 20);
//        
//        return (Movable movable) -> {
//        
//            if(count<10){firstActuator.actuate(movable);count++;}
//            else if(count<29){secondActuator.actuate(movable);count++;}
//            else if(count==29){secondActuator.actuate(movable); count=0;}
//         
//        };
//    }
//}
//
//     
//         
//interface Movable {
// /** Vrati aktualni polohu. */
// Pair<Integer, Integer> getPosition();
// /** Pohne timto objektem o dx pixelu doprava a dy dolu. */
// void move(int dx, int dy);
//}
//interface Actuator {
// /** Pohne objektem v parametru. */
// void actuate(Movable movable);
//}
///** Instance tridy Pair reprezentuji dvojice hodnot. */
//class Pair<A, B> {
// final A first;
// final B second;
// public Pair(A first, B second) {
// this.first = first;
// this.second = second;
// }
//}
//
//class Keyboard {
// static boolean leftArrowPressedDown() {
// /* code */
//     return false;
// }
// static boolean rightArrowPressedDown() {
// /* code */
//     return false;
// }
//}
//class Assignment7 {
// static Actuator getFirstActuator() {
//     return null;
// }
// 
// static Actuator getSecondActuator() {
//return null;
// }
// static Actuator getThirdActuator(final Actuator firstActuator, Actuator
//secondActuator) {
//     return null;
// }
//}