package com.example.fixed_assets.controller;

import com.example.fixed_assets.entity.FixedAsset;
import com.example.fixed_assets.service.FixedAssetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(SpringExtension.class)
public class FixedAssetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FixedAssetService fixedAssetService;

    @InjectMocks
    private FixedAssetController fixedAssetController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(fixedAssetController).build();
    }

    @AfterEach
    public void tearDown() {
        reset(fixedAssetService);
    }

    @Test
    public void testAddFixedAsset() throws Exception {
        FixedAsset fixedAsset = new FixedAsset(1, 1, "Laptop", new Date(), new BigDecimal("1200.00"), "Available");

        mockMvc.perform(post("/fixedAssets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fixedAsset)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Asset added successfully."));

        verify(fixedAssetService).addFixedAsset(any(FixedAsset.class));
    }

    @Test
    public void testUpdateFixedAsset() throws Exception {
        FixedAsset fixedAsset = new FixedAsset(1, 1, "1", new Date(), new BigDecimal("1.00"), "Available");

        mockMvc.perform(put("/fixedAssets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fixedAsset)))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset updated successfully."));

        verify(fixedAssetService).modifyFixedAsset(any(FixedAsset.class));
    }

    @Test
    public void testDeleteFixedAsset() throws Exception {
        mockMvc.perform(delete("/fixedAssets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Asset deleted successfully."));

        verify(fixedAssetService).removeFixedAsset(1);
    }

    @Test
    public void testGetFixedAssetById() throws Exception {
        FixedAsset fixedAsset = new FixedAsset(1, 1, "Laptop", new Date(), new BigDecimal("1200.00"), "Available");

        given(fixedAssetService.getFixedAssetById(1)).willReturn(fixedAsset);

        mockMvc.perform(get("/fixedAssets/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(fixedAsset)));

        verify(fixedAssetService).getFixedAssetById(1);
    }

    @Test
    public void testGetAllFixedAssets() throws Exception {
        FixedAsset fixedAsset1 = new FixedAsset(1, 1, "Laptop", new Date(), new BigDecimal("1200.00"), "Available");
        FixedAsset fixedAsset2 = new FixedAsset(2, 2, "Printer", new Date(), new BigDecimal("300.00"), "Available");

        given(fixedAssetService.getAllFixedAssets()).willReturn(List.of(fixedAsset1, fixedAsset2));

        mockMvc.perform(get("/fixedAssets"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of(fixedAsset1, fixedAsset2))));

        verify(fixedAssetService).getAllFixedAssets();
    }

}