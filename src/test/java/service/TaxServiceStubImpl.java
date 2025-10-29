package service;

import com.sg.flooringmastery.model.Tax;
import com.sg.flooringmastery.service.TaxServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TaxServiceImplTest {

    @Test
    void testGetTax() {
        TaxServiceImpl taxService = new TaxServiceImpl(); // reads test file or stub file
        Tax tax = taxService.getTax("TX");

        assertNotNull(tax);
        assertEquals("TX", tax.getState());
        assertEquals(new BigDecimal("4.45"), tax.getTaxRate());
    }

    @Test
    void testInvalidState() {
        TaxServiceImpl taxService = new TaxServiceImpl();
        assertThrows(IllegalArgumentException.class, () -> taxService.getTax("XY"));
    }
}