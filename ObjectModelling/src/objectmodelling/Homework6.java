//package objectmodelling;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.ImmutableList.Builder;
//import java.util.Iterator;
//import java.util.LinkedList;
// 
//
//
// class HorizontalFlip implements ActuatorVisitor{
//
//        @Override
//        public Actuator visitUp(Up actuator) {
//            System.out.println("up");
//            Actuator up = new Up();
//            return up;
//            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        @Override
//        public Actuator visitDown(Down actuator) {
//            System.out.println("down");
//             Actuator down = new Down();
//            return down;
//       }
//
//        @Override
//        public Actuator visitLeft(Left actuator) {
//            System.out.println("left");
//            Actuator right = new Right();
//            return right;
//      }
//
//        @Override
//        public Actuator visitRight(Right actuator) {
//            System.out.println("right");
//            Actuator left= new Left();
//            return left;
//    }
//        @Override
//     public Actuator visitParallellyComposingActuator(ParallellyComposingActuator actuator) {
//         System.out.println("parallel");
//       
//           //public ParallellyComposingActuator(ImmutableList<Actuator> motors)- ImmutableList vstupni parametr
//           Builder<Actuator> moto = new ImmutableList.Builder<>();
//           //Functional Opetartion, NetBeans wanted me to write this way, I used usual forEach
//
////           actuator.motors.stream().forEach((i) -> {
////               moto.add(i.accept(this));
////            });
//         
//            for (Actuator act: actuator.motors) {
//                moto.add(act.accept(this));
//         }
//            return new ParallellyComposingActuator(moto.build());
//     }
//
//  
//        @Override
//        public Actuator visitAmplifyingActuator(AmplifyingActuator actuator) {
//            System.out.println("amplif");
//            Actuator amplifyingAct = new AmplifyingActuator(actuator.motor.accept(this),actuator.factor);
//            return amplifyingAct;
//        }
//
//
// 
//        @Override
//        public Actuator visitSequentiallyComposingActuator(SequentiallyComposingActuator actuator) {
//            System.out.println("sequent");
//            
//             return new Actuator(){
//                 
//                @Override
//                 public Actuator accept(ActuatorVisitor v) {
//                     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                 }
//
//                 @Override
//                 public void actuate(Movable movable) {
//                     actuator.actuate(new DecoratorMovable(movable));
//                }
//        };
//  }
//}
//
//class DecoratorMovable implements Movable {
//    
//    Movable movable;
//   
//    public DecoratorMovable(Movable movable) {
//        System.out.println("decor");
//        this.movable = movable;
//}
//  
//    @Override
//    public void move(int dx, int dy){
//       movable.move(-dx, dy);
//}
//}
