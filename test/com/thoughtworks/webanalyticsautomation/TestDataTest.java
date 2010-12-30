package com.thoughtworks.webanalyticsautomation;

/**
 * Created by: Anand Bagmar
 * Email: anandb@thoughtworks.com, abagmar@gmail.com
 * Date: Dec 29, 2010
 * Time: 9:34:02 AM
 */

import com.thoughtworks.webanalyticsautomation.inputdata.Section;
import com.thoughtworks.webanalyticsautomation.inputdata.TestData;
import com.thoughtworks.webanalyticsautomation.utils.TestBase;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;

public class TestDataTest extends TestBase {

    @Test
    public void loadOmnitureDataInMemoryTest() {
        actionName = "OpenUpcomingPage";
        ArrayList sections = TestData.sectionsFor(inputDataFileName, actionName);
        assertEquals(sections.size(), 1);
        assertEquals(((Section)sections.get(0)).getLoadedTagList().size(), 13);
        logger.info("loadOmnitureDataInMemory test complete");
    }
}
