package com.naman.taskutility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem("arrays", "done"));
        workItems.add(new WorkItem("arrays", "pending"));
        workItems.add(new WorkItem("strings", "done"));
        workItems.add(new WorkItem("lambda", "pending"));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> groupedResult = utility.groupWorkItems(workItems);

        System.out.println(groupedResult);
    }
}
