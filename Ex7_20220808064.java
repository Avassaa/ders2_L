import java.util.ArrayList;
import java.util.Collections;
public class Ex7_20220808064  {

}
interface Damageable{
    void takeDamage(int damage);
    void takeHealing(int healing);
    boolean isAlive();
}
interface Caster{
    void castSpell(Damageable target);
    void learnSpell(Spell spell);

}
interface Combat extends Damageable{
    void attack(Damageable target);
    void lootWeapon(Weapon weapon);
}
interface Useable {
    int use();
}
class Spell implements Useable{
    private int minHeal;
    private int maxHeal;
    private String name;

    public Spell(String name,int minHeal,int maxHeal){
        this.name = name;
        this.minHeal = minHeal;
        this.maxHeal = maxHeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private int heal(){
        return (int)(Math.random()*(maxHeal-minHeal)+minHeal);
    }
    public int use(){
        return heal();
    }
}
 class Weapon implements Useable{
    private int minDamage;
    private int maxDamage;
    private String name;

    public Weapon(String name,int minDamage,int maxDamage){
        this.name = name;
        this.minDamage=minDamage;
        this.maxDamage=maxDamage;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int attack(){
        return (int)(Math.random()*(maxDamage-minDamage)+minDamage);
    }
    public int use(){
        return attack();
    }
}
class Attributes{
    private int strength;
    private int intelligence;

    public Attributes(){
        this.strength=3;
        this.intelligence=3;
    }
    public Attributes(int strength,int intelligence){
        this.strength=strength;
        this.intelligence=intelligence;
    }
    public void increaseStrength(int amount){
        this.strength+=amount;

    }
    public void increaseIntelligence(int amount){
        this.intelligence+=amount;

    }

    public int getStrength() {
        return this.strength;
    }

    public int getIntelligence() {
        return this.intelligence;
    }

    public String toString(){
        return "Attributes [Strength="+strength+", intelligence="+intelligence+"]";
    }

}
abstract class Character implements Comparable<Character>{
    private String name;
    protected int level;
    protected Attributes attributes;
    protected int health;

    public int compareTo(Character c1){
        if(this.level>c1.level){
            return 1;
        }
        if(this.level<c1.level){
            return -1;
        }
        else{
            return 0;
        }
    }
    public Character(String name, Attributes attributes){
        this.name=name;
        this.attributes=attributes;
    }

    public String getName() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }


    public abstract void levelUp();
    public String toString(){
        return this.getClass().getName()+" Lvl:"+level+"-"+attributes;
    }
}


abstract class PlayableCharacter extends Character implements Damageable {

    private boolean inParty;
    private Party party;

    public PlayableCharacter(String name, Attributes attributes) {
        super(name, attributes);
    }

    public boolean isInParty() {
        return this.inParty;
    }

    public void joinParty(Party party) {
        try {
            party.addCharacter(this);
            this.party=party;
            this.inParty = true;

        } catch (PartyLimitReachedException ex) {
            ex.printStackTrace();
        } catch (AlreadyInPartyException ex) {
            ex.printStackTrace();
        }
    }

    public void quitParty() {
        try {
            party.removeCharacter(this);
            this.inParty = false;
            this.party = null;
        } catch (CharacterIsNotInPartyException ex) {
            ex.printStackTrace();
        }
    }
}

abstract class NonPlayableCharacter extends Character{
    public NonPlayableCharacter(String name, Attributes attributes){
        super(name, attributes);
    }
}



class Merchant extends NonPlayableCharacter{
    public Merchant(String name){
        super(name,new Attributes(0,0));
    }
    public void levelUp(){}
}



class Skeleton extends NonPlayableCharacter implements Combat{

    public Skeleton(String name,Attributes attributes){
        super(name,attributes);
    }
    @Override
    public void attack(Damageable target){
        int attributeSum=this.attributes.getIntelligence()+this.attributes.getStrength();
        target.takeDamage(attributeSum);
    }

    @Override
    public void takeDamage(int damage){
        this.health-=damage;
    }
    public void takeHealing(int healing){
        this.health-=healing;
    }
    public boolean isAlive(){
        return this.health>0;
    }

    @Override
    public void lootWeapon(Weapon weapon){}

    @Override
    public void levelUp(){
        this.attributes.increaseIntelligence(1);
        this.attributes.increaseStrength(1);
        this.level++;
    }
}

class Warrior extends PlayableCharacter implements Combat{
    private Useable weapon;

    public Warrior(String name){
        super(name,new Attributes(4,2));
        this.health=35;

    }
    @Override
    public void levelUp(){
        this.attributes.increaseIntelligence(1);
        this.attributes.increaseStrength(2);
        this.level++;
    }
    public void takeDamage(int damage){
        this.health-=damage;
    }
    public void takeHealing(int healing){
        this.health+=healing;
    }
    public boolean isAlive(){
        return health>0;
    }
    public void attack(Damageable target){
        int totalDamage=this.weapon.use()+this.attributes.getStrength();
        target.takeDamage(totalDamage);
    }
    public void lootWeapon(Weapon weapon){
        this.weapon=weapon;
    }
}

class Cleric extends PlayableCharacter implements Caster{
    private Useable spell;

    public Cleric(String name){
        super(name,new Attributes(2,4));
        this.health=25;
    }

    public void levelUp(){
        this.attributes.increaseStrength(1);
        this.attributes.increaseIntelligence(2);
        this.level++;
    }
    public boolean isAlive(){
        return this.health>0;
    }
    public void takeDamage(int damage){
        this.health-=damage;
    }
    public void takeHealing(int healing){
        this.health+=healing;
    }
    public void castSpell(Damageable target){
        int healAmount=this.attributes.getIntelligence()+this.spell.use();
        target.takeHealing(healAmount);

    }
    public void learnSpell(Spell spell){
        this.spell=spell;
    }
}

class Paladin extends PlayableCharacter implements Combat,Caster{
    private Useable weapon;
    private Useable spell;

    public Paladin(String name){
        super(name,new Attributes());
        this.health=30;

    }

    @Override
    public void levelUp(){
        int strLvl=this.attributes.getStrength();
        int intLvl=this.attributes.getIntelligence();

        if(this.level%2==0){
            this.attributes.increaseStrength(1);
            this.attributes.increaseIntelligence(2);
            this.level++;
        }else{
            this.attributes.increaseStrength(2);
            this.attributes.increaseIntelligence(1);
            this.level++;
        }
    }

    public void takeDamage(int damage){
        this.health-=damage;
    }
    public void takeHealing(int healing){
        this.health+=healing;
    }
    public void attack(Damageable target){
        int totalDamage=this.attributes.getStrength()+this.weapon.use();
        target.takeDamage(totalDamage);
    }

    public void lootWeapon(Weapon weapon){
        this.weapon=weapon;
    }
    public boolean isAlive(){
        return this.health>0;
    }

    public void castSpell(Damageable target){
        int totalHealing=this.attributes.getIntelligence()+this.spell.use();
        target.takeHealing(totalHealing);
    }
    public void learnSpell(Spell spell){
        this.spell=spell;
    }
}




class Party {
    private final static int partyLimit = 8;
    private ArrayList<Combat> fighters = new ArrayList<>();
    private ArrayList<Caster> healers = new ArrayList<>();
    private int mixedCount;

    public void addCharacter(PlayableCharacter character) throws PartyLimitReachedException, AlreadyInPartyException { //BAK *********************************
        int currentPartySize = fighters.size() + healers.size() - mixedCount;
        if (currentPartySize < partyLimit) {
            if (character.isInParty()) {
                throw new AlreadyInPartyException("AlreadyInPartyException:CHARACTER IS ALREADY IN A PARTY");
            } else {
                if (character instanceof Combat && character instanceof Caster) {
                    fighters.add((Combat) character);
                    healers.add((Caster) character);
                } else if (character instanceof Caster) {
                    healers.add((Caster) character);
                } else if (character instanceof Combat) {
                    fighters.add((Combat) character);
                }
                if (character instanceof Paladin) {
                    mixedCount++;
                }
            }

        } else {
            throw new PartyLimitReachedException("PartyLimitException:PARTY LIMIT REACHED");
        }
    }


    public void removeCharacter(PlayableCharacter character) throws CharacterIsNotInPartyException {
        if (!character.isInParty()) {
            throw new CharacterIsNotInPartyException("CharacterIsNotInPartyException:CHARACTER IS NOT IN A PARTY");
        } else {
            if (character instanceof Combat && character instanceof Caster) {
                fighters.remove((Combat) character);
                healers.remove((Caster) character);
                mixedCount--;

            } else if (character instanceof Caster) {
                healers.remove((Caster) character);
            } else if (character instanceof Combat) {
                fighters.remove((Combat) character);
            }

        }
    }


    public void partyLevelUp() {
        for (Caster character : healers) {
            if (character instanceof Paladin)
                continue;
            PlayableCharacter character1 = (PlayableCharacter) character;
            character1.levelUp();
        }
        for (Combat character : fighters) {
            PlayableCharacter character1 = (PlayableCharacter) character;
            character1.levelUp();
        }
    }

    @Override
    public String toString() {
        StringBuilder charactersInOrder = new StringBuilder("Party members:\n");
        ArrayList<PlayableCharacter> characters = new ArrayList<>();
        for (Combat character : fighters) {
            PlayableCharacter character1 = (PlayableCharacter) character;
            characters.add(character1);
        }
        for (Caster character : healers) {
            if ((character instanceof Paladin))
                continue;
            PlayableCharacter character1 = (PlayableCharacter) character;
            characters.add(character1);
        }
        Collections.sort(characters);

        int listLength = characters.size();
        for (int i = 0; i < listLength; i++) {
            charactersInOrder.append("\t").append(characters.get(i)).append("\n");
        }

        return String.valueOf(charactersInOrder);

    }
}


class Barrel implements Damageable{
    private int health=30;
    private int capacity=10;



    public void explode(){
        System.out.println("EXPLODES");
    }
    public void repair(){
        System.out.println("REPAIRING");
    }
    public void takeDamage(int damage){
        this.health-=damage;
        if(health<0){
            explode();
        }
    }
    public void takeHealing(int healing){
        this.health+=healing;
        repair();
    }

    public boolean isAlive(){
        return health>0;
    }
}

class TrainingDummy implements Damageable{
    private int health=25;

    public void takeDamage(int damage){
        this.health-=damage;
    }
    public void takeHealing(int healing){
        this.health+=healing;
    }
    public boolean isAlive(){
        return health>0;
    }

}

class PartyLimitReachedException extends Exception {
    public PartyLimitReachedException(String msg) {
        super(msg);
    }
}

class AlreadyInPartyException extends Exception {
    public AlreadyInPartyException(String msg) {
        super(msg);
    }
}

class CharacterIsNotInPartyException extends Exception {
    public CharacterIsNotInPartyException(String msg) {
        super(msg);
    }
}

