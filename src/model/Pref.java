package model;

/**
 * Created by jhcpokemon on 05/12/15.
 */
public class Pref {
    String left;
    String right;

    public Pref(String source) {
        String[] temp = source.split("::=");
        this.left = temp[0];
        this.right = temp[1];
        if (!(this.isLowcase())){
            this.right = temp[1].replaceAll("\\|","");
        }
    }

    public String getLeft() {
        return this.left;
    }

    public String getRight() {
        return this.right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public boolean isLowcase() {
        return (right.toLowerCase().equals(right));
    }

    public String print() {
        return left + "=" + right;
    }
}
