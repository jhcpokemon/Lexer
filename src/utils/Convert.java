package utils;

import model.Pref;

import java.util.ArrayList;

/**
 * Created by jhcpokemon on 05/12/15.
 */
public class Convert {
    String[] leftOfEqual = new String[10];
    String[] rightOfEqual = new String[10];
    String[] results = new String[10];
    ArrayList<Pref> prefs = new ArrayList<>();

    public String[] convertLeftToRight(String source) {
        String[] lefts = source.split("\n");
        for (int i = 0; i < lefts.length; i++) {
            String[] singleLeft = lefts[i].split("::=");
            leftOfEqual[i] = singleLeft[0];
            rightOfEqual[i] = singleLeft[1];
            if (leftOfEqual[i].equals("S")) {
                if (rightOfEqual[i].toLowerCase().equals(rightOfEqual[i])) {
                    leftOfEqual[i] = singleLeft[0];
                    rightOfEqual[i] = singleLeft[1];
                } else {
                    leftOfEqual[i] = rightOfEqual[i].substring(0, 1);
                    rightOfEqual[i] = rightOfEqual[i].substring(1, 2);
                }
            } else if (!(leftOfEqual[i].equals("S"))) {
                if (rightOfEqual[i].toLowerCase().equals(rightOfEqual[i])) {
                    rightOfEqual[i] = rightOfEqual[i] + leftOfEqual[i];
                    leftOfEqual[i] = "S";
                } else {
                    String temp = leftOfEqual[i];
                    leftOfEqual[i] = rightOfEqual[i].substring(0, 1);
                    rightOfEqual[i] = rightOfEqual[i].substring(1, 2) + temp;
                }
            }
            results[i] = leftOfEqual[i] + "::=" + rightOfEqual[i];
        }
        return results;
    }

    public String[] convertRightToLeft(String source) {
        String[] rights = source.split("\n");
        for (int i = 0; i < rights.length; i++) {
            String[] singleRight = rights[i].split("::=");
            leftOfEqual[i] = singleRight[0];
            rightOfEqual[i] = singleRight[1];
            if (leftOfEqual[i].equals("S")) {
                if (rightOfEqual[i].toLowerCase().equals(rightOfEqual[i])) {
                    leftOfEqual[i] = singleRight[0];
                    rightOfEqual[i] = singleRight[1];
                } else {
                    leftOfEqual[i] = rightOfEqual[i].substring(1, 2);
                    rightOfEqual[i] = rightOfEqual[i].substring(0, 1);
                }
            } else if (!(leftOfEqual[i].equals("S"))) {
                if (rightOfEqual[i].toLowerCase().equals(rightOfEqual[i])) {
                    rightOfEqual[i] = leftOfEqual[i] + rightOfEqual[i];
                    leftOfEqual[i] = "S";
                } else {
                    String temp = leftOfEqual[i];
                    leftOfEqual[i] = rightOfEqual[i].substring(1, 2);
                    rightOfEqual[i] = temp + rightOfEqual[i].substring(0, 1);
                }
            }
            results[i] = leftOfEqual[i] + "::=" + rightOfEqual[i];
        }
        return results;
    }

    public String convertToDefine(String source) {
        String[] contents = source.split("\n");
        for (String str : contents) {
            prefs.add(new Pref(str));
        }
        convert(prefs);
        return prefs.get(0).print();
    }

    public void convert(ArrayList<Pref> prefs) {
        this.prefs = prefs;
        for (Pref pref : prefs) {
            String temp = pref.getRight().replaceAll(pref.getLeft(), "*");
            pref.setRight(temp);
        }
        for (Pref pref : prefs) {
            for (Pref pref1 : prefs) {
                if (!(pref1.equals(pref))) {
                    if (pref.getRight().contains(pref1.getLeft())) {
                        String temp1 = pref.getRight().replaceAll(pref1.getLeft(), pref1.getRight());
                        pref.setRight(temp1);
                    }
                }
            }
        }
    }
}
