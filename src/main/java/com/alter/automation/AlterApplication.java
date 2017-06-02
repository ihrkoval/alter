package com.alter.automation;

import com.alter.automation.connection.AlterDB;
import com.alter.automation.dao.ClientDao;
import com.alter.automation.dao.impl.ClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.alter.automation","com.alter.automation.xls"})
public class AlterApplication {

	@Bean//(destroyMethod="")
	public AlterDB alterDB(@Value("${alter.database.url}") String url, @Value("${alter.database.user}") String user, @Value("${alter.database.password}") String password) {
		AlterDB db = new AlterDB();
		db.setUrl(url);
		db.setName(user);
		db.setPassword(password);
		return db;
	}

	/*@Bean
	public ClientDao clientDao() {
		return new ClientImpl();
	}*/

	public static void main(String[] args) {
		SpringApplication.run(AlterApplication.class, args);
	}
}
