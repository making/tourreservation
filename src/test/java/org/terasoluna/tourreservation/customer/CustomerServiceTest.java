package org.terasoluna.tourreservation.customer;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CustomerServiceTest {

	CustomerService customerService;

	CustomerMapper customerMapper;

	@BeforeEach
	@SuppressWarnings("deprecation")
	void setUp() {
		this.customerMapper = mock(CustomerMapper.class);
		this.customerService = new CustomerService(customerMapper,
				new DelegatingPasswordEncoder("noop", Map.of("noop", NoOpPasswordEncoder.getInstance())));
	}

	@Test
	void findOne01() {
		Customer c = new Customer();
		when(this.customerMapper.findById("xxx")).thenReturn(Optional.of(c));

		Customer result = this.customerService.findOne("xxx");
		assertThat(result).isEqualTo(c);
	}

	@Test
	void findOne02() {
		when(this.customerMapper.findById("xxx")).thenReturn(Optional.empty());

		Customer result = this.customerService.findOne("xxx");
		assertThat(result).isNull();
	}

	@Test
	void register01() {
		Customer c = new Customer();
		this.customerService.register(c, "foo");

		ArgumentCaptor<Customer> customerArg = ArgumentCaptor.forClass(Customer.class);

		verify(this.customerMapper, times(1)).insert(customerArg.capture());
		assertThat(customerArg.getValue()).isEqualTo(c);
		assertThat(customerArg.getValue().getCustomerPass()).isEqualTo("{noop}foo");
	}

}