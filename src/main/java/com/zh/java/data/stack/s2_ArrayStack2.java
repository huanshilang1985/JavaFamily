package com.zh.java.data.stack;

/**
 * @Author zhanghe
 * @Desc: 数组实现的顺序栈-自动扩容
 * @Date 2018/11/5 19:13
 */
public class s2_ArrayStack2 {


    private String[] items;  //数组
    private int count;       //栈的元素个数
    private int n;           //栈的大小

    public s2_ArrayStack2(){

    }
    /**
     * 初始化数组
     * @param n  栈的大小
     */
    public s2_ArrayStack2(int n){
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
            n = n * 2;
            String[] newArr = new String[n];
//            items = Arrays.copyOf(items, (n * 2), String.class);
//            String[] newArr2 = Arrays.copyOf(items, 10);
            System.arraycopy(items, 0, newArr, 0, items.length);
            items = newArr;
//            elementData = Arrays.copyOf(elementData, size, Object[].class);
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
        s2_ArrayStack2 stock = new s2_ArrayStack2(1);
        System.out.println(stock.push("111"));
        System.out.println(stock.push("2222"));
        System.out.println(stock.push("wlkjlaj"));
        System.out.println(stock.pup());
        System.out.println(stock.pup());
        System.out.println(stock.pup());

    }


}
