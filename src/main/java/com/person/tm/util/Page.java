package com.person.tm.util;

public class Page {
    private int start; //开始位置
    private int count; //每页显示个数
    private int total; //总页数
    private String param; //参数

    private final static int DEFAULTCOUNT = 5;  //每页默认的个数

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    Page() {
        count = DEFAULTCOUNT;
    }

    Page(int start, int count) {
        this();
        this.start = start;
        this.count = count;
    }

    //是否有前一页
    public boolean isHasPreviouse() {
        if (start == 0)
            return false;
        return true;
    }


    //是否有下一页
    public boolean isHasNext() {
        if (start == getLast()) {
            return false;
        } else return true;

    }

    //得到总页数
    public int getTotalPage() {
        int totalPage = 0;
        //如果总数是100，count是5，那么可以被整除，总页数是20
        if (0 == total % count) {
            totalPage = total / count;

        }
        //如果总数是101，count是5，那么不可以被整除，有余数，总页数是21。
        else {
            totalPage = total / count + 1;
        }


        //如果总数是0,则总页数是1
        if (0 == total) {
            totalPage = 1;
        }
        return totalPage;
    }

    //得到最后一页的start
    public int getLast() {
        int last = 0;
        //同理.当总数是100，count是5，那么可以被整除，最后一页的start 是95
        if (0 == total % count) {
            last = total - count;
        } else {
            //当不能被整除时，
            last=total-total%count;
        }
        return last;

    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + isHasPreviouse() + ", isHasNext()="
                + isHasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }


}
