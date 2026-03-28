package com.prakash.wallet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.prakash.wallet.repository.WalletRepository;

@SpringBootTest(
	properties = {
		"spring.autoconfigure.exclude="
			+ "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
			+ "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
			+ "org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration"
	}
)
class WalletApplicationTests {

	@MockBean
	private WalletRepository walletRepository;

	@Test
	void contextLoads() {
	}
}
