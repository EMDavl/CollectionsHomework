package tasks.first;

import java.util.ArrayDeque;

public class FirstTaskSolution implements FirstTask {
    @Override
    public String breadthFirst(boolean[][] adjacencyMatrix, int startIndex) {
        String res = startIndex + ",";
        ArrayDeque<Integer> ad = new ArrayDeque<>();
        boolean[] ba = new boolean[adjacencyMatrix.length];
        ba[startIndex] = true;
        ad.offerLast(startIndex);
        for (int i = 0; i < adjacencyMatrix.length && !ad.isEmpty(); i++) {
            int nodeNum = ad.pollFirst();
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                if (adjacencyMatrix[nodeNum][j] && !ba[j]) {
                    ad.offerLast(j);
                    ba[j] = true;
                    res = res.concat(j + ",");
                }
            }
        }
        return res;
    }

    @Override
    public Boolean validateBrackets(String s) {
        boolean res = false;
        char[] chArr = {'(', '[', '{', ')', ']', '}'};
        ArrayDeque<Character> chAD = new ArrayDeque<>();

        if (s == null) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (charContains(chArr, ch)) {
                if (chAD.isEmpty()) {
                    chAD.offerFirst(ch);
                } else {
                    if (goodBrackets(chAD.peekFirst(), ch)) {
                        chAD.pollFirst();
                    } else {
                        chAD.offerFirst(ch);
                    }
                }
            }
        }
        return chAD.isEmpty();
    }

    public boolean charContains(char[] chars, char ch) {
        for (char c : chars) {
            if (ch == c) {
                return true;
            }
        }
        return false;
    }

    public boolean goodBrackets(char first, char last) {
        return (last - first < 3 && last - first > 0);
    }

    @Override
    public Long polishCalculation(String s) {
        if (s.equals("")) {
            return null;
        }
        Long res = 0L;
        String[] strArr = s.split(" ");
        ArrayDeque<Long> longAD = new ArrayDeque<>();
        boolean operandFlag = false;
        for (int i = 0; i < strArr.length; i++) {
            if (!operandFlag && !isOperand(strArr[i])) {
                longAD.offerFirst((long) Integer.parseInt(strArr[i]));
            } else {
                operandFlag = true;
                try {
                    longAD.offerFirst(doOperation(strArr[i], longAD.pollFirst(), longAD.pollFirst()));
                } catch (NullPointerException npe) {
                    return null;
                }
                res = longAD.peekFirst();
            }
        }

        return longAD.size() == 1 ? res : null;
    }

    public boolean isOperand(String str) {
        if (str.charAt(0) == '+') return true;
        if (str.charAt(0) == '*') return true;
        if (str.charAt(0) == '-') return true;
        if (str.charAt(0) == '/') return true;
        return false;
    }

    public long doOperation(String operand, long elem1, long elem2) {
        if (operand.equals("+")) {
            return elem1 + elem2;
        }
        if (operand.equals("-")) {
            return elem2 - elem1;
        }
        if (operand.equals("*")) {
            return elem1 * elem2;
        }
        if (operand.equals("/")) {
            return elem2 / elem1;
        }
        return 0;
    }
}

