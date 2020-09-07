
package condicionescompetencias;
import javax.swing.JTextArea;

public class Hilo extends Thread{
    private JTextArea area;
    private RCompartido rc;
    private boolean pausado = false;   
    private boolean corriendo = true;    
    
    Hilo(JTextArea area, RCompartido rc){
        this.area = area;
        this.rc = rc;
    }
   
    @Override
    public void run(){
        try{
            while(true){
                rc.setDatoCompartido(this.getName());
                area.append(rc.getDatoCompartido()+ "\n");
                sleep(1000);
                synchronized(this){
                    if(pausado)
                        wait();
                    if(!corriendo)
                        join();
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }    
    }
    
    public void pausar(){
        this.pausado = true;
    }
    
    public void continuar(){
        synchronized(this){
            pausado = false;
            notifyAll();
        }
    }
    
    public void parar(){
        this.corriendo = false;
    }
}
