package com.collab.g5.demo.regulation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.collab.g5.demo.regulations.Regulation;
import com.collab.g5.demo.regulations.RegulationLimit;
import com.collab.g5.demo.regulations.RegulationRepository;
import com.collab.g5.demo.regulations.RegulationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RegulationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class RegulationServiceImplTest {
    @MockBean
    private RegulationRepository regulationRepository;

    @Autowired
    private RegulationServiceImpl regulationServiceImpl;

    @Test
    void testSave() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        when(this.regulationRepository.save((Regulation) any())).thenReturn(regulation);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        assertSame(regulation, this.regulationServiceImpl.save(regulation1));
        verify(this.regulationRepository).save((Regulation) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testGetAllRegulation() {
        ArrayList<Regulation> regulationList = new ArrayList<Regulation>();
        when(this.regulationRepository.findAll()).thenReturn(regulationList);
        List<Regulation> actualAllRegulation = this.regulationServiceImpl.getAllRegulation();
        assertSame(regulationList, actualAllRegulation);
        assertTrue(actualAllRegulation.isEmpty());
        verify(this.regulationRepository).findAll();
    }

    @Test
    void testGetAllRegulationWithLimit() {
        ArrayList<List<String>> listList = new ArrayList<List<String>>();
        when(this.regulationRepository.findAllRegulationWithLimit((String) any())).thenReturn(listList);
        List<List<String>> actualAllRegulationWithLimit = this.regulationServiceImpl
                .getAllRegulationWithLimit("jane.doe@example.org");
        assertSame(listList, actualAllRegulationWithLimit);
        assertTrue(actualAllRegulationWithLimit.isEmpty());
        verify(this.regulationRepository).findAllRegulationWithLimit((String) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testGetRegulationById() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        Optional<Regulation> ofResult = Optional.<Regulation>of(regulation);
        when(this.regulationRepository.findById((LocalDate) any())).thenReturn(ofResult);
        assertSame(regulation, this.regulationServiceImpl.getRegulationById(LocalDate.ofEpochDay(1L)));
        verify(this.regulationRepository).findById((LocalDate) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testGetRegulationById2() {
        when(this.regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>empty());
        assertNull(this.regulationServiceImpl.getRegulationById(LocalDate.ofEpochDay(1L)));
        verify(this.regulationRepository).findById((LocalDate) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testUpdateRegulation() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        Optional<Regulation> ofResult = Optional.<Regulation>of(regulation);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        when(this.regulationRepository.save((Regulation) any())).thenReturn(regulation1);
        when(this.regulationRepository.findById((LocalDate) any())).thenReturn(ofResult);
        LocalDate startDate = LocalDate.ofEpochDay(1L);

        Regulation regulation2 = new Regulation();
        regulation2.setStartDate(LocalDate.ofEpochDay(1L));
        regulation2.setEndDate(LocalDate.ofEpochDay(1L));
        regulation2.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation2.setPercentage(1);
        assertSame(regulation1, this.regulationServiceImpl.updateRegulation(startDate, regulation2));
        verify(this.regulationRepository).findById((LocalDate) any());
        verify(this.regulationRepository).save((Regulation) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testUpdateRegulation2() {
        Regulation regulation = new Regulation();
        regulation.setStartDate(LocalDate.ofEpochDay(1L));
        regulation.setEndDate(LocalDate.ofEpochDay(1L));
        regulation.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation.setPercentage(1);
        when(this.regulationRepository.save((Regulation) any())).thenReturn(regulation);
        when(this.regulationRepository.findById((LocalDate) any())).thenReturn(Optional.<Regulation>empty());
        LocalDate startDate = LocalDate.ofEpochDay(1L);

        Regulation regulation1 = new Regulation();
        regulation1.setStartDate(LocalDate.ofEpochDay(1L));
        regulation1.setEndDate(LocalDate.ofEpochDay(1L));
        regulation1.setRegulationLimits(new ArrayList<RegulationLimit>());
        regulation1.setPercentage(1);
        assertNull(this.regulationServiceImpl.updateRegulation(startDate, regulation1));
        verify(this.regulationRepository).findById((LocalDate) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }

    @Test
    void testDeleteRegulationById() {
        doNothing().when(this.regulationRepository).deleteById((LocalDate) any());
        this.regulationServiceImpl.deleteRegulationById(LocalDate.ofEpochDay(1L));
        verify(this.regulationRepository).deleteById((LocalDate) any());
        assertTrue(this.regulationServiceImpl.getAllRegulation().isEmpty());
    }
}

