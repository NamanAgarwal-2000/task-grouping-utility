import com.naman.taskutility.Problem;
import com.naman.taskutility.ProblemProgressReportGenerator;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class ProblemProgressReportGeneratorTest {

    public static void assertEquals(int expected, int actual) {

        if (expected == actual) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test Failed");
        }
    }

    public  static void shouldGenerateReportCorrectly() {
        List<Problem> problems = new ArrayList<>();


        problems.add(new Problem("Two Sum", "arrays", "easy", "done", 30));

        problems.add(new Problem("Binary Search", "arrays", "easy", "pending", 20));

        problems.add(new Problem("Valid Parentheses", "strings", "medium", "done", 25));

        problems.add(new Problem("Knapsack", "dp", "hard", "pending", 60));

        ProblemProgressReportGenerator utility = new ProblemProgressReportGenerator();

        Map<String, Map<String, Integer>> result = utility.generateReport(problems);

        assertEquals(2, result.get("arrays").get("done") + result.get("arrays").get("pending"));
        assertEquals(1, result.get("strings").get("done"));


    }
    public static void main(String[] args) {
        shouldGenerateReportCorrectly();
    }
}





