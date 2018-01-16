package com.springinaction.spitter.mvc;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.springinaction.spitter.domain.Spittle;
import com.springinaction.spitter.service.SpitterService;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeControllerTest {
    @Test
    public void souldDisplayRecentSpittles() {
        List<Spittle> expectedSpittles = Arrays.asList(new Spittle(), new Spittle(), new Spittle());

        SpitterService spitterService = mock(SpitterService.class);

        when(spitterService.getRecentSpittles(HomeController.DEFAULT_SPITTLES_PER_PAGE))
                .thenReturn(expectedSpittles);

        HomeController controller = new HomeController(spitterService);

        Map<String, Object> model = new HashMap<>();

        String viewName = controller.showHomePage(model);

        assertEquals("home", viewName);

        assertSame(expectedSpittles, model.get("spittles"));

        verify(spitterService).getRecentSpittles(HomeController.DEFAULT_SPITTLES_PER_PAGE);
    }
}
