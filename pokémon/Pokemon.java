public abstract class Pokemon {
   private String name;
   private String color;
   private int age;
   public int friendliness = 0;

   // Constructor
    public Pokemon(String nParam, String cParam) {
        this.name = nParam;
        this.color = cParam;
    }

    public void doMove(int moveIndex) {
	// TODO Auto-generated method stub
	
}

	// Accessors and Mutators
    public String getName() { return this.name; }
    public void setName(String nParam) { this.name = nParam; }
    public void addFriendliness() { this.friendliness++; }
    public void printFriendliness() {System.out.println(friendliness + " ");}
    // Method
    public String toString() { return this.name + " : " + this.color; }
    public static void main(String args[]) {
   	 Pikachu pikachu = new Pikachu("Pika", "yellow");
   	 pikachu.doMove(4);
    }
    public abstract void doMove();
}

abstract class LightningPokemon extends Pokemon {

	public LightningPokemon(String nParam, String cParam) {
		super(nParam, cParam);
	}
	
	
}
class Pikachu extends LightningPokemon {
	//Constructor
	public Pikachu (String Name, String Color) {
		super(Name, Color);
	}
	
	@Override
	public void addFriendliness() {
		this.friendliness += 5;
	}
	
	
	private String[] moves = {"Thunder shock", "Thunder wave", "Electro ball", "Spark"};
	@Override
	public void doMove(int moveIndex){
		try {
			System.out.println(this.getName() + " used " + moves[moveIndex]);
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println(this.getName() + " Only has 4 moves so valid moveIndexes are 0, 1, 2, and 3");
		}
	}
}

