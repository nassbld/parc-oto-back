package com.bld.parc_oto_back.exposition;

import com.bld.parc_oto_back.application.ReportService;
import com.bld.parc_oto_back.domain.Report;
import com.bld.parc_oto_back.domain.enums.ReportType;
import com.bld.parc_oto_back.dto.ReportDTO;
import com.bld.parc_oto_back.infrastructure.mapper.ReportMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService reportService;

    @MockBean
    private ReportMapper reportMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getReportById_shouldReturnReport_whenReportExists() throws Exception {
        Report report = new Report();
        report.setId(1L);
        report.setDescription("Test Report");
        report.setType(ReportType.DAMAGE);
        report.setReportDateTime(LocalDateTime.now());

        when(reportService.getReportById(1L)).thenReturn(Optional.of(report).map(reportMapper::toDto));

        mockMvc.perform(get("/reports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Test Report"))
                .andExpect(jsonPath("$.type").value("DAMAGE"));
    }

    @Test
    void getReportById_shouldReturnNotFound_whenReportDoesNotExist() throws Exception {
        when(reportService.getReportById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/reports/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createReport_shouldReturnNoContent() throws Exception {
        Report report = new Report();
        report.setDescription("New Report");
        report.setType(ReportType.LATE_RETURN);
        report.setReportDateTime(LocalDateTime.now());

        mockMvc.perform(post("/reports/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isNoContent());

        verify(reportService, times(1)).createReport(eq(1L), any(ReportDTO.class));
    }

    @Test
    void deleteReport_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/reports/1"))
                .andExpect(status().isNoContent());

        verify(reportService, times(1)).deleteReport(1L);
    }
}
