package model;

/**
 * Created by jhcpokemon on 05/12/15.
 */
public class Part {
    String left;
    String right;

    public Part(String source){
        String[] temp = source.split("=");
        this.left = temp[0];
        this.right = temp[1];
    }
    public String getLeft(){
        return this.left;
    }

    public String getRight() {
        return right;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }
}
