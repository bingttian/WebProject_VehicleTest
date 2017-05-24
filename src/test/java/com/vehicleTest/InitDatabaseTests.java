package com.vehicleTest;


import com.vehicleTest.DAO.UpFileDAO;
import com.vehicleTest.DAO.UserDAO;
import com.vehicleTest.model.UpFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = VehicleTestApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {
	@Autowired
	UserDAO userDAO;

	@Autowired
	UpFileDAO upFileDAO;

	@Test
	public void contextLoads() {
		for (int i=0;i<5;i++){
			UpFile upFile=new UpFile();
			upFile.setUsername(String.format("USER%d",i));
			upFile.setUp_time(new Date());
			upFile.setFilename(String.format("USER%d",i));
			upFileDAO.addfile(upFile);
		}
	}
}
