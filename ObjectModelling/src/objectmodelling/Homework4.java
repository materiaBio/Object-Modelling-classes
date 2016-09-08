package objectmodelling;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;







public class Homework4 extends MessageVisitor {
   
    public Homework4(Peer peer) {
        super(peer);
  }
    
    
    // zde doplnte svuj kod
    /**
     * void have(PeerInterface sender, int blockIndex);
     * Sdeli vzdalenemu uzlu, ze uzel sender prave obdrzel blok s indexem
     * blockIndex.
     */
    
     /*
     * Zpracuje zpravu "have": v lokalnim uzlu vyznaci, ze dany vzdaleny uzel ma
     * k dispozici blok s danym indexem.
     * Vzdy vrati false.
     */
   
    private void fixtrue( Map<PeerInterface, boolean[]> map, PeerInterface sender, int blockIndex){
        //System.out.println(blockIndex);
        map.get(sender)[blockIndex] = true;
          }
  
    @Override
    boolean visitHaveMessage(HaveMessage message) {
        
         int indexBlock = message.blockIndex;
        // System.out.println("have: index = " + message.blockIndex + " sender" + message.sender);
        Map<PeerInterface, boolean[]> map = this.peer.peers2BlocksMap;
       // System.out.println("this.peer: " + this.peer.totalBlocksCount);
       // System.out.println("sender:    "+message.sender);
       if(map.containsKey(message.sender)){
         fixtrue(map, message.sender, indexBlock);
       }
      return false;
    }
  

    /*
     * Zpracuje zpravu "request": pokud ma lokalni uzel pozadovany blok k
     * dispozici, obratem ho posle vzdalenemu uzlu pomoci zpravy "piece". Pokud
     * ne, pozadavek ignoruje.
     *
     * Vzdy vrati false.
     */
    

    private boolean ifTrue(Peer p, int blockIndex){
        
    return (p.data[blockIndex]!=null)==true;
    }
    
    @Override
    boolean visitRequestMessage(RequestMessage message) {
        if(ifTrue(this.peer,message.blockIndex)){
             message.sender.piece(this.peer, message.blockIndex,this.peer.data[message.blockIndex]);
       }
        
     return false;
}
    
 
    
    /*
     * Zpracuje zpravu "piece": ulozi obdrzena data do lokalniho uzlu, zvysi
     * pocet stazenych bloku a vsem vzdalenym uzlum (vcetne toho, od ktereho
     * data obdrzel) rozesle zpravu "have".
     *
     * Vrati true, pokud lokalni uzel stahl vsechny bloky, false jinak.
     */
    
    @Override
    boolean visitPieceMessage(PieceMessage message) {
        
        this.peer.data[message.blockIndex] = message.data;
      this.peer.downloadedBlocksCount++;
        
      Iterator iter =  this.peer.peers2BlocksMap.keySet().iterator();
      
       while(iter.hasNext()){
           PeerInterface p = (PeerInterface)iter.next();
           p.have(this.peer, message.blockIndex);
    }
        //this.peer.have(message.sender, message.blockIndex);
        
      if(this.peer.downloadedBlocksCount==this.peer.totalBlocksCount) return true;
        return false;
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
     /*
     * Zpracuje zpravu "idle": vybere nejvzacnejsi(самый важный) jeste nestazeny blok a zazada
     * o nej u nektereho z jeho vlastniku. Nejvzacnejsi blok je takovy, ktery
     * vlastni nejmene vzdalenych uzlu. Pokud je nejvzacnejsich bloku nekolik,
     * vybere jeden z nich.
     *
     * Vzdy vrati false.
     */
    
    public List<Integer> list() {
        List<Integer> list = new LinkedList();
        for (int index = 0; index <this.peer.data.length; index++) {
            if(this.peer.data[index]==null){
           
                list.add(index);
          }
   }
        return list;
 }
    

    @Override
    boolean visitIdleMessage(IdleMessage message) {
       
      List<Integer> list = list();//list indexu nazpracovanych bloku
      //System.out.println(list);
      PeerInterface who=null;
      int whoHaveVzacnyIndex = this.peer.data.length;
      int hledaVzacny=0;
      int allImportant = 0;
      
        for (int notDownloadIndex : list) {
             hledaVzacny = 0;
             PeerInterface smb = null;
            for(PeerInterface pi: this.peer.peers2BlocksMap.keySet()){
                
                if(this.peer.peers2BlocksMap.get(pi)[notDownloadIndex]==true){
                    hledaVzacny++;
                    smb = pi;
              }
          }
            if(whoHaveVzacnyIndex>hledaVzacny){
                whoHaveVzacnyIndex = hledaVzacny;
                allImportant = notDownloadIndex;
                who=smb;
            }
 }
       who.request(this.peer,allImportant);
       return false; // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


/**
* Instance implementujici PeerInterface reprezentuje lokalni uzel nebo uzel
* pripojeny pres sit.
*/
interface PeerInterface {
 /**
 * Sdeli vzdalenemu uzlu, ze uzel sender prave obdrzel blok s indexem
 * blockIndex.
 */
 void have(PeerInterface sender, int blockIndex);
 /**
 * Pozada vzdaleny uzel o blok s indexem blockIndex.
 */
 void request(PeerInterface sender, int blockIndex);
 /**
 * Zasle vzdalenemu uzlu blok s indexem blockIndex.
 */
 void piece(PeerInterface sender, int blockIndex, byte[] data);
}


/**
* Instance tridy Peer reprezentuje lokalni uzel.
*/
class Peer implements PeerInterface {
 /**
 * Fronta nezpracovanych zprav.
 */
 final Queue<Message> messageQueue = new LinkedBlockingQueue<>();
 /**
 * Mapovani z uzlu na bitovou mapu urcujici ktere bloky ma dany uzel k
 * dispozici.
 */
 final Map<PeerInterface, boolean[]> peers2BlocksMap;
 /**
 * Celkovy pocet bloku ve stahovanem souboru.
 */
 final int totalBlocksCount;
 /**
 * Pole se stazenymi bloky.
 */
 final byte[][] data;
 /**
 * Pocet stazenych bloku.
 */
 int downloadedBlocksCount = 0;
 public Peer(Map<PeerInterface, boolean[]> peers2BlocksMap, int
totalBlocksCount) {
 this.peers2BlocksMap = peers2BlocksMap;
 this.totalBlocksCount = totalBlocksCount;
 data = new byte[totalBlocksCount][];
 }
 /**
 * Prijme zpravu "have" a prida ji do fronty zprav.
 */
 public void have(PeerInterface sender, int blockIndex) {
 messageQueue.add(new HaveMessage(blockIndex, sender));
 }
 /**
 * Prijme zpravu "request" a prida ji do fronty zprav.*/
 public void request(PeerInterface sender, int blockIndex) {
 messageQueue.add(new RequestMessage(blockIndex, sender));
 }
 /**
 * Prijme zpravu "piece" a prida ji do fronty zprav.
 */
 public void piece(PeerInterface sender, int blockIndex, byte[] data) {
 messageQueue.add(new PieceMessage(blockIndex, data, sender));
 }
 /**
 * Vyzvedne nejstarsi zpravu z fronty zprav a zpracuje ji pomoci zadaneho
 * navstevnika. Pokud ve fronte zadna zprava neni, zasle sam sobe a zpracuje
 * zpravu "idle". Vrati true v pripade, ze tento uzel stahnul vsechny bloky,
 * false jinak.
 */
 boolean processMessage(MessageVisitor visitor) {
 return (messageQueue.isEmpty() ? new IdleMessage(this) :
messageQueue.poll()).accept(visitor);
 }
}
/**
* Instance tridy MessageVisitor reprezentuji navstevniky zpracovavajici zpravy.
*/
abstract class MessageVisitor {
 final Peer peer;
 public MessageVisitor(Peer peer) {
 this.peer = peer;
 }
 /*
 * Zpracuje zpravu "have": v lokalnim uzlu vyznaci, ze dany vzdaleny uzel ma
 * k dispozici blok s danym indexem.
 *
 * Vzdy vrati false.
 */
 abstract boolean visitHaveMessage(HaveMessage message);
 /*
 * Zpracuje zpravu "request": pokud ma lokalni uzel pozadovany blok k
 * dispozici, obratem ho posle vzdalenemu uzlu pomoci zpravy "piece". Pokud
 * ne, pozadavek ignoruje.
 *
 * Vzdy vrati false.
 */
 abstract boolean visitRequestMessage(RequestMessage message);
 /*
 * Zpracuje zpravu "piece": ulozi obdrzena data do lokalniho uzlu, zvysi
 * pocet stazenych bloku a vsem vzdalenym uzlum (vcetne toho, od ktereho
 * data obdrzel) rozesle zpravu "have".
 *
 * Vrati true, pokud lokalni uzel stahl vsechny bloky, false jinak.
 */
 abstract boolean visitPieceMessage(PieceMessage message);
 /*
 * Zpracuje zpravu "idle": vybere nejvzacnejsi jeste nestazeny blok a zazada
 * o nej u nektereho z jeho vlastniku. Nejvzacnejsi blok je takovy, ktery
 * vlastni nejmene vzdalenych uzlu. Pokud je nejvzacnejsich bloku nekolik,
 * vybere jeden z nich.
 *
 * Vzdy vrati false.
 */abstract boolean visitIdleMessage(IdleMessage message);
}
abstract class Message {
 final PeerInterface sender;
 public Message(PeerInterface sender) {
 this.sender = sender;
 }
 abstract boolean accept(MessageVisitor visitor);
}
class HaveMessage extends Message {
 final int blockIndex;
 public HaveMessage(int blockIndex, PeerInterface sender) {
 super(sender);
 this.blockIndex = blockIndex;
 }
 boolean accept(MessageVisitor visitor) {
 return visitor.visitHaveMessage(this);
 }
}
class RequestMessage extends Message {
 final int blockIndex;
 public RequestMessage(int blockIndex, PeerInterface sender) {
 super(sender);
 this.blockIndex = blockIndex;
 }
 boolean accept(MessageVisitor visitor) {
 return visitor.visitRequestMessage(this);
 }
}
class PieceMessage extends Message {
 final int blockIndex;
 final byte[] data;
 public PieceMessage(int blockIndex, byte[] data, PeerInterface sender) {
 super(sender);
 this.blockIndex = blockIndex;
 this.data = data;
 }
 boolean accept(MessageVisitor visitor) {
 return visitor.visitPieceMessage(this);
 }
}
class IdleMessage extends Message {
 public IdleMessage(PeerInterface sender) {
 super(sender);
 }
 @Override
 boolean accept(MessageVisitor visitor) {
 return visitor.visitIdleMessage(this);
 }
}






 




 








 

 

 

