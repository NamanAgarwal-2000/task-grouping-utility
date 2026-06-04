package com.naman.taskutility;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
@Import(GlobalExceptionHandler.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProblemService problemService;

    @Test
    void shouldGenerateReport() throws Exception {

        List<Problem> problems = List.of(
                new Problem(
                        "Two Sum",
                        "Array",
                        "Easy",
                        "Completed",
                        30
                )
        );

        when(problemService.applyFilters(anyList(), any()))
                .thenReturn(problems);

        when(problemService.applySorting(anyList(), any()))
                .thenReturn(problems);

        String requestBody = """
                {
                  "problems": [
                    {
                      "title": "Two Sum",
                      "status": "Completed",
                      "category": "Array",
                      "difficulty": "Easy",
                      "timeSpentMinutes": 30
                    }
                  ]
                }
                """;

        mockMvc.perform(post("/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalProblems").value(1));
    }
    @Test
    void shouldReturnBadRequestWhenProblemsListIsEmpty() throws Exception {

        String requestBody = """
    {
      "problems": []
    }
    """;

        mockMvc.perform(post("/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Problems list cannot be empty"));
    }
    @Test
    void shouldGenerateReportWithFiltersAndSorting() throws Exception {

        String requestBody = """
    {
      "problems": [
        {
          "title": "Two Sum",
          "status": "Completed",
          "category": "Array",
          "difficulty": "Easy",
          "timeSpentMinutes": 30
        }
      ],
      "status": "completed",
      "sortBy": "timeSpent",
      "sortOrder": "asc"
    }
    """;

        mockMvc.perform(post("/reports")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }
}