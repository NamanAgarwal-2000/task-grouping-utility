package com.naman.taskutility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskGroupingUtility {

    public Map<String, Map<String, Integer>> groupWorkItems(List<WorkItem> workItems) {
        Map<String, Map<String, Integer>> groupedResult = new HashMap<>();

        if (workItems == null ||  workItems.isEmpty()){
            return groupedResult;
        }
        for (WorkItem workItem : workItems){

            String category = workItem.getCategory();
            String status = workItem.getStatus();

         // Handle null or blank category values
            if(category == null || category.isBlank()){
                category = "Unknown Category";
            }
            // Handle null or blank status values
            if(status == null || status.isBlank()) {
                status = "Unknown Status";
            }

            groupedResult.putIfAbsent(category, new HashMap<>());

            Map<String, Integer> statusCounts = groupedResult.get(category);

            statusCounts.put(status, statusCounts.getOrDefault(status,0) + 1);
        }
        return groupedResult;
    }
}
