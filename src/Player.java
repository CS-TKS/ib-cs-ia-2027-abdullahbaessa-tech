package app;

public class Player {
    private int playerId;
    private String name;
    private double height;
    private double weight;
    private int age;
    private double speed;
    private int agility;

    public Player(String name, double height, double weight, int age, double speed, int agility) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.speed = speed;
        this.agility = agility;
    }

    public Player(int playerId, String name, double height, double weight, int age, double speed, int agility) {
        this.playerId = playerId;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.speed = speed;
        this.agility = agility;
    }

    public double getBMI() {
        double heightMetres = height / 100;
        return weight / (heightMetres * heightMetres);
    }

    // Adult reference bands used only for this programming example.
    public String getBMICategory() {
        double bmi = getBMI();
        if (bmi < 18.5) return "Below adult reference range";
        else if (bmi < 25) return "Within adult reference range";
        else if (bmi < 30) return "Above adult reference range";
        else return "High adult BMI range";
    }

    public int getPlayerId() { return playerId; }
    public void setPlayerId(int playerId) { this.playerId = playerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getSpeed() { return speed; }
    public void setSpeed(double speed) { this.speed = speed; }
    public int getAgility() { return agility; }
    public void setAgility(int agility) { this.agility = agility; }
}
