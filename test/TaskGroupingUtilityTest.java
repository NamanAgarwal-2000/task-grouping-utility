import com.naman.taskutility.TaskGroupingUtility;
import com.naman.taskutility.WorkItem;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaskGroupingUtilityTest {
    @Test
    public void shouldGroupTasksCorrectly() {
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem("arrays", "done"));
        workItems.add(new WorkItem("arrays", "pending"));
        workItems.add(new WorkItem("strings", "done"));
        workItems.add(new WorkItem("lambda", "pending"));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(2, result.get("arrays").get("done") + result.get("arrays").get("pending"));
        assertEquals(1, result.get("strings").get("done"));


    }

    @Test
    public void shouldHandleNullCategory() {
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem(null, "done"));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(1, result.get("Unknown Category").get("done"));

    }

    @Test
    public void shouldHandleNullStatus() {
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem("arrays", null));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(1, result.get("arrays").get("Unknown Status"));


    }

    @Test
    public void shouldIncreaseCountCorrectly() {
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem("arrays", "done"));
        workItems.add(new WorkItem("arrays", "done"));
        workItems.add(new WorkItem("arrays", "done"));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(3, result.get("arrays").get("done"));


    }

    @Test
    public void shouldReturnEmptyMapWhenListIsEmpty() {
        List<WorkItem> workItems = new ArrayList<>();

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(0, result.size());
    }

    @Test
    public void shouldHandleBlankCategory() {
        List<WorkItem> workItems = new ArrayList<>();

        workItems.add(new WorkItem("", "done"));

        TaskGroupingUtility utility = new TaskGroupingUtility();

        Map<String, Map<String, Integer>> result = utility.groupWorkItems(workItems);

        assertEquals(1, result.get("Unknown Category").get("done"));

    }
}


