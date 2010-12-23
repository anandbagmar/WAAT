package com.thoughtworks.webanalyticsautomation;

import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.webanalyticsautomation.utils.TestBase;
import org.testng.annotations.Test;
import java.util.ArrayList;
import static org.testng.Assert.*;

public class TestDataTest extends TestBase {

    @Test
    public void loadOmnitureDataInMemoryTest() {
        ArrayList sections = TestData.sectionsFor(inputDataFileName, actionName);
        assertEquals(sections.size(), 1);
        assertEquals(((Section)sections.get(0)).getLoadedKeyList().size(), 2);
        assertEquals(((Section)sections.get(0)).getLoadedTagList().size(), 5);
        logger.info("loadOmnitureDataInMemory test complete");
    }
}
