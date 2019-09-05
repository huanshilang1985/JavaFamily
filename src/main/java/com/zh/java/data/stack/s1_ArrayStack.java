package com.zh.java.data.stack;

/**
 * @Author zhanghe
 * @Desc: 数组实现的顺序栈
 *      栈是一种抽象的数据结构
 * @Date 2018/11/5 19:13
 */
public class s1_ArrayStack {

    private String[] items;  //数组
    private int count;       //栈的元素个数
    private int n;           //栈的大小

    public s1_ArrayStack(){

    }
    /**
     * 初始化数组
     * @param n  栈的大小
     */
    public s1_ArrayStack(int n){
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    /**
     * 入栈操作
     * @param item 入栈元素
     * @return boolean
     */
    public boolean push(String item){
        //如果数组空间不够，返回false，入栈失败
        if(count == n){
            return false;
        }
        //将item放到下标为count的位置，并且count加1
        items[count] = item;
        ++count;
        return true;
    }

    /**
     * 出栈操作
     * @return String
     */
    public String pup(){
        //如果栈为空，返回false，出栈失败
        if(count == 0){
            return null;
        }
        //返回下标为count-1的数组元素，并且栈内元素个数count减1
        String item = items[count - 1];
        --count;
        return item;
    }

    public static void main(String[] args) {
        s1_ArrayStack stock = new s1_ArrayStack(2);
        System.out.println(stock.push("111"));
        System.out.println(stock.push("2222"));
        System.out.println(stock.push("wlkjlaj"));
        System.out.println(stock.pup());
        System.out.println(stock.push("333"));
        System.out.println(stock.pup());

    }



}
