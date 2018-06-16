package com.ra.courses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudyServiceTest {

    private StudyService studyService;

    @BeforeEach
    public void beforeTests() {
        studyService = new StudyService();
    }

    @Test
    public void whenReturnNewStringExecutedTestWillReturn() {
        assertTrue("Test".equals(studyService.returnNewString()));
    }
}
