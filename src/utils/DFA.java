package utils;

import model.Part;

/**
 * Created by jhcpokemon on 05/12/15.
 */
public class DFA {
    Part part;
    String[] lefts = new String[10];
    String[] rights = new String[10];

    public DFA(String source) {
        source = source.replaceAll("\\(|\\)|:|M|,", "");
        String[] sources = source.split("\n");
        for (int i = 0; i < sources.length; i++) {
            part = new Part(sources[i]);
            lefts[i] = part.getLeft();
            rights[i] = part.getRight();
        }
    }

    public String convertRuleToDFA() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------DFA定义式如下---------------\n");
        for (int i = 0; i < lefts.length; i++) {
            if (lefts[i] != null && rights[i] != null) {
                if (rights[i].length() == 1) {
                    sb.append("M(" + lefts[i] + "," + rights[i] + ")=Z\n");
                } else {
                    sb.append("M(" + lefts[i] + "," + rights[i].substring(0, 1) + ")=" + rights[i].substring(1, 2) + "\n");
                }
            }
        }
        return sb.toString();
    }

    public String convertDFAToRule() {
        StringBuilder sb = new StringBuilder();
        sb.append("---------------文法产生式如下---------------\n");
        for (int i = 0; i < lefts.length; i++) {
            if (lefts[i] != null && rights[i] != null) {
                if (rights[i].equals("Z")) {
                    sb.append(lefts[i].substring(0, 1) + "::=" + lefts[i].substring(1, 2) + "\n");
                } else {
                    sb.append(lefts[i].substring(0, 1) + "::=" + lefts[i].substring(1, 2) + rights[i] + "\n");
                }
            }
        }
        return sb.toString();
    }
}
