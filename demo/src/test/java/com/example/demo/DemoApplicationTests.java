package com.example.demo;

import com.example.demo.application.port.in.AuthLogin;
import com.example.demo.application.port.in.AuthLogout;
import com.example.demo.application.port.in.PersonCreate;
import com.example.demo.application.port.in.PersonDelete;
import com.example.demo.application.port.in.PersonGetAll;
import com.example.demo.application.port.in.PersonGetById;
import com.example.demo.application.port.in.PersonUpdate;
import com.example.demo.application.port.out.PasswordEncoderPort;
import com.example.demo.application.port.out.PersonRepositoryPort;
import com.example.demo.application.port.out.SessionRepositoryPort;
import com.example.demo.application.port.out.TokenGeneratorPort;
import com.example.demo.infrestructure.adapter.repositories.person.PersonRepositoryJpa;
import com.example.demo.infrestructure.adapter.repositories.session.SessionRepositoryJpa;
import com.example.demo.infrestructure.config.BeanConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DemoApplicationTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
			.withUserConfiguration(BeanConfiguration.class)
			.withBean(PersonRepositoryJpa.class, () -> mock(PersonRepositoryJpa.class))
			.withBean(SessionRepositoryJpa.class, () -> mock(SessionRepositoryJpa.class));

	@Test
	void contextLoads() {
		contextRunner.run(context -> assertThat(context).hasNotFailed());
	}

	@Test
	void puertosDeEntradaTienenImplementacion() {
		contextRunner.run(context -> {
			assertThat(context).hasSingleBean(PersonCreate.class);
			assertThat(context).hasSingleBean(PersonGetAll.class);
			assertThat(context).hasSingleBean(PersonGetById.class);
			assertThat(context).hasSingleBean(PersonUpdate.class);
			assertThat(context).hasSingleBean(PersonDelete.class);
			assertThat(context).hasSingleBean(AuthLogin.class);
			assertThat(context).hasSingleBean(AuthLogout.class);
		});
	}

	@Test
	void puertosDeSalidaTienenImplementacion() {
		contextRunner.run(context -> {
			assertThat(context).hasSingleBean(PersonRepositoryPort.class);
			assertThat(context).hasSingleBean(SessionRepositoryPort.class);
			assertThat(context).hasSingleBean(PasswordEncoderPort.class);
			assertThat(context).hasSingleBean(TokenGeneratorPort.class);
		});
	}

}
