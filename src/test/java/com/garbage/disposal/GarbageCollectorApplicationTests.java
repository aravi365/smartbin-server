package com.garbage.disposal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.garbage.disposal.datalayer.GarbageStatusDAO;
import com.garbage.disposal.model.GarbageBin;
import com.garbage.disposal.service.GarbageStatusControllerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GarbageCollectorApplicationTests {
	
	@Autowired
	GarbageStatusControllerService garbageStatusControllerService;

	@Test
	public void contextLoads() {
	}
	
	/*@Test
	public void testSetBinStatus()
	{
		assertEquals("Status updation successful",this.garbageStatusControllerService.setBinStatus(129, 90));
	}*/

}
