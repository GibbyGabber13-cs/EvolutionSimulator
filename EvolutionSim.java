import java.util.*;
import java.io.*;
class EvolutionSim{
//facilitates mite eating and death
   public static void eat(ArrayList<obj> arr){
      for(int i = 0; i < arr.size(); i++){
         if(arr.get(i) instanceof mite){
            boolean foundfood = false;
            mite michale = (mite) arr.get(i);
               for(obj object2 : arr){
                  if(object2.posX() - 1 == arr.get(i).posX() || object2.posX() + 1 == arr.get(i).posX() || object2.posX() == arr.get(i).posX()){
                        if(object2.posY() - 1 == arr.get(i).posY() || object2.posY() + 1 == arr.get(i).posY() || object2.posY() == arr.get(i).posY()){
                           if(object2 instanceof berry){
                              //System.out.println(object2.getName());
                              michale.setFood(michale.getFood() + 1);
                              michale.setHealth(michale.getHealth() + 10);
                              object2.delete();
                              foundfood = true;
                           }
                     }
                  }
               }
            if(foundfood == false && michale.food > 0){
               michale.food = michale.food - 1;
            }
            if(arr.get(i).getHealth() <= 0){
               System.out.println(arr.get(i).getName() + " at (" + arr.get(i).posX() + "," + arr.get(i).posY() + ") died of starvation");
               arr.get(i).delete();
            }
            System.out.println(michale.food + " " + michale.getHealth());
         }
      }
   }
   
   //shows the objects from the existance plane on the screen
   public static void print(ArrayList<obj> arr){
      boolean live = false;
      try{
         FileWriter myObj = new FileWriter("EvolutionOutput.txt", true);
            int currentY = 0;
            int largestY = 0;
            int largestX = 0;
            boolean found = false;
            ArrayList<obj> arr2 = new ArrayList();
            for(int i = 0; i < arr.size(); i++){
               if(arr.get(i).posY() > largestY){
                  largestY = arr.get(i).posY();
               }
               if(arr.get(i).posX() > largestX){
                  largestX = arr.get(i).posX(); // geeeeeeeeeeeeeeeeeeeeeeeeeeeet
               }
            }
            //System.out.println("Largest X: " + largestX);
            //System.out.println("Largest Y: " + largestY);
      
            for(int b = 0; b <= largestY; b++){
            
               for(int c = 0; c < arr.size(); c++){
                  if(arr.get(c).posY() == b){
                     arr2.add(arr.get(c));
                  }
               }
               if(arr2.isEmpty()){
                  for(int h = 0; h <= largestX; h++){
                     System.out.print("[_] ");
                     myObj.write("[_] ");
                  }
                  //System.out.println();
               }else{
                  for(int d = 0; d <= largestX; d++){
                     for(int e = 0; e < arr2.size(); e++){
                        if(arr2.get(e).posX() == d && arr2.get(e).posX() <= largestX){
                           System.out.print("[" + arr2.get(e).getName().charAt(arr2.get(e).getName().length() - 1) + "] "); //arr2.get(e).getName().length() - 1
                           myObj.write("[" + arr2.get(e).getName().charAt(arr2.get(e).getName().length() - 1) + "] ");
                           found = true;
                        }
                     }
                     if(found == false){
                        System.out.print("[_] ");
                        myObj.write("[_] ");
                     }
                     found = false;
                  }
               }
               arr2.clear();
               System.out.println();
               myObj.write("\n");
            }
            myObj.write("------------------------------------------------------------------\n");
            myObj.close();
      } catch (IOException e){
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
   }
   //facilitates mite life
   public static ArrayList<obj> birth(ArrayList<obj> arr, int MSD){
      ArrayList<obj> arr2 = new ArrayList();
      for(obj object : arr){
         arr2.add(object);
      }
      for(int i = 0; i < arr.size(); i++){
         if(arr.get(i) instanceof mite){
            mite matt = (mite) arr.get(i);
            if(matt.food > 1){
               Random rand = new Random();
               int randx = rand.nextInt(3) - 1;
               int randy = rand.nextInt(3) - 1;
               int randombiasx = rand.nextInt(3) - 1;
               int randombiasy = rand.nextInt(3) - 1;
               if(randx == 0 && randy == 0){
                  matt.food = matt.food - 1;
                  break;
               }
               boolean found = false;
               for(obj object : arr2){
                  if(object.posX() == (matt.posX() + randx) && object.posY() == (matt.posY() + randy)){
                     found = true;
                  }
               }
               if(found){
                  matt.food = matt.food - 1;
                  break;
               }
               int randHealth = rand.nextInt((matt.getHealth() - 10), (matt.getHealth() + 10));//acedentail mutation SUPERMITES!!!
               mite mason = new mite((matt.getName() + " a"), randHealth, (matt.posX() + randx), (matt.posY() + randy), (((matt.biasx + randombiasx) > 1) ? 1 : ((matt.biasx + randombiasx) < -1) ? -1 : (matt.biasx + randombiasx)), (((matt.biasy + randombiasy) > 1) ? 1 : ((matt.biasy + randombiasy) < -1) ? -1 : (matt.biasy + randombiasy)));
               matt.setHealth(100);
               arr2.add(mason);
               matt.food = matt.food - 1;
            }
            if(matt.food <= 0){
               matt.setHealth(matt.getHealth() - MSD);
            }
         }
      }
      return arr2;
   }
   //facilitates berry life and death
   public static ArrayList<obj> berryBirth(ArrayList<obj> arr, int BD, int BB){
      ArrayList<obj> arr2 = new ArrayList();
      for(obj object : arr){
         arr2.add(object);
      }
      for(int i = 0; i < arr.size(); i++){
         if(arr.get(i) instanceof berry){
            berry brenda = (berry) arr.get(i);
            if(brenda.age > BB){
               Random rand = new Random();
               int randx = rand.nextInt(3) - 1;
               int randy = rand.nextInt(3) - 1;
               if(randx == 0 && randy == 0){
                  brenda.age = brenda.age + 1;
                  break;
               }
               boolean found = false;
               for(obj object : arr2){
                  if(object.posX() == (brenda.posX() + randx) && object.posY() == (brenda.posY() + randy)){
                     found = true;
                  }
               }
               if(found){
                  brenda.age = brenda.age + 1;
                  break;
               }
               String nameing = (brenda.getName() + rand.nextInt(9));
               berry beatrice = new berry(nameing, (brenda.posX() + randx), (brenda.posY() + randy));
               System.out.println(nameing + " born at: (" + (beatrice.posX()) + "," + (beatrice.posY()) + ")");
               arr2.add(beatrice);
            }
            if(brenda.age > BD){
               System.out.println(brenda.getName() + " died at (" + brenda.posX() + "," + brenda.posY() + ")");
               brenda.delete();
            }else{
               brenda.age = brenda.age + 1;
            }
         }
      }
      return arr2;
   }
   //give the ability for prefabricated setups using a file called objects.txt
   public static ArrayList<obj> importing(ArrayList<obj> arr) {
      ArrayList<obj> arr2 = new ArrayList<>();
      for (obj object : arr) {
         arr2.add(object);
      }
      File objectsfile = new File("objects.txt");
      try {
         Scanner reader = new Scanner(objectsfile);
         while (reader.hasNextLine()) {
            String data = reader.nextLine().trim(); // Trim whitespace
            if (data.isEmpty()) {
               continue; // Skip empty lines
            }
            String[] datarr = data.split(" ");
            if (datarr[0].charAt(0) == 'm') {
               mite mateo = new mite(datarr[1], Integer.parseInt(datarr[2]), Integer.parseInt(datarr[3]),
                     Integer.parseInt(datarr[4]), Integer.parseInt(datarr[5]), Integer.parseInt(datarr[6]));
               arr2.add(mateo);
            } else if (datarr[0].charAt(0) == 'b') {
               berry bethany = new berry(datarr[1], Integer.parseInt(datarr[2]), Integer.parseInt(datarr[3]));
               arr2.add(bethany);
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
      return arr2;
   }
   //moves the mites in one random direction of there choosing
   public static void move(ArrayList<obj> arr){
      ArrayList<obj> arr2 = new ArrayList();
      for(obj object : arr){
         arr2.add(object);
      }
      for(obj object : arr){
         if(object instanceof mite){
            mite matthew = (mite) object;
            Random rand = new Random();
            int randx = rand.nextInt(3) - 1;
            int randy = rand.nextInt(3) - 1;
            boolean found = false;
            int movementx = ((matthew.posX() + randx + matthew.biasx) > 2) ? (matthew.posX() + 1) : ((matthew.posX() + randx + matthew.biasx) < -2) ? (matthew.posX() + -1) : (matthew.posX() + randx + matthew.biasx);
            int movementy = ((matthew.posY() + randy + matthew.biasy) > 2) ? (matthew.posY() + 1) : ((matthew.posY() + randy + matthew.biasy) < -2) ? (matthew.posY() + -1) : (matthew.posY() + randy + matthew.biasy);
            for(obj object2 : arr2){
               if(object2.posX() == movementx && object2.posY() == movementy){
                  found = true;
               }
            }
            if(found == false && movementx > 0 && movementy > 0){
               matthew.setX(movementx);
               matthew.setY(movementy);
               System.out.println(matthew.getName() + " moved to " + movementx + " " + movementy);
               System.out.println(matthew.biasx);
               System.out.println(matthew.biasy);
            }
         }
      }
      
   }
   
   public static void main(String[] args){
      System.out.println("how many iterations would you like to run?");
      Scanner scanner = new Scanner(System.in);
      int nput = scanner.nextInt();
      int berrydecay = 2;
      int berryboom = 1;
      int miteStarveDamage = 10;
      //existance plane. the place where all livving obj's are realised
      ArrayList<obj> arr = new ArrayList();
      arr = importing(arr);
      mite mike = new mite("mike", 100, 3, 2, 0, 0);
      arr.add(mike);
      berry berry = new berry("berry1", 2,2);
      arr.add(berry);
      berry berry2 = new berry("berry2", 3,1);
      arr.add(berry2);
      berry berry3 = new berry("berry3", 6,3);
      arr.add(berry3);
      try{
         FileWriter myObj = new FileWriter("EvolutionOutput.txt");
         myObj.close();
      }catch (IOException e){
         System.out.println("An error occurred.");
         e.printStackTrace();
      }
      int live = 0;
      boolean isalive;
      for(int i = 0; i < nput; i++){
         isalive = false;
         for(obj object : arr){
            if(object instanceof mite){
              isalive = true;
            }
         }
         if(isalive == true){
            live++;
         }
         System.out.println("start " + i);
         print(arr);
         eat(arr);
         arr.removeIf(obj -> obj.isDead);
         System.out.println("post eat " + i);
         print(arr);
         arr = birth(arr, miteStarveDamage);
         System.out.println("post birth " + i);
         print(arr);
         arr = berryBirth(arr, berrydecay, berryboom);
         arr.removeIf(obj -> obj.isDead);
         System.out.println("post berry birth " + i);
         print(arr);
         move(arr);
         System.out.println("post move " + i);
         print(arr);
         System.out.println(live);
      }
      //break -------------------------------------
   }

}
class obj{
   String name;
   private int x = 0;
   private int y = 0;
   boolean isDead = false;
   public obj(String n, int x1, int y1){
      name = n;
      x = x1;
      y = y1;
   }
      //these are not dirivitives buddy, keep on moving
   void move(int dx, int dy){
      x += dx;
      y += dy;
   }
   //used to delete objects from the existance plane
   void delete(){
      isDead = true;
   }
   void setFood(int i){
   
   }
   int getHealth(){
      return 0;
   }
   int posX(){
      return this.x;
   }
   int posY(){
      return this.y;
   }
   String getName(){
      return name;
   }
   void setX(int x1){
      x = x1;
   }
   void setY(int y1){
      y = y1;
   }
   void setName(String n1){
      name = n1;
   }
}
class mite extends obj{
   private int health = 100; //keeps track of health, used in eat();
   int food = 0; //used in eat() and birth(). keeps track of how much food has been eaten
   int biasx; // biases what x direction the mite will move in
   int biasy; // biases what y direction the mite moves in
   boolean mate = false;//using asexual life forms at the moment, no need for this so far
   
   public mite(String n, int hp, int x1, int y1, int bx, int by){
      super(n, x1, y1);
      this.health = hp;
      Random rand = new Random();
      if(bx != 0 || by != 0){
         this.biasx = bx;
         this.biasy = by;
      }else{
         this.biasx = rand.nextInt(3) - 1;
         this.biasy = rand.nextInt(3) - 1;
      }
   }
   int getHealth(){
      return health;
   }
   void setHealth(int healthin){
      health = healthin;
   }
   int getFood(){
      return food;
   }
   void setFood(int foodin){
      food = foodin;
   }

}
class berry extends obj{
   int age;//used to determine how old the berry is in order to govern berrybirth() and death
   public berry(String n, int x1, int y1){
      super(n, x1, y1);
      age = 1;
   }
   int getAge(){
      return age;
   }
   void setAge(int a1){
      age = a1;
   }

}