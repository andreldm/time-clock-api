package com.andreldm.timeclockapi.unit;

import com.andreldm.timeclockapi.util.ValidationUtil;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for PIS validation.
 */
public class ValidationUtilTest {
    @Test
    public void testValid() {
        assertTrue(ValidationUtil.validatePis("48030020160"));
        assertTrue(ValidationUtil.validatePis("67334519471"));
        assertTrue(ValidationUtil.validatePis("49325761992"));
        assertTrue(ValidationUtil.validatePis("20033825933"));
        assertTrue(ValidationUtil.validatePis("92207511014"));
        assertTrue(ValidationUtil.validatePis("85572310365"));
        assertTrue(ValidationUtil.validatePis("87263394846"));
        assertTrue(ValidationUtil.validatePis("42514601057"));
        assertTrue(ValidationUtil.validatePis("03948630218"));
        assertTrue(ValidationUtil.validatePis("30560605589"));
        assertTrue(ValidationUtil.validatePis("00032145446"));
        assertTrue(ValidationUtil.validatePis("00000000000"));

        assertTrue(ValidationUtil.validatePis(48030020160L));
        assertTrue(ValidationUtil.validatePis(67334519471L));
        assertTrue(ValidationUtil.validatePis(49325761992L));
        assertTrue(ValidationUtil.validatePis(20033825933L));
        assertTrue(ValidationUtil.validatePis(92207511014L));
        assertTrue(ValidationUtil.validatePis(85572310365L));
        assertTrue(ValidationUtil.validatePis(87263394846L));
        assertTrue(ValidationUtil.validatePis(42514601057L));
        assertTrue(ValidationUtil.validatePis(3948630218L));
        assertTrue(ValidationUtil.validatePis(30560605589L));
        assertTrue(ValidationUtil.validatePis(32145446L));
        assertTrue(ValidationUtil.validatePis(0L));
    }

    @Test
    public void testInvalid() {
        assertFalse(ValidationUtil.validatePis((String) null));
        assertFalse(ValidationUtil.validatePis(""));
        assertFalse(ValidationUtil.validatePis("   "));
        assertFalse(ValidationUtil.validatePis("0000000000"));
        assertFalse(ValidationUtil.validatePis("abcdefghjkl"));
        assertFalse(ValidationUtil.validatePis("11111a11111"));
        assertFalse(ValidationUtil.validatePis("11111111111"));
        assertFalse(ValidationUtil.validatePis("65168798798"));
        assertFalse(ValidationUtil.validatePis("98198465410"));

        assertFalse(ValidationUtil.validatePis((Long) null));
        assertFalse(ValidationUtil.validatePis(1L));
        assertFalse(ValidationUtil.validatePis(-1L));
        assertFalse(ValidationUtil.validatePis(11111111111L));
        assertFalse(ValidationUtil.validatePis(65168798798L));
        assertFalse(ValidationUtil.validatePis(98198465410L));
    }
}
