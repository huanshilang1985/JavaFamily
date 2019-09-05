package com.zh.java.data.question;

import java.util.Stack;

/**
 * Author zhanghe
 * Desc: 括号配对方法   [] {} () 随便一个括号组合，判断是否配对
 * 比如：[{}()]  true
 * [({)}] false
 * Date 2019/8/27 13:05
 */
public class Parentheses_Pair {

    public static void main(String[] args) {
        String str = "[{}()]";
        System.out.println(Parentheses_Pair.deal(str));
    }

    public static boolean deal(String str) {
        char[] chars = str.toCharArray();

        Stack<String> stack = new Stack<>();
        for (Character c : chars) {
            String s = c.toString();
            if (s.equals("{") || s.equals("[") || s.equals("(")) {
                stack.push(s);
                continue;
            }
            String pop = stack.pop();
            if (s.equals(")") && !pop.equals("(")) {
                return false;
            }
            if (s.equals("]") && !pop.equals("[")) {
                return false;
            }
            if (s.equals("}") && !pop.equals("{")) {
                return false;
            }
        }
        return true;
    }


}
