package com.naman.taskutility;

public class WorkItem {

    private String category;
    private String status;

    public WorkItem(String category, String status){
        this.category = category;
        this.status = status;
    }
    public String getCategory(){
        return category;
    }
    public String getStatus(){
        return status;
    }
}
