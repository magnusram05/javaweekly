public class Cafe {
  public static void main(String [] args){
    CoffeeMachine coffeeMachine = new CoffeeMachine();
    coffeeMachine.getEspresso();
  }

  static class CoffeeMachine {

    public void getEspresso(){
      grind();      
      workupSteam();
      blend();    
    }
  
    private void grind()         {  System.out.println("Grinding beans!");               }
    private void workupSteam()   {  System.out.println("Working up steam!");             }  
    private void blend()         {  System.out.println("Delivering the magic potion!");  }
  }

}
